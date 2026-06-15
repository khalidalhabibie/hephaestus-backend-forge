# Pretest - Validation & Error Handling

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang validasi request dan error handling pada REST API.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Validation Basic

### 1. Apa itu validasi request?

Jawaban:

```text
Validasi request adalah proses pengecekan data yang dikirim oleh user sebelum diproses.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Karena Backend bukan hanya dikonsumsi oleh Frontend tetapi juga oleh service lainnya. Sehingga penting untuk Backend tetap memastikan data yang masuk tetap valid dan aman.
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Data yang disimpan menjadi missing, tidak valid, dan memungkinkan mengganggu proses bisnis.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Memastikan tipe data untuk field full_name memiliki tipe String, wajib diisi (tidak boleh kosong), dan memiliki panjang karakter tertentu.
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Email wajib diisi dan harus memiliki format email yang benar, misalnya user@email.com.
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Nomor telepon wajib diisi, hanya boleh berisi angka, dan panjangnya harus sesuai ketentuan panjang nomor telepon misalnya 10-15 digit.
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis fokus pada bentuk data untuk memeriksa format data, sedangkan validasi bisnis fokus pada aturan domain yang dilakukan di service layer untuk memeriksa apakah data memenuhi aturan bisnis.
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
@Valid digunakan untuk menjalankan proses validasi.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
@NotBlank digunakan untuk memastikan field tidak null, kosong, atau hanya berisi spasi.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
@NotNull digunakan untuk memastikan field tidak null.
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
@Email digunakan untuk memastikan nilai field memiliki format email.
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
@Size digunakan untuk mengatur batas minimum dan maksimum panjang karakter.
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya memastikan nilai tidak null, sedangkan @NotBlank memastikan nilai tidak null, tidak kosong, dan tidak hanya berisi spasi.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Biasanya diletakkan di DTO (Data Transfer Object).
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error handling adalah mekanisme untuk menangani error yang terjadi agar aplikasi tetap berjalan dengan baik. Jika terjadi error maka user dapat mengetahui penyebab terjadinya error.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Agar mudah dipahami user dan mudah dilakukan debugging.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Memungkinankan kebocoran detail internal aplikasi yang tidak boleh diketahui oleh sembarang orang.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 digunakan ketika request dari client tidak valid, 404 ketika data yang dicari tidak ditemukan, dan 500 ketika terjadi kesalahan di sisi server.
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request yang dikirim client tidak sesuai aturan validasi.
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika data yang diminta tidak ditemukan di sistem.
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Ketika terjadi error yang tidak terduga pada server.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah kondisi error yang terjadi saat program berjalan.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
RuntimeException adalah exception yang terjadi saat aplikasi dijalankan.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception yang dibuat sendiri untuk menangani kondisi tertentu sesuai kebutuhan.
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Agar kita mengetahui error yang terjadi jika customer tidak ditemukan lebih spesifik sehingga mudah ditangani.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error --> error yang terjadi jika input tidak valid
Business error --> error yang terjadi tidak sesuai dengan aturan pada prses bisnis.
System error --> error yang terjadi jika terjadi masalah teknis pada sistem
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah komponen yang digunakan untuk menangani exception secara terpusat.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler adalah komponen yang digunakan untuk menentukan cara penanganan exception tergantung jenis exception.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Karena membuat code menjadi berulang dan mudah dimaintain.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Centralized error handling dilakukan supaya penanganan error ditangani secara terpusat.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |1|
| Bean Validation |1|
| HTTP status code |3|
| Exception |2|
| Custom exception |1|
| Global error handling |2|
| Standard error response |2|

## Notes

```text
Karena ini merupakan hal yang baru untuk diplajari sehinggan yang sulit adalah memahami hal materi dari 0.
```

