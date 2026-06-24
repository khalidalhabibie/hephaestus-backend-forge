# Customer API Contract

## Base URL

http://localhost:8080/api/v1/customers

http://localhost:8080/swagger-ui/index.html#/

---

## 1. Create Customer

**Method**  
POST

**URL**  
/api/v1/customers

**Description**  
Membuat data customer baru.

**Request Body**

```json
{
	"full_name": "string (min: 3, max: 100)",
	"email": "string (email format)",
	"phone_number": "string (min: 10)"
}
```

**Success Response**  
Status Code: 201 Created

```json
{
	"message": "Customer created successfully",
	"data": {
		"id": 1,
		"full_name": "John Doe",
		"email": "john@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T10:00:00",
		"updated_at": "2026-06-17T10:00:00"
	}
}
```

**Error Response**  
Status Code: 400 Bad Request

```json
{
	"message": "Validation error"
}
```

---

## 2. Get All Customers

**Method**  
GET

**URL**  
/api/v1/customers

**Description**  
Mengambil semua data customer.

**Request Body**  
Tidak ada

**Success Response**  
Status Code: 200 OK

```json
[
	{
		"id": 1,
		"full_name": "John Doe",
		"email": "john@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T10:00:00",
		"updated_at": "2026-06-17T10:00:00"
	}
]
```

**Error Response**  
Status Code: 500 Internal Server Error

```json
{
	"message": "Internal server error"
}
```

---

## 3. Get Customer by ID

**Method**  
GET

**URL**  
/api/v1/customers/{id}

**Description**  
Mengambil data customer berdasarkan ID.

**Request Body**  
Tidak ada

**Success Response**  
Status Code: 200 OK

```json
{
	"id": 1,
	"full_name": "John Doe",
	"email": "john@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T10:00:00",
	"updated_at": "2026-06-17T10:00:00"
}
```

**Error Response**  
Status Code: 404 Not Found

```json
{
	"message": "Customer tidak ditemukan"
}
```

---

## 4. Update Customer

**Method**  
PUT

**URL**  
/api/v1/customers/{id}

**Description**  
Mengupdate seluruh data customer.

**Request Body**

```json
{
	"full_name": "string",
	"email": "string",
	"phone_number": "string"
}
```

**Success Response**  
Status Code: 200 OK

```json
{
	"message": "Customer data updated successfully",
	"data": {
		"id": 1,
		"full_name": "Updated Name",
		"email": "updated@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T10:00:00",
		"updated_at": "2026-06-17T10:10:00"
	}
}
```

**Error Response**  
Status Code: 400 Bad Request / 404 Not Found

```json
{
	"message": "Error"
}
```

---

## 5. Patch Customer

**Method**  
PATCH

**URL**  
/api/v1/customers/{id}

**Description**  
Mengupdate sebagian data customer.

**Request Body**

```json
{
	"full_name": "string (optional)",
	"email": "string (optional)",
	"phone_number": "string (optional)"
}
```

**Success Response**  
Status Code: 200 OK

```json
{
	"message": "Customer data updated successfully",
	"data": {
		"id": 1,
		"full_name": "John Doe",
		"email": "new@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T10:00:00",
		"updated_at": "2026-06-17T10:15:00"
	}
}
```

**Error Response**  
Status Code: 400 Bad Request / 404 Not Found

```json
{
	"message": "Error"
}
```

---

## 6. Search Customer by Email

**Method**  
GET

**URL**  
/api/v1/customers/search?email=example

**Description**  
Mencari customer berdasarkan email.

**Success Response**  
Status Code: 200 OK

```json
[
	{
		"id": 1,
		"full_name": "John Doe",
		"email": "john@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T10:00:00",
		"updated_at": "2026-06-17T10:00:00"
	}
]
```

---

## 7. Pagination (Simple)

**Method**  
GET

**URL**  
/api/v1/customers?page=0&size=10

**Description**  
Mengambil data customer dengan pagination.

**Response Example**

```json
{
	"page": 0,
	"size": 10,
	"total_data": 100,
	"data": [
		{
			"id": 1,
			"full_name": "John Doe",
			"email": "john@example.com",
			"phone_number": "081234567890"
		}
	]
}
```

---

## 8. ErrorResponse Contract

```json
{
	"timestamp": "2026-06-17T10:00:00",
	"status": 400,
	"error": "Bad Request",
	"message": "Validation error",
	"path": "/api/v1/customers"
}
```