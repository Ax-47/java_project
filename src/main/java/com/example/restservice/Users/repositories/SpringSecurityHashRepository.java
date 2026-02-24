package com.example.restservice.Users.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Repository;
import com.example.restservice.Users.domain.HashRepository;

@Repository
public class SpringSecurityHashRepository implements HashRepository {
  private final PasswordEncoder passwordEncoder;

  public SpringSecurityHashRepository() {
    String defaultId = "argon2@SpringSecurity_v5_8";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(
        defaultId,
        Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put(
        "pbkdf2",
        Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
    this.passwordEncoder = new DelegatingPasswordEncoder(defaultId, encoders);
  }

  @Override
  public String hash(String raw) {
    return passwordEncoder.encode(raw);
  }

  @Override
  public boolean matches(String raw, String hashed) {
    return passwordEncoder.matches(raw, hashed);
  }
}
