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
API Contract adalah kesepakatan atau spesifikasi yang mendefinisikan bagaimana client dan server berinteraksi melalui API. API contract menjelaskan endpoint, method GET POST PUT PATCH DELETE, format request dan response, parameter, serta aturan yang harus diikuti oleh kedua pihak.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
Agar developer Frontend dan Backend bisa bekerja di waktu yang sama dan tidak saling tunggu. Jadi developer frontend bekerja bedasarkan API contract yang telah dibuat sehingga mengurangi kesalahan komunikasi, mempercepat development, dan memastikan integrasi berjalan dengan konsisten.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
Isi dari API Contract :
    1. Endpoint API
    2. HTTP Method
    3. Parameter Request
    4. Format Response
    5. Status code
    6. Rules and Constraint.
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL pada API yang digunakan untuk mengakses resource atau fungsi terentu di Backend / server.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
Method yang digunakan client untuk memberitahu server apa yang ingin dilakukan terhadap suatu resource melalui API.
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim oleh client ke server melalui API, biasanya digunakan pada HTTP method seperti POST, PUT, atau PATCH untuk membuat atau memperbarui data.
```

### 7. Apa itu response body?

Jawaban:

```text
Respobse body adalah data yang dikirimkan oleh server sebagai jawaban dari request yang diminta melalui API.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
adalah code angka yang dikirim oleh server untuk menujukkan hasil dari request yang diminta oleh client
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Agar komunikasi antar client dan server tidak terjadi kesalahan, memastikan data yang dikirim dan diterima sesuai harapkan.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Bisa terjadi miskomunikasi antara frontend dan backend, error saat integrasi, dan bisa penghambat proses development.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO adalah objek yang digunakan untuk mengirim data antara client dan server.
```

### 12. Apa itu request DTO?

Jawaban:

```text
DTO yang digunakan untuk membawa data dari client ke server serta melakukan request ke API.
```

### 13. Apa itu response DTO?

Jawaban:

```text
adalah DTO yang digunakan untuk mengirim data dari server ke client sebagai jawaban dari request api.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
agar memisahkan anatara sturktur data internal dengan data yang dikirim ke client / frontend. Sehingga data lebih aman, fleksibel dan mudah dikelola.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
JSON biasanya menggunakan snake case karena best practice nya seperti itu, sedangkan java menggunakan camel case karena mengikuti konvensi penamaan java.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Anotasi tersebut digunakan untuk mengconvert penamaan attribute menjadi mengikuti standar JSON yaitu snake case.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
Metode yang berfungsi untuk mengirimkan, dan menambahkan data ke database.
```

### 18. Apa fungsi GET?

Jawaban:

```text
Metode yang berfungsi untuk mendapatkan dan mengambil data.
```

### 19. Apa fungsi PUT?

Jawaban:

```text
Digunakan untuk mengubah keselurhan data, jadi data lama akan terperbarui dengan data yang baru (replace).
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
Mengubah sebagian nilai data, hanya mengubah field field tertentu.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT mengubah keseluruhan data, sedangkan patch sebagaian data saja.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika server backend berhasil menambahkan resource ke database.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat service backend berhasil mengakses dan mereturn data ke client.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika data yang dikirim frontend ke server sebagai request body itu tidak valid.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika backend mengakses resource yang tidak ada.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
Testing API itu adalah pengujian API yang telah dibuat oleh developer backend, mengecek hasil return actual dengan harapan yang ditetapkan di api contract.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
Untuk memastikan bahwa hasil return actual sesuai dengan harapan yang ditetapkan di contract.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
POSTMAN, SWAGGER, CMD PAKE CURL.
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Response message, response structure, response data, status code.
```

### 30. Apa itu expected response?

Jawaban:

```text
Expected response adalah hasil atau output yang diharapkan oleh client setelah mengirim request ke server.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah salah satu tools yang digunakan untuk menguji API.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
standar yang dijelaskan untuk mendeskripsikan REST API secara terstruktur.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Tools yang digunakan untuk visualisasi dan dokumentasi REST APIs
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Swagger digunakan untuk membantu desain dan dokumentasi api, sedangkan postman digunakan untuk melakukan pengujian, debugging api.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Ya bisa, karena swagger bisa mengenerate daftar api yang ada.
```

## Self Assessment

| Area         | Score 1-5 |
| ------------ | --------- |
| API contract |           |
| DTO          |           |
| HTTP method  |           |
| API testing  |           |
| Swagger UI   |           |
| OpenAPI      |           |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
