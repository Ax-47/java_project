package com.example.restservice.Reviews.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Reviews.models.ReviewModel;

public interface JpaReviewRepository extends JpaRepository<ReviewModel, UUID> {
  Page<ReviewModel> findAllByUserId(UUID userId, Pageable pageable);

  Page<ReviewModel> findAllByProductId(UUID productId, Pageable pageable);

  Page<ReviewModel> findAllByUserIdAndProductId(UUID userId, UUID productId, Pageable pageable);

  Optional<ReviewModel> findByUserIdAndProductId(UUID userId, UUID productId);
}
