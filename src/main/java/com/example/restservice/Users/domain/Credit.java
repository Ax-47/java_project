package com.example.restservice.Users.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.example.restservice.Users.exceptions.*;

public final class Credit {

  private static final int SCALE = 2;

  private final BigDecimal value;

  private Credit(BigDecimal value) {
    Objects.requireNonNull(value, "Credit cannot be null");

    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new CreditCannotBeNegativeException();
    }

    this.value = normalize(value);
  }

  public static Credit zero() {
    return new Credit(BigDecimal.ZERO);
  }

  public static Credit of(BigDecimal amount) {
    return new Credit(amount);
  }

  public Credit add(Credit amount) {
    return new Credit(this.value.add(amount.value));
  }

  public Credit subtract(Credit amount) {
    Objects.requireNonNull(amount);

    BigDecimal result = this.value.subtract(amount.value);

    if (result.compareTo(BigDecimal.ZERO) < 0) {
      throw new InsufficientCreditException();
    }

    return new Credit(result);
  }

  public BigDecimal getValue() {
    return value;
  }

  private BigDecimal normalize(BigDecimal value) {
    return value.setScale(SCALE, RoundingMode.HALF_UP);
  }
}
