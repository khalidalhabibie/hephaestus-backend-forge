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
sebuah perjanjian antara provider dan consumer dalam dokumentasi API
```

### 2. Kenapa API contract penting?

Jawaban:

```text
agar API konsisten dalam mengirim dan menerima data
```

### 3. Apa saja isi API contract?

Jawaban:

```text
tidak tau
```

### 4. Apa itu endpoint?

Jawaban:

```text
sebuah titik atau alamat API
```

### 5. Apa itu HTTP method?

Jawaban:

```text
sebuah instruksi untuk standar pengiriman request dari klien/server antar web
```

### 6. Apa itu request body?

Jawaban:

```text
isi dari request yang berdasarkan format body yang telah ditentukan
```

### 7. Apa itu response body?

Jawaban:

```text
isi dari response yang berdasarkan format body yang telah ditentukan

```

### 8. Apa itu HTTP status code?

Jawaban:

```text
sebuah kode untuk menampilkan status response
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
agar kita hanya perlu mengirimkan dan mengembalikan data sesuai yang dibutuhkan saja
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
maka akan terjadi inkonsistensi dan adanya celah keamnanan
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
data transfer object, yang berfungsi sebagai format atau template dari request dan response
```

### 12. Apa itu request DTO?

Jawaban:

```text
sebuah format request
```

### 13. Apa itu response DTO?

Jawaban:

```text
sebuah format response
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
karena dto hanya berisi atribut yang diperlukan untuk mengoper data, sedangkan model berisi seluruh data yang ada di database
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
karena itu merupakan standar yang telah ditetapkan untuk memudahkan pembacaan variable
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mengubah nama variable menjadi standar JSON, yakni snake case
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
untuk menambah data
```

### 18. Apa fungsi GET?

Jawaban:

```text
untuk mengambil data
```

### 19. Apa fungsi PUT?

Jawaban:

```text
untuk mengupdate seluruh data
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
untuk mengupdate sebagian data
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
put digunakan untuk mengupdate keseluruhan data, sedangkan patch hanya sebagian
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
ketika berhasil menambah data baru
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika berhasil mengambil data
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika request dari klien tidak sesuai dengan format request
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika request yang diminta tidak ada di server
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
sebuah proses untuk mengetes API apakah berjalan dengan normal atau tidak
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
supaya bisa mengetahui apakah data berhasil terkirim dan dikirim kembali
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
postman & swagger
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
semua method get, post, put, patch dan delete. lalu kita harus mengecek apakah error response sudah sesuai dengan kebutuhan
```

### 30. Apa itu expected response?

Jawaban:

```text
response yang diharapkan dari server ketika mengirim sebuah data
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
platform dokumentasi dan testing api
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
tidak tau
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
mendokumentasikan sekaligus testing api
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
postman hanya untuk testing, sedangkan swagger bisa dokumentasi sekaligus testing
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
bisa, karena swagger bisa mendeteksi secara otomatis api yang kita buat
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |3|
| DTO |3|
| HTTP method |3|
| API testing |3|
| Swagger UI |3|
| OpenAPI |2|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
