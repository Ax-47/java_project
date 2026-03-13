package com.example.restservice.Categories.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Categories.models.CategoryModel;

public interface JpaCategoryRepository extends JpaRepository<CategoryModel, UUID> {}
