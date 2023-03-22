package com.esm.security.controller;

import com.esm.security.dto.request.BuyCertificatesRequestDTO;
import com.esm.security.dto.response.UserInfoResponseDto;
import com.esm.security.dto.response.UserResponseDto;
import com.esm.security.exception.AppNotFoundException;
import com.esm.security.exception.BadRequestException;
import com.esm.security.exception.ErrorCode;
import com.esm.security.model.Order;
import com.esm.security.service.impl.UserServiceImpl;
import com.esm.security.urlCreator.UserUrlCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    UserUrlCreator userUrlCreator;




    @GetMapping("/getAllUsers/{page}")
    public CollectionModel<UserInfoResponseDto> getAllUsersWithPage(@PathVariable(value = "page") int page) {
        if (page < 0) {
            throw new BadRequestException("Page must be positive", ErrorCode.BAD_REQUEST_ERROR);
        }
        Pageable pageable = PageRequest.of(page, 10);

        Page<UserInfoResponseDto> list = userService.getUserInfo(pageable);

        List<Link> links = new ArrayList<>();


        return CollectionModel.of(list, links);
    }


    @GetMapping("/getAllUsers")
    public CollectionModel<UserInfoResponseDto> getAllUsers(@PageableDefault Pageable pageable) {

        Page<UserInfoResponseDto> list = userService.getUserInfo(pageable);

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());


        return CollectionModel.of(list, links);

    }


    @PostMapping("/buyCertificate/{id}")
    public CollectionModel<UserResponseDto> buyCertificate(@RequestBody BuyCertificatesRequestDTO certificates,
                                                           @PathVariable(value = "id") int id) {
        List<UserResponseDto> list = new ArrayList<>();
        if (certificates.getCertificates() == null) {
            throw new AppNotFoundException("string is empty", ErrorCode.BAD_REQUEST_ERROR);
        }

        list.add(userService.addCertificate(certificates, id));

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());

        links.add(userUrlCreator.addOrder(id, certificates));
        links.add(userUrlCreator.getUserById(id));
        return CollectionModel.of(list, links);


    }

    @GetMapping("/orders/{id}")

    public CollectionModel<UserResponseDto> getOrdersById(@PathVariable(value = "id") int id) {
        List<UserResponseDto> list = new ArrayList<>();

        list.add(userService.getUserOrders(id));

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());


        return CollectionModel.of(list, links);

    }

    @GetMapping("/orders/{id}/{order_id}")
    public CollectionModel<Order> getOrderById(@PathVariable(value = "id") int id, @PathVariable
            (value = "order_id") int orderId) {

        List<UserResponseDto> list = new ArrayList<>();

        list.add(userService.getUserOrders(id));

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < list.get(0).getOrders().size(); i++) {
            if (list.get(0).getOrders().get(i).getId() == orderId) {
                orders.add(list.get(0).getOrders().get(i));
            }
        }

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());
        return CollectionModel.of(orders, links);

    }


    @GetMapping("/{id}")
    public CollectionModel<UserInfoResponseDto> getById(@PathVariable(value = "id") int id) {
        List<UserInfoResponseDto> list = new ArrayList<>();

        list.add(userService.getUserById(id));

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());


        return CollectionModel.of(list, links);

    }

    @GetMapping("/richUser")
    public CollectionModel<UserResponseDto> getRichUser() {


        List<UserResponseDto> list = new ArrayList<>();

        list.add(userService.getUserWithMostExpensiveOrder());

        List<Link> links = new ArrayList<>();
        links.add(userUrlCreator.getAllUsers());
        return CollectionModel.of(list, links);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable(value = "id") Integer id) {
        userService.deleteUser(id);
    }

}
