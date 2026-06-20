package com.example.training.entity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class CustomerEntity {
    public class Customer {
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        private UUID id;

        @Column(nullable = false)
        private String full_name;

        @Column(nullable = false, unique = true, length = 16)
        @Size(min = 16, max = 16)
        private String nik;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false, length = 15)
        private String phone_number;

        @Column(nullable = false)
        private OffsetDateTime created_at;

        @Column(nullable = false)
        private OffsetDateTime updated_at;

        @OneToMany(mappedBy = "customer")
        private List<LoanApplicationEntity> loanApplications;
    }
}

// id
// customer_id
// loan_amount
// tenor_month
// purpose
// status
// created_at
// updated_at
