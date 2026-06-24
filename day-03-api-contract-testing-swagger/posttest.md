# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah kesepakatan (agreement) yang mendefinisikan bagaimana sebuah API digunakan antara client (frontend / consumer) dan server (backend / provider).
```

### 2. Apa saja isi API contract?

Jawaban:

```text
Isi API contract adalah komponen‑komponen yang mendefinisikan bagaimana sebuah API digunakan secara jelas dan konsisten.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
API contract sangat penting untuk frontend/mobile developer karena menjadi pedoman utama saat mengintegrasikan UI dengan backend. Tanpanya, development bisa kacau dan penuh trial-error.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek sederhana yang digunakan untuk mengirim data antar layer atau antar sistem.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO digunakan untuk menampung data yang dikirim oleh client ke server, sedangkan Response DTO digunakan untuk menampung data yang dikirim oleh server kembali ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
DTO dan model sebaiknya dipisah agar struktur data untuk transfer (API) tidak langsung bergantung pada struktur database, sehingga lebih aman, fleksibel, dan mudah diubah tanpa memengaruhi bagian lain dari sistem.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty adalah anotasi (annotation) dari library Jackson (Java) yang digunakan untuk mengatur mapping antara nama field di JSON dengan field di object (DTO/model).
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
@RequestBody adalah anotasi di Spring Boot (Java) yang digunakan untuk mengambil data dari request body (biasanya JSON) dan mengubahnya menjadi object Java (DTO).
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
@PathVariable adalah anotasi di Spring Boot (Java) yang digunakan untuk mengambil nilai dari parameter di URL (path) dan mengubahnya menjadi variabel di method controller.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid adalah anotasi di Spring Boot (Java) yang digunakan untuk menjalankan validasi otomatis terhadap data (biasanya DTO) sebelum diproses di controller.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data dari server tanpa mengubah apa pun, sedangkan POST digunakan untuk mengirim data ke server untuk membuat atau memproses data baru.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT digunakan untuk mengganti seluruh data resource, sedangkan PATCH digunakan untuk mengubah sebagian data saja tanpa mengirim seluruh field.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika server berhasil membuat resource baru sebagai hasil dari request client (biasanya pada operasi POST).
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketika request berhasil diproses dan server mengembalikan hasil yang diminta tanpa membuat resource baru.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid atau tidak sesuai format yang diharapkan oleh server (misalnya field kurang, format salah, atau data tidak valid).
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika resource yang diminta oleh client tidak ditemukan di server (misalnya ID data tidak ada).
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses untuk menguji apakah API bekerja dengan benar sesuai dengan API contract.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Saat melakukan API testing, ada beberapa hal penting yang harus dicek agar API benar‑benar berjalan sesuai spesifikasi (API contract).
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual response adalah response yang benar‑benar dikembalikan oleh API saat testing, sedangkan expected response adalah response yang seharusnya dikembalikan oleh API sesuai API contract atau spesifikasi.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah sebuah tool dan standar yang digunakan untuk mendokumentasikan, mendesain, dan menguji API secara interaktif.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standar (specification) yang digunakan untuk mendefinisikan struktur dan aturan sebuah API secara formal dalam bentuk file (JSON atau YAML).
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI bermanfaat untuk menampilkan dokumentasi API sekaligus memungkinkan developer menguji endpoint secara langsung dalam satu tampilan interaktif.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
URL untuk membuka Swagger UI biasanya adalah:
/swagger-ui/index.html

atau di beberapa versi:
/swagger-ui.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
URL untuk membuka OpenAPI JSON biasanya adalah:
/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag adalah anotasi di Springdoc OpenAPI / Swagger yang digunakan untuk mengelompokkan dan memberi label pada API endpoint di dokumentasi Swagger UI.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation adalah anotasi di Springdoc OpenAPI / Swagger yang digunakan untuk memberikan deskripsi detail pada suatu endpoint API.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse adalah anotasi di Springdoc OpenAPI / Swagger yang digunakan untuk mendefinisikan dan mendokumentasikan response yang mungkin dihasilkan oleh sebuah endpoint API (termasuk status code dan deskripsinya).
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk melihat dokumentasi API dan mencoba endpoint secara langsung di browser, sedangkan Postman digunakan untuk mengirim request API secara fleksibel untuk testing dan debugging.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Jika Swagger UI menampilkan 404, berarti endpoint Swagger tidak ditemukan sehingga perlu dicek URL, dependency, server yang berjalan, serta konfigurasi path dan security.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Bagian yang paling sulit dari Day 3 biasanya adalah memahami integrasi Swagger/OpenAPI dengan Spring Boot serta konfigurasi agar dokumentasi dan endpoint bisa tampil dengan benar tanpa error (misalnya 404).
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. API contract itu penting sebagai kesepakatan antara frontend dan backend
2. DTO digunakan untuk memisahkan data yang dikirim/diterima dari struktur database
3. Swagger/OpenAPI membantu dokumentasi dan testing API secara interaktif
```

Apa 2 hal yang masih membingungkan?

```text
1. Perbedaan detail penggunaan annotation Swagger seperti @Operation, @ApiResponse, dan @Tag
2. Konfigurasi Swagger/OpenAPI di Spring Boot
```

Apa 1 pertanyaan untuk mentor?

```text
Kapan sebaiknya kita menggunakan Swagger/OpenAPI secara manual dibandingkan auto-generate dari Spring Boot?
```
