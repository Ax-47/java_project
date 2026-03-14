package com.example.restservice.Reviews.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindReveiwByProductUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;

  public FindReveiwByProductUsecase(DatabaseReviewRepository databaseReviewRepository) {

    this.databaseReviewRepository = databaseReviewRepository;
  }

  public Page<ReviewResponseDTO> execute(UUID productId, PageQuery query) {
    Page<Review> usersPage = databaseReviewRepository.findReviewByProductId(productId, query);
    List<ReviewResponseDTO> content =
        usersPage.content().stream().map(ReviewResponseDTO::from).toList();
    return new Page<>(
        content,
        usersPage.totalElements(),
        usersPage.totalPages(),
        usersPage.page(),
        usersPage.size());
  }
}
