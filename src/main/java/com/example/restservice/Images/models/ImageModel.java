package com.example.restservice.Images.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.restservice.Images.domain.Image;
import com.example.restservice.Images.domain.ImageResource;
import com.example.restservice.Images.domain.ImageResourceType;

import jakarta.persistence.*;

@Entity
@Table(
    name = "images",
    indexes = {
      @Index(name = "idx_images_resource", columnList = "resource_type, resource_id"),
      @Index(name = "idx_images_sort", columnList = "resource_type, resource_id, sort_order")
    })
public class ImageModel {

  @Id private UUID id;

  @Column(name = "resource_id", nullable = false)
  private UUID resourceId;

  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type", nullable = false, length = 20)
  private ImageResourceType resourceType;

  @Column(name = "sort_order", nullable = false)
  private int sortOrder;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  protected ImageModel() {}

  public ImageModel(UUID id, UUID resourceId, ImageResourceType resourceType, int sortOrder) {
    this.id = id;
    this.resourceId = resourceId;
    this.resourceType = resourceType;
    this.sortOrder = sortOrder;
  }

  public UUID getId() {
    return id;
  }

  public UUID getResourceId() {
    return resourceId;
  }

  public ImageResourceType getResourceType() {
    return resourceType;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public static Image toDomain(ImageModel model) {

    return Image.rehydrate(
        model.getId(),
        ImageResource.of(model.getResourceId(), model.getResourceType()),
        model.getSortOrder(),
        model.getCreatedAt());
  }

  public static List<Image> toDomain(List<ImageModel> models) {
    return models.stream().map(ImageModel::toDomain).toList();
  }

  public static ImageModel fromDomain(Image image) {

    return new ImageModel(
        image.getId(),
        image.getResource().getResourceId(),
        image.getResource().getResourceType(),
        image.getSortOrder());
  }
}
