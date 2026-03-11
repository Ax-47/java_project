package com.example.restservice.Auth.repositories;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Users.domain.User;

@Repository
public class JwtRepositoryImpl implements TokenRepository {

  private static final String ISSUER = "MikhailAkhsakov";
  private static final String ROLE_CLAIM = "role";

  private final JwtEncoder jwtEncoder;
  private final long accessTokenExpiredInSeconds;
  private final long refreshTokenExpiredInSeconds;

  public JwtRepositoryImpl(
      JwtEncoder jwtEncoder,
      @Value("${token.access-token-expired-in-seconds}") long accessTokenExpiredInSeconds,
      @Value("${token.refresh-token-expired-in-seconds}") long refreshTokenExpiredInSeconds) {
    this.accessTokenExpiredInSeconds = accessTokenExpiredInSeconds;
    this.jwtEncoder = jwtEncoder;
    this.refreshTokenExpiredInSeconds = refreshTokenExpiredInSeconds;
  }

  public String issueAccessToken(User user, Instant issueDate) {
    return generateToken(user, issueDate, accessTokenExpiredInSeconds);
  }

  public String issueRefreshToken(User user, Instant issueDate) {
    return generateToken(user, issueDate, refreshTokenExpiredInSeconds);
  }

  public String generateToken(User user, Instant issueDate, long expiredInSeconds) {
    Instant expire = issueDate.plusSeconds(expiredInSeconds);
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer(ISSUER)
        .issuedAt(issueDate)
        .subject(String.valueOf(user.getId()))
        .claim("username", user.getUsername())
        .claim(ROLE_CLAIM, user.isAdmin() ? "ADMIN" : "USER")
        .expiresAt(expire)
        .build();

    return encodeClaimToJwt(claims);
  }

  public String encodeClaimToJwt(JwtClaimsSet claims) {
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
