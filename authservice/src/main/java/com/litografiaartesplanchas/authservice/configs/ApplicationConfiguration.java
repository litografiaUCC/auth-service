package com.litografiaartesplanchas.authservice.configs;

import com.litografiaartesplanchas.authservice.repository.ClientRepository;
import com.litografiaartesplanchas.authservice.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    UserDetailsService userDetailsService() {
        try{
            return username -> employeeRepository.findByEmail(username) 
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }catch(Exception e){
            System.out.println("Esta aqui el error?");
            return username -> clientRepository.findByEmail(username) 
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}