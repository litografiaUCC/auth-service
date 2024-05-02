package com.litografiaartesplanchas.authservice.service;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.litografiaartesplanchas.authservice.model.Employee;
import com.litografiaartesplanchas.authservice.dtos.LoginUserDto;
import com.litografiaartesplanchas.authservice.dtos.RegisterUserDto;
import com.litografiaartesplanchas.authservice.model.Client;
import com.litografiaartesplanchas.authservice.repository.ClientRepository;
import com.litografiaartesplanchas.authservice.repository.EmployeeRepository;

@Service
public class AuthenticationService {

    private final ClientRepository clientRepository;

    private final EmployeeRepository employeeRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        ClientRepository clientRepository,
        EmployeeRepository employeeRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.clientRepository = clientRepository;
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Object signup(RegisterUserDto input) {
        if (input.getTypeUser() == 1) {
            Employee employee = new Employee();
            employee.setDocumentNumber(input.getDocumentNumber());
            employee.setEmail(input.getEmail());
            employee.setLastName(input.getLastName());
            employee.setName(input.getName());
            employee.setPhoneNumber(input.getPhoneNumber());
            employee.setPhoto(input.getPhoto());
            employee.setPassword(passwordEncoder.encode(input.getPassword()));
            return employeeRepository.save(employee);

        } else if (input.getTypeUser() == 2) {
            Client client = new Client();
            client.setNumberDocument(input.getDocumentNumber().toString());
            client.setEmail(input.getEmail());
            client.setLastName(input.getLastName());
            client.setName(input.getName());
            client.setPhone(input.getPhoneNumber());
            client.setPhoto(input.getPhoto());
            client.setPassword(passwordEncoder.encode(input.getPassword()));
            return clientRepository.save(client);

        } else {
            throw new IllegalArgumentException("Invalid typeUser");
        }
    }

    public Object authenticate(LoginUserDto input) {
        System.out.println(input.toString());
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (input.getTypeUser() == 1) {
            return employeeRepository.findByEmail(input.getEmail())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
        } else if (input.getTypeUser() == 2) {
            return clientRepository.findByEmail(input.getEmail())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
        } else {
            throw new IllegalArgumentException("Invalid typeUser");
        }
        /* if (input.getTypeUser() == 1) {
            return employeeRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        } else if (input.getTypeUser() == 2) {
            return clientRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        } else {
            throw new IllegalArgumentException("Invalid typeUser");
        } */
    }
}