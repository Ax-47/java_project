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

  public Price add(Price amount) {
    Objects.requireNonNull(amount);
    return new Price(this.value.add(amount.value));
  }

  public Price subtract(Price amount) {
    Objects.requireNonNull(amount);

    BigDecimal result = this.value.subtract(amount.value);

    if (result.compareTo(BigDecimal.ZERO) < 0) {
      throw new InsufficientPriceException();
    }

    return new Price(result);
  }

  public BigDecimal getValue() {
    return value;
  }

  private BigDecimal normalize(BigDecimal value) {
    return value.setScale(SCALE, RoundingMode.HALF_UP);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Price)) return false;
    Price price = (Price) o;
    return value.compareTo(price.value) == 0;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
