package com.example.jpabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_nik", columnList = "nik"),
        @Index(name = "idx_customer_email", columnList = "email")
})
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String nik;
    private String email;
    private String phoneNumber;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public CustomerEntity(Long id, String fullName, String nik, String email, String phoneNumber,
            ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.nik = nik;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CustomerEntity() {

    }

    // relasi ke loan
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<LoanApplicationEntity> loans;

    public List<LoanApplicationEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanApplicationEntity> loans) {
        this.loans = loans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}