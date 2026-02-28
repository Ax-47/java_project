package com.example.restservice.Auth.domain;

import java.util.Set;

public class OAuthClient {

  private final String clientId;
  private final String clientSecret;
  private final Set<String> scopes;

  public OAuthClient(String clientId, String clientSecret, Set<String> scopes) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.scopes = scopes;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public Set<String> getScopes() {
    return scopes;
  }
}
