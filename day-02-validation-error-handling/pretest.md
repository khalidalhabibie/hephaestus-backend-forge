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
Suatu hal yang dilakukan untuk meng-handle permintaan dari client
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Untuk meminimalisir resiko
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Akan terjadi error 400 Bad Request ataupun bisa saja 500 Internal Server Error
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Tidak boleh ada angka
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Harus berakhiran @email.com
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Tidak boleh ada huruf
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis dari permintaan API, validasi bisnis yang mengarah ke database
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Untuk mengecek apakah field valid atau tidak
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Validasi field tidak boleh kalau tidak dikirim
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Validasi field tidak boleh kosong/null
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Validasi field tidak harus berbentuk email, harus berakhiran @email.com
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Validasi maksimal ukuran
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotBlank wajib dikirim, @NotNull tidak boleh kosong/null
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Controller
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Suatu cara untuk mengatasi potensi error di code
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Untuk lebih mempermudah FrontEnd dan UX yang nyaman karena konsisten
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Durasi menunggu response dari BackEnd akan sangat lama
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 itu bad request, 404 not found (tidak ditemukan), 500 itu internal server error
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika format field yang dikirim dari client tidak valid
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika data yang dicari oleh client tidak ditemukan di database
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Untuk handle error (exception) di controller maupun service
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Pengecualian dalam kode untuk meng-handle error
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
Handle untuk handle error "Run Time Error"
```

### 24. Apa itu custom exception?

Jawaban:

```text
Handling error untuk error yang dikustomisasi
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Untuk mengatasi jika user/client mencari data customer, tetapi data customer tersebut tidak ada di database
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error untuk handle request dari client, business error ialah error di dalam service, dan system error ialah error dari servernya itu sendiri
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
Anotasi untuk menandai suatu class adalah controller
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Handler untuk mengatasi error
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Untuk meminimalisir server crash
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Agar error yang dikirim ke client akan konsisten dan tidak berubah-ubah
```

## Self Assessment

| Area                    | Score 1-5 |
| ----------------------- | --------- |
| Request validation      | 4         |
| Bean Validation         | 4         |
| HTTP status code        | 4         |
| Exception               | 4         |
| Custom exception        | 4         |
| Global error handling   | 4         |
| Standard error response | 4         |

## Notes

```text
Seputar best practice handling error di BackEnd
```
