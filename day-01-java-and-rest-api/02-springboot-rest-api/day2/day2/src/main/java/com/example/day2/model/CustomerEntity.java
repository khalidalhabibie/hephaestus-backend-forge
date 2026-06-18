package com.example.day2.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customers") 
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}