package com.example.restservice.Auth.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Auth.models.RefreshTokenModel;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {
  @Modifying
  @Transactional
  @Query(
      """
                                                UPDATE RefreshTokenModel rt
                                                SET rt.isExpired = true
                                                WHERE rt.userId = :userId
                                                AND rt.id = :tokenId
                                                                                                """)
  void revokeRefreshToken(@Param("tokenId") UUID tokenId, @Param("userId") UUID userId);

  @Modifying
  @Transactional
  @Query(
      """
                                                    UPDATE RefreshTokenModel rt
                                                    SET rt.isExpired = true
                                                    WHERE rt.id = :tokenId
                                                """)
  void revokeRefreshToken(@Param("tokenId") UUID tokenId);
}
