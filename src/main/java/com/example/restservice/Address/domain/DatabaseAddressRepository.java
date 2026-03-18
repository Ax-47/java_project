package com.example.restservice.Address.domain;

import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

public interface DatabaseAddressRepository {
  public Address save(Address address);

  Optional<Address> findById(UUID id);

  Optional<Address> findByUserId(UUID userId);

  public Address delete(Address address);

  public void clearDefaultByUserId(UUID userId);

  public void setDefaultAddress(UUID addressId, UUID userId);

  Page<Address> findAllAddresses(PageQuery pageable);

  Page<Address> findAllAddressesByUserId(UUID userId, PageQuery pageable);
}
