package com.example.restservice.Address.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restservice.Address.models.AddressModel;

public interface JpaAddressRepository extends JpaRepository<AddressModel, UUID> {
  @Modifying
  @Query(
      """
                            UPDATE AddressModel a
                            SET a.isDefault = false
                            WHERE a.userId = :userId AND a.isDefault = true
                        """)
  void clearDefault(@Param("userId") UUID userId);

  @Modifying
  @Query(
      """
                            UPDATE AddressModel a
                            SET a.isDefault = true
                            WHERE a.id = :addressId AND a.userId = :userId
                        """)
  void setDefault(@Param("userId") UUID userId, @Param("addressId") UUID addressId);

  Optional<AddressModel> findByUserId(UUID userId);
}
