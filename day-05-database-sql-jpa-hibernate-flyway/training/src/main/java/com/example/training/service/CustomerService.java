package com.example.training.service;

import org.springframework.stereotype.Service;

import com.example.training.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


}
