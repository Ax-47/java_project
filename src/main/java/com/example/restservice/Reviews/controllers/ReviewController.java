package com.example.restservice.Reviews.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

  public ReviewController() {
  }

  // GET /api/review
  // GET /api/review/products/{productId}
  // GET /api/review/{reviewId}
  // GET /api/review/users/{userId}
  // GET /api/review/products/{productId}/users/{userId}
  // PUT /api/review/{reviewId}
  // DELETE /api/review/{reviewId}
  // POST /api/review/{reviewId}/images
  // DELETE /api/products/{reviewId}/images/{imageId}
}
