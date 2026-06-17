# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
kesepakatan antara backend dan frontend tentang bagaimana api digunakan. Isinya menjelaskan endpoint, request, response, dan aturan yang harus diikuti
```

### 2. Apa saja isi API contract?

Jawaban:

```text
berisi method (GET, POST, put,patch,delete), URL endpoint, request body, response body, status code, dan deskripsi api
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
karena frontend bisa mulai develop tanpa harus menunggu backend selesai. mereka sudah tahu struktur data yang akan dipakai dari api contract
```

### 4. Apa itu DTO?

Jawaban:

```text
adalah object yang digunakan untuk membawa data antara client dan server
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
request dto digunakan untuk menerima data dari client

response dto digunakan untuk mengirim data ke client
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
supaya lebih aman dan fleksibel karena kita bisa kontrol data apa yang boleh masuk atau keluar tanpa tergantung langsung ke model database

```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk meng konversi data dari camelCase java ke snake_case yang mudah dibaca json
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
untuk mengambil data dari body request yang biasanya berbentuk json ke dalam object java
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
untuk mengambil nilai dari URL path, misalnya /customers/{id}
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
untuk mengaktifkan validasi pada input request sesuai aturan di dto
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
post untuk membuat data baru

get untuk mengambil data

```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
put untuk update seluruh data

patch hanya update sebagian field
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
saat berhasil membuat data baru
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
saat request berhasil, biasanya untuk get atau update
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
saat input dari client tidak valid atau salah format
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat data yang dicari tidak ditemukan
```

### 17. Apa itu API testing?

Jawaban:

```text
proses untuk memastikan api berjalan sesuai contract dan menghasilkan response yang benar
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
status code, response body, validation, error handling, dan apakah sesuai dengan API contract
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
actual response adalah hasil dari API saat dijalankan

expected response adalah hasil yang seharusnya sesuai contract
```

### 20. Apa itu Swagger?

Jawaban:

```text
swagger adalah tools untuk dokumentasi api yang bisa menampilkan endpoint dan digunakan untuk testing
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
standar spesifikasi untuk mendefinisikan api
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
memudahkan developer melihat, memahami, dan mencoba api langsung dari browser
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
untuk mengelompokkan api di swagger
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
untuk memberikan deskripsi pada endpoint api
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
untuk mendokumentasikan response yang bisa dihasilkan oleh api
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
swagger ui untuk dokumentasi dan testing sederhana di browser

postman untuk testing api secara manual dan lebih comperhensif
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
cek dependency swagger, konfigurasi path, dan pastikan aplikasi sudah running
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
membuat best practise dalam pembuatan api contract
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. cara membuat api contract untuk komunikasi FE dan BE
2. cara set swagger pada project untuk dokumentasi
3. cara menggunakan tag, operation, dan api response
```

Apa 2 hal yang masih membingungkan?

```text
1.  best practice dalam menentukan response format yang konsisten
2.  bingung dokumentasi swagger seperti api response dan operation ini skenario harus ditaruh sampai level production kah atau cukup pas development saja
```

Apa 1 pertanyaan untuk mentor?

```text
bagaimana best practice dalam menentukan response format yang konsisten ketika project sudah besar
```
