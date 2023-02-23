package com.esm.security.dto.response;



import com.esm.security.model.Order;
import com.esm.security.model.Role;

import java.util.ArrayList;
import java.util.List;

public class UserInfoResponseDto {
    private int userId;

    String email;

    String surname;




    private Role role;

    String login;

    private List<Order> orders = new ArrayList<>();
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRoles() {
        return role;
    }

    public void setRoles(Role roles) {
        this.role = roles;
    }



    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


}
