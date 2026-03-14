package com.example.restservice.Images.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DatabaseImageRepository {
  public Image save(Image image);

  public Optional<Image> findById(UUID id);

  void delete(UUID imageId);

  List<Image> findByResource(ImageResource resource);
}
