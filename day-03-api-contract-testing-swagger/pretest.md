# Pretest - API Contract, API Testing & Swagger

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang API contract, API testing, dan Swagger.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - API Contract

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah sekumpulan API dan dokumentasinya yang bisa di akses oleh semua developer agar mengerti skema dari api yang sudah dibuat dan cara pengunaannya.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
agar semua developer mengerti skema dari api yang sudah dibuat dan cara pengunaannya. sehingga tidak salah dalam consumenya dll
```

### 3. Apa saja isi API contract?

Jawaban:

```text
API contract berisi definisi endpoint, metode HTTP, format request–response, parameter, autentikasi, dan kode error sebagai kesepakatan komunikasi antara client dan server.
```

### 4. Apa itu endpoint?

Jawaban:

```text
alamat url spesifik untuk mengakses api, guna mendapatkan data atau service tertentu
```

### 5. Apa itu HTTP method?

Jawaban:

```text
Metode http untuk menentukan event yang terjadi -> get post patch put delete dll
```

### 6. Apa itu request body?

Jawaban:

```text
Masukan yang diterima oleh controller jika memerlukan data untuk dikirmkan ke server
```

### 7. Apa itu response body?

Jawaban:

```text
Keluaran yang diberikan oleh server ke client 
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
aturan code spesifik untuk menandakan event yang terjadi. 
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
agar 
- request : mengirimkan data yang tepat dan sesuai format supaya bisa di consume oleh service dan tidak eror
- response : client mengetahui apa yang terjadi lewat response yang diberikan oleh server
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Tim developer bisa memiliki pengertian sendiri tentang api dan akhirnya miskom dan salah dalam mengonsumsi, logika bisnis ambigu dan salah, keluaran dari server salah, dan akhrnya bisnis bisa mengalami kerugian. 
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
Data transfer object, untuk menerima dan mengirimkan data supaya tidak model langsung yang dilempar untuk menjaga keamanan.
```

### 12. Apa itu request DTO?

Jawaban:

```text
input yang dimita dan harus dikirimkan ke server untuk mengakses api tertentu. 
```

### 13. Apa itu response DTO?

Jawaban:

```text
template response yang sudah dipersiapkan untuk diterima oleh user, supaya server tidak mengirimkan data data sensitif
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
supaya server tidak mengirimkan data data sensitif, dan menyebabkan security issue
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena json dan java memiliki standar yang berbeda dalam penulisan code yang harus diikuti. dimana java camelCase dan json snake_case
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mengubah variable tertentu menjadi format yang kita tentukan sendiri - biasanya untuk dto dan snake_case
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
HTTP method yang bertugas untuk Memasukan/menambahkan data ke dalam database
```

### 18. Apa fungsi GET?

Jawaban:

```text
HTTP method yang bertugas untuk Mendapatkan data dari database
```

### 19. Apa fungsi PUT?

Jawaban:

```text
HTTP method yang bertugas untuk mengedit/mengubah data ke dalam database dengan mengubah semua value yang sudah ada.
```

### 20. Apa fungsi PATCH?

Jawaban:

```text

HTTP method yang bertugas untuk mengedit/mengubah data ke dalam database dengan mengubah salah satu atau lebih value yang sudah ada.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
Put mengubah semua value / Patch tidak harus semua value
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika data baru ditambahkan ke dalam database
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika event atau service berhasil di eksekusi
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request body tidak sesuai dengan format yang diminta atau ditentukan
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika tidak terdapat data tertentu dari database/api/page dll
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
Test untuk melihat request dan response dari api tertentu, untuk memastikan apakah sudah sesuai kesepakatan yang ditentukan atau belum
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Agar memastikan apakah sudah sesuai dengan kesepakatan atau belum, sehingga nantinya ketika di consume atau digunakan api tidak eror dan sesuai dengan bisnis
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman / swagger 
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Request, response, error, status code dll
```

### 30. Apa itu expected response?

Jawaban:

```text
standar response yang diharapkan yang dikeluarkan oleh server ke client
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
dependency/library untuk membuat api contract secara otomatis
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
standarisasi untuk mendefinisikan rest api secara terstruktur
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
untuk mengakses semua list api yang sudah dibuat, cara pakainya, requestbodynya, responsenya, errornya dll.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman adalah tool untuk mengirim dan menguji request API secara manual (testing), sedangkan Swagger UI adalah tool untuk mendokumentasikan dan menampilkan API secara interaktif agar mudah dipahami dan dicoba langsung dari dokumentasi.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Swagger dapat menggantikan sebagian besar dokumentasi API manual karena mampu menghasilkan dokumentasi otomatis yang interaktif dan selalu sinkron dengan kode, namun tetap tidak sepenuhnya menggantikan dokumentasi manual karena penjelasan tambahan seperti use case, business logic, dan panduan penggunaan yang lebih naratif masih diperlukan.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |4|
| DTO |4|
| HTTP method |5|
| API testing |3|
| Swagger UI | 3|
| OpenAPI |3|

## Notes

```text
Bagaimana cara membuat api contract menggunakan swaggerw
```
