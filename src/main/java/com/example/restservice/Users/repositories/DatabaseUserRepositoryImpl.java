package com.example.restservice.Users.repositories;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.restservice.Users.domain.DatabaseUserRepository;
import com.example.restservice.Users.domain.User;
import com.example.restservice.Users.models.UserModel;

@Repository
public class DatabaseUserRepositoryImpl implements DatabaseUserRepository {

  private final JpaUserRepository jpaUserRepository;

  public DatabaseUserRepositoryImpl(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Override
  public User save(User user) {
    UserModel model = UserModel.fromDomain(user);
    UserModel saved = jpaUserRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public boolean existsByUsername(String name) {
    return jpaUserRepository.existsByUsername(name);
  }

  @Override
  public User findUserByUsername(String username) {
    UserModel user = jpaUserRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return User.rehydrate(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getCredit(),
        user.isAdmin(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}
