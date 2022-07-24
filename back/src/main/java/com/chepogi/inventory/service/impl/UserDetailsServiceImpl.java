package com.chepogi.inventory.service.impl;

import com.chepogi.inventory.dto.UserDetailsFactory;
import com.chepogi.inventory.repository.AppUserRepository;
import com.chepogi.inventory.security.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with login '%s' wasnt found", username)));
        return UserDetailsFactory.AppUserToUserDetails(user);
    }
}
