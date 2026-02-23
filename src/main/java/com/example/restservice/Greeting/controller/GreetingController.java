package com.example.restservice.Greeting.controller;

import com.example.restservice.Greeting.usecase.GreetingUseCase;
import com.example.restservice.Greeting.dto.GreetingRequest;
import com.example.restservice.Greeting.dto.GreetingResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {

  private final GreetingUseCase greetingUseCase;

  public GreetingController(GreetingUseCase useCase) {
    this.greetingUseCase = useCase;
  }

  @PostMapping
  public GreetingResponse greet(@RequestBody GreetingRequest request) {
    return greetingUseCase.execute(request);
  }
}
