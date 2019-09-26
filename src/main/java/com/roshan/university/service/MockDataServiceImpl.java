package com.roshan.university.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.roshan.university.model.License;
import com.roshan.university.model.LicenseUser;
import com.roshan.university.model.User;

@Service
@SuppressWarnings("nls")
public class MockDataServiceImpl implements MockDataService {

    private List<LicenseUser> licenseUsers = new ArrayList<>();

    private List<License> licenses = new ArrayList<>();

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {

        List<String> userRoles = new ArrayList<>();
        userRoles.add("ROLE_USER");

        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("ROLE_ADMIN");

        this.users.add(new User("user", "user", userRoles));
        this.users.add(new User("olduser", "olduser", userRoles));
        this.users.add(new User("roshan", "roshan", userRoles));
        this.users.add(new User("navinder", "navinder", userRoles));
        this.users.add(new User("admin", "admin", userRoles));

        Date currentDate = new Date();
        LocalDateTime currentLocalDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime futureLocalDateTime = currentLocalDateTime.plusYears(1);
        LocalDateTime pastLocalDateTime = currentLocalDateTime.minusYears(1);

        Date futureDate = Date.from(futureLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date pastDate = Date.from(pastLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        this.licenses.add(new License("0000", pastDate));
        this.licenses.add(new License("1111", futureDate));
        this.licenses.add(new License("2222", futureDate));
        this.licenses.add(new License("3333", futureDate));
        this.licenses.add(new License("4444", futureDate));
    }

    @Override
    public List<LicenseUser> getLicenseUsers() {
        return this.licenseUsers;
    }

    public void setLicenseUsers(List<LicenseUser> licenseUsers) {
        this.licenseUsers = licenseUsers;
    }

    @Override
    public List<License> getLicenses() {
        return this.licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void addLicenseUser(LicenseUser licenseUser) {
        this.licenseUsers.add(licenseUser);

    }

    @Override
    public void updateLicense(License license) {
        license.setUsed(true);

    }

    @Override
    public License getLicenseByKey(String key) {
        return this.getLicenses().stream().filter(license -> key.equalsIgnoreCase(license.getLicenseKey())).findAny()
                .orElse(null);
    }

    @Override
    public LicenseUser getLicenseUserByUsername(String username) {
        return this.getLicenseUsers().stream()
                .filter(licenseUser -> username.equalsIgnoreCase(licenseUser.getUsername())).findAny().orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.getUsers().stream().filter(user -> username.equalsIgnoreCase(user.getUsername())).findAny()
                .orElse(null);
    }

}
