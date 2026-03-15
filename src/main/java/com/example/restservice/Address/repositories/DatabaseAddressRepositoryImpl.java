package com.example.restservice.Address.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.models.AddressModel;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

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
  public Optional<Address> findByUserId(UUID userId) {
    return jpaAddressRepository.findByUserId(userId).map(AddressModel::toDomain);
  }

  @Override
  public Address delete(Address address) {
    jpaAddressRepository.deleteById(address.getId());
    return address;
  }

  @Override
  public void clearDefaultByUserId(UUID userId) {
    jpaAddressRepository.clearDefault(userId);
  }

  @Override
  public void setDefaultAddress(UUID addressId, UUID userId) {
    jpaAddressRepository.setDefault(userId, addressId);
  }

  @Override
  public Page<Address> findAllAddresses(PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<AddressModel> page =
        jpaAddressRepository.findAll(pageable);

    List<Address> addresses =
        page.getContent().stream()
            .map(
                model ->
                    Address.rehydrate(
                        model.getId(),
                        model.getUserId(),
                        model.getFullName(),
                        model.getPhoneNumber(),
                        model.getAddressLine1(),
                        model.getAddressLine2(),
                        model.getSubDistrict(),
                        model.getDistrict(),
                        model.getProvince(),
                        model.getPostalCode(),
                        model.getCountry(),
                        model.getLabel(),
                        model.isDefault(),
                        model.getCreatedAt(),
                        model.getUpdatedAt()))
            .toList();

    return new Page<>(
        addresses, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }
}
