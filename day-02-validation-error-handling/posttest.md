# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
proses pengecekan data yang dikirim client agar sesuai dengan aturan
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Karena frontend bisa dilewati, sehingga backend tetap harus memastikan data aman
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
memicu proses validasi pada object request berdasarkan annotation validasi
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
memastikan field tidak bernilai null, tidak kosong, dan tidak hanya berisi spasi
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya memastikan value tidak null, sedangkan @NotBlank juga memastikan value tidak kosong dan tidak hanya spasi
```

### 6. Apa fungsi @Email?

Jawaban:

```text
memastikan format string sesuai dengan format email yang valid
```

### 7. Apa fungsi @Size?

Jawaban:

```text
untuk membatasi panjang minimum dan/atau maksimum string, collection, atau array
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Spring akan melempar exception dan mengembalikan response error ke client
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
exception yang terjadi ketika validasi request body gagal saat menggunakan @Valid pada method controller
```

### 10. Apa itu standard error response?

Jawaban:

```text
format response error yang konsisten, biasanya berisi status, message, error code
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
agar mudah dipahami client, memudahkan handling di frontend
```

### 12. Apa itu field-level error?

Jawaban:

```text
error yang terjadi pada field tertentu dalam request
```

### 13. Apa itu custom exception?

Jawaban:

```text
exception buatan sendiri yang merepresentasikan error bisnis tertentu
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
karena lebih spesifik, mudah dipahami, dan mempermudah mapping ke HTTP status
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
untuk menangani exception secara global (centralized) di seluruh controller.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
untuk menentukan method yang menangani exception tertentu.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
agar kode lebih rapi, konsisten, mudah dirawat, dan tidak duplikasi logic error
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request dari client tidak valid
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika resource yang diminta tidak ditemukan
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika terjadi error di sisi server yang tidak terduga
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
karena bisa membocorkan informasi internal aplikasi dan berisiko terhadap keamanan
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
request masuk → @Valid memicu validasi → field email gagal @Email → MethodArgumentNotValidException dilempar → ditangani ControllerAdvice → response 400 dengan field error dikirim ke client
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
controller memanggil service → data tidak ditemukan → CustomerNotFoundException dilempar → ditangani ControllerAdvice → response 404 dikirim ke client
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation error: data request tidak valid
Business error: aturan bisnis tidak terpenuhi
System error: error teknis/internal server

```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
diawal bingung kenapa bisa memanggil global exception tanpa membuat object
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Cara kerja Spring Boot membaca component
2. Peletakan Error Handling
3. Clean Architecture (controller, service, repository)
```

Apa 2 hal yang masih membingungkan?

```text
1. Syntax spring boot
2.
```

Apa 1 pertanyaan untuk mentor?

```text
apa aja syntax spring yang sangat useful untuk dipakai dikepedannya
```
