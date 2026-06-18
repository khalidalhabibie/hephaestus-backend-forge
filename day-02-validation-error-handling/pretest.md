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
berfungsi untuk memvalidasi request dari klien apakah sudah sesuai atau belum
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
supaya keamanan validasi tetap berlayer dan kita bisa sepenuhnya percaya dengan frontend
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
dapat menimbulkan masalah keamanan data
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
input harus berupa string
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
input harus berupa string dan berformat @email.com
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
input harus berupa string dan diberikan regex agar hurud tidak bisa diinput
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
validasi teknis memastikan bahwa input sesuai jenis data nya (string/integer) sedangkan validasi bisnis memastikan bahwa input sesuai dengan bisnis (stok barang)
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
tidak tau
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
field tidak boleh kosong, tidak boleh ada whitespace
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
field tidak boleh null tetapi boleh berupa 0 atau white space
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
field harus beformat email
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
tidak tau
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
notnull memperbolehkan input 0 atau whitespace sedangkan notblank melarang input kosong sama sekali dan tidak boleh ada whitespace
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
di dto
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
proses untuk menangani error supaya mudah untuk di trace ketika terjadi
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
agar mudah untuk dikenali
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
maka client bisa mengetahui risiko keamanan
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
404 adalah ketika request yang diminta tidak ada di server, 400 adalah ketika inputan client tidak sesuai dengan format request yang ada, sedangkan 500 adalah server mengalami error yang tidak terduga
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika inputan client tidak sesuai dengan format request yang ada
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika request yang diminta tidak ada di server
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika server mengalami error 
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
sebuah error yang terjadi karena adanya faktor eksternal seperti library atau file
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
sebuah error yang terjadi ketika di run karena adanya bug dalam pemrograman
```

### 24. Apa itu custom exception?

Jawaban:

```text
exception yang dikustomisasi sesuai dengan kebutuhan
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
untuk menangani apabila customer yang dicari tidak ada dalam server
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
validation error adalah kesalahan input pengguna, business error adalah adanya pelanggaran pada logika bisnis, sedangkan system error adalah kegagalan sistem/teknis
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
tidak tau
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
untuk menangani error tertentu
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
agar mudah di maintain dan format error nya konsisten
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
semua error mudah di maintain dan mudah untuk di trace ketika terjadi error
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Request validation |3|
| Bean Validation |3|
| HTTP status code |4|
| Exception |3|
| Custom exception |3|
| Global error handling |3|
| Standard error response |3|

## Notes

```text
masih berusaha untuk memahami penggunaan exception
```
