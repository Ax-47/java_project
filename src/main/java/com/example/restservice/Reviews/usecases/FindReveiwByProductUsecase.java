package com.example.restservice.Reviews.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.Reviews.domain.DatabaseReviewRepository;
import com.example.restservice.Reviews.domain.Review;
import com.example.restservice.Reviews.dto.ReviewWithUserResponseDTO;
import com.example.restservice.Users.domain.DatabaseUserRepository;
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

    List<ReviewWithUserResponseDTO> content =
        reviewsPage.content().stream()
            .map(
                review -> {
                  String username =
                      databaseUserRepository
                          .findUserByUserId(review.getUserId())
                          .map(user -> user.getUsername())
                          .orElse("Anonymous");
                  ImageResource profileResource =
                      ImageResource.of(review.getUserId(), ImageResourceType.USER_PROFILE);
                  List<Image> profileList = databaseImageRepository.findByResource(profileResource);
                  String profileUrl =
                      profileList.stream()
                          .findFirst()
                          .map(
                              image ->
                                  "/images"
                                      + profileResource.genFilename(
                                          image.getId(), ImageSize.MEDIUM))
                          .orElse("/images/profile-pic.png");
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
