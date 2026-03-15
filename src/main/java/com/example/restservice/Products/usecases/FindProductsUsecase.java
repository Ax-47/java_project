package com.example.restservice.Products.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.dto.ProductResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindProductsUsecase {

  private final DatabaseProductRepository databaseProductRepository;

  public FindProductsUsecase(DatabaseProductRepository databaseProductRepository) {
    this.databaseProductRepository = databaseProductRepository;
  }

  public Page<ProductResponseDTO> execute(PageQuery query) {
    Page<Product> usersPage = databaseProductRepository.findAllProducts(query);
    List<ProductResponseDTO> content =
        usersPage.content().stream().map(ProductResponseDTO::from).toList();
    return new Page<>(
        content,
        usersPage.totalElements(),
        usersPage.totalPages(),
        usersPage.page(),
        usersPage.size());
  }
}
