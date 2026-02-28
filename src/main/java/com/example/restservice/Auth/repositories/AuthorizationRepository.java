package com.example.restservice.Auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.Auth.models.AuthorizationModel;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, String> {
  Optional<AuthorizationModel> findByState(String state);

  Optional<AuthorizationModel> findByAuthorizationCodeValue(String authorizationCode);

  Optional<AuthorizationModel> findByAccessTokenValue(String accessToken);

  Optional<AuthorizationModel> findByRefreshTokenValue(String refreshToken);

  Optional<AuthorizationModel> findByOidcIdTokenValue(String idToken);

  Optional<AuthorizationModel> findByUserCodeValue(String userCode);

  Optional<AuthorizationModel> findByDeviceCodeValue(String deviceCode);

  @Query("select a from AuthorizationModel a where a.state = :token" +
      " or a.authorizationCodeValue = :token" +
      " or a.accessTokenValue = :token" +
      " or a.refreshTokenValue = :token" +
      " or a.oidcIdTokenValue = :token" +
      " or a.userCodeValue = :token" +
      " or a.deviceCodeValue = :token")
  Optional<AuthorizationModel> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(
      @Param("token") String token);
}
