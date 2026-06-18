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
API contract adalah kesepakatan antara client dan backend mengenai cara API.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
Karena dengan API contract client dan backend memiliki pemahaman yang sama terkait API.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
- End Point
- Metode HTTP
- Format Request-Response
- Parameter
- Autentifikasi
- Status Code HTTP
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah alamat URL yang digunakan untuk mengakses API.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP Method adalah metode yang digunakan untuk menentukan event apa yang terjadi. HTTP Method diantaranya Get, Post, Patch, Put, Delete, dll.
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah data yang dikirim client ke backend dalam sebuah request.
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah data yang dikirim backend kepada client sebagai hasil dari request.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah kode yang menunjukkan hasil dari request yang dilakukan client ke server.
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Agar client mengetahui data apa yang harus dikirim dan data apa yang akan diterima dari API.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Risikonya adalah client dapat salah menggunakan API.
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO atau Data Transfer Object adalah object yang digunakan untuk mengirim dan menerima data antara client dan aplikasi. DTO membantu memisahkan data API dengan model internal aplikasi.
```

### 12. Apa itu request DTO?

Jawaban:

```text
Request DTO adalah objek yang menampung data yang dikirim oleh client ke backend.
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah objek yang menampung data yang dikirim oleh backend ke client.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Agar struktur data untuk API terpisah dari model internal aplikasi dan lebih aman untuk digunakan. Selain itu, model adalah sesuatu yang jarang diubah sehingga jika ada yang perlu diubah di DTO tidak akan mempengaruhi model.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena keduanya mengikuti konvensi yang umum digunakan pada masing-masing lingkungan. JSON sering menggunakan snake_case agar mudah dibaca, sedangkan Java menggunakan camelCase sebagai standar penulisan nama variabel dan method.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty digunakan untuk menghubungkan nama field JSON dengan nama field di Java. Misalnya JSON menggunakan full_name sedangkan Java menggunakan fullName.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
POST digunakan untuk membuat atau menambahkan data baru.
```

### 18. Apa fungsi GET?

Jawaban:

```text
GET digunakan untuk mengambil atau membaca data.
```

### 19. Apa fungsi PUT?

Jawaban:

```text
PUT digunakan untuk memperbarui seluruh data yang sudah ada.
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
PATCH digunakan untuk memperbarui sebagian data yang sudah ada.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
PUT biasanya mengirim seluruh data untuk update, sedangkan PATCH hanya mengirim field yang ingin diubah.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika request berhasil membuat resource atau data baru.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika request berhasil diproses dan mengembalikan hasil dengan sukses.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika request yang dikirim client tidak valid atau tidak sesuai format yang diharapkan.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource atau data yang diminta tidak ditemukan.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API testing adalah proses menguji API untuk memastikan request dan response bekerja sesuai yang diharapkan.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
API perlu dites untuk memastikan apakah alamat url API mengghasilkan berkeja dengan baik dan hasil yang sesuai dengan expected value yang dicantukan di API Contract.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman dan Swagger
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Status code, response body, format data, error response, dan perilaku API sesuai requirement.
```

### 30. Apa itu expected response?

Jawaban:

```text
Expected response adalah hasil yang seharusnya diterima ketika request dikirim ke API.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools yang digunakan untuk membuat API Contract secara otomatis.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standarisasi yang digunakan yntuk mendefinisikan REST API secara terstruktur.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI membantu developer melihat dokumentasi API dan mencoba endpoint langsung dari browser.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Postman digunakan untuk testing API secara lebih lengkap, sedangkan Swagger UI lebih fokus pada dokumentasi dan percobaan endpoint berdasarkan spesifikasi API.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Swagger dapat membantu menggantikan sebagian besar dokumentasi API karena dibuat langsung dari spesifikasi API. Namun, penjelasan bisnis, aturan khusus, dan contoh penggunaan biasanya tetap membutuhkan dokumentasi tambahan.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| API contract |3|
| DTO |3|
| HTTP method |3|
| API testing |3|
| Swagger UI |3|
| OpenAPI |3|

## Notes

```text
Hal yang membingungkan adalah belum terbayang cara untuk membuat API Contract.
```

