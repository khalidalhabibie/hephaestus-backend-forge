# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Untuk melakukan validasi terhadap request untuk memastikan data yang masuk sesuai dengan ketentuan yang ada.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Karena validasi di frontend lebih berfokus pada menyaring data yang masuk sedangkan validasi backend lebih berfokus pada validasi business logic.
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
Untuk melakukan validasi pada field-field di aplikasi.
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
Untuk melakukan validasi bahwa field tidak boleh null, kosong, atau hanya berisi spasi.
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotBlank berguna untuk melakukan validasi bahwa field tidak boleh null, kosong, atau hanya berisi spasi, sedangkan @NotNull hanya memastikan bahwa field tidak boleh null, tapi boleh spasi.
```

### 6. Apa fungsi @Email?

Jawaban:

```text
Untuk melakukan validasi bahwa field email merupakan alamat email yang valid (mis: @gmail.com, @yahoo.com)
```

### 7. Apa fungsi @Size?

Jawaban:

```text
Untuk melakukan validasi bahwa value di field tidak lebih dari batas max dan tidak kurang dari batas min.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Maka request body tidak akan lolos ke dto dan prosesnya akan terhenti pada exception handling.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
Exception handling untuk menangani keadaan di mana input nya tidak lolos validasi.
```

### 10. Apa itu standard error response?

Jawaban:

```text
Standard error response adalah error message yang dimunculkan oleh program secata otomatis tanpa kustomisasi.
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Agar standar dan mudah dipahami oleh berbagai pihak yang terlibat dalam project aplikasi tsb.
```

### 12. Apa itu field-level error?

Jawaban:

```text
Field-level error adalah jenis error yang terjadi pada field  tertentu, biasanya terkait dengan validasi input.
```

### 13. Apa itu custom exception?

Jawaban:

```text
Exception yang dibuat sendiri secara custom dengan message dan parameter sendiri untuk memenuhi kebutuhan bisnis, tidak menggunakan jenis exception built-in dari Spring.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
Karena bersifat lebih spesifik, bahwa error terjadi karena resource yang dicari tidak ditemukan.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah anotasi di Spring yang berfungsi untuk menangani exception global yang berlaku di seluruh controller.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler adalah anotasi di Spring yang berfungsi untuk menangani exception tertentu secara spesifik di dalam aplikasi.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Agar lebih mudah untuk di-mantain dan di-scale serta lebih konsisten dalam mapping exception handling nya.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request dari client tidak valid, misalnya tidak memenuhi validasi yang sudah ditentukan.
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource tidak ditemukan, misalnya ketika mengakses halaman yang belum dibuat routing API nya.
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Ketika ada error dari sisi server, misalnya crash.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Karena dapat menyebabkan risiko keamanan, kebocoran informasi, serta berpotensi membuat pengalaman user yang buruk.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
Client kirim request -> Request masuk ke Controller -> Proses validasi dilakukan -> Validasi gagal karena email invalid -> Exception ditangkap oleh MethodArgumentNotValidException yang sudah di-set -> Melakukan ekstraksi field-level error -> Error message dikirim ke client (response)
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Client kirim request -> Request masuk ke Controller -> Sistem mencari data sesuai dengan path parameter -> Data tidak ditemukan -> Exception ditangkap oleh CustomerNotFoundException yang sudah di-set -> Error message dikirim ke client (response)
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error -> terjadi karena input customer tidak valid
Business error -> terjadi karena business logic tidak terpenuhi meskipun inputnya valid
System error -> terjadi karena masalah di sistem, misalnya server crash/error
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Melakukan set up exception handling untuk berbagai jenis exception.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. HTTP Status Code
2. Jenis-jenis exception
3. Custom Exception
```

Apa 2 hal yang masih membingungkan?

```text
1. Melakukan coding untuk custom exception
2. Cara menggabungkan beberapa error dalam 1 list
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara nya agar terbiasa melakukan coding, terutama ketika sudah semakin kompleks dengan exception2?
```
