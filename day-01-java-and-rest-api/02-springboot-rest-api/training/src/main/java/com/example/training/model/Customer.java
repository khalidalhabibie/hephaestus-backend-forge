package com.example.training.model;

public class Customer {
    // nah disini kita buat basis Customer ini bakal punya apa aja var nya. 
    // misalnya dia punya id, nama lengkap, notelp, dan email. nah nanti kita buat constructor, getter dan setter nya juga.
    // kenapa dia private?
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    // ini constructor kosong (dia itu kayak method tapi tanpa parameter tanpa return), 
    

    public Customer() {
    }

    // ini constructor yang parameterized (dia itu kayak method tapi dengan parameter dan tanpa return),
    // nah ini gunanya supaya gw bisa langsung buat object Customer dengan ngasih nilai ke field nya sekaligus, 
    // jadi gak perlu buat object dulu baru set satu-satu field nya.
    public Customer(Long id, String fullName, String email, String phoneNumber) {
        // ini fungsi this nya adalah untuk membedakan antara parameter yang namanya sama dengan field nya, 
        // jadi this.id itu merujuk ke field id yang ada di class ini, 
        // sedangkan id itu merujuk ke parameter yang dikirim ke constructor ini(yg didalam parameter itu lho).
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    // nah ini getter untuk Id, getter itu fungsinya untuk mengambil nilai dari field id,
    // jadi misalnya kita punya object Customer yang namanya customer, 
    // terus kita mau tau id dari customer itu berapa, kita bisa panggil customer.getId() 
    // nanti dia bakal ngembaliin nilai id nya.
    public Long getId() {
        return id;
    }
    // ini namanya setter untuk Id, 
    // setter itu fungsinya untuk mengubah atau memberikan nilai ke field id,
    // jadi misalnya kita punya object Customer yang namanya customer,
    // terus kita mau set id nya jadi 123, kita bisa panggil customer.setId(123)
    // nanti dia bakal ngubah nilai id nya jadi 123.
    public void setId(Long id) {
        this.id = id;
    }

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
