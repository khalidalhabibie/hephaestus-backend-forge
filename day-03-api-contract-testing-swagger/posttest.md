# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
sebuah perjanjian API antar developer dan consumer
```

### 2. Apa saja isi API contract?

Jawaban:

```text
berisi ketentuan API yang akan di consume, termasuk paramater dan API response
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
agar mudah untuk dibaca karena semua ketentuan API sudah terdokumentasi
```

### 4. Apa itu DTO?

Jawaban:

```text
data transfer object merupakan format atau template request dan response
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
request berisi data yang diinput dari sisi klien dan response menampilkan data yang dibutuhkan ke klien
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
karena tidak semua data di model harus ditampilkan, maka dibuatlah dto sebagai response sesuai dengan kebutuhan
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mengubah penamaan sebuah atribut menjadi snake_case (umumnnya)
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
berisi format request untuk objek data
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
 untuk mengambil nilai dinamis yang disisipkan langsung ke dalam struktur URL (URI path) dan mengikatnya ke parameter method
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
memicu proses validasi objek secara otomatis berdasarkan aturan yang telah didefinisikan
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
post dilakukan untuk menambah data baru sedangkan get untuk mengambil data
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
put mengupdate keseluruhan objek data sedangkan patch hanya sebagian
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
ketika berhasil menambah data baru
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika berhasil menampilkan data/ mengambil data
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request dari klien tidak sesuai dengan format
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika data yang ada di server tidak ditemukan karena request tidak sesuai
```

### 17. Apa itu API testing?

Jawaban:

```text
proses mengetes apakah API berjalan dengan baik dan response nya
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
path, objek request & response, http status
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
actual adalah hasil yang sebenarnya sedangkan expected adalah hasil yang diharapkan
```

### 20. Apa itu Swagger?

Jawaban:

```text
sebuah tools untuk dokumentasi dan testing API
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
Sistem yang memungkinkan pengembang perangkat lunak untuk mengintegrasikan dan menghubungkan aplikasi atau layanan mereka dengan aplikasi pihak ketiga
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
untuk mendokumentasikan API yang didetect dari controller secara otomatis
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui/index.html#/
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
berfungsi untuk mengelompokkan endpoint API ke dalam kategori tertentu dan memberikan nama serta deskripsi 
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
berfungsi untuk mendeskripsikan dan memberikan informasi detail mengenai sebuah endpoint API
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
adalah untuk mendokumentasikan kode status HTTP (seperti 200, 404, atau 500) yang mungkin dihasilkan oleh sebuah endpoint API
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
swagger digunakan untuk dokumentasi dan testing sedangkan postman untuk testing 
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
cek url path atau cek dependency pada bagian pom.xml
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
menambahkan logic di service dan menambahkan open api description, sehingga saya masih kesulitan dalam mengerjakan optional challenge nya
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Swagger UI
2. Open API
3. API Contract & Documentation
```

Apa 2 hal yang masih membingungkan?

```text
1. kesulitan membuat logic di service
2. memahami schema open api description
```

Apa 1 pertanyaan untuk mentor?

```text
cara menggunakan schema open api
```
