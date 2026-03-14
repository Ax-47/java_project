package com.example.restservice.Images.domain;

public enum ImageSize {
  THUMBNAIL(ImageCategory.STANDARD, 200, 200),
  MEDIUM(ImageCategory.STANDARD, 800, 800),
  LARGE(ImageCategory.STANDARD, 1600, 1600),

  BACKGROUND_SMALL(ImageCategory.BACKGROUND, 640, 360),
  BACKGROUND_MEDIUM(ImageCategory.BACKGROUND, 1280, 720),
  BACKGROUND_LARGE(ImageCategory.BACKGROUND, 1920, 1080);

  private final ImageCategory category;
  private final int width;
  private final int height;

  ImageSize(ImageCategory category, int width, int height) {
    this.category = category;
    this.width = width;
    this.height = height;
  }

  public ImageCategory category() {
    return category;
  }

  public int width() {
    return width;
  }

  public int height() {
    return height;
  }

  public String value() {
    return name().toLowerCase();
  }
}
