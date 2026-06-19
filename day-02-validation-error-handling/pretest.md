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
Validasi request  adalah proses validasi untuk memastikan request yang akan dijalankan sudah sesuai dengan yang dibutuhkan.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Backend memerlukan validasi untuk memastikan aplikasi berjalan sync dengan business logic.
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Dari bayangan saya sepertinya akan terjadi error karena data yang terkirim tidak memiliki format yang sama.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
full_name -> boleh kosong.
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
email ->  harus terdapat @ dan mail.
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
phone number -> harus berisi angka dan tidak boleh huruf dengan panjang yang dilimitkan sesuai dengan panjang minimal serta maksimal phone number di negara tertentu.
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis -> validasi berfungsi untuk memastikan data yang dimasukan sudah sesuai dengan format aplikasi (e.g. panjang input atau format data.)
Valdiasi bisnis -> validasi berfungsi untuk memastikan flow business sudah sesuai (e.g. legalitas umur).
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
@Valid berfungsi untuk memastikan data yang masuk sudah sesuai dengan format yang diinginkan.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
@NotBlank berfungsi untuk memastikan field tidak boleh kosong, khusus untuk tipe String.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
@NotNull berfungi untuk memastikan field tidak boleh kosong, khusus untuk tipe numerik.
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Tulis jawaban di sini.
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Tulis jawaban di sini.
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotBlank -> untuk tipe data String.
@NotNull -> untuk tipe data numerik.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Validasi di Spring Boot diletakkan di DTO.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error handling adalah logic yang berfungsi untuk mengawasi atau mengontrol agar tidak terjadinya error (e.g. program menginformasikan ketika user mengetik data dengan format yang salah sebelum user bisa submit datanya, sehingga user bisa menggantinya terlebih dahulu sebelum submit data).
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Error response perlu dibuat konsisten agar user dapat jauh lebih memahami ketika terjadi error dan langsung tau apa yang harus dilakukan.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Tulis jawaban di sini.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text

```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
404 Bad Request digunakan untuk memberitahukan bahwa request client ke server tidak baik atau tidak valid.
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan untuk memberitahukan bahwa URL yang dituju tidak ditemukan.
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 Internal Server Error digunakan untuk memberitahukan bahwa terjadi error pada server internal.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah kondisi di mana aplikasi mengalami kejadian yang tidak normal.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
Tulis jawaban di sini.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Tulis jawaban di sini.
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Tulis jawaban di sini.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error -> data tidak valid atau sesuai format (e.g. email tidak menggunakan @).
Business error -> melanggar rule bisnis (e.g. user di bawah 17 tahun).
System error -> kesalahan sistem ketika aplikasi diproses.
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
Tulis jawaban di sini.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Tulis jawaban di sini.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Agar error handling tidak menumpuk satu sama lain.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Centralized error handling berfungsi agar kode dapat dikelola lebih mudah.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation | 4 |
| Bean Validation | 3 |
| HTTP status code | 4 |
| Exception |3 |
| Custom exception |2 |
| Global error handling | 2 |
| Standard error response | 2 |

## Notes

```text
Perbedaan antara masing-masing exception.
```
