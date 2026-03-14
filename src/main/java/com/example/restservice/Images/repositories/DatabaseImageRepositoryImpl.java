package com.example.restservice.Images.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.restservice.Images.domain.DatabaseImageRepository;
import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.models.ImageModel;

@Repository
public class DatabaseImageRepositoryImpl implements DatabaseImageRepository {
  private final JpaImageRepository jpaImageRepository;

  public DatabaseImageRepositoryImpl(JpaImageRepository jpaImageRepository) {
    this.jpaImageRepository = jpaImageRepository;
  }

  @Override
  public Image save(Image image) {
    ImageModel model = ImageModel.fromDomain(image);
    ImageModel saved = jpaImageRepository.save(model);
    return ImageModel.toDomain(saved);
  }

  @Override
  public Optional<Image> findById(UUID id) {
    return jpaImageRepository.findById(id).map(ImageModel::toDomain);
  }

  @Override
  public void delete(UUID imageId) {
    jpaImageRepository.deleteById(imageId);
  }

  @Override
  public List<Image> findByResource(ImageResource resource) {
    return jpaImageRepository
        .findByResourceIdAndResourceTypeOrderBySortOrderAsc(
            resource.getResourceId(), resource.getResourceType())
        .stream()
        .map(ImageModel::toDomain)
        .toList();
  }
}
