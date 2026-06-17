package com.example.training.service;

import com.example.training.dto.*;
import com.example.training.model.*;

import java.time.ZonedDateTime;
import java.util.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import com.example.training.exception.CustomerNotFoundException;

// Class ini adalah Service layer yang berisi logika bisnis untuk mengelola data Customer.
// Data disimpan sementara di dalam Map (bukan database), jadi data akan hilang kalau aplikasi di-restart
@Service
public class CustomerService {

    // customerStorage adalah "database sementara" berbentuk Map, key-nya adalah ID customer (Long),
    // value-nya adalah objek Customer
    private Map<Long, Customer> customerStorage = new HashMap<>();

    // sequence digunakan sebagai auto-increment ID untuk setiap customer baru yang dibuat
    private Long sequence = 1L;

    // Method ini untuk mengambil semua data Customer yang ada di customerStorage,
    // lalu dikonversi satu per satu menjadi CustomerResponse dan dikumpulkan dalam List
    public PageResponse<CustomerResponse> getCustomers(String email, int page, int size) {
    List<CustomerResponse> all = new ArrayList<>();
    for (Customer customer : customerStorage.values()) {
        if (email != null && !customer.getEmail().equalsIgnoreCase(email)) {
            continue;
        }
        CustomerResponse cr = new CustomerResponse();
        cr.setId(customer.getId());
        cr.setFullName(customer.getFullName());
        cr.setEmail(customer.getEmail());
        cr.setPhoneNumber(customer.getPhoneNumber());
        cr.setCreatedAt(customer.getCreatedAt());
        cr.setUpdatedAt(customer.getUpdatedAt());
        all.add(cr);
    }

    // manual pagination
    int fromIndex = page * size;
    int toIndex = Math.min(fromIndex + size, all.size());
    List<CustomerResponse> paged = fromIndex >= all.size() ? new ArrayList<>() : all.subList(fromIndex, toIndex);

    return new PageResponse<>(paged, page, size, all.size());
    // note: total_pages dihitung dari all.size(), bukan paged
}

    // Method ini untuk membuat Customer baru berdasarkan data yang dikirim dari request.
    // Customer disimpan ke customerStorage, lalu dikembalikan sebagai CustomerResponse.
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest entity) {
        ZonedDateTime now = ZonedDateTime.now();
        Customer newCustomer = new Customer(sequence, entity.getFullName(), entity.getEmail(), entity.getPhoneNumber(), now, now);
        
        customerStorage.put(sequence, newCustomer);
        sequence++;

        CustomerResponse response = new CustomerResponse();

        response.setId(sequence); //
        response.setFullName(newCustomer.getFullName());
        response.setEmail(newCustomer.getEmail());
        response.setPhoneNumber(newCustomer.getPhoneNumber());
        response.setCreatedAt(newCustomer.getCreatedAt());
        response.setUpdatedAt(newCustomer.getUpdatedAt());

        return response;
    }

    // Method ini untuk mengambil satu data Customer berdasarkan ID.
    // Kalau customer dengan ID tersebut tidak ditemukan, block if-nya kosong sehingga tidak ada penanganan error,
    // dan kode akan lanjut jalan dan menyebabkan NullPointerException saat mengakses kastomer yang null
    public CustomerResponse getCustomerById(@PathVariable Long id) throws CustomerNotFoundException {
        Customer kastomer = customerStorage.get(id);

        if(kastomer == null) {
            throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " +id, null);
        }

        CustomerResponse response = new CustomerResponse();
        response.setId(kastomer.getId());
        response.setFullName(kastomer.getFullName());
        response.setEmail(kastomer.getEmail());
        response.setPhoneNumber(kastomer.getPhoneNumber());
        response.setCreatedAt(kastomer.getCreatedAt());
        response.setUpdatedAt(kastomer.getUpdatedAt());
        return response;
    }

    
    public CustomerResponse deleteCustomer(long id) throws CustomerNotFoundException {
        Customer kastomer = customerStorage.get(id);
        if (kastomer == null) {
            throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + id, null);
            // throw new CustomerNotFoundException(String.format("Customer not found with id: %s", id), null, null);
        }
        
        customerStorage.remove(id);
        CustomerResponse response = new CustomerResponse(kastomer.getId(), kastomer.getFullName(), kastomer.getEmail(), kastomer.getPhoneNumber(), kastomer.getCreatedAt(), kastomer.getUpdatedAt());
        return response;
    }
    public CustomerResponse updateCustomer(@PathVariable Long id, CreateCustomerRequest entity) throws CustomerNotFoundException {
        Customer kastomer = customerStorage.get(id);

        if(kastomer == null) {
            throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + id, null);
        }
        kastomer.setFullName(entity.getFullName());
        kastomer.setEmail(entity.getEmail());
        kastomer.setPhoneNumber(entity.getPhoneNumber());
        kastomer.setCreatedAt(ZonedDateTime.now());
        kastomer.setUpdatedAt(ZonedDateTime.now());
        customerStorage.put(id, kastomer);

        CustomerResponse response = new CustomerResponse(kastomer.getId(), kastomer.getFullName(), kastomer.getEmail(), kastomer.getPhoneNumber(), kastomer.getCreatedAt(), kastomer.getUpdatedAt());

        return response;
        
    }

    public CustomerResponse patchCustomer(@PathVariable Long id, PatchCustomerRequest broski) {
        Customer kastomer = customerStorage.get(id);

        if(kastomer == null) {
            throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND", "Customer dengan id " + id + "tidak ditemukan", null);

        }
        if(broski.getFullName() != null){
            kastomer.setFullName(broski.getFullName());
        }
        if(broski.getEmail() != null){
            kastomer.setEmail(broski.getEmail());
        }
        if(broski.getPhoneNumber() != null){
            kastomer.setPhoneNumber(broski.getPhoneNumber());
        }
        if(broski.getCreatedAt() != null){
            kastomer.setCreatedAt(broski.getCreatedAt());
        }
        if(broski.getUpdatedAt() != null){
            kastomer.setUpdatedAt(broski.getUpdatedAt());
        }

        customerStorage.put(id, kastomer);

        CustomerResponse response = new CustomerResponse(kastomer.getId(), kastomer.getFullName(), kastomer.getEmail(), kastomer.getPhoneNumber(), kastomer.getCreatedAt(), kastomer.getUpdatedAt());

        return response;
    }

    // Method ini untuk mengembalikan data Customer default (hardcoded),
    // biasanya dipakai untuk testing atau sebagai fallback data
    public CustomerResponse getDefaultCustomer() {
        return buatResponKustomer(1L, "Rico Simanjuntak", "rjuntak@gmail.com", "081111");
    }

    // Method private helper ini untuk membuat objek CustomerResponse dari parameter yang diberikan,
    // tujuannya supaya tidak perlu menulis ulang kode yang sama setiap kali mau bikin CustomerResponse
    private CustomerResponse buatResponKustomer(Long id, String fullName, String email, String phoneNumber) {
        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setFullName(fullName);
        response.setEmail(email);
        response.setPhoneNumber(phoneNumber);
        // response.setCreatedAt(createdAt);
        // response.setUpdatedAt(updatedAt);

        return response;
    }

}