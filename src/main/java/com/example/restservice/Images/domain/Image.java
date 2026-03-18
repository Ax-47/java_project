package com.example.restservice.Images.domain;

import java.time.Instant;
import java.util.UUID;

public class Image {

  private UUID id;
  private ImageResource resource;
  private int sortOrder;
  private Instant createdAt;

  private Image(UUID id, ImageResource resource, int sortOrder, Instant createdAt) {

    this.id = id;
    this.resource = resource;
    this.sortOrder = sortOrder;
    this.createdAt = createdAt;
  }

  public static Image create(ImageResource resource, int sortOrder) {

    return new Image(UUID.randomUUID(), resource, sortOrder, Instant.now());
  }

  public String getMediumImage(ImageResource resource) {
    return "/images" + resource.genFilename(id, ImageSize.MEDIUM);
  }

  public UUID getId() {
    return id;
  }

  public ImageResource getResource() {
    return resource;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public static Image rehydrate(UUID id, ImageResource resource, int sortOrder, Instant createdAt) {

    return new Image(id, resource, sortOrder, createdAt);
  }

  public void changeSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public int getSortOrder() {
    return sortOrder;
  }
}
