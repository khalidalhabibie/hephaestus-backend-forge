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

Base URL: `/api/v1/customers`

***

## 1. Create Customer

### Method

`POST`

### URL

`/api/v1/customers`

### Description

Create a new customer.

### Request Body

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+628123456789",
  "address": "Jakarta, Indonesia"
}
```

### Success Response

```json
{
  "id": "cst_12345",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+628123456789",
  "address": "Jakarta, Indonesia",
  "created_at": "2026-06-17T07:00:00Z"
}
```

### Error Response

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Email is invalid"
}
```

### Status Code

* `201 Created`
* `400 Bad Request`

***

## 2. Get Customers (List)

### Method

`GET`

### URL

`/api/v1/customers`

### Description

Retrieve a list of customers.

### Request Body

None

### Success Response

```json
{
  "data": [
    {
      "id": "cst_12345",
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  ],
  "total": 1
}
```

### Error Response

```json
{
  "error": "INTERNAL_SERVER_ERROR",
  "message": "Something went wrong"
}
```

### Status Code

* `200 OK`
* `500 Internal Server Error`

***

## 3. Get Customer by ID

### Method

`GET`

### URL

`/api/v1/customers/{id}`

### Description

Retrieve customer details by customer ID.

### Request Body

None

### Success Response

```json
{
  "id": "cst_12345",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+628123456789",
  "address": "Jakarta, Indonesia"
}
```

### Error Response

```json
{
  "error": "NOT_FOUND",
  "message": "Customer not found"
}
```

### Status Code

* `200 OK`
* `404 Not Found`

***

## 4. Update Customer (Full Update)

### Method

`PUT`

### URL

`/api/v1/customers/{id}`

### Description

Replace all customer data by ID.

### Request Body

```json
{
  "name": "John Smith",
  "email": "john.smith@example.com",
  "phone": "+628987654321",
  "address": "Bandung, Indonesia"
}
```

### Success Response

```json
{
  "id": "cst_12345",
  "name": "John Smith",
  "email": "john.smith@example.com",
  "phone": "+628987654321",
  "address": "Bandung, Indonesia",
  "updated_at": "2026-06-17T08:00:00Z"
}
```

### Error Response

```json
{
  "error": "NOT_FOUND",
  "message": "Customer not found"
}
```

### Status Code

* `200 OK`
* `400 Bad Request`
* `404 Not Found`

***

## 5. Update Customer (Partial Update)

### Method

`PATCH`

### URL

`/api/v1/customers/{id}`

### Description

Update specific customer fields by ID.

### Request Body

```json
{
  "phone": "+628111222333"
}
```

### Success Response

```json
{
  "id": "cst_12345",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+628111222333",
  "updated_at": "2026-06-17T08:30:00Z"
}
```

### Error Response

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Invalid phone number"
}
```

### Status Code

* `200 OK`
* `400 Bad Request`
* `404 Not Found`



## Updated: Get Customers (List with Pagination)

### Method

`GET`

### URL

`/api/v1/customers`

### Description

Retrieve a paginated list of customers.

### Query Parameters

```
?page=1
&limit=10
```

* `page` (optional): Page number (default: 1)
* `limit` (optional): Number of records per page (default: 10)

### Request Body

None

### Success Response

```json
{
  "data": [
    {
      "id": "cst_12345",
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  ],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total_data": 25,
    "total_page": 3
  }
}
```

### Error Response

```json
{
  "error": "INVALID_PAGINATION",
  "message": "Page must be greater than 0"
}
```

### Status Code

* `200 OK`
* `400 Bad Request`
* `500 Internal Server Error`

***




