package com.andyana.exerciseday02.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.andyana.exerciseday02.dto.CreateCustomerRequest;
import com.andyana.exerciseday02.dto.CustomerResponse;
import com.andyana.exerciseday02.exception.CustomerNotFoundException;
import com.andyana.exerciseday02.model.Customer;

@Service //artinya bahwa kelas ini adalah sebuah service yang akan digunakan untuk menangani logika bisnis terkait dengan customer, seperti membuat, mengambil, memperbarui, dan menghapus data customer. Dengan menggunakan @Service, Spring akan secara otomatis mendeteksi kelas ini sebagai komponen yang dapat di-inject ke dalam controller atau komponen lain yang membutuhkannya.
public class CustomerService {
    private Map<Long, Customer> customerMap = new HashMap<>();
    private Long idCounter = 1L;
    // fungsi dari createCustomer dibawah ini adalah untuk membuat customer baru berdasarkan data yang diterima dari request, menyimpan customer tersebut dalam map, dan mengembalikan response yang berisi data customer yang baru dibuat
    public CustomerResponse createCustomer(CreateCustomerRequest request) { 
        Long newId = idCounter++;
        Customer customer = new Customer(newId, request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerMap.put(newId, customer);
        return convertToResponse(customer);
    }
    
    // fungsi dari getCustomerById dibawah ini adalah untuk mengambil data customer berdasarkan ID yang diberikan. Jika customer dengan ID tersebut ditemukan, maka data customer akan dikonversi menjadi response dan dikembalikan. Namun, jika customer tidak ditemukan, maka akan dilemparkan exception CustomerNotFoundException dengan pesan yang sesuai.
    public CustomerResponse getCustomerById(Long id) {
    return Optional.ofNullable(customerMap.get(id))
            .map(this::convertToResponse)
            .orElseThrow(() ->
                    new CustomerNotFoundException(
                            "Customer dengan ID " + id + " tidak ditemukan"));
}
    
    // fungsi dari getAllCustomers dibawah ini adalah untuk mengambil semua data customer yang tersimpan dalam map dan mengembalikan list response yang berisi data semua customer.
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerMap.values()) {
            responses.add(convertToResponse(customer));
        }
        return responses;
    }
    // fungsi dari convertToResponse dibawah ini adalah untuk mengkonversi objek Customer menjadi objek CustomerResponse yang akan digunakan sebagai response dalam API. Fungsi ini mengambil data dari objek Customer dan membuat objek CustomerResponse dengan data yang sesuai.
    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            customer.getPhoneNumber()
        );
    }

    // fungsi dari updateCustomer dibawah ini adalah untuk memperbarui data customer yang sudah ada berdasarkan ID yang diberikan. Jika customer dengan ID tersebut ditemukan, maka data customer akan diperbarui dengan data yang diterima dari request, dan response yang berisi data customer yang sudah diperbarui akan dikembalikan. Namun, jika customer tidak ditemukan, maka akan dilemparkan exception CustomerNotFoundException dengan pesan yang sesuai.
    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        if (!customerMap.containsKey(id)) {
            throw new CustomerNotFoundException("Customer dengan ID " + id + " tidak ditemukan");
        }
        // Ambil customer yang sudah ada dari map berdasarkan ID
        Customer existingCustomer = customerMap.get(id);
        existingCustomer.setFullName(request.getFullName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        return convertToResponse(existingCustomer);

    }

    // fungsi dari deleteCustomer dibawah ini adalah untuk menghapus data customer berdasarkan ID yang diberikan. Jika customer dengan ID tersebut ditemukan, maka data customer akan dihapus dari map. Namun, jika customer tidak ditemukan, maka akan dilemparkan exception CustomerNotFoundException dengan pesan yang sesuai.
    public void deleteCustomer(Long id) {
    if (!customerMap.containsKey(id)) {
        throw new CustomerNotFoundException("Customer dengan ID " + id + " tidak ditemukan");
    }
    customerMap.remove(id);
}

}
