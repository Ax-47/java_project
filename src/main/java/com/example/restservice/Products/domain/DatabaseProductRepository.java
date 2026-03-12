package com.example.restservice.Products.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseProductRepository {
  public Product save(Product product);

  Optional<Product> findById(UUID id);

  public Product delete(Product product);
}
