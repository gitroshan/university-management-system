package com.roshan.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roshan.university.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}
