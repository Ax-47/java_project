package com.example.restservice.Auth.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restservice.Address.models.AddressModel;
import com.example.restservice.Auth.models.RefreshTokenModel;

public interface JpaRefreshTokenRepository
                extends JpaRepository<RefreshTokenModel, UUID> {
}
