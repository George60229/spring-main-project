package com.esm.security.service.impl;


import com.esm.security.converter.UserConverter;
import com.esm.security.dto.request.BuyCertificatesRequestDTO;
import com.esm.security.dto.request.UserRequestDto;
import com.esm.security.dto.response.UserInfoResponseDto;
import com.esm.security.dto.response.UserResponseDto;
import com.esm.security.exception.AppNotFoundException;
import com.esm.security.exception.BadRequestException;
import com.esm.security.exception.ErrorCode;
import com.esm.security.model.GiftCertificate;
import com.esm.security.model.User;
import com.esm.security.repository.CertificateRepository;
import com.esm.security.repository.UserRepository;
import com.esm.security.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserInfoService {
    private final UserRepository userRepository;

    private final CertificateRepository certificateRepository;


    @Autowired
    private UserConverter converter;


    public UserServiceImpl(UserRepository userRepository, CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;


    }


    @Override
    public UserResponseDto getUserOrders(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new AppNotFoundException("User with this id is not found " + id, ErrorCode.USER_NOT_FOUND);
        }
        return converter.convertOneToDTO(userOptional.get());
    }

    @Override
    public void setConverter(UserConverter converter) {
        this.converter = converter;
    }

    @Override
    public UserInfoResponseDto create(UserRequestDto user) {

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new BadRequestException("Login is used", ErrorCode.BAD_REQUEST_ERROR);

        }

        if (!user.getCertificates().isEmpty()) {
            List<GiftCertificate> result = new ArrayList<>();
            for (int i = 0; i < user.getCertificates().size(); i++) {
                List<GiftCertificate> giftCertificates = certificateRepository.findByName(user.getCertificates().get(i).getName());
                if (!certificateRepository.findByName(user.getCertificates().get(i).getName()).isEmpty()) {
                    result.addAll(giftCertificates);
                }
            }
            user.setCertificates(result);
        }


        return converter.convertOneToInfoDTO((userRepository.save(converter.convertDTOtoModel(user))));
    }

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return converter.convert(userRepository.findAll(pageable));
    }

    @Override
    public UserResponseDto addCertificate(BuyCertificatesRequestDTO name, int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppNotFoundException("User is not found " + id, ErrorCode.USER_NOT_FOUND);
        }
        User myUser = user.get();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        String[] certificates = name.getCertificates();

        for (String certificate : certificates) {
            if (certificateRepository.findByName(certificate).isEmpty()) {
                throw new AppNotFoundException("Certificate is not found with this name " + certificate, ErrorCode.CERTIFICATE_NOT_FOUND);
            } else {
                giftCertificates.addAll(certificateRepository.findByName(certificate));
            }

        }

        myUser.addOrder(converter.getOrder(giftCertificates));

        userRepository.save(myUser);
        Optional<User> responseDto = userRepository.findById(id);
        if (responseDto.isEmpty()) {
            throw new AppNotFoundException("User with this id is not found " + id, ErrorCode.USER_NOT_FOUND);
        }
        return converter.convertOneToDTO(responseDto.get());
    }

    @Override
    public UserInfoResponseDto getUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new AppNotFoundException("User with this id is not found " + id, ErrorCode.USER_NOT_FOUND);
        }
        return converter.convertOneToInfoDTO(userOptional.get());
    }


    @Override
    public Page<UserInfoResponseDto> getUserInfo(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return converter.convertToInfo(users);
    }


    @Override
    public UserResponseDto getUserWithMostExpensiveOrder() {
        List<User> result = userRepository.findExpensiveOrder();
        result.removeAll(Collections.singletonList(null));
        List<User> res = result.stream().sorted(Comparator.comparingInt(User::getAllPrice).reversed()).toList();
        return converter.convertOneToDTO(res.get(0));

    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new AppNotFoundException("Username is not found", ErrorCode.USER_NOT_FOUND);
        }

        return user;
    }


}
