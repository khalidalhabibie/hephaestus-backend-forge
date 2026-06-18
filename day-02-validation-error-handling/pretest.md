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
Validasi request ini merupakan sebuah prosedur dalam program untuk melakukan pengecekan terhadap data atau permintaan yang masuk melalui API.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Validasi di level backend tetap harus dilakukan walau di frontend sudah dilakukan adalah meminimalisir terjadinya kesalahan akibat adanya celah validasi di frontend dan langsung masuk ke database karena tidak ada validasi di backend
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Risiko jika API menerima data kosong atau format data yang salah adalah menyebabkan terjadinya error atau logic error apalagi jika data salah itu sampai masuk ke database
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Validasi untuk field name dapat berupa maksimal karakter, tidak boleh kosong, tidak boleh ada angka
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
validasi untuk field email dapat berupa formatnya harus email (dengan @email), maksimal panjang karakter, tidak boleh dikosongkan, tidak boleh ada huruf kapital
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Validasi untuk field phone_number dapat berupa inputan yang dimasukkan harus berupa angka, ,maksimal dan minimal jumlah digit nomor, tidak boleh dikosongkan
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Perbedaan validasi teknis dan validasi bisnis adalah validasi teknis biasanya terkait dengan kesesuaian format data yang masuk ke program sedangkan validasi bisnis berkaitan dengan ketentuan yang berkaitan dengan rule bisnis contoh seperti batas usia
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Anotation @Valid berfungsi untuk memicu proses validasi terhadap objek yang diterima berdasarkan aturan validasi yang telah didefinisikan menggunakan annotation seperti @NotNull, @Size, @Email, dan lain-lain.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
@NotBlank digunakan untuk melakukan validasi bahwa inputan yang masuk tidak boleh kosong
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
@NotNull itu untuk melakukan validasi tidak boleh value data tidak boleh bernilai NULL
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Anotasi @email itu untuk memastikan bahwa data yang masuk itu memiliki format email
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Fungsi @Size ini untuk melakukan validasi maksimal panjang karakter yang diizinkan
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
Perbedaan @NotBlank dan @NotNull adalah jika @NotBlank tidak boleh kosong nilai datanya, sedangkan @NotNull masih boleh tapi tidak boleh bernilai NULL
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Validasi di Spring Boot biasanya diletakkan di object DTO (Data Transfer Object), khususnya pada field-field di dalam class tersebut menggunakan annotation seperti @NotNull, @Size, @Email, dan lain-lain.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error handling adalah proses menangani error atau exception yang terjadi dalam aplikasi agar tidak menyebabkan crash dan dapat memberikan respon yang jelas kepada client.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text

Error response perlu konsisten agar mudah dipahami oleh client (frontend atau API consumer), memudahkan debugging, dan menjaga standar komunikasi antar sistem.

```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Mengirim stack trace ke client berisiko membocorkan informasi internal aplikasi seperti struktur kode, library yang digunakan, dan logic bisnis.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text

- 400 Bad Request: Client mengirim request yang tidak valid (misalnya format salah).
- 404 Not Found: Resource yang diminta tidak ditemukan.
- 500 Internal Server Error: Terjadi kesalahan di server yang tidak dapat diprediksi.
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Digunakan ketika request dari client tidak valid, seperti data tidak sesuai format, missing field, atau gagal validasi.
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Digunakan ketika resource yang diminta tidak ditemukan, misalnya ID tidak ada di database
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Digunakan ketika terjadi kesalahan di server yang tidak diketahui atau tidak tertangani, seperti null pointer exception atau error sistem lainnya.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah kondisi error yang terjadi saat program dijalankan (runtime) yang mengganggu alur normal program.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
RuntimeException adalah jenis exception yang terjadi saat runtime dan tidak wajib ditangani (unchecked exception), seperti NullPointerException atau IllegalArgumentException.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception yang dibuat sendiri oleh developer untuk merepresentasikan kondisi error tertentu sesuai dengan kebutuhan bisnis aplikasi.

```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Untuk merepresentasikan error spesifik ketika data customer tidak ditemukan, sehingga code lebih jelas, terstruktur, dan mudah dibaca dibanding menggunakan exception umum.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error: Error karena input tidak valid (misalnya format salah).
Business error: Error karena melanggar aturan bisnis (misalnya saldo tidak cukup).
System error: Error karena masalah teknis sistem (misalnya server crash atau koneksi database gagal).
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah annotation di Spring yang digunakan untuk menangani exception secara global di seluruh controller.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler digunakan untuk mendefinisikan method yang menangani jenis exception tertentu.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Karena akan menyebabkan duplikasi kode, sulit dipelihara, dan tidak konsisten dalam response error.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Memudahkan maintain code, menghindari duplikasi, memastikan konsistensi response error, dan mempermudah logging serta debugging.
```

## Self Assessment

| Area                    | Score 1-5 |
| ----------------------- | --------- |
| Request validation      | 4         |
| Bean Validation         | 3         |
| HTTP status code        | 4         |
| Exception               | 3         |
| Custom exception        | 3         |
| Global error handling   | 3         |
| Standard error response | 3         |

## Notes

```text

```
