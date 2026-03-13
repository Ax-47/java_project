package com.example.restservice.Categories.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.restservice.Categories.models.CategoryModel;

public interface JpaCategoryRepository extends JpaRepository<CategoryModel, UUID> {

  @Modifying
  @Query("DELETE FROM CategoryModel c WHERE c.id = :id")
  int deleteCategory(UUID id);
}
