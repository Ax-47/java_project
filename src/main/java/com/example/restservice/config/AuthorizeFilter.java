package com.example.restservice.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
import com.example.restservice.Auth.domain.UserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {

  private final TokenRepository tokenRepository;

  public AuthorizeFilter(@Lazy TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = extractToken(request);

    if (token != null) {

      Authentication auth = authenticate(token);

      if (auth != null) {
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {

    if (request.getCookies() == null) return null;

    for (Cookie cookie : request.getCookies()) {
      if ("access_token".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }

    return null;
  }

  public Authentication authenticate(String token) {

    DecodedToken decodedToken = tokenRepository.decode(token);

    return new UsernamePasswordAuthenticationToken(
        new UserPrincipal(decodedToken.userId(), decodedToken.username()),
        null,
        isAdmin(decodedToken.role()));
  }

  public Collection<? extends GrantedAuthority> isAdmin(String role) {
    return role.equals("ADMIN")
        ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        : List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }
}
