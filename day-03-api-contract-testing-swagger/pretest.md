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
kesepakatan antara backend dan frontend tentang bagaimana API digunakan (endpoint, request, response, dll)
```

### 2. Kenapa API contract penting?

Jawaban:

```text
agar tim frontend dan backend bisa bekerja konsisten tanpa miskomunikasi dalam API
```

### 3. Apa saja isi API contract?

Jawaban:

```text
Endpoint, HTTP method, request, response, status code, dan format data
```

### 4. Apa itu endpoint?

Jawaban:

```text
URL yang digunakan untuk mengakses API
```

### 5. Apa itu HTTP method?

Jawaban:

```text
tipe operasi pada API, seperti GET, POST, PUT, DELETE
```

### 6. Apa itu request body?

Jawaban:

```text
data yang dikirim dari client ke server, isinya dalam bentuk JSON
```

### 7. Apa itu response body?

Jawaban:

```text
data yang dikirim dari server ke client, isinya dalam bentuk JSON sebagai hasil request (jawaban dari request)
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
kode yang menunjukkan hasil dari request
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
agar tidak terjadi kesalahan integrasi antara frontend dan backend
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
bisa menyebabkan bug, miskomunikasi, dan integrasi gagal
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
object untuk transfer data antara layer (dari controller ke client)
```

### 12. Apa itu request DTO?

Jawaban:

```text
DTO yang digunakan untuk menerima data dari client
```

### 13. Apa itu response DTO?

Jawaban:

```text
DTO yang digunakan untuk mengirim data ke client
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
agar lebih aman, fleksibel, dan tidak terhubung ke database langsung
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
karena standar JSON biasanya snake_case, sedangkan Java camelCase
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mapping nama field JSON ke field di Java
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
untuk membuat data baru
```

### 18. Apa fungsi GET?

Jawaban:

```text
untuk mengambil data
```

### 19. Apa fungsi PUT?

Jawaban:

```text
untuk update seluruh data
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
untuk update sebagian data
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
- PUT update semua field
- PATCH hanya sebagian field
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
saat berhasil membuat data baru
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
saat request berhasil diproses
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
saat request tidak valid atau error dari client
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat data yang dicari tidak ditemukan
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
menguji API untuk memastikan berjalan sesuai fungsi
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
memastikan API bekerja benar dan tidak ada bug
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman dan Swagger UI
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
status code, response body, error handling, dan validasi data
```

### 30. Apa itu expected response?

Jawaban:

```text
response yang diharapkan sesuai dengan API contract
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
tool untuk dokumentasi dan testing API secara otomatis
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
standar spesifikasi untuk mendefinisikan API
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
untuk melihat dan mencoba API langsung di browser
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Swagger untuk dokumentasi dan testing API, sedangkan Postman fokus untuk testing API saja
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
bisa, tapi tidak sepenuhnya menggantikan karena dokumentasi manual masih dibutuhkan untuk penjelasan detail bisnis
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |3|
| DTO |3|
| HTTP method |3|
| API testing |3|
| Swagger UI |3|
| OpenAPI |3|

## Notes

```text
Tulis bagian yang masih membingungkan.
```