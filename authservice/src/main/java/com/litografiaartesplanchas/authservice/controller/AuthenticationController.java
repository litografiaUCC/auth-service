package com.litografiaartesplanchas.authservice.controller;

import com.litografiaartesplanchas.authservice.service.JwtService;
import com.litografiaartesplanchas.authservice.model.Employee;
import com.litografiaartesplanchas.authservice.model.Client;
import com.litografiaartesplanchas.authservice.service.AuthenticationService;
import com.litografiaartesplanchas.authservice.dtos.RegisterUserDto;
import com.litografiaartesplanchas.authservice.dtos.LoginUserDto;
import com.litografiaartesplanchas.authservice.responses.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            Object registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            Object authenticatedUser = authenticationService.authenticate(loginUserDto);
            if (authenticatedUser instanceof Employee) {
                String jwtToken = jwtService.generateToken((Employee) authenticatedUser);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwtToken);
                loginResponse.setExpiresIn(jwtService.getExpirationTime());

                return ResponseEntity.ok(loginResponse);

            } else if (authenticatedUser instanceof Client) {
                String jwtToken = jwtService.generateToken((Client) authenticatedUser);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(jwtToken);
                loginResponse.setExpiresIn(jwtService.getExpirationTime());
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.badRequest().body("Invalid user type");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

