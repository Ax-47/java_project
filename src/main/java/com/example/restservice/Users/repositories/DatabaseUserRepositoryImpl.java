package com.example.restservice.Users.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import com.example.restservice.Users.domain.*;
import com.example.restservice.Users.exceptions.*;
import com.example.restservice.Users.models.*;

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
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    return User.rehydrate(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getCredit(),
        user.isAdmin(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }

  @Override
  public Page<User> findAllUsers(PageQuery query) {

    Sort sort = query.ascending()
        ? Sort.by(query.sortBy()).ascending()
        : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(
        query.page(),
        query.size(),
        sort);

    org.springframework.data.domain.Page<UserModel> page = jpaUserRepository.findAll(pageable);
    List<User> users = page.getContent()
        .stream()
        .map(user -> User.rehydrate(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getCredit(),
            user.isAdmin(),
            user.getCreatedAt(),
            user.getUpdatedAt()))
        .toList();

    return new Page<>(
        users,
        page.getTotalElements(),
        page.getTotalPages(),
        page.getNumber(),
        page.getSize());
  }
}
