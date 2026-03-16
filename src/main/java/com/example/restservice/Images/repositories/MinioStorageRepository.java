package com.example.restservice.Images.repositories;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.restservice.Images.domain.ImageStorageRepository;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Repository
public class MinioStorageRepository implements ImageStorageRepository {
  @Value("${minio.bucket}")
  private String bucket;

  private final MinioClient minioClient;

  public MinioStorageRepository(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  @Override
  public String upload(InputStream stream, String filename, long size) {

    try {
      minioClient.putObject(
          PutObjectArgs.builder().bucket(bucket).object(filename).stream(stream, size, -1)
              .contentType("image/webp")
              .build());

      return filename;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InputStream get(String key) {

    try {
      return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(key).build());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
