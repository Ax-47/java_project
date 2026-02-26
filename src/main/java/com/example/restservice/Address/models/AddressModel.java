package com.example.restservice.Address.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.restservice.Address.domain.Address;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses") // กำหนดชื่อตาราง (ปรับให้ตรงกับฐานข้อมูลจริงของคุณได้)
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String fullName;

    @Column(length = 10, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String addressLine1;

    @Column(length = 100)
    private String addressLine2;

    @Column(length = 100, nullable = false)
    private String subDistrict;

    @Column(length = 100, nullable = false) // ปรับ District เป็นตัวพิมพ์เล็ก
    private String district;

    @Column(length = 100, nullable = false)
    private String province;

    @Column( length = 10, nullable = false)
    private String postalCode;

    @Column( length = 100, nullable = false)
    private String country;

    @Column(length = 100)
    private String label;

    private boolean isDefault;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected AddressModel(){} 
    
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddressLine1() { return addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public String getSubDistrict() { return subDistrict; }
    public String getDistrict() { return district; }
    public String getProvince() { return province; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public String getLabel() { return label; }
    public boolean isDefault() { return isDefault; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Address toDomain() {
        return Address.rehydrate(
                this.id,
                this.userId,
                this.fullName,
                this.phoneNumber,
                this.addressLine1,
                this.addressLine2,
                this.subDistrict,
                this.district,
                this.province,
                this.postalCode,
                this.country,
                this.label,
                this.isDefault,
                this.createdAt,
                this.updatedAt
        );
    }

    public static AddressModel fromDomain(Address address) {
        if (address == null) {
            return null;
        }

        AddressModel entity = new AddressModel();
        
        if (address.getId() != null) {
            entity.id = address.getId();
        }
        
        entity.userId = address.getUserId();
        entity.fullName = address.getFullName();
        entity.phoneNumber = address.getPhoneNumber().value();
        entity.addressLine1 = address.getAddressLine1();
        entity.addressLine2 = address.getAddressLine2();
        entity.subDistrict = address.getSubDistrict();
        entity.district = address.getDistrict();
        entity.province = address.getProvince();
        entity.postalCode = address.getPostalCode();
        entity.country = address.getCountry();
        entity.label = address.getLabel();
        entity.isDefault = address.isDefault();
        

        return entity;
  }
}
