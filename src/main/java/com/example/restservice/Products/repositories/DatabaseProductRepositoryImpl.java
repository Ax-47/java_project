package com.example.restservice.Products.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.Products.domain.DatabaseProductRepository;
import com.example.restservice.Products.domain.Product;
import com.example.restservice.Products.models.ProductModel;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

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
  public Page<Product> findAllProducts(PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    var page = jpaProductRepository.findAll(pageable);
    List<Product> products = page.getContent().stream().map(product -> product.toDomain()).toList();
    return new Page<>(
        products, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Page<Product> findByIds(List<UUID> productIds, PageQuery query) {
    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();
    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);
    var page = jpaProductRepository.findByIdIn(productIds, pageable);
    List<Product> products = page.getContent().stream().map(product -> product.toDomain()).toList();
    return new Page<>(
        products, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Product delete(Product product) {
    jpaProductRepository.deleteById(product.getId());
    return product;
  }
}
