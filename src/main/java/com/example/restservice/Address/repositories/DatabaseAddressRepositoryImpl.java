package com.example.restservice.Address.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.models.AddressModel;

@Repository
public class DatabaseAddressRepositoryImpl implements DatabaseAddressRepository {
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

  @Override
  public Optional<Address> findById(UUID id) {
    return jpaAddressRepository.findById(id).map(AddressModel::toDomain);
  }

  @Override
  public Address delete(Address address) {
    jpaAddressRepository.deleteById(address.getId());
    return address;
  }

}
