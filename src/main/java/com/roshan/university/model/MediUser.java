package com.roshan.university.model;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;

public class MediUser extends User {

    private static final long serialVersionUID = -3531439484732724601L;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final long phNumber;

    private final long altPhNumber;

    public MediUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection authorities, String firstName,
            String middleName, String lastName, long phNumber, long altPhNumber) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phNumber = phNumber;
        this.altPhNumber = altPhNumber;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPhNumber() {
        return phNumber;
    }

    public long getAltPhNumber() {
        return altPhNumber;
    }

}
