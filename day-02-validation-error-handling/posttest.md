# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Validasi untuk request body - memastikan sudah sesuai format.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
agar ketika frontend kelewatan dan memberikan data yang salah pada requestbody backend akan tetap memastikan bahwa format yang di proses sudah benar.
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
untuk mengaktifkan anotasi yang ada di validation dto/class.
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
Melakukan cek dan validasi apakah input yang dimasukan tidak berupa string kosong, space, dan null
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@Null hanya memastikan data yg dimasukan tidak null, @notblank : Melakukan cek dan validasi apakah input yang dimasukan tidak berupa string kosong, space, dan null

```

### 6. Apa fungsi @Email?

Jawaban:

```text
Melakukan validasi bahwa input yang masukan adalah email -> mengandung @
```

### 7. Apa fungsi @Size?

Jawaban:

```text
memastikan input yang dimasukan sudah sesuai lengthnya berdasarkan min length dan max length yang sudah di set pada annotationnya.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
data yang diterima tidak diteruskan ke logic utama / service dan aplikasi akan mengembalikan error.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
MethodArgumentNotValidException adalah exception dalam framework Spring yang muncul secara otomatis ketika data yang dikirimkan oleh klien ke aplikasi (seperti payload JSON dalam request body) gagal memenuhi aturan validasi yang telah ditentukan (seperti anotasi @NotNull, @Size, atau @Email).
```

### 10. Apa itu standard error response?

Jawaban:

```text
Standard error response adalah format pesan kesalahan yang seragam dan konsisten yang dikembalikan oleh API kepada klien ketika sebuah permintaan (request) gagal diproses
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Agar klien (frontend/mobile) dapat memproses dan menampilkan pesan kesalahan kepada pengguna secara seragam, otomatis, dan tanpa kendala, terlepas dari rute API mana yang mengalami kegagalan.
```

### 12. Apa itu field-level error?

Jawaban:

```text
Field-level error adalah pesan kesalahan spesifik yang terikat pada satu kolom input tertentu (seperti email atau password) karena data yang dimasukkan tidak memenuhi aturan validasi yang diwajibkan
```

### 13. Apa itu custom exception?

Jawaban:

```text
Exception yang disesuaikan secara manual agar keluaran yang diberikan bisa diesuaikan.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
CustomerNotFoundException lebih baik karena memberikan deskripsi yang spesifik, memudahkan pelacakan (debugging), dan bisa langsung diikat dengan status output HTTP 404 (Not Found). RuntimeException terlalu umum dan menghasilkan output HTTP 500 (Internal Server Error) yang membingungkan klien API.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
Anotation untuk mengontrol semua exception yang terjadi di keseluruhan project spring boot sehingga menghasilkan erorresponse yang seragam
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
Annotation yang ditempatkan di atas method untuk mendandai bahwa exception yang akan di eksekusi adalah method yang ada dibawahnya -> biasanya parameternya adalah class default exceptionnya.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Agar mudah di mantain di satu tempat saja sehingga ketika berganti developer nantinya tidak bingung.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika requestbody yang dikirimkan tidak sesuai dengan format requestbody yang seharusnya
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika page/data/endpoint tidak ditemukan.
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Ketika terjadi kesalahan yang tak terduga pada service backend, server atau database.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
karena stack trace akan mengembalikan erorr di line mana di function dan di code mana terlihat semua oleh client, bisa juga data data sensitif akan ikut terkirim ke client dan akan bermasalah dengan security concern/keamanan data.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
- Controller akan menerima request dari user
- data akan di validasi melalui anotasi @valid yang akan melihat ke dto dari request body
- jika requestbody yang diberikan tidak sesuai dengan format, backend akan throw eror yang sudah di custom dari globalexception.
- jika sesuai maka data akan diterima dan akan diteruskan ke service
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
- Controller menerima pathvariabel yang dikirimkan -> mencari users by id
- Controller meneruskan ke service
- Service akan melakukan checking ke database customerStorage
- Jika data yang di request tidak tersedia, service akan throw eror CustomerNotFoundException
- Jika data tersedia, service akan mapping ke response dan meneruskan kembali ke controller dan ke user.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
- Validation error terjadi karena input dari client tidak sesuai format atau aturan (contoh: email tidak valid, menghasilkan output HTTP 400).

- Business error terjadi karena pelanggaran aturan logika bisnis aplikasi meskipun input valid (contoh: saldo tidak cukup, menghasilkan output HTTP 422 atau 400).

- System error terjadi karena kegagalan infrastruktur atau teknis backend (contoh: database down, menghasilkan output HTTP 500).
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
- Memahami Anotasi dan kapan anotasi di eksekusi, khususnya exception
- Sintaksis Builder
- Sintaksis GlobalExceptionHandler
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Cara Menggunakan Anotasi yang memudahkan dalam membuat backend
2. Memahami anotasi builder dan cara menggunakannya
3. Memahami cara membuat GlobalErrorHandler yang terpusat dan clean
```

Apa 2 hal yang masih membingungkan?

```text
1. Penggunakan ORM JPA dan Hibernate
2. Penggunaan REDIS di project yg di doker
```

Apa 1 pertanyaan untuk mentor?

```text
belum ada. Thankyou sudah banyak membantu mas hehe
```
