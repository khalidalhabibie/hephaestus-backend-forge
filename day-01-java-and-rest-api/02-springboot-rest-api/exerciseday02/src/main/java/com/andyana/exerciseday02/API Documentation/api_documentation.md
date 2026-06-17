# API Documentation - Customer Management API

Base path: `/api/v1/customers`

## API Contract (Detailed)

### POST /api/v1/customers
- Method: `POST`
- URL: `/api/v1/customers`
- Description: Membuat Customer baru.
- Request body (application/json):

```json
{
	"full_name": "Andyana Cantik",
	"email": "andyana@example.com",
	"phone_number": "081234567890"
}
```

- Request schema:
	- `full_name` (string, required) — max 100 chars
	- `email` (string, required) — valid email
	- `phone_number` (string, required) — 10-12 chars

- Success response (201 Created):

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

- Error responses:
	- 400 Bad Request (validation error):

```json
{
	"success": false,
	"code": "VALIDATION_ERROR",
	"message": "Invalid request",
	"errors": [ { "field": "full_name", "message": "Mohon Isi Nama Lengkap Anda" } ],
	"data": null
}
```

	- 500 Internal Server Error:

```json
{
	"success": false,
	"code": "INTERNAL_SERVER_ERROR",
	"message": "Internal server error: {exception message}",
	"errors": [],
	"data": null
}
```

---

### GET /api/v1/customers
- Method: `GET`
- URL: `/api/v1/customers`
- Description: Mendapatkan semua customer.
- Request body: none
- Success response (200 OK):

```json
[
	{
		"id": 1,
		"full_name": "Andyana Cantik",
		"email": "andyana@example.com",
		"phone_number": "081234567890",
		"created_at": "2026-06-17T12:00:00Z",
		"updated_at": "2026-06-17T12:00:00Z"
	}
]
```

- Error responses:
	- 500 Internal Server Error (same format as above)

---

### GET /api/v1/customers/{id}
- Method: `GET`
- URL: `/api/v1/customers/{id}`
- Description: Mendapatkan customer berdasarkan ID.
- Request path params:
	- `id` (Long) — ID customer
- Success response (200 OK):

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

- Error responses:
	- 404 Not Found (CustomerNotFoundException):

```json
{
	"success": false,
	"code": "CUSTOMER_NOT_FOUND",
	"message": "Customer dengan ID {id} tidak ditemukan",
	"errors": [],
	"data": null
}
```

	- 500 Internal Server Error

---

### PUT /api/v1/customers/{id}
- Method: `PUT`
- URL: `/api/v1/customers/{id}`
- Description: Memperbarui seluruh data customer (replace semua field).
- Request body (application/json): sama schema dengan `POST`.

- Success response (200 OK): updated `CustomerResponse` (lihat contoh GET/POST)

- Error responses:
	- 400 Bad Request (validation error)
	- 404 Not Found (jika ID tidak ada)
	- 500 Internal Server Error

---

### PATCH /api/v1/customers/{id}
- Method: `PATCH`
- URL: `/api/v1/customers/{id}`
- Description: Memperbarui sebagian field customer (opsional fields).
- Request body (application/json) — contoh:

```json
{
	"email": "baru@example.com"
}
```

- Allowed fields: `full_name`, `email`, `phone_number` (semua optional; validation applied if present)
- Success response (200 OK): updated `CustomerResponse`
- Error responses:
	- 400 Bad Request (validation error)
	- 404 Not Found
	- 500 Internal Server Error

---

Notes:
- Error response structure for 400/404/500 follows `ErrorResponse`:

```json
{
	"success": false,
	"code": "<ERROR_CODE>",
	"message": "<message>",
	"errors": [ { "field": "<field>", "message": "<msg>" } ],
	"data": null
}
```

Status codes used:
- `201 Created` — for successful POST
- `200 OK` — for successful GET/PUT/PATCH
- `204 No Content` — for successful DELETE
- `400 Bad Request` — validation errors
- `404 Not Found` — missing resource (CustomerNotFoundException)
- `500 Internal Server Error` — unexpected errors

## Endpoints

### 1 Create Customer
- Method: `POST`
- URL: `/api/v1/customers`
- Description: Membuat Customer Baru
- Request body (JSON):

```json
{
	"full_name": "Andyana Cantik",
	"email": "Andyana@example.com",
	"phone_number": "081234567890"
}
```

- Validation:
	- `full_name`: required, max 100 characters
	- `email`: required, must be valid email
	- `phone_number`: required, 10-12 characters

- Success Response (201 Created):

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "Andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

### 2) Get Customer by ID
- Method: `GET`
- URL: `/api/v1/customers/{id}`
- Description: Mendapatkan data customer berdasarkan ID
- Success Response (200 OK):

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "Andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

- Error Response (404 Not Found):

```json
{
	"success": false,
	"code": "CUSTOMER_NOT_FOUND",
	"message": "Customer dengan ID {id} tidak ditemukan",
	"errors": [],
	"data": null
}
```

### 3) Get All Customers
- Method: `GET`
- URL: `/api/v1/customers`
- Description: Mendapatkan daftar semua customer
- Success Response (200 OK):

```json
[
	{
		"id": 1,
		"full_name": "Andyana Cantik",
        "email": "Andyana@example.com",
        "phone_number": "081234567890",
		"created_at": "2026-06-17T12:00:00Z",
		"updated_at": "2026-06-17T12:00:00Z"
	}
]
```

### 4) Update Customer (Full)
- Method: `PUT`
- URL: `/api/v1/customers/{id}`
- Description: Memperbarui data customer (replace semua field)
- Request body: sama seperti Create Customer
- Success Response (200 OK): sama format `CustomerResponse`

### 5) Patch Customer (Partial)
- Method: `PATCH`
- URL: `/api/v1/customers/{id}`
- Description: Memperbarui sebagian field customer (opsional)
- Request body (contoh):

```json
{
	"email": "baru@example.com"
}
```

- Success Response (200 OK): updated `CustomerResponse`

### 6) Delete Customer
- Method: `DELETE`
- URL: `/api/v1/customers/{id}`
- Description: Menghapus data customer berdasarkan ID
- Success Response: `204 No Content`

## Response formats — Success & Error (lengkap)

Semua error responses menggunakan struktur `ErrorResponse` kecuali endpoint sukses yang mengembalikan `CustomerResponse` atau `204 No Content` untuk penghapusan.

1) Create Customer — Success (201 Created)

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

2) Get / Update / Patch — Success (200 OK)

```json
{
	"id": 1,
	"full_name": "Andyana Cantik",
	"email": "andyana@example.com",
	"phone_number": "081234567890",
	"created_at": "2026-06-17T12:00:00Z",
	"updated_at": "2026-06-17T12:00:00Z"
}
```

3) Delete — Success (204 No Content)

- Response body: kosong

---

4) Validation Error (400 Bad Request)

Contoh yang dikembalikan oleh `GlobalExceptionHandler` saat validation gagal (MethodArgumentNotValidException / HandlerMethodValidationException):

```json
{
	"success": false,
	"code": "VALIDATION_ERROR",
	"message": "Invalid request",
	"errors": [
		{ "field": "full_name", "message": "Mohon Isi Nama Lengkap Anda" },
		{ "field": "email", "message": "Format email tidak valid" }
	],
	"data": null
}
```

Notes: field names dikonversi ke snake_case oleh handler (mis. `fullName` -> `full_name`). Pesan validasi berasal dari anotasi DTO:

- "Mohon Isi Nama Lengkap Anda"
- "Nama Lengkap maksimal 100 karakter"
- "Mohon Isi Email Anda"
- "Format email tidak valid"
- "Mohon Isi Nomor Telepon Anda"
- "Nomor Telepon harus antara 10-12 karakter"

5) Not Found (404)

Contoh untuk `CustomerNotFoundException`:

```json
{
	"success": false,
	"code": "CUSTOMER_NOT_FOUND",
	"message": "Customer dengan ID 5 tidak ditemukan",
	"errors": [],
	"data": null
}
```

6) IllegalArgumentException mapping (treated as Not Found in handler)

Jika suatu `IllegalArgumentException` dilempar dan memiliki pesan, handler akan meneruskan pesan tersebut (status 404):

```json
{
	"success": false,
	"code": "CUSTOMER_NOT_FOUND",
	"message": "{exception message}",
	"errors": [],
	"data": null
}
```

7) Internal Server Error (500)

Generic exception handler mengembalikan:

```json
{
	"success": false,
	"code": "INTERNAL_SERVER_ERROR",
	"message": "Internal server error: {exception message}",
	"errors": [],
	"data": null
}
```

---

Ringkasan struktur `ErrorResponse`:

```json
{
	"success": false,
	"code": "<ERROR_CODE>",
	"message": "<human readable message>",
	"errors": [ { "field": "<field_name>", "message": "<error message>" } ],
	"data": null
}
```


## Daftar Semua Pesan (Sukses & Error)

Berikut semua pesan teks yang muncul di kode sumber (Controller, DTO, Service, Exception, dan GlobalExceptionHandler): 

1) Pesan sukses / deskripsi di anotasi `@ApiResponses`:
	- "Data Customer Berhasil Dibuat"
	- "Data Customer Ditemukan"
	- "Data Customer Berhasil Diperbarui"
	- "Data Customer Berhasil Dihapus"
	- "Permintaan tidak sesuai"
	- "Terjadi kesalahan pada server"

2) Pesan sukses umum di `ErrorResponse.success`:
	- code: "200"
	- message: "Success"

3) Kode error yang dipakai oleh handler:
	- "VALIDATION_ERROR"
	- "CUSTOMER_NOT_FOUND"
	- "INTERNAL_SERVER_ERROR"

4) Pesan exception / runtime:
	- `CustomerNotFoundException` dibuat dengan pesan: "Customer dengan ID {id} tidak ditemukan"
	- `IllegalArgumentException`: pesan aslinya diteruskan (`ex.getMessage()`)
	- General exception: handler mengembalikan "Internal server error: {exception message}"

5) Pesan validasi (persis seperti di anotasi DTO):
	- "Mohon Isi Nama Lengkap Anda"
	- "Nama Lengkap maksimal 100 karakter"
	- "Mohon Isi Email Anda"
	- "Format email tidak valid"
	- "Mohon Isi Nomor Telepon Anda"
	- "Nomor Telepon harus antara 10-12 karakter"


