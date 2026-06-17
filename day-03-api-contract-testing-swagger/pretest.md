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
API contract ialah kontrak yang dibangun untuk aturan request dan response dari endpoint-endpoint API
```

### 2. Kenapa API contract penting?

Jawaban:

```text
Agar FrontEnd tau aturan dan panduan untuk melakukan aksi CRUD ke endpoint yang disediakan BackEnd
```

### 3. Apa saja isi API contract?

Jawaban:

```text
Panduan dan aturan request - response dari endpoint API yang disediakan BackEnd
```

### 4. Apa itu endpoint?

Jawaban:

```text
Titik akses (URL) dalam API yang digunakan untuk operasi CRUD
```

### 5. Apa itu HTTP method?

Jawaban:

```text
Metode/jenis operasi yang digunakan untuk melakukan operasi terhadap endpoint, baik itu mendapatkan maupun mengolah data
```

### 6. Apa itu request body?

Jawaban:

```text
Data tambahan yang dikirim oleh client ke endpoint API
```

### 7. Apa itu response body?

Jawaban:

```text
Jawaban yang dikirim dari server/BackEnd ke client/FrontEnd melalui endpoint API
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
Jenis code yang mendefinisikan jenis jawaban dari endpoint API, apakah itu berhasil ataupun gagal
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Agar FrontEnd tahu cara melakukan operasi ke endpoint yang disediakan BackEnd di API
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
FrontEnd tidak tahu panduan, sehingga pada saat mengakses endpoint URL API, akan error. Bisa saja Bad Request (400), dan bisa saja sampai Internal Server Error (500)
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
Data Transfer Object, class yang digunakan untuk menangani response dan request dari controller (pintu masuk client ke API)
```

### 12. Apa itu request DTO?

Jawaban:

```text
DTO yang meng-handle request body yang didapatkan dari request body API
```

### 13. Apa itu response DTO?

Jawaban:

```text
DTO yang meng-handle data dari API untuk dikirim ke client sebagai response/jawaban
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Karena tujuan penggunaannya beda. DTO untuk meng-handle request dan response client, dan Model sendiri merupakan representasi tabel di database yang dibuat dalam bentuk kode pemrograman
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Agar JSON mudah dibaca lintas bahasa, sedangkan Java menggunakan camelCase karena mengikuti konvensi penamaan resmi dan standar gaya dari bahasa Java itu sendiri
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Anotasi yang digunakan untuk mengubah key response ke API yang awalnya camelCase menjadi snake_case
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
Method untuk menambahkan data baru
```

### 18. Apa fungsi GET?

Jawaban:

```text
Method untuk mendapatkan data
```

### 19. Apa fungsi PUT?

Jawaban:

```text
Mehtod untuk mengubah/memperbarui data dengan mengubah keseluruhan data di resource-nya
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
Mehtod untuk mengubah/memperbarui data dengan mengubah data sesuai key yang dikirim saja
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk mengganti seluruh resource, sedangkan PATCH digunakan untuk memperbarui sebagian resource saja
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika data berhasil ditambahkan
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika API me-response ke client dengan baik, biasanya digunakan untuk GET
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika data yang dikirim dari client tidak sesuai dengan format yang disediakan dari BackEnd
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika data yang diminta oleh user tidak ditemukan, biasanya untuk method GET by id
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
Testing yang dilakukan apakah API sudah valid dan tidak ada error
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Untuk memastikan agar API sudah siap untuk di-consume oleh client/FrontEnd
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman, dan bisa juga Swagger
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Response dan request dari API sudah sesuai dengan yang ada di API contract
```

### 30. Apa itu expected response?

Jawaban:

```text
Response yang seharusnya ditampilkan/dikeluarkan dari API
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Tools untuk API testing
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
Standar spesifikasi untuk mendefinisikan dan mendokumentasikan REST API secara terstruktur
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Untuk memudahkan dalam testing request dan response dari API yang dibuat
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman adalah aplikasi tersendiri untuk menguji dan mengirim request API secara langsung, sedangkan Swagger UI adalah halaman yang ditambahkan di project yang bisa diakses berbasis web dan berguna sebagai dokumentasi interaktif untuk melihat dan mencoba endpoint API berdasarkan spesifikasi
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Menurut saya, Swagger tidak sepenuhnya menggantikan dokumentasi API manual, tetapi bisa efektif sebagai dokumentasi teknis otomatis yang akurat, sementara dokumentasi manual tetap dibutuhkan untuk konteks bisnis dan penjelasan konseptual
```

## Self Assessment

| Area         | Score 1-5 |
| ------------ | --------- |
| API contract | 4         |
| DTO          | 5         |
| HTTP method  | 5         |
| API testing  | 4         |
| Swagger UI   | 4         |
| OpenAPI      | 4         |

## Notes

```text
- Cara membuat API Contract secara best practice bagaimana?
- Apa saja komponen yang harus ada di API Contract?
```
