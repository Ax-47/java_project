package com.example.restservice.Address.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.restservice.Address.models.AddressModel;

public interface JpaAddressRepository
                extends JpaRepository<AddressModel, UUID> {
        @Modifying
        @Query("UPDATE AddressModel a SET a.isDefault = false WHERE a.userId = :userId")
        public void clearDefaultAddressForUser(UUID userId);
}
