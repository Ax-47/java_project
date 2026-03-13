package com.example.restservice.TransactionStatements.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsRequestDTO;
import com.example.restservice.TransactionStatements.dto.CreateTransactionStatementsResponseDTO;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserRequestDTO;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserResponseDTO;
import com.example.restservice.TransactionStatements.usecases.CreateTransactionStatementsUsecase;
import com.example.restservice.TransactionStatements.usecases.GetTransactionStatementsByUserUsecase;
import com.example.restservice.common.PageQuery;
import com.example.restservice.common.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions") // กำหนด Path หลักของ Controller นี้
public class TransactionStatementsController {

  private final CreateTransactionStatementsUsecase createUsecase;
  private final GetTransactionStatementsByUserUsecase getByUserUsecase;

  public TransactionStatementsController(
      CreateTransactionStatementsUsecase createUsecase,
      GetTransactionStatementsByUserUsecase getByUserUsecase) {
    this.createUsecase = createUsecase;
    this.getByUserUsecase = getByUserUsecase;
  }

  @PostMapping
  public ResponseEntity<CreateTransactionStatementsResponseDTO> create(
      @Valid @RequestBody CreateTransactionStatementsRequestDTO request) {

    CreateTransactionStatementsResponseDTO response = createUsecase.execute(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<PageResponse<GetTransactionStatementsByUserResponseDTO>> getByUserId(
      @PathVariable("userId") UUID userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "false") boolean asc) {

    PageQuery query = new PageQuery(page, size, sortBy, asc);

    GetTransactionStatementsByUserRequestDTO request =
        new GetTransactionStatementsByUserRequestDTO(userId, query);

    return ResponseEntity.ok(PageResponse.from(this.getByUserUsecase.execute(request)));
  }
}
