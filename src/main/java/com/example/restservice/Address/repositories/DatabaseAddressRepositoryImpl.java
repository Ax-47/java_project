package com.example.restservice.Address.repositories;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.models.AddressModel;

public class DatabaseAddressRepositoryImpl implements DatabaseAddressRepository{
  private final JpaAddressRepository jpaAddressRepository;

  public DatabaseAddressRepositoryImpl(JpaAddressRepository jpaAddressRepository) {
    this.jpaAddressRepository = jpaAddressRepository;
  }
  @Override
  public Address save(Address address) {
    AddressModel model = AddressModel.fromDomain(address);
    AddressModel saved = jpaAddressRepository.save(model);
    return saved.toDomain();
  }
 
}  
