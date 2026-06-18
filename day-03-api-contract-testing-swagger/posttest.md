# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
kontrak yang berisikan :
- banyak api
- endpointnya
- cara pakenya
- request responnya
- pathnya
- status codenya
- http method dll
yang disepakati dan digunakan oleh semua developer
```

### 2. Apa saja isi API contract?

Jawaban:

```text
- url link / endpoint
- cara pake endpointnya
- request body
- response body
- status code
- http method dll
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Karena API contract menjadi acuan agar frontend/mobile bisa integrasi tanpa menunggu backend selesai secara penuh. dan meminimalisir error saat consume jika semuanya jelas.
```

### 4. Apa itu DTO?z

Jawaban:

```text
DTO adalah objek untuk mentransfer data antar layer tanpa membawa logic bisnis. digunakan di request dan response.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO digunakan untuk menerima data dari client sedangkan response DTO untuk mengirim data ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Agar menjaga keamanan, fleksibilitas, dan memisahkan struktur internal database dengan data yang dikirim ke client.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
JsonProperty berfungsi untuk mapping nama field JSON ke atribut pada object Java. camelCase ke snake_case
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
RequestBody digunakan untuk mengambil data request body dan mengubahnya menjadi object Java.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
PathVariable digunakan untuk mengambil nilai parameter dari URL path.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
@Valid digunakan untuk melakukan validasi otomatis pada request DTO berdasarkan constraint.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
GET digunakan untuk mengambil data sedangkan POST untuk membuat data baru.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
UT digunakan untuk update seluruh data sedangkan PATCH untuk update sebagian data.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan saat berhasil membuat resource baru.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan saat request berhasil diproses tanpa membuat resource baru.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan saat request client tidak valid atau salah format.
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan saat resource yang diminta tidak ditemukan.
```

### 17. Apa itu API testing?

Jawaban:

```text
API testing adalah proses menguji endpoint API untuk memastikan sesuai dengan contract.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
tatus code, response body, validasi data, error handling, dan performa.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Actual response adalah response nyata dari API sedangkan expected response adalah hasil yang diharapkan sesuai contract.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools untuk mendokumentasikan dan menguji API secara interaktif.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah spesifikasi standar untuk mendeskripsikan API dalam format JSON/YAML.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI memudahkan developer melihat, mencoba, dan memahami API langsung dari browser.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
URL Swagger UI biasanya diakses melalui /swagger-ui/index.html.
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
URL OpenAPI JSON biasanya diakses melalui /v3/api-docs.
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag digunakan untuk mengelompokkan API berdasarkan kategori.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation digunakan untuk mendeskripsikan detail fungsi endpoint API.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse digunakan untuk mendokumentasikan kemungkinan response dari endpoint.
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI digunakan untuk dokumentasi dan testing cepat sedangkan Postman lebih fleksibel untuk testing kompleks.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Cek apakah dependency benar, konfigurasi path benar, dan aplikasi berjalan dengan baik.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Menambahkan email di query param getallcustomer
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Cara membuat api contract menggunakan swagger
2. Bagaimana cara menggunakan status code yg benar
3. Membuat getall berdasarkan query param
```

Apa 2 hal yang masih membingungkan?

```text
1. Membuat code menjadi lebih clean dan modular, terkadang masih membuat code yg kurang reuseable
2. Membuat DTO yang tepat saat production
```

Apa 1 pertanyaan untuk mentor?

```text
belum ada.
```
