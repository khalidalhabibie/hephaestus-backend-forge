package com.example.training.dto;

// ini adalah constructor yang akan digunakan untuk menerima data 
// dari client ketika ingin membuat customer baru, 
// jadi nanti di controller kita akan menerima object ini 
// sebagai parameter untuk create customer.
public class CreateCustomerRequest {
    // disini kita declare field apa aja yg dibutuhin
    // untuk buat customer baru.
    // nah disini cthnya kita butuh nama lengkap, email, dan nomor telepon 
    // untuk buat customer baru.
    private String fullName;
    private String email;
    private String phoneNumber;

    // nah ini getter and setter 
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    
}
