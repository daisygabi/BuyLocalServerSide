package com.gra.local.configuration;

import com.gra.local.persistence.domain.VendorAccount;
import com.gra.local.persistence.repositories.VendorAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private VendorAccountRepository vendorAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        try {
            final VendorAccount user = vendorAccountRepository.findByPhone(phone).get();
            if (user.getId() == null) {
                throw new UsernameNotFoundException("Vendor account [" + phone + "] not found");
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(phone)
                    .password(user.getPassword())
                    .authorities(user.getRole())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } catch (Exception e) {
            throw new BadCredentialsException("Account authentication failed");
        }
    }
}