package com.example.restservice.Images.repositories;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.example.restservice.Images.domain.ImageCategory;
import com.example.restservice.Images.domain.ImageProcessingRepository;
import com.example.restservice.Images.domain.ImageSize;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.ImageIOReader;
import com.sksamuel.scrimage.webp.WebpImageReader;
import com.sksamuel.scrimage.webp.WebpWriter;

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
      // โหลดรูป -> ย่อขนาดโดยรักษาอัตราส่วน (ไม่เกินกล่องที่กำหนด) -> เซฟเป็น WebP
      // คุณภาพ 80%
      return ImmutableImage.loader()
          .withImageReaders(Arrays.asList(new ImageIOReader(), new WebpImageReader()))
          .fromBytes(input)
          .max(size.width(), size.height())
          .bytes(WebpWriter.DEFAULT.withQ(80));

    } catch (Exception e) {
      throw new RuntimeException("Failed to resize standard image", e);
    }
  }

  private byte[] resizeBackground(byte[] input, ImageSize size) {
    try {
      // โหลดรูป -> ย่อและตัดขอบส่วนเกินออกให้อยู่ตรงกลางพอดี -> เซฟเป็น WebP คุณภาพ
      // 85%
      return ImmutableImage.loader()
          .withImageReaders(Arrays.asList(new ImageIOReader(), new WebpImageReader()))
          .fromBytes(input)
          .cover(size.width(), size.height())
          .bytes(WebpWriter.DEFAULT.withQ(85));

    } catch (Exception e) {
      throw new RuntimeException("Failed to resize background image", e);
    }
  }
}
