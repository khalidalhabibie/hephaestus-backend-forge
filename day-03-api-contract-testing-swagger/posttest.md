# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API Contract adalah kesepakatan atau spesifikasi yang mendefinisikan bagaimana client dan server berinteraksi melalui API. API contract menjelaskan endpoint, method GET POST PUT PATCH DELETE, format request dan response, parameter, serta aturan yang harus diikuti oleh kedua pihak.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
Isi dari API Contract :
    1. Endpoint API
    2. HTTP Method
    3. Parameter Request
    4. Format Response
    5. Status code
    6. Rules and Constraint.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Contract API ini berfungsi sebagai perjanjian yang jelas antara backend dan frontend terkait bagaimana data dikirim dan diterima. Mengurangi ambiguitas, memungkinkan development secara bersamaan, menjaga konsistensi dan stabilitas.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO adalah objek yang digunakan untuk mengirim data antara client dan server.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO yang digunakan untuk membawa data dari client ke server serta melakukan request ke API.

Response DTO adalah DTO yang digunakan untuk mengirim data dari server ke client sebagai jawaban dari request api.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
agar memisahkan anatara sturktur data internal dengan data yang dikirim ke client / frontend. Sehingga data lebih aman, fleksibel dan mudah dikelola.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
Anotasi tersebut digunakan untuk mengconvert penamaan attribute menjadi mengikuti standar JSON yaitu snake case.
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
@RequestBody adalah anotasi di Spring Boot yang digunakan untuk mengambil data dari HTTP request body dan mengkonversinya menjadi objek Java.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
@PathVariable adalah anotasi digunakan pada Spring Boot untuk mengambil nilai dari url path dan memasukkannya ke dalam parameter method pada controller
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid adalah anotasi springboot untuk melakukan validasi terhadap data yang diterima.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data dari server. Sedangkan POST adalah digunakan untuk mengirim atau menyimpan data ke server.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT dan Patch sama sama untuk mempeberharui data pada server. PUT digunakan untuk mengupdate data secara keseluruhan. Sedangkan PATCH digunakan untuk mengupdate sebagian data saja.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika server backend berhasil menambahkan resource ke database.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat service backend berhasil mengakses dan mereturn data ke client.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika data yang dikirim frontend ke server sebagai request body itu tidak valid.
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika backend mengakses resource yang tidak ada.
```

### 17. Apa itu API testing?

Jawaban:

```text
Testing API itu adalah pengujian API yang telah dibuat oleh developer backend, mengecek hasil return actual dengan harapan yang ditetapkan di api contract.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
1. Status Code
2. Response Body
3. Response Time
4. Validasi Data
5. Header
6. Endpoint and Method
7. Message
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual Response adalah hasil response yang benar benar dikembalikan oleh API saat dilakukan testing. Sedangkan expected adalah hasil response yang diharapkan yang seharusnya muncul sesuai spesifikasi.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools yang digunakan untuk melakukan dokumentasi, pengujian, dan mendesain API secara interaktif. Swagger juga menyediakan UI yang akan memudahkan developer untuk melihat endpoint API, merancang, mencoba request lansgung.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
Standar yang digunakan untuk mendeskripsikan API secara terstruktur. Open API mendefinisikan endpoint, request, response, paramater, dalam format JSON.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI digunakan untuk menampilkan dokumentasi API secara interaktif sehingga devloper dapat dengan mudah memahami dan mencopa api.
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
@Tag adalah anotasi yang digunakan dalam Swagger/OpenAPI untuk mengelompokkan endpoint API ke dalam kategori tertentu.

```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation adalah anotasi dalam Swagger/OpenAPI yang digunakan untuk memberikan deskripsi detail pada setiap endpoint API.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse adalah anotasi dalam Swagger/OpenAPI yang digunakan untuk mendefinisikan response yang mungkin dihasilkan oleh suatu endpoint API.
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Keduanya sama sama digunakan untuk testing api. Swagger UI terintegrasi langsung dengan aplikasi backend. Sedangnkan postman terpisah sehingga harus mendaftarkan api manual.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
1. Url Swagger
2. Dependency Swagger
3. Aplikasi berjalan
4. Port aplikasi
5. Konfigurasi path.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
1. Konfigurasi Swagger di Code.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Swagger UI
2. Konfigurasi Swagger
3. Testing API
```

Apa 2 hal yang masih membingungkan?

```text
1. Konfigurasi Swagger di Code.
2. Dan membuat anotasi swagger di java
```

Apa 1 pertanyaan untuk mentor?

```text
1. Bagaimana cara agar cepat membuat anotasi swagger di java
```
