# Customer API Documentation

REST API untuk mengelola data customer menggunakan CRUD operations, search, dan pagination.

## Base URL

```http
http://localhost:8080/api/v2
```

---

# Create Customer

## Method

```http
POST
```

## URL

```http
/api/v2/customers
```

## Description

Membuat data customer baru.

## Request Body

```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890"
}
```

### Validation Rules

| Field | Type | Required | Validation |
|---------|---------|---------|---------|
| fullName | String | Yes | Min 3 karakter, Max 100 karakter |
| email | String | Yes | Format email valid |
| phoneNumber | String | Yes | Minimal 10 karakter |

## Success Response

### Status Code

```http
201 Created
```

### Response Body

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890",
  "createdAt": "2026-06-17T10:00:00",
  "updatedAt": "2026-06-17T10:00:00"
}
```

## Error Response

### Status Code

```http
400 Bad Request
```

```json
{
  "message": "Validation failed"
}
```

## Test With

```bash
curl --location 'http://localhost:8080/api/v2/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName":"John Doe",
    "email":"john.doe@example.com",
    "phoneNumber":"081234567890"
}'
```

---

# Get All Customers

## Method

```http
GET
```

## URL

```http
/api/v2/customers
```

## Description

Mengambil seluruh data customer.

## Request Body

Tidak ada.

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
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "081234567890",
    "createdAt": "2026-06-17T10:00:00",
    "updatedAt": "2026-06-17T10:00:00"
  }
]
```

## Error Response

Tidak ada response error khusus.

## Test With

```bash
curl --location 'http://localhost:8080/api/v2/customers'
```

---

# Get Customer By ID

## Method

```http
GET
```

## URL

```http
/api/v2/customers/{id}
```

## Description

Mengambil data customer berdasarkan ID.

## Path Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| id | Long | Yes |

## Success Response

### Status Code

```http
200 OK
```

### Response Body

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890",
  "createdAt": "2026-06-17T10:00:00",
  "updatedAt": "2026-06-17T10:00:00"
}
```

## Error Response

### Status Code

```http
404 Not Found
```

```json
{
  "message": "Customer not found"
}
```

## Test With

```bash
curl --location 'http://localhost:8080/api/v2/customers/1'
```

---

# Update Customer (PUT)

## Method

```http
PUT
```

## URL

```http
/api/v2/customers/{id}
```

## Description

Mengubah seluruh data customer berdasarkan ID.

## Path Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| id | Long | Yes |

## Request Body

```json
{
  "fullName": "John Doe Updated",
  "email": "john.updated@example.com",
  "phoneNumber": "089876543210"
}
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
  "fullName": "John Doe Updated",
  "email": "john.updated@example.com",
  "phoneNumber": "089876543210",
  "createdAt": "2026-06-17T10:00:00",
  "updatedAt": "2026-06-17T11:00:00"
}
```

## Error Response

### Status Code

```http
400 Bad Request
```

```json
{
  "message": "Validation failed"
}
```

### Status Code

```http
404 Not Found
```

```json
{
  "message": "Customer not found"
}
```

## Test With

```bash
curl --location --request PUT 'http://localhost:8080/api/v2/customers/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName":"John Doe Updated",
    "email":"john.updated@example.com",
    "phoneNumber":"089876543210"
}'
```

---

# Patch Customer

## Method

```http
PATCH
```

## URL

```http
/api/v2/customers/{id}
```

## Description

Mengubah sebagian data customer berdasarkan ID.

## Path Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| id | Long | Yes |

## Request Body

Semua field bersifat opsional.

```json
{
  "email": "newemail@example.com"
}
```

Atau

```json
{
  "fullName": "John Doe Updated"
}
```

Atau

```json
{
  "phoneNumber": "089876543210"
}
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
  "fullName": "John Doe Updated",
  "email": "newemail@example.com",
  "phoneNumber": "081234567890",
  "createdAt": "2026-06-17T10:00:00",
  "updatedAt": "2026-06-17T11:30:00"
}
```

## Error Response

### Status Code

```http
400 Bad Request
```

```json
{
  "message": "Validation failed"
}
```

### Status Code

```http
404 Not Found
```

```json
{
  "message": "Customer not found"
}
```

## Test With

```bash
curl --location --request PATCH 'http://localhost:8080/api/v2/customers/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"newemail@example.com"
}'
```

---

# Delete Customer

## Method

```http
DELETE
```

## URL

```http
/api/v2/customers/{id}
```

## Description

Menghapus customer berdasarkan ID.

## Path Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| id | Long | Yes |

## Request Body

Tidak ada.

## Success Response

### Status Code

```http
204 No Content
```

Response body kosong.

## Error Response

### Status Code

```http
404 Not Found
```

```json
{
  "message": "Customer not found"
}
```

## Test With

```bash
curl --location --request DELETE 'http://localhost:8080/api/v2/customers/1'
```

---

# Search Customer By Email

## Method

```http
GET
```

## URL

```http
/api/v2/customers/search?email={email}
```

## Description

Mencari customer berdasarkan email.

## Query Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| email | String | Yes |

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
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "081234567890",
    "createdAt": "2026-06-17T10:00:00",
    "updatedAt": "2026-06-17T10:00:00"
  }
]
```

## Error Response

### Status Code

```http
404 Not Found
```

```json
[]
```

## Test With

```bash
curl --location 'http://localhost:8080/api/v2/customers/search?email=john.doe@example.com'
```

---

# Get Customers With Pagination

## Method

```http
GET
```

## URL

```http
/api/v2/customers/pagination?page={page}&size={size}
```

## Description

Mengambil data customer dengan pagination.

## Query Parameters

| Parameter | Type | Required |
|------------|---------|---------|
| page | Integer | Yes |
| size | Integer | Yes |

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
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "081234567890",
    "createdAt": "2026-06-17T10:00:00",
    "updatedAt": "2026-06-17T10:00:00"
  }
]
```

## Error Response

Tidak ada response error khusus.

## Test With

```bash
curl --location 'http://localhost:8080/api/v2/customers/pagination?page=0&size=10'
```

---

# Data Models

## CreateCustomerRequest

```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890"
}
```

## PatchCustomerRequest

```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890"
}
```

Semua field bersifat opsional.

## CustomerResponse

```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890",
  "createdAt": "2026-06-17T10:00:00",
  "updatedAt": "2026-06-17T10:00:00"
}
```