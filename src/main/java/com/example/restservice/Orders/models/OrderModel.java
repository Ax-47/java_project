package com.example.restservice.Orders.models;

import com.example.restservice.Address.domain.PhoneNumber;
import com.example.restservice.Orders.domain.Order;
import com.example.restservice.Orders.domain.OrderAddress;
import com.example.restservice.Orders.domain.OrderStatus;
import com.example.restservice.Orders.domain.ProductSnapshot;
import com.example.restservice.Products.domain.Price;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false, length = 255)
    private String productName;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal productPrice;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String addressLine1;

    @Column(length = 100)
    private String addressLine2;

    @Column(length = 100)
    private String subDistrict;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected OrderModel() {
    }

    public Order toDomain() {
        ProductSnapshot snapshot = new ProductSnapshot(
                this.productId,
                this.productName,
                Price.of(this.productPrice));

        OrderAddress address = new OrderAddress(
                this.fullName,
                PhoneNumber.of(this.phoneNumber),
                this.addressLine1,
                this.addressLine2,
                this.subDistrict,
                this.district,
                this.province,
                this.postalCode,
                this.country);

        return Order.rehydrate(
                this.id,
                this.userId,
                snapshot,
                address,
                this.status,
                this.createdAt,
                this.updatedAt);
    }

    public static OrderModel fromDomain(Order order) {
        if (order == null)
            return null;

        OrderModel model = new OrderModel();

        if (order.getId() != null) {
            model.id = order.getId();
        }

        model.userId = order.getUserId();

        ProductSnapshot snap = order.getProduct();
        model.productId = snap.getProductId();
        model.productName = snap.getProductName();
        model.productPrice = snap.getPrice().getValue();

        OrderAddress addr = order.getShippingAddress();
        model.fullName = addr.getFullName();
        model.phoneNumber = addr.getPhoneNumber().value();
        model.addressLine1 = addr.getAddressLine1();
        model.addressLine2 = addr.getAddressLine2();
        model.subDistrict = addr.getSubDistrict();
        model.district = addr.getDistrict();
        model.province = addr.getProvince();
        model.postalCode = addr.getPostalCode();
        model.country = addr.getCountry();

        model.status = order.getStatus();

        return model;
    }
}
