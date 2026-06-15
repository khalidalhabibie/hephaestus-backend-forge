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
Validasi Request adalah proses validasi atau pengecekan dari sisi request yang masuk untuk memastikan data yang dikirim dari request sesuai dengan ketentuan.
```

### 2. Kenapa backend tetap perlu melakukan validasi walaupun frontend sudah melakukan validasi?

Jawaban:

```text
Karena backend service kita tidak hanya digunakan oleh frontend saja, tapi juga bisa di gunakan oleh service backend server lainnya. Jadi jika hanya dilakukan validasi di frontend tapi tidak di backend, bisa saja validasi tidak terjadi jika service yang akan hit backend kita.
```

### 3. Apa risiko jika API menerima data kosong atau format data yang salah?

Jawaban:

```text
Server / service kita bisa rentan dan error karena data yang dikirimkan tidak sesuai dengan standarnya.
```

### 4. Sebutkan contoh validasi untuk field full_name.

Jawaban:

```text
Contoh validasi yang ditempel di modelnya. Menggunakan Annotation @NotBlank untuk menjaga agar fullname tidak boleh kosong.

@NotBlank(message = "Nama lengkap tidak boleh kosong")
private String fullname;
```

### 5. Sebutkan contoh validasi untuk field email.

Jawaban:

```text
Contoh validasi yang ditempel di modelnya. Menggunakan Annotation @NotBlank, @Email, @Size untuk menjaga agar email tidak boleh kosong, menjaga agar sesuai format email, dan tidak boleh lebih dari 100 karakter.

@NotBlank(message = "Email tidak boleh kosong")
@Email(message = "Format email tidak valid")
@Size(max = 100, message = "Email maksimal 100 karakter")
private String email;
```

### 6. Sebutkan contoh validasi untuk field phone_number.

Jawaban:

```text
Contoh validasi yang ditempel di modelnya. Menggunakan Annotation @NotBlank, @Pattern untuk menjaga agar phoneNumber tidak boleh kosong, dan sesuai pattern untuk nomor telfon.

@NotBlank(message = "Nomor telepon tidak boleh kosong")
@Pattern(regexp = "^(\\+62|0)[0-9]{9,12}$", message = "Nomor telepon harus dimulai dengan +62 atau 0, diikuti 9-12 digit")
private String phoneNumber;
```

### 7. Apa perbedaan validasi teknis dan validasi bisnis?

Jawaban:

```text
Validasi teknis adalah validasi atau pengecekan di sisi teknis seperti @NotBlank, @Pattern, @Email. Jadi validasi yang bertujuan untuk memastikan bisa berjalan dengan benar sesuai teknis.
Contoh -> fullName tidak boleh kosong, format email harus benar.

Validasi bisnis adalah validasi yang dilakukan disisi bisnis, memastikan bahwa flow yang dijalankan sesuai dengan bisnis rule.
Contoh -> Pada proses pengajuan kredit, pengecekan apakah umur calon kustomer sudah melewati minimal umur yang ditentukan.
```

## Section B - Bean Validation

### 8. Apa fungsi annotation @Valid?

Jawaban:

```text
@Valid adalah anotasi java untuk melakukan validasi automatis pada suatu objek. @Valid ini biasanya digunakan untuk validasi RequestBody dan juga bisa declare langsung untuk field pada objek.
```

### 9. Apa fungsi annotation @NotBlank?

Jawaban:

```text
@NotBlank adalah anotasi java untuk melakukan validasi text values, memastikan bahaw property tersebut tidak null atau tidak hanya spasi.
```

### 10. Apa fungsi annotation @NotNull?

Jawaban:

```text
@NotNull adalah anotasi java untuk melakukan validasi properti tidak boleh null.
```

### 11. Apa fungsi annotation @Email?

Jawaban:

```text
@Email adalah anotasi java untuk melakukan validasi properti email address untuk memastikan format email itu valid.
```

### 12. Apa fungsi annotation @Size?

Jawaban:

```text
@Size adalah anotasi java untuk melakukan validasi pengecekan attribute itu tidak melebihi size tertentu atau tidak kurang dari minimal size yang telah ditentukan.
```

### 13. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull -> memastikan suatu nuilai itu tidak boleh bernilai null. -> untuk semua tipe data.
@NotBlank -> memastikan bahwa suatu nilai tidak bernilai null, bukan string kosong, minimal memiliki satu karakter selain spasi. -> Hanya di tipe data String.
```

### 14. Di Spring Boot, validasi biasanya diletakkan di object apa?

Jawaban:

```text
@NotBlank -> untuk memastikan bahwa property tidak null
@Email -> untuk memastikan format email benar
@Min, @Max -> untuk memastikan sebuah value sesuai dengan ketentuan.
```

## Section C - Error Handling

### 15. Apa itu error handling?

Jawaban:

```text
Error Handling adalah proses antisipasi, deteksi yang terjadi di sebuah program yang sedang berjalan.
```

### 16. Kenapa error response perlu dibuat konsisten?

Jawaban:

```text
Memudahkan integrasi sistem, mempercepat proses debugging. Memudahkan developer frontend dan mobile untuk konsume api kita.
```

### 17. Apa risiko jika stack trace dikirim ke client?

Jawaban:

```text
Informasi yang diberikan client bisa saja sangat fatal dan risiko keamanan. Yang hanya bisa diliat oleh developer.
```

### 18. Apa perbedaan HTTP status 400, 404, dan 500?

Jawaban:

```text
400 -> Bad Request
404 -> Not Found
500 -> Internal server error
```

### 19. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika kesalahan ini pada sisi client
```

### 20. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource yang dicari tidak ada.
```

### 21. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Kesalahan dari server.
```

## Section D - Exception

### 22. Apa itu exception?

Jawaban:

```text
Sebuah kejadian tidak normal saat program berjalan.
```

### 23. Apa itu RuntimeException?

Jawaban:

```text
ketika terjadi mendadak saat program sedang berjalan.
```

### 24. Apa itu custom exception?

Jawaban:

```text
Exceptopn yang dibuat sendiri untuk mewakili logika bisnis sendiri.
```

### 25. Kenapa kita perlu membuat CustomerNotFoundException?

Jawaban:

```text
Untuk menjelaskan kejelasan spesifikan mengenai kegagalan logika bisnis. Untuk handle jika customer yang dicari tidak ditemukan.
```

### 26. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation Error -> data yang dimasukan formatnya salah atau tidak lengkap

Business error -> input sudah benar, tapi logika bisnis salah.

System error -> Kesalahan internal dari komputer atau program yang crash.
```

## Section E - Global Exception Handler

### 27. Apa itu @ControllerAdvice?

Jawaban:

```text
anotasi dalam framework untuk membuat penanganan error global untuk seluruh controller dalam aplikasi
```

### 28. Apa itu @ExceptionHandler?

Jawaban:

```text
Untuk menandai sebuah fungsi sebagai pemroses khusus untuk error tertentu.
```

### 29. Kenapa error handling sebaiknya tidak ditulis berulang di setiap Controller?

Jawaban:

```text
Duplikasi kode, sulit dimaintenance, format response tidak konsisten
```

### 30. Apa manfaat centralized error handling?

Jawaban:

```text
Kode lebih clean, format respon konsisten, mudah di rawat, keamanan terjamin.
```

## Self Assessment

| Area                    | Score 1-5 |
| ----------------------- | --------- |
| Request validation      | 3         |
| Bean Validation         | 2         |
| HTTP status code        | 5         |
| Exception               | 2         |
| Custom exception        | 2         |
| Global error handling   | 2         |
| Standard error response | 2         |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
