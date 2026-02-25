package com.example.restservice.Address.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public final class PhoneNumber {

  private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9+\\-]{8,20}$");

  private final String value;

  private PhoneNumber(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Phone number is required");
    }

    if (!PHONE_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid phone number format");
    }

    this.value = value;
  }

  public static PhoneNumber of(String value) {
    return new PhoneNumber(value);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof PhoneNumber))
      return false;
    PhoneNumber that = (PhoneNumber) o;
    return value.equals(that.value);
  }

  public String masked() {
    return value.substring(0, 3) + "****" + value.substring(value.length() - 2);
  }

  @Override
  public String toString() {
    return this.masked();
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
