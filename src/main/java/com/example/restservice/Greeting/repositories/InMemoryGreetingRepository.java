package com.example.restservice.Greeting.repositories;

import org.springframework.stereotype.Repository;

import com.example.restservice.Greeting.domain.Greeting;
import com.example.restservice.Greeting.domain.GreetingRepository;

@Repository
public class InMemoryGreetingRepository implements GreetingRepository {

  @Override
  public void save(Greeting greeting) {
    System.out.println("Saved: " + greeting.getMessage());
  }
}
