# Customer API Contract

Base URL: `http://localhost:8080/api/v1/customers`

---

# 1. POST /api/v1/customers

**Method:** POST

**Description:** Create a new customer.

### Request Body

```json
{
  "full_name": "Budi Santoso",
  "email": "budi@mail.com",
  "phone_number": "08123456789"
}
```

### Success Response

**Status:** `201 Created`

```json
{
  "email": "budi@mail.com",
  "full_name": "Budi Santoso",
  "id": 1,
  "phone_number": "08123456789"
}
```

### Error Response

#### Validation Error

**Status:** `400 Bad Request`

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "phone_number",
      "message": "must not be blank"
    }
  ]
}
```

---

# 2. GET /api/v1/customers

**Method:** GET

**Description:** Retrieve all customers.

Optional query parameter:

```text
?name=Budi
```

### Request Body

None

### Success Response

**Status:** `200 OK`

```json
[
  {
    "email": "budi@mail.com",
    "full_name": "Budi Santoso",
    "id": 1,
    "phone_number": "08123456789"
  }
]
```

### Error Response

#### Internal Server Error

**Status:** `500 Internal Server Error`

```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Unexpected error occurred",
  "errors": []
}
```

---

# 3. GET /api/v1/customers/{id}

**Method:** GET

**Description:** Retrieve customer by ID.

### Request Body

None

### Success Response

**Status:** `200 OK`

```json
{
  "email": "budi@mail.com",
  "full_name": "Budi Santoso",
  "id": 1,
  "phone_number": "08123456789"
}
```

### Error Response

#### Customer Not Found

**Status:** `404 Not Found`

```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 123",
  "errors": []
}
```

---

# 4. PUT /api/v1/customers/{id}

**Method:** PUT

**Description:** Replace all customer information.

### Request Body

```json
{
  "full_name": "Budi Santoso 3 EDIT",
  "email": "budiedit@mail.com",
  "phone_number": "08123456789123"
}
```

### Success Response

**Status:** `200 OK`

```json
{
  "email": "budiedit@mail.com",
  "full_name": "Budi Santoso 3 EDIT",
  "id": 1,
  "phone_number": "08123456789123"
}
```

### Error Response

#### Validation Error

**Status:** `400 Bad Request`

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "phone_number",
      "message": "must not be blank"
    }
  ]
}
```

#### Customer Not Found

**Status:** `404 Not Found`

```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 123",
  "errors": []
}
```

---

# 5. PATCH /api/v1/customers/{id}

**Method:** PATCH

**Description:** Update one or more customer fields.

### Request Body

```json
{
  "full_name": "Budi Santoso 3 EDIT V2"
}
```

### Success Response

**Status:** `200 OK`

```json
{
  "email": "budiedit@mail.com",
  "full_name": "Budi Santoso 3 EDIT V2",
  "id": 1,
  "phone_number": "08123456789123"
}
```

### Error Response

#### Validation Error

**Status:** `400 Bad Request`

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": [
    {
      "field": "phone_number",
      "message": "size must be between 10 and 2147483647"
    }
  ]
}
```

#### Customer Not Found

**Status:** `404 Not Found`

```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 123",
  "errors": []
}
```

---

# 6. DELETE /api/v1/customers/{id}

**Method:** DELETE

**Description:** Delete a customer by ID.

### Request Body

None

### Success Response

**Status:** `200 OK`

```json
{
  "email": "budiedit@mail.com",
  "full_name": "Budi Santoso 3 EDIT V2",
  "id": 1,
  "phone_number": "08123456789123"
}
```

### Error Response

#### Customer Not Found

**Status:** `404 Not Found`

```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found with id: 123",
  "errors": []
}
```
