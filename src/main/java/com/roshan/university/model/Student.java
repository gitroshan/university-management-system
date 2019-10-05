package com.roshan.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.roshan.university.utils.ContactNumberConstraint;
import com.roshan.university.utils.DateConstraint;

@Entity
@Table(name = "student")
public class Student extends AuditModel {

    private static final long serialVersionUID = 9044076075846668244L;

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

    @NotEmpty(message = "Please provide a birthdate")
    @DateConstraint
    private String birthdate;

    @Transient
    @NotEmpty(message = "Please provide a password")
    private String password;

    @NotNull(message = "Group cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

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

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student [id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email="
                + this.email + ", phoneNumber=" + this.phoneNumber + ", birthdate=" + this.birthdate + ", password="
                + this.password + ", group=" + this.group + "]";
    }

}
