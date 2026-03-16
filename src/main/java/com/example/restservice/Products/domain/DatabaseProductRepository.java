package com.example.restservice.Products.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

public interface DatabaseProductRepository {
  Product save(Product product);

  Optional<Product> findById(UUID id);

  Product delete(Product product);

  Page<Product> findAllProducts(PageQuery query);

  Page<Product> findByIds(List<UUID> productIds, PageQuery query);
}
