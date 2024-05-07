package com.litografiaartesplanchas.authservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litografiaartesplanchas.authservice.dto.AuthRequest;
import com.litografiaartesplanchas.authservice.dto.RegisterUserDto;
import com.litografiaartesplanchas.authservice.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import com.litografiaartesplanchas.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addUser")
    public Object addNewUser(@RequestBody RegisterUserDto userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<HashMap<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	System.out.println(authRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	HashMap<String, String> data = new HashMap<String, String>();
        	data.put("token", jwtService.generateToken(authRequest.getUsername()));
            return ResponseEntity.ok(data);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/userInfo")
    public UserDetails getUserInfo(HttpServletRequest request) {
        String token = service.extractTokenFromRequest(request);
        String username = jwtService.extractUsername(token);
        return service.loadUserByUsername(username);
    }

    @GetMapping("/tokenData")
    public String getTokenInfo(HttpServletRequest request){
        String token = service.extractTokenFromRequest(request);
        Claims tokenClaims = jwtService.extractAllClaims(token);
        
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : tokenClaims.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return stringBuilder.toString();
    }
}