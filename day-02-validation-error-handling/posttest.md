# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
memvalidasi input dari client
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
karena untuk keamanan yang berlayer dan kita tidak bisa sepenuhnya percaya pada frontend
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
untuk memvalidasi terhadap request body
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
lebih strict dan tidak menerima whitespace ataupun null
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
notnull masih menerima whitespace sedangkan notblank tidak menerima sama sekali
```

### 6. Apa fungsi @Email?

Jawaban:

```text
agar input request sesuai dengan format email
```

### 7. Apa fungsi @Size?

Jawaban:

```text
agar input memiliki nilai minimal atau maksimal karakter
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
maka akan keluar hasil validation error karena tidak sesuai format
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
request yang dibuat tidak sesuai format
```

### 10. Apa itu standard error response?

Jawaban:

```
format pesan yang dikirim dari server untuk menampilkan error
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
mempercepat pemecahan masalah, mencegah aplikasi crash, dan memberikan pengalaman pengguna yang jelas
```

### 12. Apa itu field-level error?

Jawaban:

```text
Error ini terjadi ketika data yang dimasukkan pengguna tidak sesuai dengan format yang diminta
```

### 13. Apa itu custom exception?

Jawaban:

```text
exception yang dibuat secara khusus oleh developer untuk error tertentu
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
agar bisa mengetahui detail errornya
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
berfungsi sebagai pusat exception handling dan konfigurasi global untuk seluruh controller
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
mekanisme penanganan error
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
agar error lebih mudah di trace dan kodenya lebih clean
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request tidak sesuai format
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika request tidak ditemukan dalam server
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika server mengalami error atau kendala
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
dapat membahayakan keamanan aplikasi dan memberikan pengalaman pengguna yang buruk
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
maka akan muncul pesan error bahwa input email harus sesuai dengan format @email yang telah kita buat di dto
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
akan muncul pesan error bahwa data yang diambil tidak ada, ini kita masukkan ke dalam CustomerNotFoundException untuk mengatur errornya
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation Error terjadi karena data input salah, Business Error ditolak karena melanggar aturan proses kerja, dan System Error terjadi akibat kerusakan teknis
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
membuat GlobalExceptionHandler karena saya masih bingung cara menggunakan setiap method exception nya, saya sempat bingung juga dalam membuat dto ErrorResponse karena harus membuat sebuah method
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. input validation
2. exception handling
3. api documentation menggunakan postman & swagger
```

Apa 2 hal yang masih membingungkan?

```text
1. cara menggunakan GlobalExceptionHandler
2. membuat method untuk membuat response dalam bentuk List di ErrorResponse
```

Apa 1 pertanyaan untuk mentor?

```text
cara membuat GlobalExceptionHandler, karena saya masih bingung cara memanggil method bawaan dari spring
```
