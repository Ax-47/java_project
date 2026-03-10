package com.example.restservice.Reviews.repositories;

import org.springframework.stereotype.Repository;

import com.example.restservice.Address.repositories.JpaAddressRepository;
import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.models.ReviewModel;

@Repository
public class DatabaseReviewRepositoryImpl implements DatabaseReviewRepository {
  @Override
  public Review save(Review review) {
    ReviewModel model = ReviewModel.fromDomain(review);
    ReviewModel saved = jpaReviewRepository.save(model);
    return saved.toDomain();
  }
  private final JpaReviewRepository jpaReviewRepository;

  public DatabaseReviewRepositoryImpl(JpaReviewRepository jpaReviewRepository) {
    this.jpaReviewRepository = jpaReviewRepository;
  }
}
