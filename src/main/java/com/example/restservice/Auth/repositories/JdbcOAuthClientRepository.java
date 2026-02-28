package com.example.restservice.Auth.repositories;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.domain.OAuthClient;
import com.example.restservice.Auth.domain.OAuthClientRepository;

@Repository
public class JdbcOAuthClientRepository implements OAuthClientRepository {

  private final JdbcRegisteredClientRepository springRepo;

  public JdbcOAuthClientRepository(JdbcTemplate jdbcTemplate) {
    this.springRepo = new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  @Override
  public void save(OAuthClient client) {

    RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(client.getClientId())
        .clientSecret(client.getClientSecret())
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope(OidcScopes.OPENID)
        .build();

    springRepo.save(registeredClient);
  }

  @Override
  public Optional<OAuthClient> findByClientId(String clientId) {
    RegisteredClient rc = springRepo.findByClientId(clientId);
    if (rc == null)
      return Optional.empty();
    return Optional.of(
        new OAuthClient(
            rc.getClientId(),
            rc.getClientSecret(),
            rc.getScopes()));
  }
}
