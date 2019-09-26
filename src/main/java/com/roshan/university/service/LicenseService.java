package com.roshan.university.service;

import java.util.List;

import com.roshan.university.model.License;

public interface LicenseService {

    void submitLicenseKey(String license, String username);

    List<License> getAllLicenses();

}
