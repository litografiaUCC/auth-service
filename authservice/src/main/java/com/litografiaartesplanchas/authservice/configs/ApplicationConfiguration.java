package com.litografiaartesplanchas.authservice.configs;

import com.litografiaartesplanchas.authservice.repository.ClientRepository;
import com.litografiaartesplanchas.authservice.repository.EmployeeRepository;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public ApplicationConfiguration(EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Bean
    public UserDetailsService employeeDetailsService() {
        return username -> employeeRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
    }

    @Bean
    public UserDetailsService clientDetailsService() {
        return username -> clientRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider createDaoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationProvider employeeAuthenticationProvider() {
        return createDaoAuthenticationProvider(employeeDetailsService());
    }

    @Bean
    public AuthenticationProvider clientAuthenticationProvider() {
        return createDaoAuthenticationProvider(clientDetailsService());
    }
}
