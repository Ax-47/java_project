package com.example.restservice.Images.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.ImageCategory;
import com.example.restservice.Images.domain.ImageProcessingRepository;
import com.example.restservice.Images.domain.ImageSize;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageProcessingRepositoryImpl implements ImageProcessingRepository {

  @Override
  public Map<ImageSize, byte[]> process(byte[] original) {
    Map<ImageSize, CompletableFuture<byte[]>> futures = new HashMap<>();
    for (ImageSize size : ImageSize.values()) {
      if (size.category() != ImageCategory.STANDARD) continue;
      futures.put(size, CompletableFuture.supplyAsync(() -> resize(original, size)));
    }
    return joinFutures(futures);
  }

  @Override
  public Map<ImageSize, byte[]> processBackground(byte[] original) {
    Map<ImageSize, CompletableFuture<byte[]>> futures = new HashMap<>();
    for (ImageSize size : ImageSize.values()) {
      if (size.category() != ImageCategory.BACKGROUND) continue;
      futures.put(size, CompletableFuture.supplyAsync(() -> resizeBackground(original, size)));
    }
    return joinFutures(futures);
  }

  private Map<ImageSize, byte[]> joinFutures(Map<ImageSize, CompletableFuture<byte[]>> futures) {

    Map<ImageSize, byte[]> result = new HashMap<>();

    for (Map.Entry<ImageSize, CompletableFuture<byte[]>> entry : futures.entrySet()) {
      result.put(entry.getKey(), entry.getValue().join());
    }

    return result;
  }

  private byte[] resize(byte[] input, ImageSize size) {
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      Thumbnails.of(new ByteArrayInputStream(input))
          .size(size.width(), size.height())
          .keepAspectRatio(true)
          .outputQuality(0.8)
          .outputFormat("webp")
          .toOutputStream(output);

      return output.toByteArray();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] resizeBackground(byte[] input, ImageSize size) {

    try {

      ByteArrayOutputStream output = new ByteArrayOutputStream();

      Thumbnails.of(new ByteArrayInputStream(input))
          .size(size.width(), size.height())
          .crop(net.coobird.thumbnailator.geometry.Positions.CENTER)
          .outputQuality(0.85)
          .outputFormat("webp")
          .toOutputStream(output);

      return output.toByteArray();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
