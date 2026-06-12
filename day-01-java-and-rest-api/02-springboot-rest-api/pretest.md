# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```
Spring boot merupakan salah satu framework back end yang menggunakan bahasa java yang biasa digunakan untuk pengembangan aplikasi.
```

### 2. Apa itu REST API?

Jawaban:

```
REST API adalah suatu metode untuk menghubungkan satu aplikasi dengan aplikasi lain agar bisa berkomunikasi dengan aturan tertentu
```

### 3. Apa itu HTTP?

Jawaban:

```
protokol yang digunakan untuk mengirim dan menerima respon data di internet biasanya antara browser dengan server 
```

### 4. Apa itu JSON?

Jawaban:

```
JSON adalah format data test yang dipergunakan agar mudah dibaca manusia yang biasanya digunakan untuk bertugas data dari aplikasi satu dengan lainnya
```

### 5. Apa itu endpoint?

Jawaban:

```
alamat URL di aplikasi web atau API tempat client mengirim request
```

### 6. Apa itu request?

Jawaban:

```
permintaan yang dikirim client ke server
```

### 7. Apa itu response?

Jawaban:

```
jawaban dari server sesuai yang diminta oleh client
```

### 8. Apa fungsi GET?

Jawaban:

```
untuk mendapatkan suatu data
```

### 9. Apa fungsi POST?

Jawaban:

```
untuk mengirim data
```

### 10. Apa fungsi PUT?

Jawaban:

```
untuk mengupdate data 
```

### 11. Apa fungsi DELETE?

Jawaban:

```
untuk menghapus suatu data
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```
digunakaan saat server berhasil memproses request dan mengembalikannya dengan berhasil
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```
saat server berhasil membuat data baru
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```
untuk memberikan respon saat request tidak valid karena datanya salah atau tidak sesuai format
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat endpoint atau resource tidak ada dan tidak ditemukan
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Saat server mengalami suatu permasalahan bisa berupa kesalahan dari sisi server
```

### 17. Apa itu path parameter?

Jawaban:

```text
merupakan bagian yang ada di URL yang mewakili nilai unik untuk mengidentifikasi resource tertentu
```

### 18. Apa itu query parameter?

Jawaban:

```text
parameter tambahan di URL setelah tanda ?, biasanya untuk filter, sorting, atau pagination.
```

### 19. Apa itu request body?

Jawaban:

```text
data yang dikirim dalam isi request, biasanya untuk POST, PUT, PATCH data biasanya dalam format JSON
```

### 20. Apa tugas Controller?

Jawaban:

```text
penghubung antara request dari client dan logic aplikasi yaitu service/model
```

### 21. Apa tugas Service?

Jawaban:

```text
Mengolah data dan menjalankan logika bisnis aplikasi
```

### 22. Apa itu DTO?

Jawaban:

```text
Objek yang digunakan untuk membawa data antar lapisan aplikasi, misalnya dari controller ke service atau dari server ke client
```

### 23. Apa itu Model?

Jawaban:

```text
representasi data tabel yang ditulis dalam bentuk java
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
karena controller seharusnya hanya bertugas untuk menangani request dan response saja. Jika business logic ditempatkan di controller, maka kode akan menjadi sulit dibaca, sulit dirawat (maintain), dan sulit digunakan kembali (reusable).
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
Request dan response perlu menggunakan DTO agar data yang dikirim dan diterima lebih terstruktur, aman, dan terkontrol.
```
