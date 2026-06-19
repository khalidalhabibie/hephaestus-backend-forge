# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Validasi request adalah code yang bisa memeriksa kesesuain data yang dikirim client sudah sesuai dengan yang diinginkan.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Karena backend memerlukan validasi agar data yang ada sudah sesuai dengan business logic yang ada, di mana ini membantu untuk meningkatkan integrasi data.
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
@Valid berfungsi untuk menjalankan proses validasi pada object data sesuai dengan data yang ada di dalam annotaion.
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
@NotBlank berfungsi untuk menjalankan proses validasi agar nilai String tidak null, tidak kosong, dan tidak hanya berisikan spasi.
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotBlank -> memastikan tidak null, tidak kosong, tidak hanya breisikan spasi.
@NotNull -> hanya memastikan tidak null.
```

### 6. Apa fungsi @Email?

Jawaban:

```text
@Email berfungsi untuk memastikan data sudah sesuai dengan format mail atau email.
```

### 7. Apa fungsi @Size?

Jawaban:

```text
@Size berfungsi untuk memastikan panjang data sudah sesuai dengan maksimal atau minimum yang diperintahkan.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Request akan diberhentikan sehingga data tidak merusak business logic yang sudah diatur.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
MethodArgumentValidException dipakai ketika terdapat request yang gagal.
```

### 10. Apa itu standard error response?

Jawaban:

```text
Standard error response adalah panduan yang ditetapkan untuk semua jenis error.
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Error response perlu konsisten agar penangan error dapat lebih mudah.
```

### 12. Apa itu field-level error?

Jawaban:

```text
Field-level error adalah error yang terjadi pada field tertentu (e.g. name).
```

### 13. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception yang dibuat sendiri sesuai dengan kepentingan business atau lainnya.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
Karena lebih spesifik dan memudahkan penanganan error sesuai kepentingan bisnis atau lainnya.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice berfungsi untuk menangani exception secara lebih terpusat dan terintegrasi.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler berfungsi untuk menentukan method yang akan menangani exception.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Karena mempermudah membaca dan menangani error, sehingga waktu maintenance menjadi lebih cepat dan mudah.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
404 Bad Request digunakan untuk memberitahukan bahwa request client ke server tidak baik atau tidak valid.
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan untuk memberitahukan bahwa URL yang dituju tidak ditemukan.
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 Internal Server Error digunakan untuk memberitahukan bahwa terjadi error pada server internal.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Karena dapat membocorkan informasi crucial yang berpotensi menjadi celah keamanan.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
Client mengirim request POST dengan email tidak valid kemudian Spring menjalankan validasi melalui @Valid.
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Client mengirim request GET kemudian Controller memanggil service yang melihat bahwa data tidak ditemukan.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error -> data tidak valid atau sesuai format (e.g. email tidak menggunakan @).
Business error -> melanggar rule bisnis (e.g. user di bawah 17 tahun).
System error -> kesalahan sistem ketika aplikasi diproses.
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Bagian yang paling sulit adalah mempraktikan semuanya dari awal walaupun secara teori dan flow sudah mulai memahami.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Cara melakukan validasi request menggunakan Bean Validation;
2. Cara membuat dan menggunakan custom exception; dan;
3. Cara menangani error secara terpusat menggunakan @ControllerAdvice.
```

Apa 2 hal yang masih membingungkan?

```text
1. Flow dari awalnya; dan;
2. Mempraktikan secara langsung.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana memahami semuanya secara langsung agar mudah dipraktikan segera mungkin.
```