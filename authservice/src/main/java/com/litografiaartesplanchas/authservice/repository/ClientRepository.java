package com.litografiaartesplanchas.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.authservice.model.Client;

@Repository
public interface ClientRepository extends JpaRepository <Client, Integer> {
    Optional<Client> findByEmail(String email);
}
