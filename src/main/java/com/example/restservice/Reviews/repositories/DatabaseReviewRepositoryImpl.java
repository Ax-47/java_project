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

  @Override
  public Optional<Review> findById(UUID id) {
    return jpaReviewRepository.findById(id).map(ReviewModel::toDomain);
  }

  @Override
  public Review delete(Review review) {
    jpaReviewRepository.deleteById(review.getId());
    return review;
  }

  private final JpaReviewRepository jpaReviewRepository;

  public DatabaseReviewRepositoryImpl(JpaReviewRepository jpaReviewRepository) {
    this.jpaReviewRepository = jpaReviewRepository;
  }
}