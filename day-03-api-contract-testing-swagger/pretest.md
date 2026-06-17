# Pretest - API Contract, API Testing & Swagger

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang API contract, API testing, dan Swagger.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - API Contract

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah sebuah dokumentasi atau rancangan dari API yang akan dibuat dalam suatu program.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
API contract penting untuk memastikan bahwa API yang dibuat sesuai dengan kesepakatan yang dibuat sejak awal dan untuk mempermudah developer dalam membangun API.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
Isi dari API contract dapat berupa API apa saja yang dibuat, endpoint dari masing masing API, request body, dan response body serta berbagai atribut API lainnya
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah alamat atau URL spesifik yang digunakan untuk mengakses suatu fungsi atau resource pada API
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP method adalah jenis aksi yang dilakukan ke endpoint, seperti GET, POST, PUT, dan DELETE
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim oleh client ke server, biasanya untuk create atau update data
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah data yang dikirim oleh server ke client sebagai hasil dari request
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah kode angka yang menunjukkan hasil dari request, apakah berhasil atau gagal
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Agar client dan server memiliki pemahaman yang sama sehingga menghindari kesalahan integrasi
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Risikonya adalah miskomunikasi, bug saat integrasi, dan perubahan API yang sulit dikontrol
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek yang digunakan untuk mengirim data antar layer atau antar sistem
```

### 12. Apa itu request DTO?

Jawaban:

```text
Request DTO adalah DTO yang merepresentasikan data yang diterima dari client
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah DTO yang merepresentasikan data yang dikirim kembali ke client
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
DTO dan model sebaiknya dipisah untuk memastikan bahwa tidak semua data yang ada di model ditampilkan di client sehingga dibuatlah DTO untuk melakukan filter field yang ditampilkan.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena mengikuti konvensi masing-masing, JSON lebih umum snake_case sedangkan Java standar camelCase
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk memetakan nama field JSON ke field Java yang berbeda namanya
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
Untuk membuat data baru
```

### 18. Apa fungsi GET?

Jawaban:

```text
Untuk mengambil atau membaca data
```

### 19. Apa fungsi PUT?

Jawaban:

```text
Untuk memperbarui seluruh data yang sudah ada
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
Untuk memperbarui sebagian data yang sudah ada
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT mengganti seluruh data, sedangkan PATCH hanya mengubah bagian tertentu
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Saat request berhasil membuat data baru
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat request berhasil diproses tanpa membuat resource baru
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Saat request dari client tidak valid atau format datanya salah
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Saat resource yang diminta tidak ditemukan
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API testing adalah proses pengujian API untuk memastikan fungsinya berjalan sesuai contract
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Untuk memastikan API bekerja dengan benar, stabil
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman, Swagger UI
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Status code, response body, struktur data, dan error handling
```

### 30. Apa itu expected response?

Jawaban:

```text
Response yang diharapkan sesuai dengan API contract dan skenario pengujian
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tool untuk mendokumentasikan dan menguji API berbasis OpenAPI
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standar spesifikasi untuk mendefinisikan API secara terstruktur
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Memudahkan membaca dokumentasi API dan mencoba endpoint secara langsung
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman fokus ke testing, Swagger UI fokus ke dokumentasi sekaligus testing ringan
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Sebagian besar bisa, karena Swagger sudah cukup lengkap, tetapi dokumentasi manual tetap diperlukan untuk penjelasan bisnis.
```

## Self Assessment

| Area         | Score 1-5 |
| ------------ | --------- |
| API contract |     3     |
| DTO          |     4     |
| HTTP method  |     4     |
| API testing  |     3     |
| Swagger UI   |     3     |
| OpenAPI      |     3     |

## Notes

```text
Bagaimana membuat unit test API apabila didalamnya diperlukan adanya authentication?
```
