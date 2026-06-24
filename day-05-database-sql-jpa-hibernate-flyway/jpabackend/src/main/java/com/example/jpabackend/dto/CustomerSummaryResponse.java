package com.example.jpabackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
public class CustomerSummaryResponse {

    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String nik;
    private String email;

    public CustomerSummaryResponse(Long id, String fullName, String nik, String email) {
        this.id = id;
        this.fullName = fullName;
        this.nik = nik;
        this.email = email;
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

}
