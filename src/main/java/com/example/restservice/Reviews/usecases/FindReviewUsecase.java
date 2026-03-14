package com.example.restservice.Reviews.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewResponseDTO;
import com.example.restservice.common.*;

@Service
public class FindReviewUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;

  public FindReviewUsecase(DatabaseReviewRepository databaseReviewRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
  }

  public Page<ReviewResponseDTO> execute(PageQuery query) {
    Page<Review> usersPage = databaseReviewRepository.findAllReviews(query);
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
