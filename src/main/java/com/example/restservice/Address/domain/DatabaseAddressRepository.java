package com.example.restservice.Address.domain;

import java.util.Optional;

public interface DatabaseAddressRepository {
  public Address save(Address address); 
  Optional<Address> findById(Long id);
  public Address delete(Address address);
}
