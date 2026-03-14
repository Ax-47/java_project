package com.example.restservice.Address.usecases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservice.Address.domain.Address;
import com.example.restservice.Address.domain.DatabaseAddressRepository;
import com.example.restservice.Address.dto.FindAddressResponseDTO;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Service
public class FindAddressesUsecase {

  private final DatabaseAddressRepository databaseAddressRepository;

  public FindAddressesUsecase(DatabaseAddressRepository databaseAddressRepository) {
    this.databaseAddressRepository = databaseAddressRepository;
  }

  @Transactional(readOnly = true)
  public Page<FindAddressResponseDTO> execute(PageQuery query) {

    Page<Address> addressesPage = databaseAddressRepository.findAllAddresses(query);

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
