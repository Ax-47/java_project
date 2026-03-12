package com.example.restservice.Reviews.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.Reviews.models.ReviewModel;

public interface JpaReviewRepository extends JpaRepository<ReviewModel, UUID> {}
