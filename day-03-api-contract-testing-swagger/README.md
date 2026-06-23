# Day 3 - API Contract, API Testing & Swagger

## Goal

Peserta belajar membuat API contract sederhana, melakukan API testing, dan menggunakan Swagger UI untuk membaca serta mencoba endpoint REST API.

## Why This Day Is Important

API adalah kontrak antara backend dan client. Client perlu tahu endpoint, HTTP method, request body, response body, status code, dan error response sebelum bisa memakai API dengan benar.

API juga perlu diuji agar behavior yang dibuat backend sesuai dengan contract. Jika contract mengatakan endpoint create customer mengembalikan `201 Created`, maka hasil testing juga harus menunjukkan status tersebut.

Swagger membantu dokumentasi API menjadi lebih mudah dibaca. Swagger UI bisa digunakan untuk mencoba API langsung dari browser tanpa membuat request manual dari awal.

Dokumentasi API yang baik membantu backend, frontend, mobile, QA, dan mentor bekerja lebih rapi karena semua pihak membaca sumber informasi yang sama.

## Learning Objectives

Setelah menyelesaikan Day 3, peserta diharapkan mampu:

- Memahami API contract.
- Memahami isi API contract.
- Memahami DTO request dan DTO response.
- Memahami POST, GET, PUT, PATCH.
- Membuat project Spring Boot dari Spring Initializr.
- Menambahkan dependency Spring Web dan Validation.
- Menambahkan dependency Swagger menggunakan `springdoc-openapi-ui`.
- Membuat Customer REST API sederhana.
- Melakukan API testing dengan Postman.
- Melakukan API testing dengan Swagger UI.
- Membaca OpenAPI JSON dari `/v3/api-docs`.
- Memahami perbedaan API contract dan API documentation.

## Tools

- Java 8
- Maven
- Spring Boot 2.7.x
- Spring Initializr
- IntelliJ IDEA or VS Code
- Postman
- Browser
- Swagger UI

## Expected Output

Di akhir Day 3, peserta diharapkan memiliki:

- Project Spring Boot bisa berjalan di `localhost:8080`.
- Customer REST API memiliki endpoint POST, GET, PUT, PATCH.
- API contract ditulis dalam markdown.
- API bisa dites menggunakan Postman.
- Swagger UI bisa dibuka dari browser.
- Endpoint bisa dicoba dari Swagger UI.
- OpenAPI JSON bisa dibuka dari `/v3/api-docs`.

## Not Covered Today

Day 3 belum membahas:

- Database.
- Authentication.
- Authorization.
- JWT.
- Role based access.
- Docker.
- Deployment.
- Advanced OpenAPI customization.

# Customer API Contract

## Base URL

http://localhost:8080/api/v1/customers

## Table of Contents

1. [Create Customer](#1-create-customer)
2. [Get All Customers](#2-get-all-customers)
3. [Get Customer By ID](#3-get-customer-by-id)
4. [Update Customer (Full Update)](#4-update-customer-full-update)
5. [Patch Customer (Partial Update)](#5-patch-customer-partial-update)
6. [Delete Customer](#6-delete-customer)

---

## 1. Create Customer

### Method

POST
/api/v1/customers

### Description

Membuat customer baru dengan validasi lengkap. Semua field harus diisi dan valid sesuai dengan aturan validasi yang berlaku.

### Request Headers

Content-Type: application/json

### Request Body

```json
{
  "fullName": "Budi Santoso",
  "email": "budi@gmail.com",
  "phoneNumber": "081234567890"
}
```

fullName -> String -> Required -> Min 3, Max 100 chars -> Nama Lengkap Customer
email -> String -> Required -> Valid email format -> Email Customer
phoneNumber -> String -> Required -> 9-12 digits -> Nomor telepon customer

Success Response
Status Code: 201 Created
{
"success": true,
"message": "Customer berhasil ditambahkan",
"data": {
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:30:45"
},
"timestamp": 1718600445000
}

Error Response
Status Code: 400 Bad Request (Validation Error)
{
"success": false,
"message": "Validation failed",
"error": "Validation error",
"details": [
"fullName: Nama lengkap tidak boleh kosong",
"email: Format email tidak valid",
"phoneNumber: Nomor telepon harus dimulai dengan +62 atau 0, diikuti 9-12 digit"
],
"timestamp": 1718600445000
}
Status Code: 500 Internal Server Error
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600445000
}

Example cURL Request
curl -X POST http://localhost:8080/api/v1/customers \
 -H "Content-Type: application/json" \
 -d '{
"fullName": "Budi Santoso",
"email": "budi@gmail.com",
"phoneNumber": "081234567890"
}'

## 2. Get All Customers

Method : Get
URL : /api/v1/customers
Description : Mendapatkan daftar semua customer atau melakukan pencarian berdasarkan nama customer atau email customer.
Request Headers : Content-Type: application/json

Query Parameters
full_name -> String -> Optional -> Filter customer bedasarkan full_name
email -> String -> Optional -> Filter customer bedasarkan email

Contoh : Get all Customers
GET /api/v1/customers

Contoh : Get all customers by fullname
GET /api/v1/customers?full_name=risjad

Contoh : Get all customers by email
GET /api/v1/customers?email=risjad@

Success Response
Status Code: 200 OK

JSON
{
"success": true,
"message": "Berhasil mendapatkan semua customer",
"data": [
{
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:30:45"
},
{
"id": 2,
"full_name": "Andi Wijaya",
"email": "andi@gmail.com",
"phone_number": "082345678901",
"created_at": "2025-06-17T10:32:10",
"updated_at": "2025-06-17T10:32:10"
}
],
"timestamp": 1718600445000
}

Success Response (Pagination)

````json
{
  "success": true,
  "message": "Berhasil mendapatkan semua customer",
  "data": [
    {
      "id": 1,
      "full_name": "Budi Santoso",
      "email": "budi@gmail.com",
      "phone_number": "081234567890",
      "created_at": "2025-06-17T10:32:10",
      "updated_at": "2025-06-17T10:32:10"
    }
  ],
  "pagination": {
    "page": 0,
    "size": 10,
    "total_elements": 25,
    "total_pages": 3,
    "has_next": true,
    "has_previous": false
  },
  "timestamp": 1718600445000
}
```

Search Response (with full_name parameter):

JSON
{
"success": true,
"message": "Pencarian customer dengan nama 'budi' berhasil",
"data": [
{
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:30:45"
}
],
"timestamp": 1718600445000
}

Search Response (with email parameter):

JSON
{
"success": true,
"message": "Pencarian customer dengan nama 'budi' berhasil",
"data": [
{
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:30:45"
}
],
"timestamp": 1718600445000
}

Error Response
Status Code: 500 Internal Server Error

JSON
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600445000
}

## 3. Get Customer By ID

Method : Get
URL : /api/v1/customers/{id}
Description : Mendapatkan data customer berdasarkan ID yang spesifik.
Request Headers : Content-Type: application/json
Path Parameters : id -> Long -> Required -> ID customer yang ingin di get

Success Response
Status Code: 200 OK
{
"success": true,
"message": "Berhasil mendapatkan customer dengan ID 1",
"data": {
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:30:45"
},
"timestamp": 1718600445000
}
Error Response
Status Code: 404 Not Found
{
"success": false,
"message": "Error",
"error": "Customer dengan ID 999 tidak ditemukan",
"timestamp": 1718600445000
}
Status Code: 500 Internal Server Error
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600445000
}

## 4. Update Customer (Put)

Method : Put
Url : /api/v1/customers/{id}
Description : Mengupdate seluruh data customer (PUT - Replace all fields). Semua field harus diisi dan valid. Jika ada field yang tidak diisi, akan mengakibatkan validation error.
Request Headers : Content-Type: application/json
Path Parameters : id -> Long -> Required -> ID customer yang put update

Request Body
JSON
{
"fullName": "Budi Santoso Updated",
"email": "budi.updated@gmail.com",
"phoneNumber": "082198765432"
}

Success Response
Status Code: 200 OK
{
"success": true,
"message": "Customer dengan ID 1 berhasil diupdate",
"data": {
"id": 1,
"full_name": "Budi Santoso Updated",
"email": "budi.updated@gmail.com",
"phone_number": "082198765432",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:35:20"
},
"timestamp": 1718600520000
}

Error Response
Status Code: 400 Bad Request (Validation Error)
{
"success": false,
"message": "Validation failed",
"error": "Validation error",
"details": [
"fullName: Nama lengkap harus antara 3-100 karakter",
"email: Format email tidak valid"
],
"timestamp": 1718600520000
}

Status Code: 404 Not Found
{
"success": false,
"message": "Error",
"error": "Customer dengan ID 999 tidak ditemukan",
"timestamp": 1718600520000
}
Status Code: 500 Internal Server Error
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600520000
}

## 5. Update Customer (Patch)

Method : Patch
Url : /api/v1/customers/{id}
Description : Mengupdate sebagian data customer (PATCH - Update only specified fields). Hanya field yang ingin diupdate yang perlu dikirim. Field yang tidak dikirim tidak akan diubah.
Request Headers : Content-Type: application/json
Path Parameters : id -> Long -> Required -> ID customer yang patch update

Request Body : Contoh update fullName only
{
"fullName": "Budi Updated"
}
Request Body (Contoh 2 - Update fullName dan phoneNumber)
{
"fullName": "Budi Santoso Updated",
"phoneNumber": "089876543210"
}

Success Response
Status Code: 200 OK (Update fullName only)
{
"success": true,
"message": "Customer dengan ID 1 berhasil di patch",
"data": {
"id": 1,
"full_name": "Budi Updated",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:38:15"
},
"timestamp": 1718600695000
}
Status Code: 200 OK (Update multiple fields)
{
"success": true,
"message": "Customer dengan ID 1 berhasil di patch",
"data": {
"id": 1,
"full_name": "Budi Santoso Updated",
"email": "budi@gmail.com",
"phone_number": "089876543210",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:38:15"
},
"timestamp": 1718600695000
}

Error Response
Status Code: 400 Bad Request (Validation Error)
{
"success": false,
"message": "Validation failed",
"error": "Validation error",
"details": [
"email: Format email tidak valid"
],
"timestamp": 1718600695000
}

Status Code: 404 Not Found
{
"success": false,
"message": "Error",
"error": "Customer dengan ID 999 tidak ditemukan",
"timestamp": 1718600695000
}

Status Code: 500 Internal Server Error
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600695000
}

## 6. Delete Customer

Method : Delete
Url : /api/v1/customers/{id}
Description : Menghapus customer berdasarkan ID yang spesifik. Setelah dihapus, data customer tidak bisa dipulihkan.
Request Headers : Content-Type: application/json
Path Parameters : id -> Long -> Required -> ID customer yang ingin dihapus

Success Response
Status Code: 200 OK
{
"success": true,
"message": "Customer dengan ID 1 berhasil dihapus",
"data": {
"id": 1,
"full_name": "Budi Santoso",
"email": "budi@gmail.com",
"phone_number": "081234567890",
"created_at": "2025-06-17T10:30:45",
"updated_at": "2025-06-17T10:35:20"
},
"timestamp": 1718600695000
}

Error Response
Status Code: 404 Not Found
{
"success": false,
"message": "Error",
"error": "Customer dengan ID 999 tidak ditemukan",
"timestamp": 1718600695000
}
Status Code: 500 Internal Server Error
{
"success": false,
"message": "Internal Server Error",
"error": "Unexpected error occurred",
"timestamp": 1718600695000
}

Curl Request
curl -X DELETE http://localhost:8080/api/v1/customers/1
````
