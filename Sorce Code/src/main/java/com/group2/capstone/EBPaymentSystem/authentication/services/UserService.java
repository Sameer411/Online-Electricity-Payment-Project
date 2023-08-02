package com.group2.capstone.EBPaymentSystem.authentication.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.authentication.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }
    public Optional<User> findById(long userid) {

        return userRepository.findById(userid);
    }
    
    public List<User> getAllConsumers(){
    	List<User> users = userRepository.findAllConsumer();
    	return users;
    }
}
