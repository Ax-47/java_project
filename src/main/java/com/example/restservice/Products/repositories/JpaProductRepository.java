package com.example.restservice.Products.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Products.models.ProductModel;

public interface JpaProductRepository extends JpaRepository<ProductModel, UUID> {}
