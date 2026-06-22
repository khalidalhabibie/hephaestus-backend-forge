package com.example.jpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpabackend.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNik(String nik);

    Optional<CustomerEntity> findByEmail(String email);

    boolean existsByNik(String nik);

    boolean existsByEmail(String email);

    List<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT c FROM CustomerEntity c WHERE c.isDeleted = false")
    List<CustomerEntity> findAllActive();

    @Query("""
                SELECT c FROM CustomerEntity c
                WHERE c.isDeleted = false
                AND LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    List<CustomerEntity> searchActive(@Param("name") String name);

}
