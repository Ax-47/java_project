package com.example.restservice.Orders.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.domain.OrderAddress;
import com.example.restservice.Orders.domain.OrderStatus;
import com.example.restservice.Orders.domain.ProductSnapshot;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderResponseDTO(
    UUID id,
    UUID userId,
    UUID productId,
    String productName,
    BigDecimal productPrice,
    String fullName,
    String phoneNumber,
    String addressLine1,
    String addressLine2,
    String subDistrict,
    String district,
    String province,
    String postalCode,
    String country,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public static OrderResponseDTO from(Order order) {
    if (order == null) return null;

    ProductSnapshot product = order.getProduct();
    OrderAddress address = order.getShippingAddress();

    return new OrderResponseDTO(
        order.getId(),
        order.getUserId(),

        // ข้อมูลสินค้า
        product.getProductId(),
        product.getProductName(),
        product.getPrice().getValue(), // 💡 ดึง BigDecimal ออกมาจาก Price

        // ข้อมูลที่อยู่จัดส่ง
        address.getFullName(),
        address.getPhoneNumber().value(), // 💡 ดึง String ออกมาจาก PhoneNumber
        address.getAddressLine1(),
        address.getAddressLine2(),
        address.getSubDistrict(),
        address.getDistrict(),
        address.getProvince(),
        address.getPostalCode(),
        address.getCountry(),

        // สถานะและเวลา
        order.getStatus(),
        order.getCreatedAt(),
        order.getUpdatedAt());
  }
}
