// package com.example.restservice.Auth.usecases;

//
// import java.util.Set;
//
// import org.springframework.stereotype.Service;
//
// import com.example.restservice.Auth.domain.OAuthClient;
// import com.example.restservice.Auth.domain.OAuthClientRepository;
//
// @Service
// public class RegisterClientUsecase {
//
// private final OAuthClientRepository repository;
//
// public RegisterClientUsecase(OAuthClientRepository repository) {
// this.repository = repository;
// }
//
// public void execute(String clientId, String secret, Set<String> scopes) {
// OAuthClient client = new OAuthClient(clientId, secret, scopes);
// repository.save(client);
// }
// }
