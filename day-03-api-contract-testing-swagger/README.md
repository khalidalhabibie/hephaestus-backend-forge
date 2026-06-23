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



# Customer Management API - Full Documentation & API Contract

## Environment
* **Base URL:** `http://localhost:8081`
* **API Version:** `v1`
* **Format Data:** `application/json`
* **Aturan Penamaan:** JSON menggunakan `snake_case`, Java menggunakan `camelCase`.

---

## Response Schema

### 1.Success Response 
Mengembalikan objek data langsung secara *flat* atau berupa *array* objek.
* Properti utama: `id`, `full_name`, `email`, `phone_number`.

### 2. Error Response
Sesuai dengan kriteria penerimaan, seluruh jalur *error* yang ditangani oleh `@ControllerAdvice` akan mengembalikan struktur berikut:

```json
{
  "code": "STRING (KODE_ERROR)",
  "message": "STRING (Pesan error ringkas)",
  "errors": [
    {
      "field": "STRING (Nama properti yang bermasalah)",
      "message": "STRING (Detail alasan kegagalan)"
    }
  ]
}
```

---

## API Contract 

###  1. Create Customer
- Method: **POST**
- URL: **/api/v1/customers**
- Headers: **Content-Type: application/json**
- Request:
```json
{
  "full_name": "Budi Santoso", -> String; Min 3; Max 100; Required
  "email": "budi.santoso@mail.com", -> Required
  "phone_number": "081234567890" -> Required; Min 10
} 
```
- Responses:
-- Code: 201 Created
```json
{
  "id": 1,
  "full_name": "Davin Bennett",
  "email": "davin@mail.com",
  "phone_number": "081234567890"
}
```
-- Code: 400 Bad Request
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "full_name",
      "message": "length must be between 3 and 100 characters"
    },
    {
      "field": "email",
      "message": "email format is invalid"
    }
  ]
}
```

###  2. Get Customer List
- Method: **GET**
- URL: **/api/v1/customers**
- Headers: **Content-Type: application/json**
- Responses:
-- Code: 200 OK
```json
[
  {
    "id": 1,
    "full_name": "Davin Bennett",
    "email": "davin@mail.com",
    "phone_number": "081234567890"
  }
]
```

###  3. Get Customer By ID
- Method: **GET**
- URL: **/api/v1/customers/{id}**
- Headers: **Content-Type: application/json**
- Responses:
-- Code: 200 OK
```json
[
  {
    "id": 1,
    "full_name": "Davin Bennett",
    "email": "davin@mail.com",
    "phone_number": "081234567890"
  }
]
```
-- Code: 404 Not Found
```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 999",
  "errors": []
}
```

###  4. PUT Customer
- Method: **PUT**
- URL: **/api/v1/customers/{id}**
- Headers: **Content-Type: application/json**
- Request:
```json
{
  "full_name": "Budi Santoso Sunting",
  "email": "budi.baru@mail.com",
  "phone_number": "089999888877"
}
- Responses:
-- Code: 200 OK
```json
{
  "id": 1,
  "full_name": "Budi Santoso Sunting",
  "email": "budi.baru@mail.com",
  "phone_number": "089999888877"
}
```
-- Code: 404 Not Found
```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 999",
  "errors": []
}
```
-- Code: 400 Bad Request
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "full_name",
      "message": "length must be between 3 and 100 characters"
    }
  ]
}
```

###  5. PATCH Customer
- Method: **PATCH**
- URL: **/api/v1/customers/{id}**
- Headers: **Content-Type: application/json**
- Request:
```json
{
  "phone_number": "089999888877"
}
- Responses:
-- Code: 200 OK
```json
{
  "id": 1,
  "full_name": "Budi Santoso Sunting",
  "email": "budi.baru@mail.com",
  "phone_number": "089999888877"
}
```
-- Code: 404 Not Found
```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 999",
  "errors": []
}
```
-- Code: 400 Bad Request
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "full_name",
      "message": "length must be between 3 and 100 characters"
    }
  ]
}
```

