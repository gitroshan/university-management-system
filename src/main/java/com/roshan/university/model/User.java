package com.roshan.university.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;

    private String password;

    private List<String> roles = new ArrayList<>();

    public User(String username, String password, List<String> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
