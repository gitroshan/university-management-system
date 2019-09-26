package com.roshan.university.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roshan.university.model.AppRole;
import com.roshan.university.model.AppUser;
import com.roshan.university.model.AuthenticatedUser;
import com.roshan.university.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private @Autowired AppUserRepository appUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = this.appUserRepository.findByUsername(username);

        if (appUser == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        Set<AppRole> roles = appUser.getRoles();

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roles != null) {
            for (AppRole role : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grantList.add(authority);
            }
        }

        // return new MediUser(username, "NA", true, true, true, true, grantList, "Roshan", "Priyadarshana", null, 0,
        // 0);

        return new AuthenticatedUser(appUser.getFirstName(), appUser.getLastName(), appUser.getUsername(),
                appUser.getPassword(), grantList);

    }

}