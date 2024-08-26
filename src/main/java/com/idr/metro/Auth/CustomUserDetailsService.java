package com.idr.metro.Auth;

import com.idr.metro.entity.User;
import com.idr.metro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User userDetail = userOptional.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(userDetail.getUserName()) // Assuming you want to use the email as the username
                .password(userDetail.getPassword())
                .authorities(userDetail.getRoles().stream() // Assuming you have a getRoles() method
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .build();
    }
}
