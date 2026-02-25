package com.example.restservice.Products.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.example.restservice.Products.exceptions.*;

public final class Price {

  private static final int SCALE = 2;

  private final BigDecimal value;

  private Price(BigDecimal value) {
    Objects.requireNonNull(value, "Price cannot be null");

    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new PriceCannotBeNegativeException();
    }

    this.value = normalize(value);
  }

  public static Price zero() {
    return new Price(BigDecimal.ZERO);
  }

  public static Price of(BigDecimal amount) {
    return new Price(amount);
  }

  public Price add(BigDecimal amount) {
    validatePositive(amount);
    return new Price(this.value.add(amount));
  }

  public Price subtract(BigDecimal amount) {
    validatePositive(amount);

    BigDecimal result = this.value.subtract(amount);

    if (result.compareTo(BigDecimal.ZERO) < 0) {
      throw new InsufficientPriceException();
    }

    return new Price(result);
  }

  public BigDecimal getValue() {
    return value;
  }

  private void validatePositive(BigDecimal amount) {
    Objects.requireNonNull(amount);

    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidPriceAmountException();
    }
  }

  private BigDecimal normalize(BigDecimal value) {
    return value.setScale(SCALE, RoundingMode.HALF_UP);
  }
}
