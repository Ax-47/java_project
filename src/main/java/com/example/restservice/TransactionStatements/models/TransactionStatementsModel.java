package com.example.restservice.TransactionStatements.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.restservice.TransactionStatements.domain.TransactionStatements;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsMethod;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsStatus;
import com.example.restservice.TransactionStatements.domain.TransactionStatementsType;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_statements")
public class TransactionStatementsModel {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID userId;

  @Column(nullable = true)
  private UUID orderId;

  @Column(nullable = false)
  private Double amount;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionStatementsType type;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionStatementsMethod method;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionStatementsStatus status;

  @Column(nullable = false, length = 255)
  private String referenceId;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  protected TransactionStatementsModel() {}

  public TransactionStatements toDomain() {
    return new TransactionStatements(
        this.id,
        this.userId,
        this.orderId,
        this.amount,
        this.type,
        this.method,
        this.status,
        this.referenceId,
        this.createdAt);
  }

  public static TransactionStatementsModel fromDomain(TransactionStatements domain) {
    if (domain == null) return null;

    TransactionStatementsModel model = new TransactionStatementsModel();

    if (domain.getId() != null) {
      model.id = domain.getId();
    }

    model.userId = domain.getUserId();
    model.orderId = domain.getOrderId();
    model.amount = domain.getAmount();
    model.type = domain.getType();
    model.method = domain.getMethod();
    model.status = domain.getStatus();
    model.referenceId = domain.getReferenceId();

    if (domain.getCreatedAt() != null) {
      model.createdAt = domain.getCreatedAt();
    }

    return model;
  }
}
