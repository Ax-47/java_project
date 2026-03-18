package com.example.restservice.config;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.restservice.Models.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Profile("prod")
public class SecurityConfig {

  private final ResourceLoader resourceLoader;
  private final AuthorizeFilter authorizeFilter;

  public SecurityConfig(ResourceLoader resourceLoader, AuthorizeFilter authorizeFilter) {
    this.resourceLoader = resourceLoader;
    this.authorizeFilter = authorizeFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    // static files
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**")
                    .permitAll()
                    // pages
                    .requestMatchers(
                        "/",
                        "/signin",
                        "/signup",
                        "/products/**",
                        "/categories/**",
                        "/review",
                        "/api/image/**")
                    .permitAll()
                    // auth api
                    .requestMatchers("/api/auth/**")
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    // other api must auth
                    .requestMatchers("/api/review/*/images")
                    .permitAll()
                    .requestMatchers("/api/**", "/products/*/purchase", "/profile", "/topup")
                    .authenticated()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(authorizeFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("role");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtConverter;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {

    DaoAuthenticationProvider authenticationProvider =
        new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public JwtEncoder jwtEncoder(RSAKeyProperties rsaInstance) {
    JWK jwk =
        new RSAKey.Builder(rsaInstance.publicKey()).privateKey(rsaInstance.privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public JwtDecoder jwtDecoder(RSAKeyProperties rsaInstance) {
    return NimbusJwtDecoder.withPublicKey(rsaInstance.publicKey()).build();
  }

  @Bean
  public RSAKeyProperties rsaInstance()
      throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
    Resource privateKeyPkcs8 = resourceLoader.getResource("classpath:private_key_pkcs8.pem");
    String privateKeyContent = new String(privateKeyPkcs8.getContentAsByteArray());
    Resource publicKey = resourceLoader.getResource("classpath:public_key.pem");
    String publicKeyContent = new String(publicKey.getContentAsByteArray());

    privateKeyContent =
        privateKeyContent
            .replaceAll("\\n", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "");
    publicKeyContent =
        publicKeyContent
            .replaceAll("\\n", "")
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "");

    KeyFactory kf = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpecPKCS8 =
        new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
    PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
    X509EncodedKeySpec keySpecX509 =
        new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
    RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
    return new RSAKeyProperties(pubKey, privKey);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
