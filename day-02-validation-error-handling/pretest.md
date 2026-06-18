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
proses pengecekan data yang dikirim oleh client agar sesuai dengan aturan yang telah ditentukan sebelum diproses lebih lanjut oleh sistem
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
karena request dapat langsung dikirim ke backend tanpa harus melalui frontend sehingga backend harus memastikan data tetap valid dan aman
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
data tidak valid dapat tersimpan di database sehingga menyebabkan error pada aplikasi serta bisa menghasilkan data yang tidak konsisten
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
data tidak boleh berisi kurang dari 3 huruf dan tidak mengandung angka(kecuali kondisi khusus), serta maksimal 100 huruf
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
data harus mengandung unsur "@" dan tidak boleh kosong
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
tidak mengandung huruf, minimal berisi 10 angka dan maksimal 12(khusus indonesia)
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis memeriksa format atau struktur data, sedangkan validasi bisnis memeriksa aturan bisnis yang berlaku
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
memiliki fungsi untuk memastikan data yang kita berikan ke server sudah sesuai aturan
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
untuk memastikan data berbentuk teks yang kita kirimkan harus memiliki nilai tidak boleh kosong
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
untuk memastikan bahwa data yang kita kirim harus memiliki nilainya biasanya terfokus pada angka
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
untuk memastikan bahwa inputan client bener bener memilkiki pola dari email
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
untuk memastikan bahwa inputan client bener bener memilkiki ukuran yang sesuai dengan yang diperbolehkan
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
notblank tidak boleh isinya bener bener kosong, notnull juga sebenernya sama tapi dia masih boleh isinya cuma spasi aja yang penting gak null
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
biasanya di taruh di dto
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
cara program mencegah terjadinya eror dengan biasanya menampilkan eror response
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
agar client memahami eror yang didapat, dan juga mempermudah developer dalam debugging atau development
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
karena bisa membocorkan informasi internal aplikasi yang berpotensi dimanfaatkan secara negatif
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 = request dari client tidak valid
404 = resource tidak ditemukan
500 = terjadi kesalahan pada server
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request yang dikirim client tidak memenuhi aturan validasi atau format yang diharapkan
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika resource yang diinginkan tidak ditemukan
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika terjadi error pada sisi server
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
objek yang merepresentasikan kondisi error saat aplikasi berjalan
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
exception yang terjadi saat program berjalan
```

### 24. Apa itu custom exception?

Jawaban:

```text
exception yang dibuat sendiri untuk menangani error tertentu
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
agar ketika program tidak bisa menemukan customer dapat bisa di berikan informasi yang lebih spesifik
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
validation error = inputan yang tidak valid
business error = terjadi kesalahan dalam proses bisnis
system error = terjadi kesalahan dari sisi sistem
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
berfungsi menangani exception pada keseluruhan controller
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
menentukan method yang akan menangani exception tertentu
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
biar error handling nya tidak redundant dan mempermudah debugging
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
cukup membuat 1 eror handling saja nanti tinggal dipanggil aja di class yang dibutuhkan jadi mudah dalam maintenance
```

## Self Assessment

| Area                    | Score 1-5 |
| ----------------------- | --------- |
| Request validation      | 4         |
| Bean Validation         | 2         |
| HTTP status code        | 2         |
| Exception               | 4         |
| Custom exception        | 3         |
| Global error handling   | 3         |
| Standard error response | 3         |

## Notes

```text
aku kadang tau dalam teori tapi belum pernah menggunakan di spesifik codenya, terutama notnull yang kenapa masih meloloskan spasi
```
