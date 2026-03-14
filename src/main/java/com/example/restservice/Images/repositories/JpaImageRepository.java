package com.example.restservice.Images.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.models.ImageModel;

public interface JpaImageRepository extends JpaRepository<ImageModel, UUID> {
  List<ImageModel> findByResourceIdAndResourceType(UUID resourceId, ImageResourceType resourceType);

  List<ImageModel> findByResourceIdAndResourceTypeOrderBySortOrderAsc(
      UUID resourceId, ImageResourceType resourceType);
}
