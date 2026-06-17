# API Contract - Customer Management System v2

### 1. Create Customer
* **Method**: `POST`
* **URL**: `/api/v2/customers`
* **Description**: Menambahkan data customer baru ke database H2 dengan validasi mandatory field.
* **Request Body** (`application/json`):
    ```json
    {
      "full_name": "Lala",
      "email": "lala@gmail.com",
      "phone_number": "088888888"
    }
    ```
* **Success Response**:
    * **Status Code**: `201 Created`
    * **Body**:
        ```json
        {
          "code": "201",
          "message": "Successfully created new customer",
          "data": {
            "id": 1,
            "full_name": "Alexander Wright",
            "email": "alex.wright@example.com",
            "phone_number": "0812345678"
          },
          "timestamp": "2026-06-17T14:52:19.123"
        }
        ```
* **Error Response (Gagal Validasi)**:
    * **Status Code**: `400 Bad Request`
    * **Body**:
        ```json
        {
          "code": "400",
          "message": "Validation failed",
          "data": [
            {
              "field": "email",
              "message": "Format email tidak valid"
            }
          ],
          "timestamp": "2026-06-17T14:53:01.456"
        }
        ```

---

### 2. Get Customer List
* **Method**: `GET`
* **URL**: `/api/v2/customers`
* **Description**: Mendapatkan seluruh daftar customer tanpa filter dari database H2.
* **Request Body**: *None*
* **Success Response**:
    * **Status Code**: `200 OK`
    * **Body**:
        ```json
        {
          "code": "200",
          "message": "Successfully fetched all customers",
          "data": [
            {
              "id": 1,
              "full_name": "Alexander Wright",
              "email": "alex.wright@example.com",
              "phone_number": "0812345678"
            }
          ],
          "timestamp": "2026-06-17T14:54:10.000"
        }
        ```

---

### 3. Get Customer By ID
* **Method**: `GET`
* **URL**: `/api/v2/customers/{id}`
* **Description**: Mencari data satu customer yang sesuai dengan ID dari database.
* **Request Body**: *None*
* **Success Response**:
    * **Status Code**: `200 OK`
    * **Body**:
        ```json
        {
          "code": "200",
          "message": "Successfully fetched customer",
          "data": {
            "id": 1,
            "full_name": "Alexander Wright",
            "email": "alex.wright@example.com",
            "phone_number": "0812345678"
          },
          "timestamp": "2026-06-17T14:55:00.111"
        }
        ```
* **Error Response (ID Tidak Ditemukan)**:
    * **Status Code**: `404 Not Found`
    * **Body**:
        ```json
        {
          "code": "404",
          "message": "Customer dengan ID 999 tidak ditemukan",
          "data": null,
          "timestamp": "2026-06-17T14:55:25.222"
        }
        ```

---

### 4. Update Customer (PUT)
* **Method**: `PUT`
* **URL**: `/api/v2/customers/{id}`
* **Description**: Mengganti/menimpa seluruh data customer lama dengan data baru berdasarkan ID. Semua field di Request Body wajib diisi.
* **Request Body** (`application/json`):
    ```json
    {
      "full_name": "Alexander Wright Jr",
      "email": "alex.jr@example.com",
      "phone_number": "0899999999"
    }
    ```
* **Success Response**:
    * **Status Code**: `200 OK`
    * **Body**:
        ```json
        {
          "code": "200",
          "message": "Successfully updated customer",
          "data": {
            "id": 1,
            "full_name": "Alexander Wright Jr",
            "email": "alex.jr@example.com",
            "phone_number": "0899999999"
          },
          "timestamp": "2026-06-17T14:56:05.888"
        }
        ```

---

### 5. Patch Customer (PATCH)
* **Method**: `PATCH`
* **URL**: `/api/v2/customers/{id}`
* **Description**: Memperbarui sebagian data customer. Hanya field yang dikirim di request body yang berubah, sisanya tetap mempertahankan data lama di database.
* **Request Body** (`application/json` - *Contoh hanya update nomor telepon*):
    ```json
    {
      "phone_number": "0855555555"
    }
    ```
* **Success Response**:
    * **Status Code**: `200 OK`
    * **Body**:
        ```json
        {
          "code": "200",
          "message": "Successfully updated customer",
          "data": {
            "id": 1,
            "full_name": "Alexander Wright Jr",
            "email": "alex.jr@example.com",
            "phone_number": "0855555555"
          },
          "timestamp": "2026-06-17T14:57:40.999"
        }
        ```