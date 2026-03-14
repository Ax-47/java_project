package com.example.restservice.Images.repositories;

import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.example.restservice.Images.domain.ImageRepository;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Repository
public class MinioStorageRepository implements ImageRepository {

  private final MinioClient minioClient;

  public MinioStorageRepository(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  @Override
  public String upload(InputStream stream, String filename, long size) {

    try {
      minioClient.putObject(
          PutObjectArgs.builder().bucket("images").object(filename).stream(stream, size, -1)
              .contentType("image/webp")
              .build());

      return filename;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
