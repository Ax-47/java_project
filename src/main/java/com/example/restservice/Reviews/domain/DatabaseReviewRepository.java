package com.example.restservice.Reviews.domain;

import java.util.Optional;
import java.util.UUID;

import com.example.restservice.common.*;

public interface DatabaseReviewRepository {
  Review save(Review review);

  Optional<Review> findById(UUID id);

  public Review delete(Review review);

  public Page<Review> findAllReviews(PageQuery query);

  public Page<Review> findReviewByUserId(UUID userId, PageQuery query);

  public Page<Review> findReviewByProductId(UUID productId, PageQuery query);

  Optional<Review> findReviewByUserIdAndProductId(UUID userId, UUID productId);
}
