package com.example.restservice.Reviews.usecases;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewWithUserResponseDTO;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.common.*;

@Service
public class FindReveiwByProductUsecase {

  private final DatabaseReviewRepository databaseReviewRepository;
  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public FindReveiwByProductUsecase(
      DatabaseReviewRepository databaseReviewRepository,
      DatabaseUserRepository databaseUserRepository,
      DatabaseImageRepository databaseImageRepository) {
    this.databaseReviewRepository = databaseReviewRepository;
    this.databaseUserRepository = databaseUserRepository;
    this.databaseImageRepository = databaseImageRepository;
  }

  public Page<ReviewWithUserResponseDTO> execute(UUID productId, PageQuery query) {
    Page<Review> reviewsPage = databaseReviewRepository.findReviewByProductId(productId, query);
    List<Review> reviews = reviewsPage.content();

    Set<UUID> userIds = reviews.stream().map(Review::getUserId).collect(Collectors.toSet());

    Map<UUID, String> userMap =
        databaseUserRepository.findAllByUserIds(userIds).stream()
            .collect(Collectors.toMap(User::getId, User::getUsername));
    Map<UUID, String> profileMap =
        databaseImageRepository.findAllByResources(userIds, ImageResourceType.USER_PROFILE).stream()
            .collect(
                Collectors.toMap(
                    image -> image.getResource().getResourceId(),
                    Image::getMediumImage,
                    (existing, replacement) -> existing));

    List<ReviewWithUserResponseDTO> content =
        reviews.stream()
            .map(
                review -> {
                  String username = userMap.getOrDefault(review.getUserId(), "Anonymous");
                  String profileUrl =
                      profileMap.getOrDefault(review.getUserId(), "/images/profile-pic.png");

                  return ReviewWithUserResponseDTO.from(review, username, profileUrl);
                })
            .toList();
    return new Page<>(
        content,
        reviewsPage.totalElements(),
        reviewsPage.totalPages(),
        reviewsPage.page(),
        reviewsPage.size());
  }
}
