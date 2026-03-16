package com.example.restservice.Images.domain;

import java.io.InputStream;

public interface ImageStorageRepository {
  String upload(InputStream stream, String filename, long size);

  InputStream get(String key);
}
