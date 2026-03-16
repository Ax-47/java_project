package com.example.restservice.Reviews.dto;

import jakarta.validation.constraints.*;

public record ReviewRequestDTO(
        @Min(value = 1, message = "rating must be at least 1")
        @Max(value = 5, message = "rating must be at most 5")
        int rating,
    @NotBlank(message = "comment cannot be blank")
        @Size(max = 500, message = "comment must not exceed 500 characters")
        String comment) {}
