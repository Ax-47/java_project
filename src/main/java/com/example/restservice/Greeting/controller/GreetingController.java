package com.example.restservice.Greeting.controller;

import org.springframework.web.bind.annotation.*;

import com.example.restservice.Greeting.dto.GreetingRequest;
import com.example.restservice.Greeting.dto.GreetingResponse;
import com.example.restservice.Greeting.usecase.GreetingUseCase;

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
