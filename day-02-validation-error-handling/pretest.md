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
sistem yang dibuat untuk mengecek apakah data yang diinputkan oleh user sudah sesuai dengan format yang seharusnya

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:
untuk memastikan kembali apakah sudah sesuai, lebih tepatnya melakukan cross check data yang masuk. Karena untuk memastikan data yang masuk sudah sesuai (takut ada data yang tidak sesuai lolos)

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:
data yang ada di dalam database akan rusak, logika yang sudah dibuat oleh developer tidak terjalan karena tidak sesuai dengan format
Contoh/ nomimal pinjaman harusnya berbentuk angka namun diisi dengan huruf, jika itu lolos masuk ke dalam sistem, itu akan merusak data, logika tidak akan berjalan

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:
- tidak boleh kosong
- ada minimal huruf yang diinputkan
- ada maksimal huruf yang diinputkan
- hanya boleh huruf dan spasi 

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:
- tidak boleh kosong
- tidak boleh huruf besar atau kapital
- harus ada @ nya
- harus ada pengecekan domainnya apakah sudah sesuai seperti @gmail.com atau @fifgrouup.ac.id

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:
- tidak boleh huruf
- wajib dan hanya boleh nomor saja
- harus ada minimal angka yang harus diinput
- harus ada maksimal angka yang boleh diinputkan 

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:
validasi teknis : fokus pada bentuk data (memastikan data tidak kosong atau null)
validasi bisnis : fokus pada aturan domain (contoh/ email harus ada @ nya ataupun harus sesuai dengan domain)

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:
untuk mengaktifkan validasi otomatis pada object berdasarkan aturan yang didefinisikan sebelumnya

### 9. Apa fungsi annotation @NotBlank?

Jawaban:
memastikan string benar-benar berisi teks (bukan kosong)

### 10. Apa fungsi annotation @NotNull?

Jawaban:
memastikan nilai tidak null

### 11. Apa fungsi annotation @Email?

Jawaban:
memastikan mengikuti format email yang benar

### 12. Apa fungsi annotation @Size?

Jawaban:
memastikan panjang data sesuai batas minimum dan maksimum

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:
@NotNull → hanya cek ada nilai (tidak null)
@NotBlank → memastikan string benar-benar berisi teks (bukan kosong)

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:
DTO (Data Transfer Object)

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:
menangani error yang terjadi dalam aplikasi agar sistem tetap berjalan

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:
untuk kemudahan penggunaan API, maintainability, dan debugging

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:
data bisa bocor dan keamanan tidak akan kuat (atau mudah dihack)

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:
400 (bad request) : server tidak bisa memproses request karena input dari client salah
404 (Not Found) : source atau link tidak ditemukan
500 (Internal Server Error) : error di server saat memproses request

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:
ketika request dari client tidak valid secara teknis sehingga server tidak bisa memprosesnya

### 20. Kapan menggunakan 404 Not Found?

Jawaban:
ketika resource atau data yang diminta tidak ditemukan di server, meskipun request yang dikirim sudah benar

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:
ketika terjadi kesalahan di sisi server yang tidak disebabkan oleh kesalahan client

## Section D - Exception

### 22. Apa itu exception?

Jawaban:
error yang terjadi saat program dijalankan (runtime) dan mengganggu alur normal eksekusi program

### 23. Apa itu RuntimeException?

Jawaban:
jenis exception yang terjadi saat program dijalankan (runtime) dan tidak wajib ditangani (unchecked exception)

### 24. Apa itu custom exception?

Jawaban:
exception yang dibuat sendiri oleh developer untuk merepresentasikan error yang spesifik sesuai kebutuhan aplikasi atau bisnis

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:
membantu sistem menjadi lebih jelas, terstruktur, dan mudah dikelola, terutama dalam menangani error yang spesifik

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:
Validation Error → input salah secara teknis
Business Error → melanggar aturan bisnis
System Error → masalah di server

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
Tulis jawaban di sini.
```

### 28. Apa itu @ExceptionHandler?

Jawaban:
annotation untuk menangani exception secara global, sehingga error handling menjadi lebih rapi, konsisten, dan mudah dikelola

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:
karena akan menimbulkan banyak masalah dalam development dan maintainance

### 30. Apa manfaat centralized error handling?

Jawaban:
menangani semua error di satu tempat (terpusat)

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |4|
| Bean Validation |4|
| HTTP status code |4|
| Exception |4|
| Custom exception |4|
| Global error handling |4|
| Standard error response |4|

## Notes
Springboot karena kemarin cuti jadi tidak tahu cara membuatnya dan cara kerjanya
