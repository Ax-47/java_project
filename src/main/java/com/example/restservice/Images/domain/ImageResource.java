package com.example.restservice.Images.domain;

import java.util.Objects;
import java.util.UUID;

public final class ImageResource {

  private final UUID resourceId;
  private final ImageResourceType resourceType;

  private ImageResource(UUID resourceId, ImageResourceType resourceType) {
    this.resourceId = resourceId;
    this.resourceType = resourceType;
  }

  public static ImageResource of(UUID resourceId, ImageResourceType resourceType) {
    return new ImageResource(resourceId, resourceType);
  }

  public String genFilename(UUID imageId, ImageSize size) {
    return resourceType.name().toLowerCase()
        + "/"
        + resourceId
        + "/"
        + imageId
        + "_"
        + size.name().toLowerCase()
        + ".webp";
  }

  public UUID getResourceId() {
    return resourceId;
  }

  public ImageResourceType getResourceType() {
    return resourceType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ImageResource that)) return false;
    return Objects.equals(resourceId, that.resourceId) && resourceType == that.resourceType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceId, resourceType);
  }
}
