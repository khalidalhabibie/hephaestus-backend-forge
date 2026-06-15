# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
 Framework Java yang digunakan untuk membuat aplikasi Java (terutama aplikasi web dan REST API) dengan cepat dan mudah. Spring Boot merupakan pengembangan dari Spring Framework yang menyederhanakan konfigurasi.
```

### 2. Apa itu REST API?

Jawaban:

```text
REST API (Representational State Transfer Application Programming Interface) adalah cara atau standar yang digunakan agar dua aplikasi bisa saling berkomunikasi melalui internet.
```

### 3. Apa itu HTTP?

Jawaban:

```text
HTTP (HyperText Transfer Protocol) adalah protokol (aturan komunikasi) yang digunakan untuk mengirim dan menerima data di internet, terutama antara browser (client) dan server.
```

### 4. Apa itu JSON?

Jawaban:

```text
JSON (JavaScript Object Notation) itu adalah format data yang mudah dibaca dan sering dipakai buat ngirim data antara aplikasi (misalnya dari backend ke frontend).
```

### 5. Apa itu endpoint?

Jawaban:

```text
Endpoint itu adalah alamat URL di API yang bisa diakses untuk melakukan sesuatu (ambil data, kirim data, dll).
```

### 6. Apa itu request?

Jawaban:

```text
Request itu adalah permintaan yang dikirim oleh client ke server.
```

### 7. Apa itu response?

Jawaban:

```text
Response itu adalah jawaban dari server setelah menerima request dari client.
```

### 8. Apa fungsi GET?

Jawaban:

```text
GET adalah salah satu method di HTTP yang fungsinya untuk mengambil / membaca data dari server.
```

### 9. Apa fungsi POST?

Jawaban:

```text
POST adalah method di HTTP yang fungsinya untuk mengirim data ke server (biasanya untuk menambah data baru).
```

### 10. Apa fungsi PUT?

Jawaban:

```text
PUT adalah method di HTTP yang fungsinya untuk mengupdate / mengganti data yang sudah ada di server.
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
DELETE adalah method di HTTP yang fungsinya untuk menghapus data di server.
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK dipakai ketika request dari client berhasil diproses dengan sukses oleh server.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created dipakai ketika request berhasil dan server berhasil membuat data baru.
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request dipakai ketika request dari client itu salah / tidak valid, jadi server gak bisa memproses request tersebut.
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found dipakai ketika resource / data yang diminta tidak ditemukan di server.
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 Internal Server Error dipakai ketika server mengalami error saat memproses request, padahal request dari client sebenarnya sudah benar.
```

### 17. Apa itu path parameter?

Jawaban:

```text
Path parameter adalah nilai yang dikirim lewat URL untuk menunjuk resource tertentu (biasanya berdasarkan ID).
```

### 18. Apa itu query parameter?

Jawaban:

```text
Query parameter adalah data tambahan yang dikirim lewat URL untuk filter, search, atau kondisi tertentu.
```

### 19. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim oleh client ke server melalui bagian body (isi) dari HTTP request, biasanya digunakan untuk mengirim informasi seperti form, JSON, atau data input ke API.
```

### 20. Apa tugas Controller?

Jawaban:

```text
Controller bertugas untuk menerima request dari client, mengatur alur proses, dan mengembalikan response ke client.
```

### 21. Apa tugas Service?

Jawaban:

```text
Service bertugas untuk menjalankan business logic atau aturan bisnis dalam aplikasi.
```

### 22. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek yang digunakan untuk membawa data antara layer dalam aplikasi, biasanya dari client ke backend (atau sebaliknya) tanpa membawa logic bisnis.
```

### 23. Apa itu Model?

Jawaban:

```text
Model adalah representasi data dalam aplikasi yang biasanya mencerminkan struktur data di database dan digunakan untuk menyimpan serta mengelola informasi.
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
Business logic tidak ditaruh di Controller karena Controller hanya bertugas menangani request dan response, sedangkan business logic perlu dipisahkan agar kode lebih rapi, terstruktur, dan mudah di-maintain.
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
Request/response perlu DTO karena untuk mengontrol dan membatasi data yang dikirim antara client dan server, sehingga lebih aman, terstruktur, dan sesuai kebutuhan API.
```
