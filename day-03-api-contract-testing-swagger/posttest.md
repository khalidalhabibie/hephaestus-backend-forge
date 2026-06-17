# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah kesepakatan yang berisikan bagaimana client dan server saling berkomunikasi, termasuk HTTP method yang ada.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
- Endpoint;
- HTTP Method (e.g. GET, POST, PUT, PATCH, DELETE);
- Request parameter;
- Request body;
- Response body;
- Status code; and;
- etc.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Karene frontend membutuhkan komunikasi dengan API, sehingga API contract dapat menjadi pacuan bagi frontend dalam menggunakan API tanpa menunggu proses development backend selesai. 
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO adalah object yang digunakan untuk mengirim dan menerima data antara client dan server.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO -> menerima data dari client.
Response DTO -> mengirim data ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Karena code akan menjadi lebih rapih dan hal ini menyebabkan proses pembacaan dan pengelolaan kode menjadi lebih baik. Database juga tidak akan langsung terakses, meningkatkan perlindungan data.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty berfungsi untuk menentukannama field yang digunakan (e.g. name, age, salary).
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
@RequestBody berfungsi untuk mengambil data dari request body.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
@PathVariable berfungsi untuk mengambil nilai parameter yang berada di URL atau endpoint (e.g. mengambil ID pada /customers/id sehingga data yang diambil hanya data dengan ID tersebut).
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid berfungsi untuk menjalankan validasi data request.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
POST -> membuat data baru yang belum exist (e.g. data Customer baru).
GET -> mengambil data (e.g. data Customer dengan ID tertentu atau list Customers).
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT -> mengubah seluruh data yang ada (e.g. mengubah seluruh field pada data Customer).
PATCH -> mengubah sebagian data yang ada (e.g. mengubah fiel name Customer saja).
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika proses pembuatan data baru berhasil dilakukan.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketikan request berhasil diproses dan menampilkan hasil yang diminta.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketikan request yang dikirim client ke server tidak valid (e.g. format data salah, email tidak menggunakan format mail).
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found ketika data yang di-request tidak ditemukan.
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses testing pengujian API untuk memastikan API bekerja sesuai dengan ketetapan yang ditentukan di awal.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
- Endpoint;
- Status code dari setiap request;
- JSON hasil request;
- Error handling;
- etc.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual response -> hasil yang benar-benar dikembalikan ketika transfering data melalui API.
Expected response -> hasil yang seharusnya dikembalikan berdasarkan API contract yang sudah dibuat.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools yang membantu mendokumentasikan proses pengujian API berbasis OpenAPI.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPi adalah spesifikasi API dalam format JSON.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI bermanfaat untuk mendokumentasikan API secara otomatis, sehingga proses testing dapat berlangsung dengan lebih cepat dan baik menyesuaikan API contract yang sudah dibuat sebelumnya.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui/index.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag berfungsi untuk mengelompokan endpoint API di Swagger UI.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation berfungsi untuk memberikan informasi mengenai endpoint API.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse berfungsi untuk mendokumentasikan response yang mungkin dikembalikan oleh endpoint.
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI -> mendokumentasi API secara otomatis dan sangat baik digunakan untuk testing API dengan menyesuaikan API contract.
Postman -> hanya untuk testing API yang tidak terintegrasi dengan OpenAPI.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Mengecek dependency dan URL yang digunakan.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Memahami semuanya secara keseluruhan dari flow awal.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Membuat API Contract
2. Melakukan testing menggunakan Swagger UI
3. Mendokumentasikan API testing menggunakan Swagger
```

Apa 2 hal yang masih membingungkan?

```text
1. Fitur Swagger secara detail
2. Flow secara konsistennya
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana API Contract dapat menjadi patokan yang terintegrasi serta konsisten antara backend dan frontend.
```
