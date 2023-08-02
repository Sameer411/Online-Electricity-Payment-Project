package com.group2.capstone.EBPaymentSystem.authentication.services;

import com.group2.capstone.EBPaymentSystem.authentication.models.LoginResponseDTO;
import com.group2.capstone.EBPaymentSystem.authentication.models.Role;
import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.authentication.repository.RoleRepository;
import com.group2.capstone.EBPaymentSystem.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // public User registerUser(String username, String password, String role) {
    // String encodedPassword = passwordEncoder.encode(password);
    // Role userRole =
    // roleRepository.findByAuthority(role).orElse(roleRepository.findByAuthority("USER").get());
    //
    // Set<Role> authorities = new HashSet<>();
    //
    // authorities.add(userRole);
    //
    // return userRepository.save(new User(0, username, encodedPassword,
    // authorities));
    // }
    public ResponseEntity<String> registerUser(String username, String password, String role, Authentication authentication) {
        if (userRepository.findByUsername(username).isPresent()) {
            return new ResponseEntity<>("Username " + username + " already exists. Please try with a different username.", HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role).orElse(roleRepository.findByAuthority("USER").get());
        Set<Role> authorities = new HashSet<>();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            authorities.add(userRole);
            // authorities.add(roleRepository.findByAuthority("ADMIN").get());
            // authorities.add(roleRepository.findByAuthority("DISTRICT_OFFICIAL").get());
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DEPARTMENT_OFFICIAL"))) {
            if (!role.equals("USER")) {
                return new ResponseEntity<>("DEPARTMENT_OFFICIAL cannot create another " + role, HttpStatus.FORBIDDEN);
            }
            authorities.add(userRole);
            // authorities.add(roleRepository.findByAuthority("DISTRICT_OFFICIAL").get());
        } else {
            return new ResponseEntity<>("User is not authorized to create another user", HttpStatus.FORBIDDEN);
        }
        User user = userRepository.save(new User(0, username, encodedPassword, authorities));
        return new ResponseEntity<>("User registered successfully! " + user, HttpStatus.CREATED);
    }

    public LoginResponseDTO loginUser(String username, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, "");
        }
    }

}
