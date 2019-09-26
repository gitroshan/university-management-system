package com.roshan.university.model;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -3185450975937452171L;

    private String token;

    public AuthenticationToken(String token, Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "AuthenticationToken [token=" + this.token + ", getCredentials()=" + getCredentials()
                + ", getPrincipal()=" + getPrincipal() + ", getAuthorities()=" + getAuthorities() + ", getName()="
                + getName() + ", isAuthenticated()=" + isAuthenticated() + ", getDetails()=" + getDetails()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
                + "]";
    }

}
