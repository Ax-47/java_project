package com.example.restservice.Images.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Images.domain.ImageResourceType;
import com.example.restservice.Images.models.ImageModel;

public interface JpaImageRepository extends JpaRepository<ImageModel, UUID> {
  List<ImageModel> findAllByResourceIdAndResourceType(
      UUID resourceId, ImageResourceType resourceType);

  List<ImageModel> findAllByResourceTypeAndResourceIdIn(
      ImageResourceType resourceType, List<UUID> resourceIds);

  List<ImageModel> findAllByResourceIdAndResourceTypeOrderBySortOrderAsc(
      UUID resourceId, ImageResourceType resourceType);

  List<ImageModel> findAllByResourceTypeAndResourceIdInOrderBySortOrderAsc(
      ImageResourceType resourceType, List<UUID> resourceIds);
}
