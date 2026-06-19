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

-------------------------------------------------------------

## API Contract

### POST /api/v1/customers
- Method: POST
- URL: /api/v1/customers
- Description: Create a new customer.
- Request body: 
``` json
{
"full_name": "edith",
"email": "edith@mail.com",
"phone_number": "082233445566"
}
```
- Success response: "[201 Created] New customer created."
``` json
{
    "id": 1,
    "email": "edith@mail.com",
    "full_name": "edith",
    "phone_number": "082233445566",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
``` json
- Error response: "[400 Bad Request] Invalid user input."
``` json
{
    "code": "VALIDATION_ERROR",
    "message": "Invalid request",
    "errors": [
        {
            "field": "fullName",
            "message": "size must be between 3 and 100"
        }
    ]
}
```

### GET /api/v1/customers
- Method: GET
- URL: /api/v1/customers
- Description: Get all customers.
- Request body: -
- Success response: "[200 OK] All customers retrieved successfully."
``` json
{
    "id": 1,
    "email": "edith@mail.com",
    "full_name": "edith",
    "phone_number": "082233445566",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
```
- Error response: 
``` json
[]
```

### GET /api/v1/customers/{id}
- Method: GET
- URL: /api/v1/customers/1
- Description: Get customer data by id.
- Request body: -
- Success response: "[200 OK] Customer data retrieved successfully."
```json
{
    "id": 1,
    "email": "edith@mail.com",
    "full_name": "edith",
    "phone_number": "082233445566",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
```
- URL: /api/v1/customers/2 --> Error response: "[404 Not Found] Customer not found."
```json
{
    "code": "CUSTOMER_NOT_FOUND",
    "message": "Customer not found with id: 2",
    "errors": null
}
```

### DELETE /api/v1/customers/{id}
- Method: DELETE
- URL: /api/v1/customers/1
- Description: Get customer data by id.
- Request body: -
- Success response: "[200 OK] Customer data deleted successfully."
``` json
{
    "id": 1,
    "full_name": "hehe",
    "email": "haha@mail.com",
    "phone_number": "082233445599",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
```
- URL: /api/v1/customers/2 --> Error response: "[404 Not Found] Customer not found."
``` json
{
    "code": "CUSTOMER_NOT_FOUND",
    "message": "Customer not found with id: 2",
    "errors": null
}
```

### PUT /api/v1/customers/{id}
- Method: PUT
- URL: /api/v1/customers/1
- Description: Updates all fields in customer data based on id.
- Request body: 
``` json
{
    "email": "haha@mail.com",
    "full_name": "haha",
    "phone_number": "082233445599"
}
```
- Success response: "[200 OK] All fields in customer data updated successfully."
``` json
{
    "id": 1,
    "full_name": "haha",
    "email": "haha@mail.com",
    "phone_number": "082233445599",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
```
- URL: /api/v1/customers/2 --> Error response: "[404 Not Found] Customer not found."
``` json
{
    "code": "CUSTOMER_NOT_FOUND",
    "message": "Customer not found with id: 2",
    "errors": null
}
```
- if full_name: "ha" --> 400 Bad Request
``` json
{
    "code": "VALIDATION_ERROR",
    "message": "Invalid request",
    "errors": [
        {
            "field": "fullName",
            "message": "size must be between 3 and 100"
        }
    ]
}
```
- if full_name is blank --> 400 Bad Request
``` json
{
    "code": "VALIDATION_ERROR",
    "message": "Invalid request",
    "errors": [
        {
            "field": "fullName",
            "message": "must not be blank"
        }
    ]
}
```

### PATCH /api/v1/customers/{id}
- Method: PATCH
- URL: /api/v1/customers/1
- Description: Updates selected fields in customer data based on id.
- Request body: 
``` json
{
    "full_name": "hehe",
}
```
- Success response: "[200 OK] Customer data updated successfully."
``` json
{
    "id": 1,
    "full_name": "hehe",
    "email": "haha@mail.com",
    "phone_number": "082233445599",
    "created_at": "2026-06-17T22:51:21.6333799+07:00",
    "updated_at": "2026-06-17T22:51:21.6333799+07:00"
}
```
- URL: /api/v1/customers/2 --> Error response: "[404 Not Found] Customer not found."
``` json
{
    "code": "CUSTOMER_NOT_FOUND",
    "message": "Customer not found with id: 2",
    "errors": null
}
```
- if full_name: "ha" --> 400 Bad Request
``` json
{
    "code": "VALIDATION_ERROR",
    "message": "Invalid request",
    "errors": [
        {
            "field": "fullName",
            "message": "size must be between 3 and 100"
        }
    ]
}
```
