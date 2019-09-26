package com.roshan.university.form;

public class UserForm {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserForm [firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email
                + ", password=" + this.password + ", confirmPassword=" + this.confirmPassword + "]";
    }

}
