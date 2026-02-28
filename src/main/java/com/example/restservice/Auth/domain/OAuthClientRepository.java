package com.example.restservice.Auth.domain;

import java.util.Optional;

public interface OAuthClientRepository {
  void save(OAuthClient client);

  Optional<OAuthClient> findByClientId(String clientId);
}
