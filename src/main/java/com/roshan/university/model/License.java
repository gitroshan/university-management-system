package com.roshan.university.model;

import java.util.Date;

public class License {

    private String licenseKey;

    private Date expireDate;

    private boolean used;

    public License(String licenseKey, Date expireDate) {
        super();
        this.licenseKey = licenseKey;
        this.expireDate = expireDate;
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

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.expireDate == null) ? 0 : this.expireDate.hashCode());
        result = prime * result + ((this.licenseKey == null) ? 0 : this.licenseKey.hashCode());
        result = prime * result + (this.used ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        License other = (License) obj;
        if (this.expireDate == null) {
            if (other.expireDate != null)
                return false;
        } else if (!this.expireDate.equals(other.expireDate))
            return false;
        if (this.licenseKey == null) {
            if (other.licenseKey != null)
                return false;
        } else if (!this.licenseKey.equals(other.licenseKey))
            return false;
        if (this.used != other.used)
            return false;
        return true;
    }

    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "License [licenseKey=" + this.licenseKey + ", expireDate=" + this.expireDate + ", used=" + this.used
                + "]";
    }

}
