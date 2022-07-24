package com.chepogi.inventory.dto;

import com.chepogi.inventory.security.AppRole;
import com.chepogi.inventory.security.AppUser;
import com.chepogi.inventory.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsFactory {
    public static User AppUserToUserDetails(AppUser appUser){
        return new User(appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                RoleToGrantedAuthority(appUser.getRole()));
    }

    private static Collection<? extends GrantedAuthority> RoleToGrantedAuthority(AppRole role){
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }
}
