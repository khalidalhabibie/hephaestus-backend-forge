# Posttest - Validation & Error Handling

## Objective
```text
Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.
```

### 1. Apa itu validasi request?
Jawaban:

```text
Jawaban:Validasi request adalah proses memeriksa data yang dikirim oleh client sebelum diproses oleh aplikasi. Tujuannya agar data yang masuk sesuai aturan, misalnya field wajib tidak kosong, format email benar, atau panjang karakter sesuai batas.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?
Jawaban:
```text
Jawaban:Backend tetap perlu validasi karena validasi di frontend bisa dilewati, misalnya menggunakan Postman atau tools lain. Backend adalah lapisan terakhir yang menjaga agar data yang masuk ke sistem tetap aman, benar, dan sesuai aturan bisnis.
```

### 3. Apa fungsi @Valid?
Jawaban:
```text
Jawaban:@Valid berfungsi untuk menjalankan proses validasi pada object request berdasarkan annotation validasi yang ada di dalam class tersebut, seperti @NotBlank, @Email, atau @Size.
```

### 4. Apa fungsi @NotBlank?
Jawaban:
```text
Jawaban:@NotBlank digunakan untuk memastikan sebuah field bertipe String tidak bernilai null, tidak kosong, dan tidak hanya berisi spasi.
```

### 5. Apa perbedaan @NotBlank dan @NotNull?
Jawaban:
```text
Jawaban:@NotNull hanya memastikan nilai tidak null, tetapi masih bisa berisi string kosong. Sedangkan @NotBlank memastikan nilai tidak null, tidak kosong, dan tidak hanya spasi, sehingga biasanya lebih cocok untuk field String yang wajib diisi.
```

### 6. Apa fungsi @Email?
Jawaban:
```text
Jawaban:@Email berfungsi untuk memastikan nilai pada field memiliki format email yang valid, misalnya harus memiliki format seperti nama@domain.com.
```

### 7. Apa fungsi @Size?
Jawaban:
```text
Jawaban:@Size digunakan untuk membatasi ukuran atau panjang data, misalnya minimal dan maksimal jumlah karakter pada String, atau jumlah item pada Collection.
```

### 8. Apa yang terjadi jika request gagal validasi?
Jawaban:
```text
Jawaban:Jika request gagal validasi, proses biasanya tidak dilanjutkan ke logic utama. Aplikasi akan mengembalikan response error, umumnya dengan status 400 Bad Request, berisi informasi field mana yang salah dan pesan errornya.
```

### 9. Apa itu MethodArgumentNotValidException?
Jawaban:
```text
Jawaban:MethodArgumentNotValidException adalah exception yang muncul ketika validasi request body dengan @Valid gagal. Exception ini biasanya berisi daftar error dari field yang tidak memenuhi aturan validasi.
```

### 10. Apa itu standard error response?
Jawaban:
```text
Jawaban:Standard error response adalah format response error yang dibuat konsisten untuk semua error di API. Contohnya berisi status code, pesan error, detail error, timestamp, dan path request.
```
### 11. Kenapa error response perlu konsisten?
Jawaban:
```text
Jawaban:Error response perlu konsisten agar frontend atau pengguna API lebih mudah membaca dan menangani error. Dengan format yang sama, proses debugging dan integrasi antar sistem juga menjadi lebih jelas.
```

### 12. Apa itu field-level error?
Jawaban:
```text
Jawaban:Field-level error adalah error validasi yang menunjukkan kesalahan pada field tertentu. Contohnya field email invalid atau field name kosong, sehingga client tahu bagian mana yang harus diperbaiki.
```

### 13. Apa itu custom exception?
Jawaban:
```text
Jawaban:Custom exception adalah exception yang dibuat sendiri untuk menggambarkan kondisi error tertentu dalam aplikasi. Contohnya CustomerNotFoundException untuk kondisi ketika data customer tidak ditemukan.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?
Jawaban:
```text
Jawaban:CustomerNotFoundException lebih baik karena nama exception-nya lebih jelas dan spesifik. Dengan begitu, error lebih mudah dipahami, ditangani, dan dipetakan ke response yang sesuai seperti 404 Not Found.
```

### 15. Apa fungsi @ControllerAdvice?
Jawaban:

```text
Jawaban:@ControllerAdvice berfungsi sebagai tempat global untuk menangani exception dari controller. Dengan annotation ini, error handling bisa dibuat terpusat dan tidak perlu ditulis berulang di setiap controller.
```

### 16. Apa fungsi @ExceptionHandler?
Jawaban:
```text
Jawaban:@ExceptionHandler digunakan untuk menentukan method yang akan menangani jenis exception tertentu. Misalnya MethodArgumentNotValidException ditangani untuk mengembalikan response validasi, atau CustomerNotFoundException untuk response 404.
```

### 17. Kenapa error handling sebaiknya centralized?
Jawaban:
```text
Jawaban:Error handling sebaiknya centralized agar kode lebih rapi, tidak duplikatif, dan format error response tetap konsisten. Selain itu, perubahan aturan response error cukup dilakukan di satu tempat.
```

### 18. Kapan menggunakan 400 Bad Request?
Jawaban:
```text
Jawaban:400 Bad Request digunakan ketika request dari client salah atau tidak valid, misalnya field wajib kosong, format email salah, atau data request tidak sesuai aturan validasi.
```

### 19. Kapan menggunakan 404 Not Found?
Jawaban:
```text
Jawaban:404 Not Found digunakan ketika resource yang diminta tidak ditemukan. Contohnya client meminta data customer dengan id tertentu, tetapi data tersebut tidak ada di database.
```

### 20. Kapan menggunakan 500 Internal Server Error?
Jawaban:

```text
Jawaban:500 Internal Server Error digunakan ketika terjadi error di sisi server yang tidak terduga, misalnya bug pada aplikasi, koneksi database bermasalah, atau exception yang belum ditangani secara spesifik.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?
Jawaban:

```text
Jawaban:Stack trace tidak boleh dikirim ke client karena bisa membocorkan detail internal aplikasi seperti struktur kode, nama class, atau informasi sistem. Hal ini bisa berisiko untuk keamanan dan juga membuat response sulit dibaca.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.
Jawaban:

```text
Jawaban:Client mengirim request POST dengan email yang formatnya salah. Spring menjalankan validasi karena request menggunakan @Valid. Annotation @Email mendeteksi format email tidak valid, lalu muncul MethodArgumentNotValidException. Exception tersebut ditangani oleh global error handler dan aplikasi mengembalikan response 400 Bad Request dengan informasi bahwa field email invalid.
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.
```text
Jawaban:Client mengirim request GET untuk customer id 999. Controller memanggil service untuk mencari data customer. Jika data tidak ditemukan, service melempar CustomerNotFoundException. Exception tersebut ditangani oleh global error handler, lalu API mengembalikan response 404 Not Found dengan pesan bahwa customer tidak ditemukan.
```

### 24. Apa perbedaan validation error, business error, dan system error?
```text
Jawaban:Validation error adalah error karena input dari client tidak sesuai aturan, misalnya email invalid. Business error adalah error karena aturan bisnis tidak terpenuhi, misalnya customer tidak ditemukan atau data tidak boleh dihapus. System error adalah error teknis dari sistem, misalnya database down atau bug yang tidak terduga.
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?
```text
Jawaban:Bagian yang paling sulit adalah memahami bagaimana validasi dan exception handling saling terhubung, mulai dari request masuk, validasi gagal, exception muncul, sampai response error dikembalikan dalam format yang konsisten.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?
```text
1. Saya memahami bahwa validasi request penting untuk memastikan data yang masuk sesuai aturan.
2. Saya memahami cara menggunakan annotation seperti @Valid, @NotBlank, @Email, dan @Size.
3. Saya memahami pentingnya centralized error handling agar response error lebih konsisten.  
```

Apa 2 hal yang masih membingungkan?
```text
1. Saya masih perlu memperdalam cara membuat struktur standard error response yang paling baik.
2. Saya masih perlu latihan membedakan kapan menggunakan status code 400, 404, dan 500 dalam kasus yang berbeda.  
```

Apa 1 pertanyaan untuk mentor?
```text
Bagaimana best practice membuat format standard error response agar mudah digunakan oleh frontend dan tetap aman untuk production?
```