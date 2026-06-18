# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
Validasi Request adalah proses validasi atau pengecekan dari sisi request yang masuk untuk memastikan data yang dikirim dari request sesuai dengan ketentuan.
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
Karena backend service kita tidak hanya digunakan oleh frontend saja, tapi juga bisa di gunakan oleh service backend server lainnya. Jadi jika hanya dilakukan validasi di frontend tapi tidak di backend, bisa saja validasi tidak terjadi jika service yang akan hit backend kita.
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
@Valid adalah anotasi java untuk melakukan validasi automatis pada suatu objek. @Valid ini biasanya digunakan untuk validasi RequestBody dan juga bisa declare langsung untuk field pada objek.
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
@NotBlank adalah anotasi java untuk melakukan validasi text values, memastikan bahaw property tersebut tidak null atau tidak hanya spasi.
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull -> memastikan suatu nuilai itu tidak boleh bernilai null. -> untuk semua tipe data.
@NotBlank -> memastikan bahwa suatu nilai tidak bernilai null, bukan string kosong, minimal memiliki satu karakter selain spasi. -> Hanya di tipe data String.
```

### 6. Apa fungsi @Email?

Jawaban:

```text
@Email adalah anotasi java untuk melakukan validasi properti email address untuk memastikan format email itu valid.
```

### 7. Apa fungsi @Size?

Jawaban:

```text
@Size adalah anotasi java untuk melakukan validasi pengecekan attribute itu tidak melebihi size tertentu atau tidak kurang dari minimal size yang telah ditentukan.
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
Reqest tidak bisa diproses lebih lanjut oleh server, jadi jika ada gagal validasi. Proses tidak akan masuk ke logic utama dan mengakses database.
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
Exception yang terjadi di springboot ketika proses validasi terhadap request body gagal (tidak sesuai).
Contoh -> @Email. Dan ketika input nya, tidak sesuai dengan standar email "risjadmail.co".
```

### 10. Apa itu standard error response?

Jawaban:

```text
Format respon error yang konsisten dan terstruktur yang digunakan oleh sebuah API untuk mengirimkan informasi ketika terjadi kesalahan. Mau itu kegagalan karna validasi, data tidak ditemukan, ataupun server error.
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
Agar developer frontend / client yang mengkonsumsi API tersebut lebih terbiasa dan terstandarisasi jadi lebih gampang memahami dan menangani error dengan cara yang konsisten dan terstandarisasi.
```

### 12. Apa itu field-level error?

Jawaban:

```text
Adalah error yang terjadi pada field tertentu dalam sebuah request yang biasanya terjadi pada saat proses validasi data.
```

### 13. Apa itu custom exception?

Jawaban:

```text
Custom exception adalah exception yang dibuat sendiri oleh developer untuk merepresentasikan kondisi error tertentu sesuai dengan logika bisnis aplikasi. Biasanya dibuat dengan cara mewarisi (inherit) dari class Exception atau turunannya.
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
CustomerNotFoundException lebih baik daripada RuntimeException biasa karena lebih spesifik dalam menggambarkan jenis error yang terjadi. Dengan menggunakan custom exception, kode menjadi lebih mudah dipahami, mempermudah debugging, dan memudahkan penanganan error yang sesuai dengan logika bisnis, seperti ketika data customer tidak ditemukan.
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
@ControllerAdvice adalah anotasi di Spring yang digunakan untuk menangani exception secara global di seluruh controller. Dengan @ControllerAdvice, kita bisa memusatkan logika error handling sehingga tidak perlu menulis try-catch di setiap controller.
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
ExceptionalHandler adalah anotasi di Spring yang digunakan untuk menangani exception secara khusus di dalam controller atau global exception handler.
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
Karena dapat membuat penanganan error menjadi lebih konsisten, rapi, dan mudah dikelola.
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request dari client tidak valid atau tidak sesuai dengan yang diharapkan oleh server.
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika client mengakses resource tertentu, tetapi resource tersebut tidak ditemukan di server.
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
500 internal server error digunakan ketika terjadi kesalahan di sisi server yang tidak terduga, sehingga server tidak dapat memproses request dengan benar.
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
Karena dapat membocorkan informasi sensitif tentang sistem internal aplikasi. Stack trace berisi detail teknis seperti struktur kode, nama class, dan package yang error.
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
1. Client mengirim HTTP request POST ke endpoint /api/v1/customers dengan mengirim body JSON
2. Request diterima oleh Controller : method createCustomer(@Valid @RequestBody CreateCustomerRequest request)
3. Spring akan otomatis melakukan validasi karena ada annotation :
    - @Valid pada parameter request
    - constraint di DTO, khususnya : @Email pada field email
4. Ketika email tidak valid, maka
    - Validasi Bean Validation (Hibernate Validator) gagal
    - Spring Tidak akan masuk ke method service
5. Spring akan throw exception MethodArgumentNotValidException
6. Exception ini akan di catch
7. Spring akan return response error ke client.
8. Proses selesai, dan data tidak disimpan ke database
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
1. Client mengirim HTTP request GET ke endpoint:
/api/v1/customers/999
2. Request diterima oleh Controller:
   method getCustomerById(@PathVariable long id)
3. Controller memanggil service:
   customerService.getCustomerById(id)
4. Di dalam Service:
   - Dilakukan pencarian data di storage:
     Customer cust = customerStorage.get(id);
5. Karena id = 999 tidak ada di storage:
   - cust akan bernilai null
6. Service melakukan pengecekan:
   if (cust == null)
7. Karena null, maka akan melempar exception:
   throw new CustomerNotFoundException(id);
8. Exception tersebut tidak ditangani di service,
   sehingga akan naik ke layer atas (Controller)
9. Exception akan di catch
10. Backend mengembalikan response error ke client:
    - HTTP Status: 404 Not Found
11. Proses selesai, tidak ada data yang dikembalikan.
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
Validation Error -> data yang dimasukan formatnya salah atau tidak lengkap

Business error -> input sudah benar, tapi logika bisnis salah.

System error -> Kesalahan internal dari komputer atau program yang crash.
```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
Tidak ada yang sulit jika kita mau belajar, tapi yang paling tricky adalah cara kita menentukan.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Custom Exception
2. Exception Handling
3. Validation level field, dan service.
```

Apa 2 hal yang masih membingungkan?

```text
1. Custom Exception
2. Custom response.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara agar bisa lansgung menentukan service apa saja dan controller apa saja.
```
