package com.example.restservice.Images.domain;

import java.util.Map;

public interface ImageProcessingRepository {
  public Map<ImageSize, byte[]> process(byte[] original);

  public Map<ImageSize, byte[]> processBackground(byte[] original);
}
