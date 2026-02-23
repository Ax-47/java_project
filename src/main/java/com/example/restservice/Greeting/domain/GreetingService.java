package com.example.restservice.Greeting.domain;

// BL
public class GreetingService {

  public Greeting create(String name) {
    if (name == null || name.isBlank()) {
      return new Greeting("Hello, World!");
    }
    return new Greeting("Hello, " + name + "!");
  }
}
