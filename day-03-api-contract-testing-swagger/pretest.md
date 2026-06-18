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
kesepakatan antara client dan server tentang bagaimana API digunakan, termasuk request dan response
```

### 2. Kenapa API contract penting?

Jawaban:

```text
agar client dan server memiliki pemahaman yang sama sehingga terhindar dari error dalam proses integrasi
```

### 3. Apa saja isi API contract?

Jawaban:

```text
1. Endpoint
2. http method (get,post, put, patch, delete)
3. request
4. response
5. error handling dan status code
6. autentikasi dan otorisasi
```

### 4. Apa itu endpoint?

Jawaban:

```text
url yang digunakan untuk mengakses api tertentu
```

### 5. Apa itu HTTP method?

Jawaban:

```text
jenis operasi yang dilakukan pada endpoint (get,post, put, patch, delete)
```

### 6. Apa itu request body?

Jawaban:

```text
data yang dikirim dari client ke server
```

### 7. Apa itu response body?

Jawaban:

```text
data yang dikirim dari server ke client sebagai hasil dari request
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
code yang menunjukkan hasil dari request (200, 201, 404, 500).
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
agar developer dari sisi frontend dan backend tidak salah dalam mengirim maupun menerima data
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
bisa terjadi bug, miskomunikasi di dalam sistem, integrasi gagal karena data yang tidak konsisten
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
objek yang membawa data antara layer tanpa membawa business logic
```

### 12. Apa itu request DTO?

Jawaban:

```text
dto yang digunakan untuk menerima data dari client
```

### 13. Apa itu response DTO?

Jawaban:

```text
dto yang digunakan untuk mengirim data ke client
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
agar model database tidak terekspos langsung dan lebih fleksibel dalam development
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
standar pengembangan JSON memang menggunakan snake_case jadi lebih mudah membaca file json dengan snake_case dibanding camelCase

java menggunakan camelCase memang karena sudah menjadi standar dalam pengembangan aplikasinya
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk meng konversi nama variable camelCase menjadi snake_case agar lebih mudah dibaca file json
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
untuk membuat data baru di database
```

### 18. Apa fungsi GET?

Jawaban:

```text
untuk mengambil data yang sudah tersedia di database
```

### 19. Apa fungsi PUT?

Jawaban:

```text
untuk mengubah data di database secara keseluruhan
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
untuk mengubah data di database secara sebagian
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT update seluruh data sedangkan PATCH hanya update sebagian data saja
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
saat kita berhasil membuat data baru
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
saat request seperti get, update, put, patch berhasil
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
saat request dari client tidak valid
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
pengujian api untuk memastikan berjalan sesuai contract yang sudah dibuat
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
agar memastikan api berfungsi sesuai kebutuhan dengan data yang konsisten
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
postman dan swagger
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
status code, response body, struktur data, dan error handling
```

### 30. Apa itu expected response?

Jawaban:

```text
response yang diharapkan sesuai dengan api contract yang dibuat
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
tools untuk dokumentasi dan testing api
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
format standar dalam mendesain dan mendokumentasikan api
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
untuk melihat dan bisa langsung mencoba api di browser
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
postman cuma tools untuk testing api manual, swagger adalah alat untuk melihat dan mendokumentasikan api
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
menurut saya bisa tergantikan secara menyeluruh karena saat ini rata rata developer sudah menggunakan swagger, tapi mungkin masih ada case khusus seperti mungkin penjelasan secara detail mengenai alur bisnis dari awal hingga akhir
```

## Self Assessment

| Area         | Score 1-5 |
| ------------ | --------- |
| API contract | 3         |
| DTO          | 3         |
| HTTP method  | 3         |
| API testing  | 3         |
| Swagger UI   | 2         |
| OpenAPI      | 2         |

## Notes

```text
saya masih bingung dengan concept openapi, apakah kita harus selalu membuat openapi terlebih dahulu kah sebelum swagger atau bisa langsung swagger
```
