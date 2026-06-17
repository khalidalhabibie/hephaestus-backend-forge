# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
mengecek dan memastikan data yang dikirim oleh user ke server sudah benar, lengkap, dan sesuai aturan sebelum diproses lebih lanjut
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
untuk memastikan kembali apakah sudah sesuai, lebih tepatnya melakukan cross check data yang masuk. Karena untuk memastikan data yang masuk sudah sesuai (takut ada data yang tidak sesuai lolos)
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
untuk mengaktifkan validasi otomatis pada object berdasarkan aturan yang didefinisikan sebelumnya
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
memastikan string benar-benar berisi teks (bukan kosong)
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull → hanya cek ada nilai (tidak null)
@NotBlank → memastikan string benar-benar berisi teks (bukan kosong)
```

### 6. Apa fungsi @Email?

Jawaban:

```text
memastikan mengikuti format email yang benar
```

### 7. Apa fungsi @Size?

Jawaban:

```text
memastikan panjang data sesuai batas minimum dan maksimum
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
backend tidak akan memproses request tersebut lebih lanjut dan langsung mengembalikan response error ke user
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
exception yang terjadi ketika validasi request gagal pada parameter method controller
```

### 10. Apa itu standard error response?

Jawaban:

```text
format baku (konsisten) untuk pesan error yang dikirim oleh backend ke user
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
untuk kemudahan integrasi, maintainability, dan reliability sistem
```

### 12. Apa itu field-level error?

Jawaban:

```text
error validasi yang spesifik ke satu field (kolom) tertentu dalam request
```

### 13. Apa itu custom exception?

Jawaban:

```text
exception yang dibuat sendiri oleh developer untuk merepresentasikan error yang spesifik sesuai kebutuhan aplikasi atau bisnis
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
karena memberikan makna yang jelas, kontrol lebih baik, dan struktur kode yang lebih rapi
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
annotation untuk menangani error (exception) secara global di seluruh controller
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
annotation untuk menangani exception secara global, sehingga error handling menjadi lebih rapi, konsisten, dan mudah dikelola
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
karena membuat sistem lebih rapi, konsisten, mudah di-maintain
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request dari client tidak valid secara teknis sehingga server tidak bisa memprosesnya
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika resource atau data yang diminta tidak ditemukan di server, meskipun request yang dikirim sudah benar
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika terjadi kesalahan di sisi server yang tidak disebabkan oleh kesalahan client
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
data bisa bocor dan keamanan tidak akan kuat (atau mudah dihack)
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
saat email invalid, request akan gagal di layer validasi, memicu MethodArgumentNotValidException, ditangani oleh @ControllerAdvice, lalu dikembalikan sebagai standard error response tanpa menjalankan business logic
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Saat data customer tidak ditemukan, service melempar CustomerNotFoundException, yang kemudian ditangani secara global oleh @ControllerAdvice dan dikembalikan sebagai response 404 Not Found ke client
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
validation error → error yang terjadi karena input dari user tidak valid
business error → error yang terjadi saat logika bisnis berjalan, tapi kondisi tidak terpenuhi
system error → error yang terjadi karena masalah di sistem / server, bukan dari user atau business logic
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
pada bagian tes API karena tadi sudah sesuai namun masih belum sesuai dengan output
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. mengetahui beberapa anotasi
2. mengetahui cara springboot bekerja
```

Apa 2 hal yang masih membingungkan?

```text
1. bagian tes API karena tadi sudah sesuai namun masih belum sesuai dengan output
```

Apa 1 pertanyaan untuk mentor?

```text
Tidak ada                                                   l
```