package com.example.restservice.Auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.models.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, String> {
  Optional<ClientModel> findByClientId(String clientId);
}
