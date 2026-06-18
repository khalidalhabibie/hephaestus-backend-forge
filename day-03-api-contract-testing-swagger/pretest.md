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
API contract adalah sekumpulan kesepakatan untuk berjalannya API antara frontend dan backend.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
Agar API bisa berjalan dan sudah di-mapping agar setiap request mapping nya jelas akan mengarah ke service apa saja, menjalankan logic apa saja, dan me-return apa saja.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
HTTP status code, endpoint, request header & body, response header & body, path parameter, query parameter, request method, naming convention, dsb.
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL atau tujuan yang diarahkan oleh Controller agar bisa menjalankan Service untuk mengakses data tertentu.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP method adalah jenis-jenis operasi pada API yang bisa dijalankan saat client mengirim request ke server, seperti GET, POST, PUT, PATCH, DELETE.
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah satu badan data yang dikirim oleh client ke server saat melakukan request.
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah satu badan data yang dikirim oleh server ke client saat merespon request dari client.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah code yang ter-generate dan ditampilkan untuk menunjukkan status response dari request yang dikirim oleh client, misalnya kode 200 apabila requestnya berhasil di-return, kode 201 apabila datanya berhasil dibuat, dsb.
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Supaya sisi client dan server bisa berkomunikasi dengan jelas sehingga sistem bekerja dengan benar.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Maka program tidak bisa berjalan dengan seharusnya, misalnya terjadi "salah alamat" di mana operasi yang dijalankan tidak sesuai dengan URL yang di-input.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah object yang berisi data-data yang dikirim untuk menjalankan komunikasi antara client dan server, seperti request body dan response body.
```

### 12. Apa itu request DTO?

Jawaban:

```text
Request DTO adalah objek atau struktur yang digunakan untuk menerima data dari client.
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah objek atau struktur yang digunakan untuk mengirim data ke client.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Karena model merupakan representasi dari database, sedangkan DTO adalah object yang digunakan untuk menerima dan merespon data dari client. Keduanya memiliki fungsi yang berbeda (DTO untuk berkomunikasi dengan client, sedangkan model untuk connect ke database) sehingga sebaiknya dipisah.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
JSON menggunakan snake_case agar lebih fleksibel dan bisa dibaca oleh berbagai bahasa, sedangkan Java menggunakan camelCase agar lebih mudah dibaca.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk menyesuaikan field name dengan naming convention JSON (snake_case)
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
Untuk membuat object baru berdasarkan data di request body.
```

### 18. Apa fungsi GET?

Jawaban:

```text
Untuk mengakses atau membaca data.
```

### 19. Apa fungsi PUT?

Jawaban:

```text
Untuk meng-update data pada database.
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
Untuk meng-update data pada database, tanpa perlu mengirim semua data atau attribute.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
Saat menggunakan PUT, kita harus mengirim ulang semua data atau attribute meskipun mungkin hanya sebagian attribute saja yang ingin di-update. Sedangkan pada PATCH, kita bisa mengirim data yang ingin di-update saja.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika menjalankan operasi POST dan object berhasil dibuat.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika request yang dikirim berhasil diproses dengan sukses, misalnya GET.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika input dari user tidak valid.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource yang di-request tidak ditemukan.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API Testing adalah proses testing untuk mengecek apakah API nya berjalan dan mengirimkan request dan mengembalikan response yang sesuai.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Untuk memastikan bahwa komunikasi frontend dan backend berjalan dengan lancar dan benar.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Yang saya tahu hanya Postman dan Swagger.
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Endpoint dan method, HTTP status code, response header & body, request header & body , error handling, dsb.
```

### 30. Apa itu expected response?

Jawaban:

```text
Response yang diharapkan sebagai standar untuk positive case.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah salah satu tools untuk melakukan API testing.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standar untuk mendeskripsikan API secara jelas dan terstruktur.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI adalah tools untuk menampilkan dan melakukan testing API.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman -> tools untuk testing API secara fleksibel
Swagger UI -> tools untuk testing API berdasarkan dokumentasi sehingga lebih terstruktur
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Tidak sepenuhnya, karena Swagger bisa melakukan dokumentasi API secara teknis seperti endpoint, status code, request & response body, dsb, tapi tidak bisa meng-capture kebutuhan atau aturan bisnis.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |2|
| DTO |2|
| HTTP method |2|
| API testing |2|
| Swagger UI |1|
| OpenAPI |1|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
