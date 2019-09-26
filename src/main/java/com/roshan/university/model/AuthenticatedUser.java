package com.roshan.university.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {

    private static final long serialVersionUID = -1788866501116671127L;

    private String firstname;

    private String lastname;

    public AuthenticatedUser(String firstName, String lastName, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "AuthenticatedUser [firstname=" + this.firstname + ", lastname=" + this.lastname + ", getAuthorities()="
                + getAuthorities() + ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername()
                + ", isEnabled()=" + isEnabled() + ", isAccountNonExpired()=" + isAccountNonExpired()
                + ", isAccountNonLocked()=" + isAccountNonLocked() + ", isCredentialsNonExpired()="
                + isCredentialsNonExpired() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
                + ", getClass()=" + getClass() + "]";
    }

}
