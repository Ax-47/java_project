package com.example.restservice.TransactionStatements.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatement;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserRequestDTO;
import com.example.restservice.TransactionStatements.dto.GetTransactionStatementsByUserResponseDTO;
import com.example.restservice.common.Page;

@Service
public class GetTransactionStatementsByUserUsecase {
  private final DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository;

  GetTransactionStatementsByUserUsecase(
      DatabaseTransactionStatementsRepository databaseTransactionStatementsRepository) {
    this.databaseTransactionStatementsRepository = databaseTransactionStatementsRepository;
  }

  public Page<GetTransactionStatementsByUserResponseDTO> execute(
      GetTransactionStatementsByUserRequestDTO request) {
    Page<TransactionStatement> transactionStatementsPage =
        databaseTransactionStatementsRepository.findByUserId(request.userId(), request.pageQuery());
    List<GetTransactionStatementsByUserResponseDTO> content =
        transactionStatementsPage.content().stream()
            .map(GetTransactionStatementsByUserResponseDTO::from)
            .toList();
    return new Page<>(
        content,
        transactionStatementsPage.totalElements(),
        transactionStatementsPage.totalPages(),
        transactionStatementsPage.page(),
        transactionStatementsPage.size());
  }
}
