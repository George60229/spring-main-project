package com.esm.security.service;


import com.esm.security.converter.UserConverter;
import com.esm.security.dto.request.BuyCertificatesRequestDTO;
import com.esm.security.dto.request.UserRequestDto;
import com.esm.security.dto.response.UserInfoResponseDto;
import com.esm.security.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserInfoService extends UserDetailsService {


    UserResponseDto getUserOrders(int id);

    void setConverter(UserConverter converter);

    UserInfoResponseDto create(UserRequestDto user);

    Page<UserResponseDto> getAll(Pageable pageable);

    UserResponseDto addCertificate(BuyCertificatesRequestDTO name, int id);

    UserInfoResponseDto getUserById(int id);

    Page<UserInfoResponseDto> getUserInfo(Pageable pageable);
    UserResponseDto getUserWithMostExpensiveOrder();

}

