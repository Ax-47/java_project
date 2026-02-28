package com.example.restservice.Auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.models.AuthorizationConsentModel;

@Repository
public interface AuthorizationConsentRepository
    extends JpaRepository<AuthorizationConsentModel, AuthorizationConsentModel.AuthorizationConsentId> {
  Optional<AuthorizationConsentModel> findByRegisteredClientIdAndPrincipalName(String registeredClientId,
      String principalName);

  void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
