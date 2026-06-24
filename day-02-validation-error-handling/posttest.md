# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Validasi request adalah proses memeriksa dan memastikan data yang dikirim oleh client (user/aplikasi) ke server sudah benar, lengkap, dan sesuai aturan sebelum diproses lebih lanjut.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Backend tetap perlu validasi karena frontend bisa dimanipulasi atau di-bypass, jadi backend harus memastikan data tetap valid dan aman.
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
@Valid adalah anotasi di Java (biasanya di Spring Boot) yang digunakan untuk mengaktifkan proses validasi otomatis terhadap data request berdasarkan aturan yang sudah didefinisikan.
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
Memastikan sebuah field String tidak null, tidak kosong, dan tidak hanya berisi spasi
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya memastikan nilai tidak boleh null, sedangkan @NotBlank memastikan nilai tidak boleh null, tidak kosong, dan tidak hanya berisi spasi.
```

### 6. Apa fungsi @Email?

Jawaban:

```text
@Email berfungsi untuk memvalidasi bahwa nilai suatu field memiliki format email yang benar, sehingga input seperti "user@email.com" diterima, sedangkan format yang tidak sesuai seperti "useremail" akan ditolak.
```

### 7. Apa fungsi @Size?

Jawaban:

```text
@Size berfungsi untuk membatasi panjang (jumlah karakter) suatu field, baik minimal, maksimal, atau keduanya, sehingga nilai input harus berada dalam rentang yang ditentukan.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Jika request gagal validasi, maka server (backend) akan langsung menolak request tersebut dan mengembalikan error ke client, biasanya dengan status 400 (Bad Request), sehingga proses di controller atau business logic tidak dijalankan.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
MethodArgumentNotValidException adalah exception di Spring Boot yang muncul ketika data request gagal lolos validasi @Valid, sehingga server menolak permintaan dengan error 400 dan tidak melanjutkan proses ke controller.
```

### 10. Apa itu standard error response?

Jawaban:

```text
Standard error response adalah format respon error yang konsisten dan terstruktur dari backend untuk memberitahu client tentang kesalahan yang terjadi dalam request.
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Error response perlu konsisten agar client lebih mudah memahami, meng-handle, dan memproses error secara seragam, sehingga mempermudah debugging dan integrasi antar sistem.
```

### 12. Apa itu field-level error?

Jawaban:

```text
Field-level error adalah kesalahan validasi yang terjadi pada field tertentu dalam request, misalnya ketika satu kolom seperti email atau password tidak memenuhi aturan yang telah ditentukan.
```

### 13. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception (kesalahan) yang dibuat sendiri oleh developer untuk menangani kondisi error tertentu sesuai kebutuhan bisnis aplikasi.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
CustomerNotFoundException lebih baik daripada RuntimeException biasa karena lebih spesifik menjelaskan jenis error yang terjadi, sehingga kode menjadi lebih jelas, mudah dipahami, dan mempermudah penanganan error secara tepat.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice berfungsi untuk menangani exception secara global di seluruh controller, sehingga error handling menjadi lebih rapi, terpusat, dan konsisten di seluruh aplikasi.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler berfungsi untuk menangkap dan menangani exception tertentu di controller atau secara global agar dapat mengembalikan response error yang sesuai.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Error handling sebaiknya centralized karena memudahkan pengelolaan error secara konsisten di seluruh aplikasi, mengurangi duplikasi kode, dan membuat maintenance serta debugging menjadi lebih sederhana dan terstruktur.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request dari client tidak valid atau tidak sesuai dengan aturan yang ditentukan oleh server.
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource atau data yang diminta tidak ditemukan di server, misalnya mencari user atau customer dengan ID tertentu tetapi datanya tidak ada di database.
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Ketika terjadi kesalahan di sisi server yang tidak terduga, sehingga server gagal memproses request meskipun request dari client sudah valid.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Stack trace tidak boleh dikirim ke client karena dapat membocorkan informasi sensitif tentang struktur internal aplikasi, sehingga berisiko dimanfaatkan untuk serangan keamanan.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
Ketika endpoint **POST /api/v1/customers menerima email yang tidak valid, request akan gagal pada tahap validasi oleh @Valid, sehingga Spring melempar MethodArgumentNotValidException dan mengembalikan response 400 Bad Request tanpa melanjutkan ke proses business logic atau database.
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Ketika endpoint **GET /api/v1/customers/999 tidak menemukan data, service biasanya melempar CustomerNotFoundException yang kemudian ditangkap oleh @ControllerAdvice untuk dikonversi menjadi response 404 Not Found, sehingga memberi tahu client bahwa resource yang diminta tidak tersedia.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Perbedaannya adalah validation error terjadi karena input tidak valid, business error terjadi karena aturan bisnis dilanggar, sedangkan system error terjadi karena masalah teknis di server atau sistem.
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Bagian yang paling sulit dari exercise Day 2 adalah memahami dan mengimplementasikan flow error handling secara lengkap (validasi, exception, hingga response yang konsisten) karena melibatkan banyak konsep sekaligus.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text

1. Pentingnya validasi request di backend untuk menjaga keamanan dan integritas data.
2. Cara kerja @Valid dan berbagai anotasi seperti @NotBlank, @Email, dan @Size dalam proses validasi.
3. Alur error handling di Spring Boot dari validasi hingga response menggunakan @ControllerAdvice dan @ExceptionHandler.

```

Apa 2 hal yang masih membingungkan?

```text
1. Cara membuat format standard error response yang lebih kompleks (misalnya ada kode error khusus).
2.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice untuk mendesain error response agar scalable dan tetap konsisten di aplikasi yang besar?
```
