package com.example.restservice.Address.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseAddressRepository {
  public Address save(Address address);

  Optional<Address> findById(UUID id);

  public Address delete(Address address);
}
