package com.esm.security.urlCreator;

import com.esm.security.controller.UserController;
import com.esm.security.dto.request.BuyCertificatesRequestDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserUrlCreator {
    public Link getAllUsers() {
        Pageable pageable = PageRequest.of(0, 5);
        return WebMvcLinkBuilder.linkTo(methodOn(UserController.class)
                .getAllUsers(pageable)).withRel("allUsers");
    }

    public Link addOrder(int id, BuyCertificatesRequestDTO requestDTO) {

        return linkTo(methodOn(UserController.class).
                buyCertificate(requestDTO, id)).withRel("addOrder");
    }




    public Link getUserById(int id) {
        return linkTo(methodOn(UserController.class).getById(id)).withRel("getUserById");
    }

}
