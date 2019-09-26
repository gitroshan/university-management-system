package com.roshan.university.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roshan.university.model.AppUser;
import com.roshan.university.repository.AppRoleRepository;
import com.roshan.university.repository.AppUserRepository;

@Service
public class AppUserServiceImpl implements AppUserService {

    private @Autowired AppUserRepository appUserRepository;

    private @Autowired AppRoleRepository appRoleRepository;

    private @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void registerUser(AppUser appUser) {

        appUser.setPassword(this.bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setRoles(new HashSet<>(this.appRoleRepository.findAll()));

        this.appUserRepository.save(appUser);

    }

}
