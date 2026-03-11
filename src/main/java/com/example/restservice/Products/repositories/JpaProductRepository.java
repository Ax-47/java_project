package com.example.restservice.Products.repositories;

import com.example.restservice.Products.models.ProductModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository
    extends JpaRepository<ProductModel, UUID> {}
