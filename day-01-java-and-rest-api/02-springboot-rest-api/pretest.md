    # Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Framework untuk membuat backend service dalam bahasa pemograman java.
```

### 2. Apa itu REST API?

Jawaban:

```text
REST API dibagi menjadi 2 kata
-> Rest - sebuah standar dalam penulisan API
-> API - Application programming interface yang berisikan protokol request response untuk berkomunikasi.
```

### 3. Apa itu HTTP?

Jawaban:

```text
protokol untuk komunikasi di internet.
```

### 4. Apa itu JSON?

Jawaban:

```text
format file yang berbentuk key dan value - basisnya dari javascript.
```

### 5. Apa itu endpoint?

Jawaban:

```text
Titik alamat api untuk mengeksekusi komunikasi client dan server
```

### 6. Apa itu request?

Jawaban:

```text
Komunikasi untuk meminta suatu service ke server, biasanya bisa tanpa payload atau dengan payload.
```

### 7. Apa itu response?

Jawaban:

```text
Response adalah keluaran yang diberikan oleh server dalam eksekusi logika service,  diterima oleh siapapun yang  melakukan request.
```

### 8. Apa fungsi GET?

Jawaban:

```text
HTTP method untuk meminta seesuatu/data ke server
```

### 9. Apa fungsi POST?

Jawaban:

```text
HTTP method untuk memberikan seesuatu/data ke server untuk disimpan.
```

### 10. Apa fungsi PUT?

Jawaban:

```text
Method http untuk mengedit data di server lewat api
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
Method http untuk menghapus data di server lewat api

```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat service yang dijalankan lewat api berhasil dilakukan.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Saat kita menginput sesuatu yang harus disimpan oleh server
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Saat server menolak karena pe request melakukan kesalahan input dll
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat data yang seharusnya diberikan oleh server namun tidak ada datanya.
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
saat server mengalami kendala yang tidak terduga.
```

### 17. Apa itu path parameter?

Jawaban:

```text
bagian dari url di api yang menunjuk langsung ke data/objek yang ingin diambil secara spesifik
```

### 18. Apa itu query parameter?

Jawaban:

```text
kunci nilai opsional pada link api untuk memfilter data yang dibutuhkan
```

### 19. Apa itu request body?

Jawaban:

```text
Body/payload atau masukan yang harus di isi untuk melakukan request
```

### 20. Apa tugas Controller?

Jawaban:

```text
menerima request dari user dan menuruskannya ke service agar bisa diproses di logika bisnis
```

### 21. Apa tugas Service?

Jawaban:

```text
Menjalan logika bisnis dari data yang diteruskan oleh kontroller
```

### 22. Apa itu DTO?

Jawaban:

```text
Template data yang digunakan untuk standar / request body dan response body yang harus diikuti. yang akan diberikan atau diterima oleh backend.
```

### 23. Apa itu Model?

Jawaban:

```text
representasi struktur data murni dari database.
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
mengisolasi agar kontroller hanya fokus pada satu tugas yaitu mengatur alur masuk dan keluar data.
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
membatasi dan menyaring data agar ketika data diterima oleh database tidak berbeda formatnya, mencegah eror, dan juga kebocoran data yang tidak perlu.
```
