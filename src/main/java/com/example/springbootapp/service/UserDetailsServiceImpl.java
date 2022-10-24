package com.example.springbootapp.service;

import com.example.springbootapp.entity.User;
import com.example.springbootapp.model.UserModel;
import com.example.springbootapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new CustomUserDetails(user.get());
    }

    public User save(UserModel user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setRole("USER");
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public Optional<User> findUser(String email) {
        return userRepository.findByEmail(email);
    }
}
