# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```
crosscheck apakah data yang diinputkan sesuai seperti yang dibutuhkan dan diset untuk keperluan bisnis sesuai agar invalid request tidak bisa masuk ke program yang bisa jadi membuat error
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```
karena validasi di frond end saja tidak cukup harus ada validasi di backend untuk memastikan bahwa data sesuai dan menjadi gerbang terakhir untuk kesalahan yang mungkin terjadi
```

### 3. Apa fungsi @Valid?

Jawaban:

```
untuk memastikan data valid yang dimasukkan sesuai dengan yang telah di set misalnya adalah "@" pada email harus ada
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```
memastikan String tidak boleh null, kosong, atau blank
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```
kalau not blank memastikan bahwa string tidak boleh null kosong atau blank, sedangkan not null itu memastikan bahwa objectnya tidak kosong
```

### 6. Apa fungsi @Email?

Jawaban:

```
validasi untuk email sesuai ada @ nya

```

### 7. Apa fungsi @Size?

Jawaban:

```
untuk mengatur ukuran mislanya jumlah karakter
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```
muncul validasi error sesuai dengan yang telah di atur di field DTO
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```
request body tidak menerima argument yang tepat sesuai dengan validaasi yang diberikan dan proses dihentikan dianggap sebagai exception 
```

### 10. Apa itu standard error response?

Jawaban:

```
format standar respon yang biasa berbentuk DTO untuk menyelaraskan schema respon diseluruh aplikasi
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```
digunakan untuk memudahkan pembacaan dan debugging untuk FE maupun BE
```

### 12. Apa itu field-level error?

Jawaban:

```
error input tidak sesuai request template
```

### 13. Apa itu custom exception?

Jawaban:

```
exception yang dibuat untuk error tertentu
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```
karena kita bisa menyesuaikan kebutuhan dan tidak terlalu general dan jadi tau detail 
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```
untuk sebagai konfigurasi global agar menangkap exception di seluruh kontroler
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```
untuk handling exception terhadap yang khusus
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```
agar dapat mudah debugging dan melakukan tracing error
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```
saat request yang dikirim tidak sesuai dengan validasi atau ketentuan yangdibuat
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```
saat data yang direquest tidak ditemukan pada database
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```
saat adanya kegagalan dari server 
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```
klien tahu kalau ada kesalahan dari system kita yang seharusnya itu di sistem kita aja 
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```
client mengirim request -> system check lewat exception handler apakah sesuai jika tidak maka system akan mengirimkan respon error sesuai dengan validasi error yang telah di set
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```
client mengirimkan request untuk get customer sesuai ID nya, lalu sistem akan mengecek apakah ada id yang dicari jika tidak maka exception handler akan melakukan check dan mengirimkan message dan code sesuai yang di set 
@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse<Void>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.error("CUSTOMER_NOT_FOUND", ex.getMessage(), null));
    }
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```
Validation Error: Terjadi karena input dari user tidak valid (contoh: email salah format, nama kosong).
Business Error: Terjadi karena aturan bisnis tidak terpenuhi (contoh: customer tidak ditemukan, saldo tidak cukup).
System Error: Terjadi karena masalah teknis pada sistem (contoh: database down, server error, koneksi gagal).
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```
ada error dan kesusah menulis untuk globalexception handler memulainya darimana
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. konsep berpikir dalam penulisan validasi 
2. kebutuhan handler seperti pesan dan set up error message
3. membuat validation dan handling error 
```

Apa 2 hal yang masih membingungkan?

```text
1. syntax dan kebutuhan untuk globalexception ataupun handling errornya pelatakannya dimana
2. alur cara kerja spring boot dalam membaca
```

Apa 1 pertanyaan untuk mentor?

```
Saya harus apa biar bisa spring boot dan BE lainnya karena saya kesusahan dalam memahami konsep berpikirnya 
```
