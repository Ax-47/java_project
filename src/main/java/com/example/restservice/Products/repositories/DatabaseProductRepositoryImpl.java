package com.example.restservice.Products.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.models.ProductModel;

@Repository
public class DatabaseProductRepositoryImpl implements DatabaseProductRepository {

  private final JpaProductRepository jpaProductRepository;

  public DatabaseProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
    this.jpaProductRepository = jpaProductRepository;
  }

  @Override
  public Product save(Product product) {
    ProductModel model = ProductModel.fromDomain(product);
    ProductModel saved = jpaProductRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public Optional<Product> findById(UUID id) {
    return jpaProductRepository.findById(id).map(ProductModel::toDomain);
  }

  @Override
  public Product delete(Product product) {
    jpaProductRepository.deleteById(product.getId());
    return product;
  }
}
