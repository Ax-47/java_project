package com.example.restservice.Address.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Address.models.AddressModel;

public interface JpaAddressRepository
        extends JpaRepository<AddressModel, UUID> {
}
