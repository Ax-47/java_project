package com.example.restservice.Auth.repositories;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.domain.DecodedToken;
import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Users.domain.User;

@Repository
public class JwtRepositoryImpl implements TokenRepository {

  private static final String ISSUER = "MikhailAkhsakov";
  private static final String ROLE_CLAIM = "role";

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private final long accessTokenExpiredInSeconds;
  private final long refreshTokenExpiredInSeconds;

  public JwtRepositoryImpl(
      JwtEncoder jwtEncoder,
      JwtDecoder jwtDecoder,
      @Value("${token.access-token-expired-in-seconds}") long accessTokenExpiredInSeconds,
      @Value("${token.refresh-token-expired-in-seconds}") long refreshTokenExpiredInSeconds) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
    this.accessTokenExpiredInSeconds = accessTokenExpiredInSeconds;
    this.refreshTokenExpiredInSeconds = refreshTokenExpiredInSeconds;
  }

  public String issueAccessToken(User user, Instant issueDate) {
    return generateToken(user, issueDate, accessTokenExpiredInSeconds);
  }

  public String issueRefreshToken(User user, UUID tokenId, String secret, Instant issueDate) {
    return generateToken(user, issueDate, tokenId, secret, refreshTokenExpiredInSeconds);
  }

  public String generateToken(User user, Instant issueDate, long expiredInSeconds) {
    Instant expire = issueDate.plusSeconds(expiredInSeconds);
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(issueDate)
            .subject(String.valueOf(user.getId()))
            .claim("username", user.getUsername())
            .claim(ROLE_CLAIM, user.isAdmin() ? "ADMIN" : "USER")
            .expiresAt(expire)
            .build();

    return encodeClaimToJwt(claims);
  }

  public String generateToken(
      User user, Instant issueDate, UUID tokenId, String secret, long expiredInSeconds) {
    Instant expire = issueDate.plusSeconds(expiredInSeconds);

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(ISSUER)
            .issuedAt(issueDate)
            .subject(String.valueOf(user.getId()))
            .claim("username", user.getUsername())
            .claim(ROLE_CLAIM, user.isAdmin() ? "ADMIN" : "USER")
            .claim("type", "refresh")
            .claim("secret", secret)
            .id(tokenId.toString())
            .expiresAt(expire)
            .build();

    return encodeClaimToJwt(claims);
  }

  public String encodeClaimToJwt(JwtClaimsSet claims) {
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public DecodedToken decode(String token) {

    Jwt jwt = jwtDecoder.decode(token);

    UUID tokenId = null;
    if (jwt.getId() != null) {
      tokenId = UUID.fromString(jwt.getId());
    }

    UUID userId = UUID.fromString(jwt.getSubject());
    String username = jwt.getClaim("username");
    String role = jwt.getClaim(ROLE_CLAIM);
    String secret = jwt.getClaim("secret");
    String type = jwt.getClaim("type");

    Instant expireAt = jwt.getExpiresAt();
    return new DecodedToken(tokenId, userId, username, role, secret, type, expireAt);
  }
}
