# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Tulis jawaban di sini.
melakukan validasi terhadap request body dengan peraturan yang sudah ditentukan pada field dalam class atau DTO dengan annotation @Valid di depan RequestBody
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Tulis jawaban di sini.
Karena backend adalah source of truth dan validasi harus dilakukan di dua sisi agar menambah layer validasi yang handal dan data tetap akurat
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
Tulis jawaban di sini.
untuk memastikan Data Valid sesuai dengan yang telah ditentukan di field biasa diberikan didepan RequestBody
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
Tulis jawaban di sini.
Untuk memastikan bahwa nilai suatu field berupa teks tidak null, panjangnya lebih dari nol, dan tidak hanya berisi spasi kosong (whitespace). Biasanya digunakan untuk tipe data String.
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
Tulis jawaban di sini.
@NotNull hanya memastikan bahwa nilai field tidak null (bisa menerima string kosong "" atau spasi " "). Sedangkan @NotBlank lebih ketat, karena memastikan nilai tidak null, tidak kosong "", dan tidak berisi spasi saja "   ".
```

### 6. Apa fungsi @Email?

Jawaban:

```text
Tulis jawaban di sini.
Untuk memvalidasi bahwa format string yang dikirimkan oleh client memenuhi standar struktur alamat email yang valid (memiliki karakter '@' dan domain yang tepat).
```

### 7. Apa fungsi @Size?

Jawaban:

```text
Tulis jawaban di sini.
Untuk membatasi jumlah panjang karakter (String) atau ukuran elemen (Collection/Array/List) berdasarkan nilai minimum (min) dan maksimum (max) yang ditentukan.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Tulis jawaban di sini.
Spring secara otomatis akan menghentikan eksekusi method di Controller, membatalkan proses logic, dan melemparkan (throw) exception berupa MethodArgumentNotValidException ke framework untuk dikembalikan sebagai error response ke client.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
Tulis jawaban di sini.
Exception bawaan Spring Boot yang otomatis dilemparkan ketika sebuah argumen yang diberi anotasi @Valid (seperti request body) gagal melewati proses validasi constraint (seperti @NotBlank atau @Size).
```

### 10. Apa itu standard error response?

Jawaban:

```text
Tulis jawaban di sini.
Format objek JSON error yang seragam dan konsisten untuk semua endpoint, biasanya berisi informasi terstruktur seperti timestamp, HTTP status code, pesan error utama, dan detail error per field.
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Tulis jawaban di sini.
Agar memudahkan tim frontend atau client aplikasi dalam melakukan parsing data error secara modular, sehingga mereka bisa menampilkan pesan error yang rapi dan seragam di sisi user interface (UI).
```

### 12. Apa itu field-level error?

Jawaban:

```text
Tulis jawaban di sini.
Detail error yang menginfokan field atau properti spesifik mana yang gagal divalidasi beserta alasan/pesan error-nya (contoh: "email": "Format email tidak valid").
```

### 13. Apa itu custom exception?

Jawaban:

```text
Tulis jawaban di sini.
Class exception yang dibuat sendiri oleh developer dengan mewarisi (extends) RuntimeException untuk merepresentasikan kondisi error bisnis yang spesifik di dalam aplikasi (domain-specific error).
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
Tulis jawaban di sini.
Karena lebih deskriptif secara bisnis, memudahkan proses debugging, dan memungkinkan @ControllerAdvice untuk menangkap error tersebut secara spesifik guna memberikan HTTP status 404 Not Found secara akurat.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai komponen global interceptor yang bertugas menangkap (intercept) setiap exception yang terjadi di seluruh Controller, sehingga penanganan error terpusat di satu tempat.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
Tulis jawaban di sini.
Anotasi yang ditempatkan di dalam method @ControllerAdvice untuk menentukan jenis exception spesifik apa yang ingin ditangani oleh method tersebut beserta format response yang akan dikembalikan.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Tulis jawaban di sini.
Agar kode di Controller tetap bersih (tidak dipenuhi blok try-catch), menghindari duplikasi kode penanganan error, dan menjamin seluruh endpoint mengembalikan format error yang seragam.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Tulis jawaban di sini.
Ketika client mengirimkan request dengan sintaks data yang salah, payload JSON rusak, atau gagal melewati proses validasi skema/field DTO.
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Tulis jawaban di sini.
Ketika resource atau data yang diminta oleh client tidak eksis di dalam database (contoh: mencari customer dengan ID yang salah).
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Tulis jawaban di sini.
Ketika terjadi kegagalan atau error yang tidak terduga di sisi server, seperti database down, NullPointerException yang tidak ditangkap, atau kegagalan sistem internal lainnya.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Tulis jawaban di sini.
Karena alasan keamanan (security risk). Stack trace membocorkan struktur kode, nama package, library yang digunakan, hingga query internal yang bisa dimanfaatkan oleh attacker untuk mencari celah keamanan.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
Tulis jawaban di sini.
1. Client mengirim request POST dengan format email salah.
2. DispatcherServlet meneruskan request ke Controller.
3. Anotasi @Valid mendeteksi pelanggaran aturan @Email pada DTO.
4. Spring melempar MethodArgumentNotValidException.
5. @ControllerAdvice menangkap exception tersebut.
6. Method dengan @ExceptionHandler(MethodArgumentNotValidException.class) mengekstrak pesan error field dan mengembalikan JSON response dengan status 400 Bad Request ke client.
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
Tulis jawaban di sini.
1. Client memanggil endpoint GET dengan ID 999.
2. Service layer melakukan query ke database, namun hasilnya kosong.
3. Service layer sengaja melempar (throw) CustomerNotFoundException.
4. Exception naik ke Controller hingga diintercept oleh @ControllerAdvice.
5. @ExceptionHandler menangkap custom exception tersebut dan menyusun struktur error response.
6. Server mengembalikan response JSON error dengan HTTP status 404 Not Found ke client.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Tulis jawaban di sini.
- Validation Error: Kesalahan input format data dari client sebelum masuk ke logika bisnis (HTTP 400).
- Business Error: Input format benar, tetapi melanggar aturan logika bisnis aplikasi, seperti data tidak ditemukan atau saldo kurang (HTTP 404 / 422).
- System Error: Kegagalan teknis pada infrastruktur server atau database (HTTP 500).
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Tulis jawaban di sini.
Bagian memeriksa postman 
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Validation
2. Exception Hanlder
3. Custom Exception
```

Apa 2 hal yang masih membingungkan?

```text
1. Custom Exception
2. Menyesuaikan Response Format
```

Apa 1 pertanyaan untuk mentor?

```text
Tulis pertanyaan di sini.
Best Practice dalam membuat GlobalExceptionHandler dan format Standard Response
```
