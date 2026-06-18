# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Suatu hal yang dilakukan untuk meng-handle permintaan dari client
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Untuk meminimalisir resiko error, dan karena celah di FrontEnd itu tetap lebih besar
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
Untuk memastikan request body menjalankan anotasi validasi di DTO request body
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
@NotBlank memastikan nilai tidak null, tidak kosong, dan tidak hanya berisikan spasi
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya memastikan nilai tidak null, sedangkan @NotBlank memastikan nilai tidak null, tidak kosong, dan tidak hanya berisikan spasi
```

### 6. Apa fungsi @Email?

Jawaban:

```text
Memastikan input harus berformat email
```

### 7. Apa fungsi @Size?

Jawaban:

```text
Untuk validasi panjang karakter (minimal dan maksimal) dari field request body
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Tulis jawaban di sini.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
Class yang mendefinisikan error bad request
```

### 10. Apa itu standard error response?

Jawaban:

```text
Error bawaan dari class Java Sprint untuk memunculkan pesan error
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Untuk menjaga UX aplikasi dengan baik, agar user/client tidak ambigu dengan berbagai jenis pesan error yang terlalu beragam
```

### 12. Apa itu field-level error?

Jawaban:

```text
Error validasi yang terjadi pada field tertentu dalam suatu request
```

### 13. Apa itu custom exception?

Jawaban:

```text
Exception yang dibuat sendiri untuk merepresentasikan kondisi error yang spesifik untuk client
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
Karena CustomerNotFoundException lebih jelas untuk menunjukkan penyebab error sehingga kode lebih mudah dipahami dan di-maintain
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
Berfungsi untuk menandai class sebagai class yang menangani exception secara global pada seluruh controller
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
Digunakan untuk menentukan method yang akan menangani jenis exception tertentu
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Agar penanganan error konsisten dan tidak terjadi duplikasi kode
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid atau tidak sesuai dengan aturan yang ditentukan
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika resource atau data yang diminta dari client tidak ditemukan
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Untuk handle error (exception) di controller maupun service
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Karena dapat membocorkan informasi internal aplikasi yang berisiko terhadap keamanan
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
Request gagal validasi -> Spring melempar MethodArgumentNotValidException -> Lalu GlobalExceptionHandler mengembalikan response 400 Bad Request.
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Service melempar CustomerNotFoundException -> Kemudian ditangani oleh GlobalExceptionHandler -> Lalu dikembalikan sebagai response 404 Not Found.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
- Validation error terjadi karena input yang tidak valid
- Business error terjadi karena aturan flow business utama error/dilanggar
- System error terjadi karena kegagalan teknis pada server
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Bukan yang paling sulit, tapi agak tricky, yakni implementasi handler error agar lebih konsisten
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan penggunaan @NotBlank, @NotNull, dan anotasi validasi lainnya pada class DTO
2. Cara membuat dan menangani custom exception menggunakan @RestControllerAdvice dan @ExceptionHandler
3. Cara menghasilkan response error yang konsisten melalui GlobalExceptionHandler
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara mengubah nama field validasi secara otomatis dari camelCase menjadi snake_case pada response error
2. Best practice penggunaan exception untuk berbagai jenis error pada aplikasi Spring Boot yang lebih kompleks
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana agar di "field" error itu otomatis dikonversi menjadi snake_case (yang awalnya masih camelCase)
```
