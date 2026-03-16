package com.example.restservice.Images.domain;

public enum ImageResourceType {
  PRODUCT("products"),
  USER_PROFILE("users/profile"),
  USER_BACKGROUND("users/background"),
  REVIEW("reviews");

  private final String path;

  ImageResourceType(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  public static ImageResourceType fromPath(String path) {

    for (ImageResourceType type : values()) {
      if (type.path.equals(path)) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unknown resource type: " + path);
  }
}
