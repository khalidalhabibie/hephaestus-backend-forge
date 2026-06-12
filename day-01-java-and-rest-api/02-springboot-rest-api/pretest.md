# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah salah satu framework/kerangka kerja dari bahasa pemrograman Java sebagai service BackEnd untuk membuat API dan koneksi ke database dengan lebih mudah
```

### 2. Apa itu REST API?

Jawaban:

```text
REST API (REpresentational State Transfer Application Programming Interface) ialah salah satu bentuk API dengan metode request dan response untuk men-generate API yang komunikasinya dalam bentuk JSON ke FrontEnd/client
```

### 3. Apa itu HTTP?

Jawaban:

```text
HTTP ialah HyperText Transfer Protocol ialah komunikasi yang digunkana untuk transfer data antara client dan server melalui web
```

### 4. Apa itu JSON?

Jawaban:

```text
JSON atau JavaScript Object Notation ialah format transfer data antara client dan server
```

### 5. Apa itu endpoint?

Jawaban:

```text
Endpoint ialah titik akses URL dalam API yang digunakan untuk berkomunikasi request dan response antara client dan server
```

### 6. Apa itu request?

Jawaban:

```text
Request ialah permintaan yang dikirim oleh client ke server melalui API untuk mendapatkan dan mengirim data
```

### 7. Apa itu response?

Jawaban:

```text
Response ialah jawaban dari server ke client melalui API untuk mengirim data
```

### 8. Apa fungsi GET?

Jawaban:

```text
GET merupakan fungsi/method dalam API untuk mengirimkan data
```

### 9. Apa fungsi POST?

Jawaban:

```text
POST ialah fungsi/method dalam API untuk membuat data
```

### 10. Apa fungsi PUT?

Jawaban:

```text
PUT ialah fungsi/method dalam API untuk meng-update data
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
DELETE ialah fungsi/method dalam API untuk menghapus data
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika API berhasil mengembalikan response dengan sukses dan lancar
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika API mengembalikan response data berhasil dibuat
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika client/user mengirimkan format request API yang kurang tepat. Misalkan yang harusnya integer, tapi dikirim string
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika data yang di-request oleh client/user tidak ditemukan di database
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Untuk meng-handle error yang tidak sengaja terjadi pada saat client request ke API
```

### 17. Apa itu path parameter?

Jawaban:

```text
Path parameter ialah tambahan endpoint untuk mengakses data spesifik tertentu
```

### 18. Apa itu query parameter?

Jawaban:

```text
Query parameter ialah tambahan pada endpoint tanpa memengaruhi hasil akhir endpoint itu sendiri. Query parameter bersifat wajib dan diawali dengan "?" dan "&" untuk pemisah, misalkan : "/user?id=123&name=Adnan"
```

### 19. Apa itu request body?

Jawaban:

```text
Data yang dikirim oleh user pada saat mengakses API, biasanya digunakan pada saat POST dan DELETE
```

### 20. Apa tugas Controller?

Jawaban:

```text
Controller bertugas untuk mengirim dan menerima response dari route/endpoint API lalu meneruskan untuk mengatur alur proses dan meneruskan ke data atau service tertentu
```

### 21. Apa tugas Service?

Jawaban:

```text
Service bertugas untuk menangani business logic utama dan proses agar terpisah dari Controller
```

### 22. Apa itu DTO?

Jawaban:

```text
DTO atau Data Transfer Object bertugas membawa/men-transfer data antar layer seperti dari controller ke service, tanpa menyertakan business logic
```

### 23. Apa itu Model?

Jawaban:

```text
Representasi tabel dalam database yang ditulis dalam bentuk kode Java
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
Agar code lebih clean dan menerapkan clean architecture, sehingga code akan lebih mudah untuk di-mantain
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
Agar data lebih aman dan lebih terstruktur, serta konsisten dan mudah divalidasi untuk kontrol response API
```
