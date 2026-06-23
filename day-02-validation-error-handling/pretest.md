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
proses memastikan data yang dikirim client ke API sesuai dengan format
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Karena frontend bisa dilewati dan backend adalah lapisan terakhir untuk menjaga keamanan
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Data tidak valid di database, bug di aplikasi, error runtime
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Tidak null, tidak kosong, panjang minimal misalnya 3 karakter
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Format email valid dan tidak kosong
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Hanya angka, panjang tertentu, dan tidak kosong
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis: format, null, panjang data
Validasi bisnis: aturan domain, misalnya email harus unik
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Untuk memicu proses validasi pada object yang diterima 
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Memastikan string tidak null, tidak kosong, dan tidak hanya berisi spasi
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Memastikan nilai tidak null.
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Memastikan format string sesuai dengan format email
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Membatasi panjang minimum dan/atau maksimum data
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya cek null, @NotBlank juga cek kosong dan spasi (khusus String)
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
DTO
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
menangani error agar aplikasi tetap terkontrol dan memberikan response yang jelas
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Agar mudah dipahami client dan memudahkan debugging
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Membocorkan detail internal aplikasi
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400: request client tidak valid
404: resource tidak ditemukan
500: kesalahan di sisi server
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Saat input client salah atau gagal validasi
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Saat resource yang diminta tidak ada
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Saat terjadi error tak terduga di server
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Kondisi error yang terjadi saat program berjalan
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
Exception yang terjadi saat runtime
```

### 24. Apa itu custom exception?

Jawaban:

```text
Exception buatan sendiri untuk merepresentasikan kasus tertentu
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Agar error lebih spesifik, mudah dipahami, dan mudah ditangani
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error: input tidak valid
Business error: melanggar aturan bisnis
System error: masalah teknis sistem
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
Class global untuk menangani exception dari seluruh controller
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Method untuk menangani jenis exception tertentu
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Menghindari duplikasi kode
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Kode lebih rapi, konsisten, dan mudah dikembangkan
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |5 |
| Bean Validation |3 |
| HTTP status code |5 |
| Exception |5 |
| Custom exception |5 |
| Global error handling |5 |
| Standard error response |5 |

## Notes

```text
Fitur yang dimiliki springboot
```
