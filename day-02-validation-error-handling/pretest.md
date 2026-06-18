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

```
untuk melakukan cross check untuk data yang di request untuk menjadi guide agar tidak ada kesalahan data yang direquest karena akan berdampak pada proses dari program
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```
karena validasi di FE tidak cukup untuk memastikdan data sesuai dengan yang diharapkan, karena validasi di FE masih ada kemungkinan untuk lolos kesalahannya masuk ke program/system
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```
nanti data yang tersimpan akan bernilai null dan akan membuat program error atau bahkan ada bug system yang menjadi kerugian yang sangat fatal
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```
datanya tidak boleh null dan type data berupa string dan menggunakan regex
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```
Email harus tidak kosong dan harus sesuai format email menggunakan regex atau annotation `@Email` untuk memastikan ada `@` dan domain yang benar.
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```
Phone number harus tidak kosong, hanya berisi angka, dan panjangnya sesuai format (misalnya 10-13 digit) atau menggunakan regex untuk memastikan hanya angka dan kode negara yang valid.
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```
validasi teknis adalah validasi yang dibuat untuk memastikan data kebutuhan dari sistem sesuai dengan program yang diharapkan, kalau validasi bisnis biasanya mungkin meliputi valifasi teknis yang menunjang terkait bisnis yang berjalan serta tidak menjadikan kerugian terhadap bisnis
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```
datanya harus valid dan benar adanya
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```
untuk menjadi validasi bahwa data harus terisi 
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```
validasi untuk data harus ada isinya tidak kosong
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```
validasi untuk email sesuai ada @ nya
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```
untuk validasi ukuran bisa berupa jumlah karakter juga
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```
kalau notblank itu untuk validasi data tp bisa ada berupa string kosong kalau not null itu yang benar-benar kosong
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```
Di Spring Boot, validasi biasanya diletakkan di object DTO atau request body object, misalnya class yang digunakan untuk menerima payload request (`@RequestBody` DTO).
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```
untuk menghandling error dan memudahkan untuk mengidentifikasi error
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```
agar jelas errornya ada dimana dan bisa jelas kesalahan dan perbaikan perlu dimana
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```
klien tahu kalau ada kesalahan dari system kita yang seharusnya itu di sistem kita aja 
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```
400 Bad Request ->  Request tidak valid yang tidak sesuai dengan validasi teknis system yang dibuat
| 404 Not Found -> Data tidak ditemukan berarti tidak ada data yang kita cari pda data yang tersimpab
| 500 Internal Server Error -> Error di server yang berarti kesalahan bukan ada pada kita
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```
saat request yang dikirim tidak sesuai dengan validasi atau ketentuan yangdibuat
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```
saat data yang direquest tidak ditemukan pada database
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```
saat adanya kegagalan dari server 
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Exception adalah kejadian error saat program berjalan karena ada kondisi yang tidak terduga atau tidak valid, misalnya akses ke objek null, input salah, atau operasi gagal.
```

### 23. Apa itu RuntimeException?

Jawaban:

```
untuk handle dari runtime error
```

### 24. Apa itu custom exception?

Jawaban:

```
error saat program berjalan pada kondisi tertentu yang kita bbuat sesuai kebutuhan
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```t
untuk memberi tau kalau customer tidak ditemukan datanya
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```
Validation error: input tidak sesuai aturan.

Business error: input valid tapi melanggar aturan bisnis.

System error: kegagalan server/infrastruktur.
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```
belum tahu
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```
annotation untuk yang menghandle exception
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```
agar tidak pemborosan cost memori dan agar mengimplementasikan DRY
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```
agar codingan rapi dan bisa digunakan berkali-kali dan mempermudah dokumentasi
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |2,5|
| Bean Validation |2|
| HTTP status code |3|
| Exception |3|
| Custom exception |2,5|
| Global error handling |3|
| Standard error response |3|

## Notes

```
karena belum pernah menggunakan secara sadar dan jelas paham konsepnya jadi masih bingung semuanya 
```
