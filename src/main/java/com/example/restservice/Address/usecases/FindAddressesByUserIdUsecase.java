package com.example.restservice.Address.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.FindAddressResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindAddressesByUserIdUsecase {

  private final DatabaseAddressRepository databaseAddressRepository;

  public FindAddressesByUserIdUsecase(DatabaseAddressRepository databaseAddressRepository) {
    this.databaseAddressRepository = databaseAddressRepository;
  }

  @Transactional(readOnly = true)
  public Page<FindAddressResponseDTO> execute(UUID userId, PageQuery query) {

    Page<Address> addressesPage = databaseAddressRepository.findAllAddressesByUserId(userId, query);
    List<FindAddressResponseDTO> content =
        addressesPage.content().stream().map(FindAddressResponseDTO::from).toList();
    return new Page<>(
        content,
        addressesPage.totalElements(),
        addressesPage.totalPages(),
        addressesPage.page(),
        addressesPage.size());
  }
}
