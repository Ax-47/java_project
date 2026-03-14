package com.example.restservice.ProductCategories.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Categories.domain.Category;
import com.example.restservice.Categories.models.CategoryModel;
import com.example.restservice.Categories.repositories.JpaCategoryRepository;
import com.example.restservice.ProductCategories.domain.DatabaseProductCategoryRepository;
import com.example.restservice.ProductCategories.domain.ProductCategory;
import com.example.restservice.ProductCategories.models.ProductCategoryId;
import com.example.restservice.ProductCategories.models.ProductCategoryModel;
import com.example.restservice.Products.models.ProductModel;
import com.example.restservice.Products.repositories.JpaProductRepository;
import com.example.restservice.common.*;

@Repository
public class DatabaseProductCategoryRepositoryImpl implements DatabaseProductCategoryRepository {
  private final JpaProductCategoryRepository jpaProductCategoryRepository;
  private final JpaProductRepository jpaProductRepository;
  private final JpaCategoryRepository jpaCategoryRepository;

  public DatabaseProductCategoryRepositoryImpl(
      JpaProductCategoryRepository jpaProductCategoryRepository,
      JpaProductRepository jpaProductRepository,
      JpaCategoryRepository jpaCategoryRepository) {
    this.jpaProductCategoryRepository = jpaProductCategoryRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaCategoryRepository = jpaCategoryRepository;
  }

  @Override
  public ProductCategory save(ProductCategory relation) {
    ProductModel product = jpaProductRepository.getReferenceById(relation.getProductId());
    CategoryModel category = jpaCategoryRepository.getReferenceById(relation.getCategoryId());
    ProductCategoryModel model = new ProductCategoryModel(product, category);
    ProductCategoryModel saved = jpaProductCategoryRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public void delete(ProductCategory relation) {
    ProductCategoryId id = new ProductCategoryId(relation.getProductId(), relation.getCategoryId());
    jpaProductCategoryRepository.deleteById(id);
  }

  @Override
  public List<Category> findCategoriesByProductId(UUID productId) {

    return jpaProductCategoryRepository.findByProductId(productId).stream()
        .map(pc -> pc.getCategory().toDomain())
        .toList();
  }
  // @Override
  // public Optional<Category> findById(UUID id) {
  // return jpaCategoryRepository.findById(id).map(CategoryModel::toDomain);
  // }
  //
  //
  // @Override
  // public Page<Category> findAllCategories(PageQuery query) {
  //
  // Sort sort = query.ascending()
  // ? Sort.by(query.sortBy()).ascending()
  // : Sort.by(query.sortBy()).descending();
  //
  // Pageable pageable = PageRequest.of(query.page(), query.size(), sort);
  //
  // org.springframework.data.domain.Page<CategoryModel> page =
  // jpaCategoryRepository.findAll(pageable);
  // List<Category> users = page.getContent().stream().map(category ->
  // category.toDomain()).toList();
  //
  // return new Page<>(
  // users, page.getTotalElements(), page.getTotalPages(), page.getNumber(),
  // page.getSize());
  // }
}
