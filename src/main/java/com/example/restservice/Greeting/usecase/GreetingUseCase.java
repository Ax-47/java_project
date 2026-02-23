package com.example.restservice.Greeting.usecase;

import com.example.restservice.Greeting.domain.*;
import com.example.restservice.Greeting.dto.*;
import org.springframework.stereotype.Service;

@Service
public class GreetingUseCase {

  private final GreetingRepository repository;
  private final GreetingService domainService;

  public GreetingUseCase(GreetingRepository repository) {
    this.repository = repository;
    this.domainService = new GreetingService();
  }

  public GreetingResponse execute(GreetingRequest request) {
    Greeting greeting = domainService.create(request.name());
    repository.save(greeting);
    return new GreetingResponse(greeting.getMessage());
  }
}
