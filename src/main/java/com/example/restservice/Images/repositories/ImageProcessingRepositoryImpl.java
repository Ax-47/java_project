package com.example.restservice.Images.repositories;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

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
      BufferedImage resizedImage =
          Thumbnails.of(new ByteArrayInputStream(input))
              .size(size.width(), size.height())
              .keepAspectRatio(true)
              .asBufferedImage();

      return writeToWebP(resizedImage, 0.8f);

    } catch (IOException e) {
      throw new RuntimeException("Failed to resize standard image", e);
    }
  }

  private byte[] resizeBackground(byte[] input, ImageSize size) {
    try {
      BufferedImage resizedImage =
          Thumbnails.of(new ByteArrayInputStream(input))
              .size(size.width(), size.height())
              .crop(net.coobird.thumbnailator.geometry.Positions.CENTER)
              .asBufferedImage();

      return writeToWebP(resizedImage, 0.85f);

    } catch (IOException e) {
      throw new RuntimeException("Failed to resize background image", e);
    }
  }

  private byte[] writeToWebP(BufferedImage image, float quality) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    Iterator<ImageWriter> writers = ImageIO.getImageWritersByMIMEType("image/webp");
    if (!writers.hasNext()) {
      throw new IllegalStateException(
          "WebP ImageWriter not found! Please check webp-imageio dependency.");
    }
    ImageWriter writer = writers.next();

    ImageWriteParam writeParam = writer.getDefaultWriteParam();
    if (writeParam.canWriteCompressed()) {
      writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

      String[] types = writeParam.getCompressionTypes();
      if (types != null && types.length > 0) {
        String typeToUse = types[0];
        for (String type : types) {
          if (type.equalsIgnoreCase("Lossy")) {
            typeToUse = type;
            break;
          }
        }
        writeParam.setCompressionType(typeToUse);
      }
      writeParam.setCompressionQuality(quality);
    }

    try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
      writer.setOutput(ios);
      writer.write(null, new IIOImage(image, null, null), writeParam);
    } finally {
      writer.dispose();
    }

    return output.toByteArray();
  }
}
