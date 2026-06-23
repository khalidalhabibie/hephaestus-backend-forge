package com.fif.loanapplication.entity;

import java.util.ArrayList;
import java.util.List;

import com.fif.loanapplication.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder

// Table in Database
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_nik", columnNames = "nik"),
        @UniqueConstraint(name = "uk_customer_email", columnNames = "email")
})

public class CustomerEntity extends BaseEntity {

    @NotBlank(message = "Full name wajib diisi")
    @Size(min = 3, max = 100, message = "Full name harus 3 sampai 100 karakter")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "NIK wajib diisi")
    @Pattern(regexp = "^[0-9]{16}$", message = "NIK harus 16 digit angka")
    @Column(name = "nik", nullable = false, unique = true, length = 16)
    private String nik;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Email tidak valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone number wajib diisi")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number harus 10 sampai 15 digit angka")
    @Column(name = "phone_number", nullable = false, length = 15, unique = true)
    private String phoneNumber;

    // Relationship
    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<LoanApplicationEntity> loanApplications = new ArrayList<>();

}
