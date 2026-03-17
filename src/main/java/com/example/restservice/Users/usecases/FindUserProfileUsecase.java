package com.example.restservice.Users.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.domain.ImageSize;
import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.dto.FindUserProfileResponseDTO;

@Service
public class FindUserProfileUsecase {

  private final DatabaseUserRepository databaseUserRepository;
  private final DatabaseImageRepository databaseImageRepository;

  public FindUserProfileUsecase(
      DatabaseUserRepository databaseUserRepository,
      DatabaseImageRepository databaseImageRepository) {
    this.databaseUserRepository = databaseUserRepository;
    this.databaseImageRepository = databaseImageRepository;
  }

  public FindUserProfileResponseDTO execute(UUID userId) {
    var user =
        databaseUserRepository
            .findUserByUserId(userId)
            .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลผู้ใช้งาน"));

    ImageResource profileResource = ImageResource.of(user.getId(), ImageResourceType.USER_PROFILE);
    ImageResource bgResource = ImageResource.of(user.getId(), ImageResourceType.USER_BACKGROUND);

    var profileList = databaseImageRepository.findByResource(profileResource);
    var bgList = databaseImageRepository.findByResource(bgResource);

    String profileUrl =
        !profileList.isEmpty()
            ? "/images"
                + profileResource.genFilename(profileList.getFirst().getId(), ImageSize.MEDIUM)
            : "/images/default-profile.png";

    String backgroundUrl =
        !bgList.isEmpty()
            ? "/images"
                + bgResource.genFilename(bgList.getFirst().getId(), ImageSize.BACKGROUND_LARGE)
            : "/images/default-bg.jpg";

    return new FindUserProfileResponseDTO(profileUrl, backgroundUrl);
  }
}
