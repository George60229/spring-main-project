package com.esm.security.converter;

import com.esm.security.dto.request.UserRequestDto;
import com.esm.security.dto.response.UserInfoResponseDto;
import com.esm.security.dto.response.UserResponseDto;
import com.esm.security.model.GiftCertificate;
import com.esm.security.model.Order;
import com.esm.security.model.User;
import com.esm.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    public Page<UserResponseDto> convert(Page<User> users) {
        return listToPage(users.stream()
                .map(this::convertOneToDTO)
                .collect(Collectors.toList()));

    }

    public Page<UserInfoResponseDto> convertToInfo(Page<User> users) {
        return listToInfoPage(users.stream()
                .map(this::convertOneToInfoDTO)
                .collect(Collectors.toList()));

    }

    public UserResponseDto convertOneToDTO(User user) {
        UserResponseDto userResponseDTO = new UserResponseDto();
        userResponseDTO.setName(user.getUsername());
        userResponseDTO.setOrders(user.getOrders());
        return userResponseDTO;
    }

    public User convertDTOtoModel(UserRequestDto userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }


    public Page<UserResponseDto> listToPage(List<UserResponseDto> userResponseDto) {

        return new PageImpl<>(userResponseDto);
    }

    public Page<UserInfoResponseDto> listToInfoPage(List<UserInfoResponseDto> userResponseDto) {

        return new PageImpl<>(userResponseDto);
    }

    public Order getOrder(List<GiftCertificate> giftCertificates) {
        Order order = new Order();

        BigDecimal price = new BigDecimal(0);
        for (GiftCertificate giftCertificate : giftCertificates) {
            if (giftCertificate.getPrice() != null) {
                price = price.add(giftCertificate.getPrice());
            }
        }
        order.setGiftCertificates(giftCertificates);
        order.setPrice(price);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public UserInfoResponseDto convertOneToInfoDTO(User user) {
        UserInfoResponseDto userResponseDTO = new UserInfoResponseDto();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setOrders(user.getOrders());
        userResponseDTO.setUserId(user.getId());
        userResponseDTO.setLogin(userResponseDTO.getLogin());
        userResponseDTO.setSurname(userResponseDTO.getSurname());
        userResponseDTO.setRoles(user.getRole());
        return userResponseDTO;
    }

}

