package com.example.restservice.Reviews.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.models.ReviewModel;
import com.example.restservice.common.*;

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
    return jpaReviewRepository.findById(id).map(model -> model.toDomain());
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

  @Override
  public Page<Review> findAllReviews(PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<ReviewModel> page = jpaReviewRepository.findAll(pageable);
    List<Review> users = page.getContent().stream().map(review -> review.toDomain()).toList();

    return new Page<>(
        users, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Page<Review> findReviewByUserId(UUID userId, PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<ReviewModel> page =
        jpaReviewRepository.findAllByUserId(userId, pageable);
    List<Review> users = page.getContent().stream().map(review -> review.toDomain()).toList();

    return new Page<>(
        users, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Page<Review> findReviewByProductId(UUID productId, PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<ReviewModel> page =
        jpaReviewRepository.findAllByProductId(productId, pageable);
    List<Review> users = page.getContent().stream().map(review -> review.toDomain()).toList();

    return new Page<>(
        users, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
  }

  @Override
  public Optional<Review> findReviewByUserIdAndProductId(UUID userId, UUID productId) {
    return jpaReviewRepository
        .findByUserIdAndProductId(userId, productId)
        .map(model -> model.toDomain());
  }
}
