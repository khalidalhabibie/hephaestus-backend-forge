# Day 1 - Java Fundamental & Spring Boot REST API

Day 1 dibagi menjadi dua modul:

1. Java Fundamental
2. Spring Boot REST API

Java Fundamental perlu dipahami terlebih dahulu sebelum masuk ke Spring Boot. Spring Boot tetap menggunakan class, object, variable, method, collection, dan konsep OOP dari Java. Annotation seperti `@RestController` atau `@Service` hanya menambahkan perilaku Spring di atas class Java biasa.

Modul Spring Boot REST API akan menggunakan konsep yang sama dari exercise Java, yaitu pengelolaan data customer sederhana. Bedanya, program akan diakses melalui HTTP endpoint dan dites menggunakan Postman.

## 1. Goal

Setelah menyelesaikan Day 1, peserta diharapkan mampu:

- Membuat program customer management sederhana dengan plain Java.
- Membuat Customer REST API sederhana menggunakan Spring Boot.
- Memahami peran Controller, Service, DTO, dan Model.
- Memahami alur request-response pada REST API.

## 2. Why This Day Is Important

Backend engineer yang menggunakan Spring Boot tetap perlu memahami Java. Jika peserta belum paham variable, class, object, method, constructor, getter, setter, `List`, dan `Map`, maka kode Spring Boot akan terasa seperti kumpulan annotation tanpa dasar yang jelas.

Day ini dibuat untuk membangun dasar tersebut secara bertahap. Pertama, peserta membuat program Java biasa. Setelah itu, konsep yang sama dipakai lagi dalam bentuk REST API.

## 3. Module Structure

```text
day-01-java-and-rest-api/
├── README.md
├── 01-java-fundamental/
│   ├── pretest.md
│   ├── materi.md
│   ├── exercise.md
│   └── posttest.md
└── 02-springboot-rest-api/
    ├── pretest.md
    ├── materi.md
    ├── exercise.md
    └── posttest.md
```

## 4. Learning Flow

Ikuti urutan belajar berikut:

1. Kerjakan `01-java-fundamental/pretest.md`.
2. Baca `01-java-fundamental/materi.md`.
3. Kerjakan `01-java-fundamental/exercise.md`.
4. Kerjakan `01-java-fundamental/posttest.md`.
5. Kerjakan `02-springboot-rest-api/pretest.md`.
6. Baca `02-springboot-rest-api/materi.md`.
7. Kerjakan `02-springboot-rest-api/exercise.md`.
8. Kerjakan `02-springboot-rest-api/posttest.md`.

## 5. Expected Output

Di akhir Day 1, peserta diharapkan memiliki:

- Program plain Java untuk customer management.
- Spring Boot Customer REST API sederhana.
- Pemahaman dasar tentang Controller, Service, DTO, dan Model.
- Pemahaman alur request dari client sampai response kembali ke client.

## 6. What Is Not Covered Today

Day 1 belum membahas:

- Database
- Detail validation
- Detail error handling
- Security
- Redis
- Deployment

Semua data pada Day 1 disimpan sementara di memory menggunakan `Map` atau `List`.


# API Contract — Customer API V2

Base URL:

```http
/api/v2/customers
```


# 1. Create Customer

## Method

```http
POST
```

## URL

```http
/api/v2/customers
```

## Operation Annotation

```java
@Operation(summary = "Create new customer", description = "Create a new customer in the database")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Customer successfully created"),
    @ApiResponse(responseCode = "400", description = "Invalid request payload"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Create a new customer in the database.

## Request Body

Menggunakan DTO:

```java
CreateCustomerRequest
```

### JSON Request Body

```json
{
  "full_name": "Budi Santoso",
  "email": "budi.santoso@example.com",
  "phone_number": "0812345678"
}
```

### Field Validation

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Ya | `@NotBlank`, `@Email` |
| `phone_number` | `String` | Ya | `@NotBlank`, `@Size(max = 10)` |

## Success Response

### Status Code

```http
201 Created
```

### Response Body

```json
{
  "id": 1,
  "full_name": "Budi Santoso",
  "email": "budi.santoso@example.com",
  "phone_number": "0812345678"
}
```

## Error Response

### 400 Bad Request — Invalid Request Payload

```json
{
  "code": "BAD_REQUEST",
  "message": "Invalid request payload",
  "errors": [
    {
      "field": "email",
      "message": "must be a well-formed email address"
    },
    {
      "field": "full_name",
      "message": "must not be blank"
    }
  ]
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `201 Created` | Customer successfully created |
| `400 Bad Request` | Invalid request payload |
| `500 Internal Server Error` | Internal server error |

---

# 2. Get All Customers / Filter by Name

## Method

```http
GET
```

## URL

```http
/api/v2/customers
```

Optional query parameter:

```http
/api/v2/customers?name={name}
```

## Operation Annotation

```java
@Operation(summary = "Get all customers", description = "Retrieve all customers or filter by name")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request parameter"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Retrieve all customers or filter customers by name. Jika query parameter `name` dikirimkan, sistem akan mengambil customer berdasarkan nama. Jika tidak dikirimkan, sistem akan mengambil seluruh customer.

## Query Parameter

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `name` | `String` | Tidak | Nama customer yang ingin dicari |
| `email` | `String` | Tidak | Email customer yang ingin dicari |
| `page` | `Integer` | Tidak | Halaman berapa yang ingin ditampilkan |
| `size` | `Integer` | Tidak | Jumlah berapa data yang ingin ditampilkan tiap halaman|

## Request Body

Tidak ada request body.

## Example Request

```http
GET /api/v2/customers
```

```http
GET /api/v2/customers?name=Budi
```

## Success Response

### Status Code

```http
200 OK
```

### Response Body

```json
[
  {
    "id": 1,
    "full_name": "Budi Santoso",
    "email": "budi.santoso@example.com",
    "phone_number": "0812345678"
  },
  {
    "id": 2,
    "full_name": "Siti Aminah",
    "email": "siti.aminah@example.com",
    "phone_number": "0812987654"
  }
]
```

Jika data tidak ditemukan pada pencarian berdasarkan nama, response dapat berupa array kosong:

```json
[]
```

## Error Response

### 400 Bad Request — Invalid Request Parameter

```json
{
  "code": "BAD_REQUEST",
  "message": "Invalid request parameter",
  "errors": [
    {
      "field": "name",
      "message": "Invalid request parameter"
    }
  ]
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `200 OK` | Customers retrieved successfully |
| `400 Bad Request` | Invalid request parameter |
| `500 Internal Server Error` | Internal server error |

---

# 3. Get Customer by ID

## Method

```http
GET
```

## URL

```http
/api/v2/customers/{id}
```

## Operation Annotation

```java
@Operation(summary = "Get customer by ID", description = "Retrieve a specific customer by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customer found"),
    @ApiResponse(responseCode = "404", description = "Customer not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Retrieve a specific customer by ID.

## Path Variable

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `id` | `Long` | Ya | ID customer |

## Request Body

Tidak ada request body.

## Example Request

```http
GET /api/v2/customers/1
```

## Success Response

### Status Code

```http
200 OK
```

### Response Body

```json
{
  "id": 1,
  "full_name": "Budi Santoso",
  "email": "budi.santoso@example.com",
  "phone_number": "0812345678"
}
```

## Error Response

### 404 Not Found — Customer Not Found

```json
{
  "code": "NOT_FOUND",
  "message": "Customer not found",
  "errors": []
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `200 OK` | Customer found |
| `404 Not Found` | Customer not found |
| `500 Internal Server Error` | Internal server error |

---

# 4. Delete Customer by ID

## Method

```http
DELETE
```

## URL

```http
/api/v2/customers/{id}
```

## Operation Annotation

```java
@Operation(summary = "Delete customer by ID", description = "Delete a specific customer by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
    @ApiResponse(responseCode = "404", description = "Customer not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Delete a specific customer by ID.

## Path Variable

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `id` | `Long` | Ya | ID customer yang akan dihapus |

## Request Body

Tidak ada request body.

## Example Request

```http
DELETE /api/v2/customers/1
```

## Success Response

### Status Code

```http
200 OK
```

### Response Body

Response berupa `String` dari service `deleteCustomerResponseById(id)`.

```text
Customer successfully deleted
```

Alternatif jika API mengembalikan JSON string:

```json
"Customer successfully deleted"
```

## Error Response

### 404 Not Found — Customer Not Found

```json
{
  "code": "NOT_FOUND",
  "message": "Customer not found",
  "errors": []
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `200 OK` | Customer successfully deleted |
| `404 Not Found` | Customer not found |
| `500 Internal Server Error` | Internal server error |

---

# 5. Update Customer Full by ID

## Method

```http
PUT
```

## URL

```http
/api/v2/customers/{id}
```

## Operation Annotation

```java
@Operation(summary = "Update customer (full)", description = "Fully update customer data by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request payload"),
    @ApiResponse(responseCode = "404", description = "Customer not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Fully update customer data by ID. Endpoint ini menggunakan metode `PUT`, sehingga request body merepresentasikan data customer lengkap yang akan diperbarui.

## Path Variable

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `id` | `Long` | Ya | ID customer yang akan diperbarui |

## Request Body

Menggunakan DTO:

```java
PutUpdateCustomerRequest
```

### JSON Request Body

```json
{
  "full_name": "Budi Santoso Updated",
  "email": "budi.updated@example.com",
  "phone_number": "0811111111"
}
```

### Field Validation

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Ya | `@NotBlank`, `@Email` |
| `phone_number` | `String` | Ya | `@NotBlank`, `@Size(max = 10)` |

## Success Response

### Status Code

```http
200 OK
```

### Response Body

```json
{
  "id": 1,
  "full_name": "Budi Santoso Updated",
  "email": "budi.updated@example.com",
  "phone_number": "0811111111"
}
```

## Error Response

### 400 Bad Request — Invalid Request Payload

```json
{
  "code": "BAD_REQUEST",
  "message": "Invalid request payload",
  "errors": [
    {
      "field": "phone_number",
      "message": "size must be between 0 and 10"
    }
  ]
}
```

### 404 Not Found — Customer Not Found

```json
{
  "code": "NOT_FOUND",
  "message": "Customer not found",
  "errors": []
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `200 OK` | Customer successfully updated |
| `400 Bad Request` | Invalid request payload |
| `404 Not Found` | Customer not found |
| `500 Internal Server Error` | Internal server error |

---

# 6. Update Customer Partial by ID

## Method

```http
PATCH
```

## URL

```http
/api/v2/customers/{id}
```

## Operation Annotation

```java
@Operation(summary = "Update customer (partial)", description = "Partially update customer data by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
    @ApiResponse(responseCode = "400", description = "Invalid request payload"),
    @ApiResponse(responseCode = "404", description = "Customer not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
```

## Description

Partially update customer data by ID. Endpoint ini digunakan untuk mengubah sebagian data customer.

> Catatan berdasarkan DTO yang diberikan: field `full_name` pada `PatchUpdateCustomerRequest` masih menggunakan `@NotBlank`, sehingga `full_name` tetap wajib dikirim walaupun endpoint bersifat partial update.

## Path Variable

| Parameter | Type | Required | Description |
|---|---|---:|---|
| `id` | `Long` | Ya | ID customer yang akan diperbarui |

## Request Body

Menggunakan DTO:

```java
PatchUpdateCustomerRequest
```

### JSON Request Body

```json
{
  "full_name": "Budi Santoso Patch",
  "email": "budi.patch@example.com",
  "phone_number": "0812222222"
}
```

Contoh request jika hanya ingin mengubah nama:

```json
{
  "full_name": "Budi Santoso Patch"
}
```

### Field Validation

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Tidak | `@Email` |
| `phone_number` | `String` | Tidak | `@Size(max = 10)` |

## Success Response

### Status Code

```http
200 OK
```

### Response Body

```json
{
  "id": 1,
  "full_name": "Budi Santoso Patch",
  "email": "budi.patch@example.com",
  "phone_number": "0812222222"
}
```

## Error Response

### 400 Bad Request — Invalid Request Payload

```json
{
  "code": "BAD_REQUEST",
  "message": "Invalid request payload",
  "errors": [
    {
      "field": "email",
      "message": "must be a well-formed email address"
    }
  ]
}
```

### 404 Not Found — Customer Not Found

```json
{
  "code": "NOT_FOUND",
  "message": "Customer not found",
  "errors": []
}
```

### 500 Internal Server Error

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Internal server error",
  "errors": []
}
```

## Status Code

| Status Code | Description |
|---:|---|
| `200 OK` | Customer successfully updated |
| `400 Bad Request` | Invalid request payload |
| `404 Not Found` | Customer not found |
| `500 Internal Server Error` | Internal server error |

---

# Data Contract

## CreateCustomerRequest

Digunakan sebagai request body pada endpoint:

```http
POST /api/v2/customers
```

### Schema

```json
{
  "full_name": "string",
  "email": "string",
  "phone_number": "string"
}
```

### Validation Rules

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Ya | `@NotBlank`, `@Email` |
| `phone_number` | `String` | Ya | `@NotBlank`, `@Size(max = 10)` |

---

## PutUpdateCustomerRequest

Digunakan sebagai request body pada endpoint:

```http
PUT /api/v2/customers/{id}
```

### Schema

```json
{
  "full_name": "string",
  "email": "string",
  "phone_number": "string"
}
```

### Validation Rules

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Ya | `@NotBlank`, `@Email` |
| `phone_number` | `String` | Ya | `@NotBlank`, `@Size(max = 10)` |

---

## PatchUpdateCustomerRequest

Digunakan sebagai request body pada endpoint:

```http
PATCH /api/v2/customers/{id}
```

### Schema

```json
{
  "full_name": "string",
  "email": "string",
  "phone_number": "string"
}
```

### Validation Rules

| Field | Type | Required | Validation |
|---|---|---:|---|
| `full_name` | `String` | Ya | `@NotBlank`, `@Size(min = 3, max = 100)` |
| `email` | `String` | Tidak | `@Email` |
| `phone_number` | `String` | Tidak | `@Size(max = 10)` |

---

## CustomerResponse

Digunakan sebagai response untuk endpoint:

```http
POST /api/v2/customers
GET /api/v2/customers
GET /api/v2/customers/{id}
PUT /api/v2/customers/{id}
PATCH /api/v2/customers/{id}
```

### Schema

```json
{
  "id": 1,
  "full_name": "string",
  "email": "string",
  "phone_number": "string"
}
```

### Field Description

| Field | Type | Description |
|---|---|---|
| `id` | `Long` | ID customer |
| `full_name` | `String` | Nama lengkap customer |
| `email` | `String` | Email customer |
| `phone_number` | `String` | Nomor telepon customer |

---

## Delete Customer Response

Digunakan sebagai success response untuk endpoint:

```http
DELETE /api/v2/customers/{id}
```

### Type

```java
String
```

### Example

```text
Customer successfully deleted
```

---

## ErrorResponse

Digunakan ketika terjadi error pada request.

### Schema

```json
{
  "code": "string",
  "message": "string",
  "errors": [
    {
      "field": "string",
      "message": "string"
    }
  ]
}
```

### Field Description

| Field | Type | Description |
|---|---|---|
| `code` | `String` | Kode error |
| `message` | `String` | Pesan error umum |
| `errors` | `List<FieldErrorResponse>` | Detail error per field |

### Example

```json
{
  "code": "BAD_REQUEST",
  "message": "Invalid request payload",
  "errors": [
    {
      "field": "email",
      "message": "must be a well-formed email address"
    }
  ]
}
```

---

## FieldErrorResponse

Digunakan di dalam `ErrorResponse.errors`.

### Schema

```json
{
  "field": "string",
  "message": "string"
}
```

### Field Description

| Field | Type | Description |
|---|---|---|
| `field` | `String` | Nama field yang error |
| `message` | `String` | Pesan error untuk field tersebut |

---

# Ringkasan Status Code

| Status Code | Meaning | Digunakan Pada |
|---:|---|---|
| `200 OK` | Request berhasil diproses | `GET`, `DELETE`, `PUT`, `PATCH` |
| `201 Created` | Customer berhasil dibuat | `POST` |
| `400 Bad Request` | Request body atau parameter tidak valid | `POST`, `GET`, `PUT`, `PATCH` |
| `404 Not Found` | Customer tidak ditemukan | `GET /{id}`, `DELETE /{id}`, `PUT /{id}`, `PATCH /{id}` |
| `500 Internal Server Error` | Terjadi kesalahan pada server | Semua endpoint |

---

