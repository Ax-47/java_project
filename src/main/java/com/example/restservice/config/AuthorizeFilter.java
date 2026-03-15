package com.example.restservice.config;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.restservice.Auth.domain.DecodedToken;
import com.example.restservice.Auth.domain.TokenRepository;
import com.example.restservice.Auth.dto.TokenResponseDTO;
import com.example.restservice.Auth.dto.UserPrincipalDTO;
import com.example.restservice.Auth.usecases.RefreshTokenUsecase;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {

  private final TokenRepository tokenRepository;
  private final RefreshTokenUsecase refreshTokenUsecase;

  private final long accessTokenExpiredInSeconds;
  private final long refreshTokenExpiredInSeconds;

  public AuthorizeFilter(
      @Lazy TokenRepository tokenRepository,
      @Lazy RefreshTokenUsecase refreshTokenUsecase,
      @Value("${token.access-token-expired-in-seconds}") long accessTokenExpiredInSeconds,
      @Value("${token.refresh-token-expired-in-seconds}") long refreshTokenExpiredInSeconds) {

    this.tokenRepository = tokenRepository;
    this.refreshTokenUsecase = refreshTokenUsecase;
    this.accessTokenExpiredInSeconds = accessTokenExpiredInSeconds;
    this.refreshTokenExpiredInSeconds = refreshTokenExpiredInSeconds;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken = getCookie(request, "access_token");
    String refreshToken = getCookie(request, "refresh_token");

    DecodedToken decodedAccess = decode(accessToken);

    if (decodedAccess != null) {

      if (refreshToken != null && shouldRotate(decodedAccess)) {
        TokenResponseDTO tokens = refreshTokenUsecase.execute(refreshToken);
        setCookies(response, tokens);
      }

      authenticate(decodedAccess);

    } else if (refreshToken != null) {

      try {

        TokenResponseDTO tokens = refreshTokenUsecase.execute(refreshToken);
        setCookies(response, tokens);

        DecodedToken newToken = decode(tokens.access_token());
        authenticate(newToken);

      } catch (Exception e) {
        clearCookies(response);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String getCookie(HttpServletRequest request, String name) {

    if (request.getCookies() == null) return null;

    for (Cookie cookie : request.getCookies()) {
      if (name.equals(cookie.getName())) {
        return cookie.getValue();
      }
    }

    return null;
  }

  private DecodedToken decode(String token) {

    if (token == null) return null;

    try {
      return tokenRepository.decode(token);
    } catch (Exception e) {
      return null;
    }
  }

  private void authenticate(DecodedToken token) {

    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            new UserPrincipalDTO(token.userId(), token.username(), token.role()),
            null,
            authorities(token.role()));

    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  private void setCookies(HttpServletResponse response, TokenResponseDTO tokens) {

    Cookie access = new Cookie("access_token", tokens.access_token());
    access.setHttpOnly(true);
    access.setPath("/");
    access.setMaxAge((int) accessTokenExpiredInSeconds);
    response.addCookie(access);

    Cookie refresh = new Cookie("refresh_token", tokens.refresh_token());
    refresh.setHttpOnly(true);
    refresh.setPath("/");
    refresh.setMaxAge((int) refreshTokenExpiredInSeconds);
    response.addCookie(refresh);
  }

  private void clearCookies(HttpServletResponse response) {

    Cookie access = new Cookie("access_token", null);
    access.setPath("/");
    access.setMaxAge(0);
    response.addCookie(access);

    Cookie refresh = new Cookie("refresh_token", null);
    refresh.setPath("/");
    refresh.setMaxAge(0);
    response.addCookie(refresh);
  }

  private Collection<? extends GrantedAuthority> authorities(String role) {

    if ("ADMIN".equals(role)) {
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  private boolean shouldRotate(DecodedToken token) {

    long secondsLeft = token.expiresAt().getEpochSecond() - Instant.now().getEpochSecond();

    return secondsLeft < 300;
  }
}
