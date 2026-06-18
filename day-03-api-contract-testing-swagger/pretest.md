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
API contract adalah kesepakatan yang berisikan bagaimana sebuah API digunakan, sehingga dapat menjadi patokan alur komunikasi yang tejadi antara client dan server.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
API contract penting karena menjadi patokan bagi team developer mulai dari backend sampai frontend dalam menggunakan API.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
- Endpoint path;
- HTTP method;
- Request and response body;
- Parameter; and;
- etc.
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL tujuan untuk mengakses resource melalui API.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP method adalah method atau yang digunakan untuk mengakses resource melalui API sesuai dengan tujuan yang diinginkan (e.g. GET, POST, DELETE, etc).
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim oleh client ke server sebagai bentuk permintaan yang diinginkan, seperti misalnya ingin menambahkan data maka request body diisi sebagai bentuk permintaan tersebut.
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah data yang dikirim oleh server sebagai bentuk balasan dari permintaan client, seperti misalnya apakah request-nya error karena data tidak valid.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah kode numerik yang menunjukkan hasil dari permintaan client ke server, kode tersebut dapat mengidentifikasi jenis error yang terjadi atau keberhasilan yang terjadi (e.g. 404 Bad Request, etc).
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Request dan response perlu ditulis jelas agar pembacaan proses setiap transfer yang terjadi antara client dan server dapat mudah dipahami, sehingga setiap error yang terjadi dapat diatasi dengat cepat dan benar.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Risikonya adalah komunikasi antardeveloper tidak dapat berjalan dengan konsisten karena tidak terdapat panduan yang menjadi patokan dalam menggunakan API. Tidak konsistennya yang terjadi dapat membuat kode-kode menjadi tidak mudah dipahami dan penanganannya menjadi lambat.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO adalah object yang menjalankan transfer data antar sistem tanpa logic.
```

### 12. Apa itu request DTO?

Jawaban:

```text
Request DTO adalah DTO yang digunakan untuk menerima data dari client ke server.
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah DTO yang digunakan untuk mengirim data dari server ke client. 
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Tulis jawaban di sini.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Tulis jawaban di sini.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty berfungsi untuk memetakan field dengan atribut pada class.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
POST berfungsi untuk membuat atau menambahkan data baru ke server (e.g. Customer baru).
```

### 18. Apa fungsi GET?

Jawaban:

```text
GET berfungsi untuk mengambil atau membaca data dari server (e.g. Customer dengan ID tertentu).
```

### 19. Apa fungsi PUT?

Jawaban:

```text
PUT berfungsi untuk memperbarui seluruh data yang sudah ada (e.g. mengubah seluruh data Customer dari ID hingga salary)
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
PATCH berfubgsi untuk memperbarui sebagian data tanpa harus mengirim seluruh data (e.g. mengubah sebagian data Customer, misalnya ID saja).
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk mengganti atau memperbarui seluruh data, sedangkan PATCH hanya memperbarui data tertentu yang berubah. PUT biasanya mengirim seluruh data, sementara PATCH hanya mengirim data yang ingin diubah.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika request berhasil membuat resource baru di server (e.g. biasanya setelah operasi POST).
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketika request berhasil diproses dan server mengembalikan hasil yang diminta.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request yang dikirim client tidak valid (e.g. format data salah atau ada field wajib yang tidak diisi).
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika endpoint yang diminta tidak ditemukan di server.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API testing adalah proses pengujian API untuk memastikan bahwa seluruh proses sesuai dengan spesifikasi yang telah ditentukan.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
API perlu di-test untuk memastikan API berfungsi dengan benar, menghasilkan data yang sesuai, dan menangani error dengan baik.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Tulis jawaban di sini.
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Tulis jawaban di sini.
```

### 30. Apa itu expected response?

Jawaban:

```text
Expected response adalah hasil yang diharapkan dari suatu request API berdasarkan spesifikasi atau API contract.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah kumpulan tools yang digunakan untuk menguji API.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
Tulis jawaban di sini.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Tulis jawaban di sini.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman -> tool yang lebih lengkap untuk membuat, mengelola, mengotomasi, dan menguji berbagai request API.
Swagger UI -> dokumentasi dan pengujian API.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Tulis jawaban di sini.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract | 2 |
| DTO | 3 |
| HTTP method | 3 |
| API testing | 3 |
| Swagger UI | 1 |
| OpenAPI | 1 |

## Notes

```text
Mungkin lebih keselurahan ketika dipraktikan.
```
