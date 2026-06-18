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
Validasi request adalah proses validasi terhadap request yang masuk sebagai mekanisme kontrol dan memastikan bahwa request yang masuk sudah sesuai dengan kondisi-kondisi yang ditetapkan.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Karena validasi di frontend lebih berfokus pada menyaring data yang masuk sedangkan validasi backend lebih berfokus pada validasi business logic.
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Setahu saya, maka datanya tidak akan masuk ke database karena kolom pada database tidak match dengan format datanya.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Contoh: Apabila ada value angka pada field full_name, maka akan me-return error message "Numbers are not allowed."
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Contoh: Apabila tidak ada "@" pada field email, maka akan me-return error message "Please enter a valid email  address."
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Contoh: Apabila ada value huruf pada field phone_number, maka akan me-return error message "Please enter a valid phone number."
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis memastikan bahwa program dapat berjalan dengan lancar, misalnya tanpa error atau infinite loop. Sedangkan validasi bisnis memastikan bahwa business logic nya sesuai dengan kebutuhan bisnis, misalnya formula untuk menghitung Debt Burden Ratio (DBR).
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Untuk melakukan validasi pada field-field di aplikasi.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Untuk melakukan validasi bahwa field tidak boleh hanya berisi spasi.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Untuk melakukan validasi bahwa field tidak boleh null (kosong).
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Untuk melakukan validasi bahwa field email merupakan alamat email yang valid (mis: @gmail.com, @yahoo.com)
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Untuk melakukan validasi bahwa value di field tidak lebih dari batas max dan tidak kurang dari batas min.
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotBlank berguna untuk melakukan validasi bahwa field tidak boleh hanya berisi spasi, sedangkan @NotNull memastikan bahwa field tidak boleh null (kosong), tapi boleh spasi.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Bisa di model pada saat deklarasi attribute ataupun di service.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error handling adalah suatu mekanisme untuk meng-handle atau mengatur aksi yang dijalankan ketika terjadi error.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Agar lebih mudah dipahami oleh user yang menggunakan dan dapat mengarahkan user untuk menggunakan solusi yang sama untuk error response yang sama.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Stack trace adalah rekam jejak flow pemanggilan request sebelum terjadi error.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 -> Request dari client tidak valid, 404 -> resource tidak ditemukan, 500 -> terjadi error di sisi server
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request dari client tidak valid, misalnya tidak memenuhi validasi yang sudah ditentukan.
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource tidak ditemukan, misalnya ketika mengakses halaman yang belum dibuat routing API nya.
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Ketika ada error dari sisi server, misalnya crash.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah error atau kejadian tidak terduga yang membuat program tidak berjalan seperti yang seharusnya.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
RuntimeException adalah error yang terjadi saat program sedang dijalankan atau di-run.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah tipe exception atau error yang dibuat secara custom dan bukan tipe exception bawaan (built-in).
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Untuk menampung error yang terjadi ketika resource atau customer yang di-request tidak ada. Misalnya, ada request untuk Customer ID 105 sedangkan jumlah customer hanya ada sampai ID 100.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation Error -> terjadi karena error pada input-an user yang tidak valid.
Business Error -> terjadi ketika inputnya valid, tetapi aktivitasnya tidak bisa diselesaikan karena tidak diperbolehkan oleh business logic. Cth: Insufficient balance
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah anotasi yang berguna untuk mengatur berbagai controller.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler adalah handler yang sudah di-set up untuk menampung exception-exception yang mungkin terjadi dan membuat handling untuk menangani exception tsb agar program tidak berhenti berjalan.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Karena tidak sesuai dengan prinsip mendasar DRY (Don't Repeat Yourself) dan dapat menimbulkan inkonsistensi.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Untuk mempercepat proses debugging dan membuat arsitektur code lebih bersih dan terstruktur.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |1|
| Bean Validation |1|
| HTTP status code |2|
| Exception |1|
| Custom exception |1|
| Global error handling |1|
| Standard error response |1|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
