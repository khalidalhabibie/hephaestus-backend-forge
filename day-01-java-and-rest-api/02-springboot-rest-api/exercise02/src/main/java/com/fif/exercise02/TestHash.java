package com.fif.exercise02;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("admin123"));
        System.out.println(encoder.encode("staff123"));
        System.out.println(encoder.encode("approver123"));
        System.out.println(encoder.encode("manager123"));
    }
}
