package com.roshan.university.service;

import java.util.List;

import com.roshan.university.model.License;
import com.roshan.university.model.LicenseUser;
import com.roshan.university.model.User;

public interface MockDataService {

    List<LicenseUser> getLicenseUsers();

    List<License> getLicenses();

    List<User> getUsers();

    void addLicenseUser(LicenseUser licenseUser);

    void updateLicense(License license);

    LicenseUser getLicenseUserByUsername(String username);

    License getLicenseByKey(String key);

    User getUserByUsername(String username);

}
