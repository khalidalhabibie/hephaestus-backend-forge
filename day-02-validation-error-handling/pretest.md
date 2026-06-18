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
Validasi request adalah proses dimana kita memastikan apa yang menjadi request untuk dikirim ke server sudah sesuai dengan format yang seharusnya.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Agar ketika frontend lalai dan salah logic dalam melakukan validasi, backend akan tetap mengamankan format requestnya sudah sesuai atau belum.  
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
backend akan eror dan yang paling parah adalah dalam proses eksekusi logic backend salah melakukan kalkulasi yang akan berdampak pada bisnis.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
@NOTBLANK, ukuran maximal input, trim atau menghilangkan space, membuat semua menjadi lowercase, cek apakah ada angka atau simbol menggunakan regex dll.
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
@NOTBLANK, @EMAIL, ukuran maximal input, trim atau menghilangkan space, membuat semua menjadi lowercase, cek apakah ada angka atau simbol @ menggunakan regex dll.
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
@NOTBLANK,  ukuran maximal input, trim atau menghilangkan space, cek apakah ada angka khusus phonenumber atau simbol menggunakan regex dll.
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis adalah yang berhubungan dengan teknis Coding, validasi bisnis adalah validasi yang disesuaikan dengan kebutuhan bisnis. 
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
Mengaktifkan validasi input yang ada pada dto dan sudah di setting annotationnya, akan bekerja ketika request dan response di eksekusi
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
Memastikan input tidak kosong.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
Memastikan input tidak null.
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
Memastiakn request berbentuk email yang mengandung @
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
Memastiakn Panjang/Length dari data yang diinput sesuai dengat yang sudah di set pada annotation size
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
Notblannk untuk validasi input yang kosong/space saja dari user, not null adalah validasi input yang null dari user.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
Data Transfer Object.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
proses untuk melakukan mitigasi error, ketika kode eror kode akan tetap jalan namun melempar exception yang sudah disiapkan.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Agar mudah dilakukan tracing erornya, dan juga ketika developer berganti mantainance tetep bisa di eksekusi dengan mudah.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Selain client jadi bingung kenapa aplikasinya eror, client juga akan bisa melihat logic yang ada di dalam code yang kita buat dan sistem kita bisa kena hack.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 -> Bad Request / permintaan tidak valid -> kesalahan format, sintaks, ukruan dll
404 -> Halaman tidak ditemukan
500 -> Internal Server Eror/ kurasakan pada database atau bug pemrograman
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
permintaan tidak valid -> kesalahan format, sintaks, ukruan dll
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika page yang ada di api tidak ditemukan/dihapus
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika terdapat kurasakan pada database atau bug pemrograman
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception  adalah kode khusus untuk mengidentifikasi dan menangani error saat program berjalan agar tidak crash.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
Runtime exception adalah error yang terjadi saat program sedang berjalan akibat kesalahan logika, seperti pembagian dengan nol atau mengakses indeks data yang salah.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Exception yang dibuat sendiri untuk mengklasifikasikan eror tertentu, agar ketika terjadi eror yang spesifik kita bisa melemparkan eror handling yang sudah di custom sendiri
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
CustomerNotFoundException perlu dibuat untuk memberikan informasi error yang spesifik dan jelas ketika data pelanggan tidak ditemukan di sistem, sehingga mempermudah proses pencarian masalah (debugging) dan penanganan kesalahan secara rapi.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
- Validation Error: Input pengguna tidak sesuai format atau aturan.
- Business Error: Pelanggaran aturan atau logika bisnis aplikasi.
- System Error: Kegagalan teknis pada infrastruktur atau database.

```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah anotasi dalam Spring Framework yang digunakan sebagai penangan interseptor global untuk mengelola exception (error) dan validasi data secara terpusat di seluruh controller.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Beans/anotation yang menandai bahwa code yang dibawahnya adalah exception - biasanya digunakan ketika kita membuat customexception
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Menjaga kode controller tetap clean dan mudah dibaca, eror handling hanya dipanggil saja.
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Manfaat centralized error handling adalah membuat kode lebih bersih, mempermudah perawatan, dan menghasilkan format respons error yang konsisten di seluruh aplikasi.

```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |4|
| Bean Validation |4|
| HTTP status code |4|
| Exception |3|
| Custom exception |3|
| Global error handling |3|
| Standard error response |3|

## Notes

```text
Kapan kita menggunakan custom exception dan batasannya seperti apa.
```
