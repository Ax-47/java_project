package com.example.restservice.Reviews.domain;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseReviewRepository {
        Review save(Review review);
}
