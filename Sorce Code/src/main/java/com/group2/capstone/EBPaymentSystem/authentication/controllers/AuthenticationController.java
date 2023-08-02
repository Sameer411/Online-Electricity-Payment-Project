package com.group2.capstone.EBPaymentSystem.authentication.controllers;

import com.group2.capstone.EBPaymentSystem.authentication.models.LoginResponseDTO;
import com.group2.capstone.EBPaymentSystem.authentication.models.RegistrationDTO;
import com.group2.capstone.EBPaymentSystem.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDTO body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getRole(), authentication);
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
