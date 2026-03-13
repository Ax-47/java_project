package com.example.restservice.TransactionStatements.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.restservice.TransactionStatements.domain.DatabaseTransactionStatementsRepository;
import com.example.restservice.TransactionStatements.domain.TransactionStatements;
import com.example.restservice.TransactionStatements.models.TransactionStatementsModel;
import com.example.restservice.common.Page;
import com.example.restservice.common.PageQuery;

@Repository
public class DatabaseTransactionStatementsRepositoryImpl
    implements DatabaseTransactionStatementsRepository {
  private final JpaTransactionRepository jpaTransactionRepository;

  DatabaseTransactionStatementsRepositoryImpl(JpaTransactionRepository jpaTransactionRepository) {
    this.jpaTransactionRepository = jpaTransactionRepository;
  }

  @Override
  public TransactionStatements save(TransactionStatements transactionStatements) {
    TransactionStatementsModel model = TransactionStatementsModel.fromDomain(transactionStatements);
    TransactionStatementsModel saved = jpaTransactionRepository.save(model);
    return saved.toDomain();
  }

  @Override
  public Page<TransactionStatements> findByUserId(UUID userId, PageQuery query) {

    Sort sort =
        query.ascending()
            ? Sort.by(query.sortBy()).ascending()
            : Sort.by(query.sortBy()).descending();

    Pageable pageable = PageRequest.of(query.page(), query.size(), sort);

    org.springframework.data.domain.Page<TransactionStatementsModel> page =
        jpaTransactionRepository.findByUserId(userId, pageable);

    List<TransactionStatements> statements =
        page.getContent().stream().map(TransactionStatementsModel::toDomain).toList();

    return new Page<>(
        statements,
        page.getTotalElements(),
        page.getTotalPages(),
        page.getNumber(),
        page.getSize());
  }
}
