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
Tulis jawaban di sini.
Validasi Request adalah sebuah tindakan untuk melakukan konfirmasi terhadap data yang ada pada request mapping apakah sudah sesuai dengan format yang diberikan dan tidak ada serangan XSS secara langsung. Request biasanya dimapping di DTO agar serangan tidak terjadi langsung ke Entity. Contoh Validasi adalah memastikan format email sudah benar sebelum data diproses.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Tulis jawaban di sini.
Karena backend adalah source of truth dan validasi harus dilakukan kedua sisi untuk melakukan validasi agar data bisa akurat diproses dan tidak ada serangan cyber
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Tulis jawaban di sini.
Akan terkena error kode 400 atau bad request karena data yang diminta oleh API tidak sesuai dengan data yang diberikan. Secara API request data bisa dilihat sebagai requirement bila tidak cocok proses akan ditolak dan tidak bisa diproses.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Tulis jawaban di sini.
bisa menggunakan NotBlack atau NotNull dan menggunakan Regex untuk membersihkan data yang kotor
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Tulis jawaban di sini.
Bisa melakukan validasi format email yang valid dan memberikkan annotation yang disebut NotNull atau NotBlank
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Tulis jawaban di sini.
Bisa memberikan NotNull dan NotBlank lalu memberikan Regex untuk memastikan bahwa yang masuk sesuai dengan data yang diperlukan atau diminta
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Tulis jawaban di sini.
validasi teknis melakukan penanganan secara sistem secara lebih mendalam daripada validasi bisnis dengan melakukan deklarasi data yang dibutuhkan dan formatnya. Sementara validasi teknis lebih berfokus secara penanganan data apakah perlu difilter, datanya tidak bersifat null, dan lain-lain.
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Tulis jawaban di sini.
Untuk  melakukan penjagaan terhadap request body agar bila request yang diminta tidak sesuai akan mengembalikan error 400 bad request.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Tulis jawaban di sini.
NotBlank untuk menghandle data yang ada namun tidak diisi misalkan seperti "", itu akan diregard sebagai memiliki data namun blank maka data akan ditolak dan akan dianggap error.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Tulis jawaban di sini.
NotNull menghandle validasi benar benar data yang tidak ada kalau "" masih dianggap string kosong, null benar-benar tidak memiliki values apapun dan sistem akan membacanya sebagai Null, NotNull sendiri melakukan validasi agar nilai Null atau undefined ini tidak akan dilanjutkan
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Tulis jawaban di sini.
anotasi email untuk melakukan validasi field bahwa values dari field tersebut harus memiliki format email
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Tulis jawaban di sini.
Melakukan Validasi terhadap besarnya data atau panjangnya sebuah data misalkan String bisa dibatasi untuk hanya memiliki panjang 255
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
Tulis jawaban di sini.
NotBlank masih melakukan validasi terhadap nilai kosong seperti String yang tidak ada isinya tapi NotNull menolak tipe data Null atau Undefined
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Tulis jawaban di sini.
Entity dan DTO
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Tulis jawaban di sini.
Error handling adalah proses untuk melakukan penanganan terhadap sebuah kesalahan atau kasus failure path harus diberikan penanganan dan response sesuai dengan kebutuhan aplikasi.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Tulis jawaban di sini.
Agar tidak membingungkan developer dan user serta meningkatkan kemudahan untuk melakukan tracing error dan debugging. Sementara sisi pengguna akan lebih mudah untuk melihat message apa yang mereka dapatkan dan mengetahui apa yang salah secara umum.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Tulis jawaban di sini.
Karena user dan FE hanya memerlukan message error yang umum dan mudah dimengerti. Stack trace bersifat detail dan dapat melihat method serta field dan line berapa yang error yang bisa membuat celah security.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
Tulis jawaban di sini.
Error 400 adalah bad request bahwa data yang dikirimkan ke API contract atau endpoint tidak sesuai dan tidak dapat diproses
Error 404 adalah module tidak ditemukan kemungkinan karena salah path atau karena memang module tidak ada
Error 500 adalah internal server error dimana data yang divalidasi sudah benar namun BE tidak bisa melakukan proses data atau data validasi ternyata masih ada kasus error seperti pembagian 0 maka akan menimbulkan divide by zero exception
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Tulis jawaban di sini.
Saat request yang diberikan oleh user atau FE bermasalah dan tidak sesuai jadi ditolak langsung di endpoint
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Tulis jawaban di sini.
Saat module tidak ditemukan atau path salah
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Tulis jawaban di sini.
Saat kesalahan ada di Server atau BE dan masalah yang dihadapi adalah Exception
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Tulis jawaban di sini.
Exception secara umum yang menangkap segala kasus kesalahan
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
Tulis jawaban di sini.
RuntimeException adalah exception yang menghandle aplikasi saat mau dimulai melihat apa yang menyebabkan aplikasi gagal di run
```

### 24. Apa itu custom exception?

Jawaban:

```text
Tulis jawaban di sini.
Exception yang dibuat khusus sendiri oleh developer untuk memberikan response error yang sesuai yang dibutuhkan
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Tulis jawaban di sini.
Agar bisa memberikanm response yang lebih spesifik memudahkan pengguna mengerti dan Developer untuk melakukan debugging. Ini memberikan message bahwa Customer tidak ada lebih mudah dimengerti daripada melempar Exception Biasa.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Tulis jawaban di sini.
Validation error adalah format data yang tidak benar, business error adalah logika bisnis yang tidak sesuai kebutuhan, dan system error adalah error dimana logic aplikasi tidak sesuai dengan logic bisnis atau menemukan exception
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
Tulis jawaban di sini.
ControllerAdivice berfungsi sebagai pembantu Controller bila menemukan sebuah kasus maka akan melakukan apa yang di state di java class Controller Advice
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Tulis jawaban di sini.
ExceptionHanler berfunsgi sebagai global Exception bila menemukan kasus misalkan IllegalArgument Exception akan mengembalikan custom exception
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Tulis jawaban di sini.x
Karena tidak best practice dan clean serta sulit dan tidak reusable. Bila dibuat class atau method khusus akan lebih mudah digunakan atau diubah
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Tulis jawaban di sini.
Lebih mudah debugging dan reusability yang tinggi
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation | 5|
| Bean Validation | 5|
| HTTP status code | 5|
| Exception | 5|
| Custom exception | 5|
| Global error handling | 5|
| Standard error response | 5|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
