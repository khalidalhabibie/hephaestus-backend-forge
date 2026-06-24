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

```
API contract adalah kesepakatan formal antara penyedia API (backend) dan konsumen API (frontend/mobile/third-party) mengenai bagaimana data akan dipertukarkan, mencakup struktur request dan response.
```

### 2. Kenapa API contract penting?

Jawaban:

```
Penting sebagai 'single source of truth' agar tim backend dan frontend bisa bekerja secara paralel tanpa saling tunggu, serta meminimalisir salah komunikasi terkait integrasi data.
```

### 3. Apa saja isi API contract?

Jawaban:

```
Endpoint (URL), HTTP Method, Request Headers, Path/Query Parameters, Request Body (jika ada), Response Status Code, dan Response Body beserta tipe datanya.
```

### 4. Apa itu endpoint?

Jawaban:

```
Alamat URL spesifik tempat API dapat diakses oleh client untuk berinteraksi dengan resource tertentu (contoh: `/api/v1/users`).
```

### 5. Apa itu HTTP method?

Jawaban:

```
Operasi atau aksi yang ingin dilakukan terhadap resource pada endpoint tersebut (contoh: GET, POST, PUT, DELETE).
```

### 6. Apa itu request body?

Jawaban:

```
Data atau payload yang dikirimkan oleh client ke server dalam bentuk format tertentu (biasanya JSON) saat melakukan aksi seperti membuat atau mengubah data.
```

### 7. Apa itu response body?

Jawaban:

```
Data atau payload yang dikembalikan oleh server kepada client sebagai hasil dari request yang telah diproses.
```

### 8. Apa itu HTTP status code?

Jawaban:

```
Kode angka standar dari server yang menginfokan kepada client tentang status keberhasilan atau kegagalan dari request yang dikirim (contoh: 200, 201, 400, 404, 500).
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```
Agar format data yang dikirim dan diterima valid (tidak menyebabkan error/crash di sisi client maupun server) dan memastikan validasi data berjalan semestinya.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```
Terjadinya miskomunikasi antar tim, proses integrasi yang hancur (integration error), pengerjaan ulang (rework), dan tertundanya rilis aplikasi.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```
Data Transfer Object; objek yang digunakan khusus untuk membawa data antar proses atau layer (misal dari client ke controller server) tanpa membawa business logic.
```

### 12. Apa itu request DTO?

Jawaban:

```
DTO yang memetakan dan menampung data input yang dikirim oleh client untuk diproses oleh server.
```

### 13. Apa itu response DTO?

Jawaban:

```
DTO yang digunakan untuk membungkus data hasil pemrosesan server yang hanya berisi informasi yang memang boleh dan perlu dilihat oleh client.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```
Untuk keamanan (menyembunyikan kolom sensitif database di Model, seperti password) dan fleksibilitas (struktur database tidak harus selalu sama persis dengan struktur yang dibutuhkan client).
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```
Karena itu adalah konvensi (best practice) bawaan dari masing-masing ekosistem: JSON lahir dari budaya web/JavaScript yang erat dengan snake_case/lowercase untuk properti API, sedangkan Java secara historis menggunakan camelCase untuk penamaan variabel.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```
Untuk memetakan (mapping) nama properti di JSON (misal: `user_id`) ke variabel di Java (misal: `userId`) saat proses serialisasi dan deserialisasi data.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```
Untuk membuat (create) resource baru di server.
```

### 18. Apa fungsi GET?

Jawaban:

```
Untuk mengambil atau membaca (read) data resource dari server.
```

### 19. Apa fungsi PUT?

Jawaban:

```
Untuk memperbarui (update) resource secara keseluruhan. Jika data belum ada, bisa berfungsi untuk membuatnya.
```

### 20. Apa fungsi PATCH?

Jawaban:

```
Untuk memperbarui (update) sebagian data dari suatu resource (parsial), bukan mengganti seluruh objek.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```
PUT mengganti seluruh data objek (idempotent), jika ada field yang absen maka field tersebut bisa hilang/null. PATCH hanya mengubah field spesifik yang dikirim tanpa mengganggu field lainnya.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```
Saat request (biasanya POST) berhasil dilakukan dan server sukses membuat resource baru.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```
Saat request (GET, PUT, PATCH, atau DELETE) berhasil diproses oleh server dan mengembalikan respon sukses standar.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```
Saat request dari client tidak valid, salah sintaks, atau ada validasi input data yang gagal di sisi server.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```
Saat resource atau endpoint yang dicari oleh client tidak ditemukan di server.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```
Proses pengujian perangkat lunak yang berfokus pada pengetesan fungsionalitas, performa, keamanan, dan keandalan dari API secara langsung tanpa melalui user interface (UI).
```

### 27. Kenapa API perlu dites?

Jawaban:

```
Untuk memastikan core business logic berjalan benar, mendeteksi bug lebih awal sebelum UI dibuat, serta menjamin integrasi data antar sistem aman.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```
Postman, Insomnia, RestAssured, JMeter, Swagger, atau Bruno.
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```
HTTP Status Code, struktur dan tipe data response body, kecepatan respon (response time), akurasi data, dan penanganan error (error handling).
```

### 30. Apa itu expected response?

Jawaban:

```
Response yang diharapkan muncul pada Happy path dan Failure Path sesuai dengan requirement yang ditetapkan
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```
Rangkaian alat (tools) open-source yang dibangun di sekitar OpenAPI Specification (OAS) untuk membantu mendesain, membangun, mendokumentasikan, dan mengonsumsi RESTful API.
```

### 32. Apa itu OpenAPI?

Jawaban:

```
Spesifikasi standar industri berbentuk format deskripsi API untuk RESTful API (biasanya ditulis dalam format YAML atau JSON).
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```
Mengubah visualisasi file OpenAPI/Swagger menjadi halaman web interaktif, sehingga developer bisa langsung membaca dokumentasi sekaligus mencoba (test run) API tersebut secara langsung.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```
Swagger UI otomatis digenerate dari code/spesifikasi server untuk visualisasi dokumentasi internal project tersebut. Postman adalah tools client/aplikasi eksternal independen yang serbaguna untuk membangun, mengetes, mengotomatisasi, dan mengelola berbagai API secara dinamis.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```
Menurut saya belum namun swagger adalah alat dokumentasi yang bisa sangat membantu dalam mendefinisikan sebuah API Contract yang dimiliki oleh sebuah aplikasi. Karena dalam API manual diperlukan juga context bisnis dan expected response yang ditetapkan oleh requirement dan kebutuhan bisnis. Jadi swagger bisa digunakan untuk pembuatan dokumentasi manual dan deskripsi.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract | 5|
| DTO | 5|
| HTTP method | 5|
| API testing |5 |
| Swagger UI | 5|
| OpenAPI | 5|

## Notes

```text
Tulis bagian yang masih membingungkan.
Dokumentasi best practice menggunakan swagger dan apakah lebih baik menggunakan swagger untuk dokumentasi atau tools lain.
```
