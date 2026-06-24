package com.fif.loanapplication.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fif.loanapplication.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsByFullName(String fullName);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNik(String nik);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailIgnoreCaseAndUidNot(String email, UUID uid);

    boolean existsByNikAndUidNot(String nik, UUID uid);

    boolean existsByPhoneNumberAndUidNot(String phoneNumber, UUID uid);

}
