package com.example.restservice.Images.domain;

import java.io.InputStream;

public interface ImageRepository {
  String upload(InputStream stream, String filename, long size);
}
