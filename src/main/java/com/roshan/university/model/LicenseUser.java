package com.roshan.university.model;

import java.util.Date;

public class LicenseUser {

    private String username;

    private String licenseKey;

    private Date expireDate;

    public LicenseUser(String username, String licenseKey, Date expireDate) {
        super();
        this.username = username;
        this.licenseKey = licenseKey;
        this.expireDate = expireDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLicenseKey() {
        return this.licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
