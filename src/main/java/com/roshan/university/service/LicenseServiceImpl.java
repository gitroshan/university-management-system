package com.roshan.university.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.roshan.university.exception.InvalidLicenseException;
import com.roshan.university.exception.InvalidUserException;
import com.roshan.university.exception.LicenseAlreadyUsedException;
import com.roshan.university.exception.LicenseExpiredException;
import com.roshan.university.exception.UserAlreadyVerifiedException;
import com.roshan.university.model.License;
import com.roshan.university.model.LicenseUser;
import com.roshan.university.model.User;

@Service
@SuppressWarnings("nls")
public class LicenseServiceImpl implements LicenseService {

    private @Autowired MockDataService mockDataService;

    @Override
    public void submitLicenseKey(String licenseKey, String username) {

        if (StringUtils.hasText(licenseKey) && StringUtils.hasText(username)) {

            LicenseUser existingLicenseUser = this.mockDataService.getLicenseUserByUsername(username);
            if (existingLicenseUser != null) {
                throw new UserAlreadyVerifiedException(
                        "You have already verified your license. Please log in to continue.");
            }

            License license = this.mockDataService.getLicenseByKey(licenseKey);
            if (license == null) {
                throw new InvalidLicenseException("Your license key is invalid. Please enter a valid license key.");
            }

            if (license.isUsed()) {
                throw new LicenseAlreadyUsedException(
                        "Your license is already used. Please enter a valid license key.");
            }

            if (license.getExpireDate().compareTo(new Date()) < 0) {
                throw new LicenseExpiredException("Your license key is expired. Please enter a valid license key.");
            }

            User user = this.mockDataService.getUserByUsername(username);
            if (user == null) {
                throw new InvalidUserException("Given user is not found. Please check your username.");
            }

            LicenseUser licenseUser = new LicenseUser(username, licenseKey, license.getExpireDate());
            this.mockDataService.addLicenseUser(licenseUser);
            this.mockDataService.updateLicense(license);

            return;
        }

        throw new IllegalArgumentException("Both Username and license are required.");

    }

    @Override
    public List<License> getAllLicenses() {

        List<License> licenses = this.mockDataService.getLicenses();
        return licenses.stream().filter(license -> !license.isUsed()).collect(Collectors.toList());

    }

}
