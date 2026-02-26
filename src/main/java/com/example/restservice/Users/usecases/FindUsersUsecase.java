package com.example.restservice.Users.usecases;

import com.example.restservice.Users.domain.*;
import com.example.restservice.Users.dto.FindUserResponseDTO;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FindUsersUsecase {

  private final DatabaseUserRepository databaseUserRepository;

  public FindUsersUsecase(DatabaseUserRepository databaseUserRepository) {
    this.databaseUserRepository = databaseUserRepository;
  }

  public Page<FindUserResponseDTO> execute(PageQuery query) {
    Page<User> usersPage = databaseUserRepository.findAllUsers(query);
    List<FindUserResponseDTO> content = usersPage.content()
        .stream()
        .map(FindUserResponseDTO::from)
        .toList();
    return new Page<>(
        content,
        usersPage.totalElements(),
        usersPage.totalPages(),
        usersPage.page(),
        usersPage.size());
  }
}
