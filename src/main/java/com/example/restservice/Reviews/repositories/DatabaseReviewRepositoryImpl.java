package com.example.restservice.Reviews.repositories;

import org.springframework.stereotype.Repository;

import com.example.restservice.Address.repositories.JpaAddressRepository;
import com.example.restservice.Reviews.domain.DatabaseReviewRepository;

@Repository
public class DatabaseReviewRepositoryImpl implements DatabaseReviewRepository {
  private final JpaAddressRepository jpaAddressRepository;

  public DatabaseReviewRepositoryImpl(JpaAddressRepository jpaAddressRepository) {
    this.jpaAddressRepository = jpaAddressRepository;
  }
}
