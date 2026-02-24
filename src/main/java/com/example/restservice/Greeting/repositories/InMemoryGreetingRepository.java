package com.example.restservice.Greeting.repositories;

import com.example.restservice.Greeting.domain.Greeting;
import com.example.restservice.Greeting.domain.GreetingRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGreetingRepository implements GreetingRepository {

  @Override
  public void save(Greeting greeting) {
    System.out.println("Saved: " + greeting.getMessage());
  }
}
