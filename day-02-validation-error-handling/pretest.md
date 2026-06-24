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
Validasi adalah proses untuk memastikan request yang diajukan oleh client adalah valid
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Backend tetap perlu validasi karena frontend bisa dimanipulasi atau di-bypass, jadi backend harus memastikan data tetap valid dan aman.
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Risikonya adalah terjadi error pada sistem, data menjadi tidak valid, serta dapat menimbulkan bug atau celah keamanan pada aplikasi.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Memastikan bahwa field tersebut wajib diisi, hanya mengandung huruf dan spasi, serta memiliki panjang minimal dan maksimal yang telah ditentukan.
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Memastikan bahwa field tersebut wajib diisi, tidak mengandung spasi, dan mengandung format penulisan email seperti "@gmail.com", dll
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Memastikan bahwa field tersebut wajib diisi, hanya berisi angka, memiliki panjang tertentu (misalnya 10–15 digit), dan mengikuti format nomor telepon yang valid.
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis memastikan format dan struktur data benar, sedangkan validasi bisnis memastikan data tersebut sesuai dengan aturan atau logika bisnis yang berlaku.
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Fungsi anotasi @Valid adalah untuk mengaktifkan proses validasi terhadap data yang diterima pada request sesuai dengan aturan validasi yang telah didefinisikan.
Provide your feedback on BizChat
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Fungsi anotasi @NotBlank adalah untuk memastikan bahwa field tidak bernilai null, tidak kosong, dan tidak berisi hanya spasi.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Fungsi anotasi @NotNull adalah untuk memastikan bahwa suatu field tidak boleh bernilai null (harus memiliki nilai).
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Fungsi anotasi @Email adalah untuk memastikan bahwa nilai pada field memiliki format alamat email yang valid sesuai standar penulisan email.
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Fungsi anotasi @Size adalah untuk membatasi panjang minimum dan maksimum suatu field, seperti jumlah karakter pada string atau jumlah elemen dalam koleksi.
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
Perbedaannya adalah @NotNull hanya memastikan field tidak bernilai null, sedangkan @NotBlank memastikan field tidak null, tidak kosong, dan tidak hanya berisi spasi.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Dalam Spring Boot, validasi biasanya diletakkan di object DTO (Data Transfer Object) atau model yang digunakan untuk menerima request dari client.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error handling adalah mekanisme untuk menangani kesalahan yang terjadi dalam aplikasi agar sistem tetap berjalan dengan baik dan memberikan respons yang jelas kepada client.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Error response perlu dibuat konsisten agar mudah dipahami oleh client, memudahkan debugging, dan menjaga standar komunikasi yang jelas antara frontend dan backend.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Risikonya adalah informasi internal sistem bisa bocor, sehingga membuka celah keamanan dan memudahkan pihak tidak bertanggung jawab untuk menyerang aplikasi.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
Perbedaannya adalah 400 digunakan ketika request dari client tidak valid, 404 ketika resource yang diminta tidak ditemukan, dan 500 ketika terjadi kesalahan di sisi server.
Provide your feedback on BizChat
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid, seperti data yang dikirim salah, tidak lengkap, atau formatnya tidak sesuai.
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
404 Not Found digunakan ketika request sudah benar, tetapi data atau resource yang diminta tidak ditemukan di server.
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 Internal Server Error digunakan ketika terjadi kesalahan di sisi server saat memproses request, meskipun request dari client sudah benar.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah kondisi atau kejadian ketika terjadi kesalahan saat program dijalankan yang mengganggu alur normal eksekusi program.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
RuntimeException adalah jenis exception yang terjadi saat program dijalankan dan biasanya disebabkan oleh kesalahan logika atau kondisi yang tidak terduga tanpa perlu ditangani secara wajib oleh programmer.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception yang dibuat sendiri oleh developer untuk merepresentasikan kondisi kesalahan tertentu sesuai dengan kebutuhan atau logika bisnis aplikasi.
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Kita perlu membuat CustomerNotFoundException agar dapat menangani secara spesifik kondisi ketika data customer tidak ditemukan dan memberikan response error yang lebih jelas serta sesuai dengan kebutuhan bisnis.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Perbedaannya adalah validation error terjadi karena input tidak valid dari client, business error terjadi karena aturan atau logika bisnis tidak terpenuhi, dan system error terjadi karena kesalahan di sisi sistem atau server.
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah anotasi di Spring Boot yang digunakan untuk menangani exception secara global agar error handling dapat dilakukan secara terpusat dan konsisten di seluruh aplikasi.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
@ExceptionHandler adalah anotasi di Spring Boot yang digunakan untuk menentukan method khusus dalam menangani jenis exception tertentu agar dapat memberikan response yang sesuai kepada client.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Error handling sebaiknya tidak ditulis berulang di setiap Controller agar kode lebih rapi, mudah dipelihara, dan konsisten dengan menggunakan penanganan error terpusat.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Manfaat centralized error handling adalah kode menjadi lebih rapi, konsisten, mudah dikelola, dan memudahkan perubahan tanpa perlu mengubah banyak bagian di setiap controller.
```

## Self Assessment

| Area                    | Score 1-5 |
| ----------------------- | --------- |
| Request validation      |     3     |
| Bean Validation         |     2     |
| HTTP status code        |     3     |
| Exception               |     3     |
| Custom exception        |     2     |
| Global error handling   |     3     |
| Standard error response |     2     |

## Notes

```text
Bingung waktu penggunaannya
```
