package com.litografiaartesplanchas.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.litografiaartesplanchas.authservice.config.ClientInfoDetails;
import com.litografiaartesplanchas.authservice.config.EmployeeInfoDetails;
import com.litografiaartesplanchas.authservice.dto.RegisterUserDto;
import com.litografiaartesplanchas.authservice.model.Client;
import com.litografiaartesplanchas.authservice.model.Employee;
import com.litografiaartesplanchas.authservice.repository.ClientRepository;
import com.litografiaartesplanchas.authservice.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByEmail(username);
        if (client.isPresent()) {
            return new ClientInfoDetails(client.get());
        }

        Optional<Employee> employee = employeeRepository.findByEmail(username);
        if (employee.isPresent()) {
            return new EmployeeInfoDetails(employee.get());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    public Object addUser(RegisterUserDto input) {
        if (input.getTypeUser() == 1) {
            Employee employee = new Employee();
            employee.setDocumentNumber(new BigInteger(input.getDocumentNumber()));
            employee.setEmail(input.getEmail());
            employee.setLastName(input.getLastName());
            employee.setName(input.getName());
            employee.setPhoneNumber(input.getPhoneNumber());
            employee.setPhoto(input.getPhoto());
            employee.setPassword(passwordEncoder.encode(input.getPassword()));
            employee.setTypeDocument(input.getTypeDocument());
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
            client.setTypeDocument(input.getTypeDocument());
            client.setTypePerson(input.getTypePerson());
            return clientRepository.save(client);

        } else {
            throw new IllegalArgumentException("Invalid typeUser");
        }
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
    
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        return authorizationHeader.substring(7);
    }

    return null;
    }

    
}
