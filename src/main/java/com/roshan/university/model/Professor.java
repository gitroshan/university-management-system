package com.roshan.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.roshan.university.utils.ContactNumberConstraint;

@Entity
@Table(name = "professor")
public class Professor extends AuditModel {

    private static final long serialVersionUID = 4853962477070515841L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please provide a first name")
    private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @NotEmpty(message = "Please provide an email")
    @Email(message = "Please provide an email")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Please provide a phone number")
    @ContactNumberConstraint
    private String phoneNumber;

    @Transient
    @NotEmpty(message = "Please provide a password")
    private String password;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Professor [id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email="
                + this.email + ", phoneNumber=" + this.phoneNumber + "]";
    }

}
