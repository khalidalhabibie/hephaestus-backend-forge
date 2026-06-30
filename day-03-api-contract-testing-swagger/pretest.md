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
API contract adalah kesepakatan tertulis (spesifikasi) antara penyedia dan konsumen layanan yang menjelaskan endpoint, HTTP method, format request/response, status code, header, autentikasi, dan contoh penggunaan.

```

### 2. Kenapa API contract penting?

Jawaban:

```text
API contract penting karena menyamakan ekspektasi antara tim, mempercepat integrasi, memudahkan pengujian, dan mengurangi bug saat klien dan server dikembangkan secara terpisah.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
Isi API contract biasanya meliputi: daftar endpoint, HTTP method, format request body, format response body, contoh request/response, status code yang mungkin dikembalikan, header yang digunakan, autentikasi/otorisasi, dan aturan validasi/error format.
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL/path pada server yang merepresentasikan resource atau aksi tertentu yang dapat diakses oleh klien, mis. `/users` atau `/orders/{id}`.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP method adalah tipe operasi yang dilakukan pada endpoint, seperti GET (baca), POST (buat), PUT (ganti/replace), PATCH (update sebagian), DELETE (hapus).
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah payload/data yang dikirim klien ke server (biasanya pada POST/PUT/PATCH) berisi informasi yang dibutuhkan server untuk memproses permintaan.
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah data/payload yang dikembalikan server ke klien sebagai hasil dari pemrosesan request, biasanya berformat JSON atau XML.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah kode numerik yang memberi tahu hasil dari permintaan misalnya adalah 200 OK, 201 Created, 400 Bad Request, 404 Not Found, 500 Internal Server Error.
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Karena dokumentasi yang jelas dapat mengantisipasi kesalahan integrasi, memudahkan pengujian otomatis, mempercepat pengembangan, dan mengurangi ambigu dalam komunikasi antar-tim.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
risiko yang mungkin muncul adalah integrasi gagal, bug produksi, versi yang tidak kompatibel, kebocoran data karena asumsi yang salah, dan peningkatan biaya pemeliharaan.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek sederhana yang digunakan untuk mengangkut data antar lapisan aplikasi atau antar layanan tanpa logika bisnis.
```

### 12. Apa itu request DTO?

Jawaban:

```text
Request DTO adalah struktur data yang mendefinisikan bentuk/polanya data yang dikirim klien ke server (mis. field yang wajib dan tipe datanya).
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah struktur data yang mendefinisikan bentuk/polanya data yang dikembalikan server ke klien.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Memisahkan DTO dan model mencegah kebocoran detail implementasi, memudahkan validasi dan versi API, serta meningkatkan keamanan dengan hanya mengekspos field yang diperlukan.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena konvensi bahasa banyak bahasa scripting memakai snake_case, sedangkan Java memakai camelCase

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
`@JsonProperty` digunakan untuk memetakan nama properti JSON ke nama field/ getter/setter Java yang berbeda, sehingga serialisasi/deserialisasi sesuai spec API.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
POST digunakan untuk membuat resource baru atau men-submit data ke server.
```

### 18. Apa fungsi GET?

Jawaban:

```text
GET digunakan untuk mengambil/membaca data.
```

### 19. Apa fungsi PUT?

Jawaban:

```text
PUT biasanya digunakan untuk mengganti (replace) resource secara keseluruhan.
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
PATCH digunakan untuk melakukan update sebagian pada resource.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT menggantikan seluruh resource , sedangkan PATCH mengubah hanya field tertentu.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Gunakan 201 Created saat sebuah resource berhasil dibuat (biasanya setelah POST) dan sertakan header `Location` menuju resource baru.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Gunakan 200 OK saat permintaan berhasil dan server mengembalikan payload yang diminta (mis. GET atau operasi sukses lainnya).
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Gunakan 400 Bad Request saat klien mengirim data yang tidak valid atau format permintaan salah.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Gunakan 404 Not Found saat resource yang diminta tidak ada di server.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API testing adalah proses memverifikasi bahwa endpoint API bekerja sesuai kontrak: status code, response schema, header, autentikasi, dan perilaku error.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Untuk memastikan keandalan, mencegah regresi, memvalidasi kontrak, dan memastikan sistem terintegrasi dengan benar.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Contoh tool: Postman, curl, HTTPie, Swagger UI, REST Assured (Java), Karate, JMeter.
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Periksa status code, response body/schema, header, waktu respons, autentikasi/otorisasi, handling error, dan side effects pada database jika relevan.
```

### 30. Apa itu expected response?

Jawaban:

```text
Expected response adalah hasil yang diharapkan dari server (kombinasi status code, header, dan payload) sesuai dengan API contract.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah kumpulan tool (termasuk Swagger UI, Swagger Editor) yang bekerja dengan OpenAPI Spec untuk mendokumentasikan dan mencoba API secara interaktif.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah spesifikasi standar berbentuk file (YAML/JSON) yang mendeskripsikan endpoint, schema, parameter, dan behavior sebuah REST API.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI menyediakan dokumentasi interaktif yang memungkinkan pengembang melihat spesifikasi dan mencoba endpoint langsung dari browser.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman fokus pada pengujian API, koleksi request, scripting dan environment, sedangkan Swagger UI otomatis menghasilkan dokumentasi interaktif dari OpenAPI spec.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Swagger sangat membantu dan dapat menggantikan sebagian besar dokumentasi teknis karena bersifat machine-readable dan interaktif, tetapi dokumentasi naratif (contoh kasus bisnis, alur kompleks, kebijakan versi) sering tetap diperlukan.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |3|
| DTO |4|
| HTTP method |3|
| API testing |5|
| Swagger UI |3|
| OpenAPI |3|

## Notes

```text
Beberapa bagian yang mungkin perlu pendalaman: strategi versioning API, backward compatibility, dan best practice error format.
```
