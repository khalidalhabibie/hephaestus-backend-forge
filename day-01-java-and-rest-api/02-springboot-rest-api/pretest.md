# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Tulis jawaban di sini.
Spring boot adalah framework java yang mempermudah pengembangan aplikasi atau web dengan berbagai fitur seperti security, batch processing, rest mapping, authorization dan berbagai fitur lainnya. Spring boot mempermudah pembuatan class, dependency injection.
```

### 2. Apa itu REST API?

Jawaban:

```text
Tulis jawaban di sini.
Representative State Application Programming Interface adalah salah satu bentuk API yang membuat controller dengan naming convention method contoh seperti post, put, get, dan delete yang membuat jembatan komunikasi dengan alamat routing seperti /get-all-product dan mengarahkan kepada service dan mengembalikan response yang dibutuhkan
```

### 3. Apa itu HTTP?

Jawaban:

```text
Tulis jawaban di sini.
Hyper Text Transfer Protocol adalah sebuah procedure untuk mengirimkan data melalui sebuah koneksi network dengan melihat IP, akses port, menyambungkan koneksi, mengambil data, mengirimkannya kembali ke client
```

### 4. Apa itu JSON?

Jawaban:

```text
Tulis jawaban di sini.
JSON adalah salah satu bentuk data yang digunakan untuk response atau request body dalam pengembangan sebuah web. Contoh bentuk dari respons JSON adalah
response :{
    nama: "Richo",
    Gaji: 10000000
}
```

### 5. Apa itu endpoint?

Jawaban:

```text
Tulis jawaban di sini.
Dalam pengembangan sebuah API, endpoint adalah tempat dimana route controller sebagai alamat jembatan agar client bisa berkomunikasi dengan server. alamat endpoint bisa seperti /get-all-product dan akan hit controller yang melakukan sejumlah operasi sesuai dengan logika service yang diberikan.
```

### 6. Apa itu request?

Jawaban:

```text
Tulis jawaban di sini.
request adalah aksi dari client untuk melakukan hit terhadap endpoint ke server (komunikasi yang dimulai oleh client kepada server) dan mengirimkan request body yang dibutuhkan oleh server (request body boleh kosong pada beberapa kasus)
```

### 7. Apa itu response?

Jawaban:

```text
Tulis jawaban di sini.
Response adalah hasil setelah request dan setelah controller dijalankan mengembalikan sebuah data atau response code contoh dalam bentuk json kepada client. Data tersebut dibungkus dalam format response yang akan dikembalikan kepada client (server ke client)
```

### 8. Apa fungsi GET?

Jawaban:

```text
Tulis jawaban di sini.
Naming convention untuk mengambil data yang ada pada sebuah server melalui sebuah endpoint
```

### 9. Apa fungsi POST?

Jawaban:

```text
Tulis jawaban di sini.
Naming convention untuk membuat data yang ada pada sebuah server melalui sebuah endpoint
```

### 10. Apa fungsi PUT?

Jawaban:

```text
Tulis jawaban di sini.
Naming convention untuk memperbaharui seluruh object data yang ada pada sebuah server melalui sebuah endpoint
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
Tulis jawaban di sini.
Naming convention untuk menghapus data yang ada pada sebuah server melalui sebuah endpoint
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
Tulis jawaban di sini.
Saat berhasil melakukan sebuah operasi dari endpoint server akan mengirimkan response code 200 biasa digunakan untuk method Get
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Tulis jawaban di sini.
Saat berhasil membuat data kepada server biasa digunakan pada method Post
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Tulis jawaban di sini.
Saat request body yang dikirimkan user kepada backend tidak sesuai atau data tidak benar sehingga operasi digagalkan
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Tulis jawaban di sini.
Saat sebuah endpoint atau service tidak exist
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
Tulis jawaban di sini.
Saat fungsi gagal dijalankan namun bukan karena eksternal reason namun internal dari logic code yang dimiliki, misal saat Logic function terkena exception walaupun data sudah benar
```

### 17. Apa itu path parameter?

Jawaban:

```text
Tulis jawaban di sini.
Path parameter adalah sebuah url spesifik yang menargetkan data secara spesifik seperti ID atau nama
```

### 18. Apa itu query parameter?

Jawaban:

```text
Tulis jawaban di sini.
Parameter yang berfungsi mengubah, memodifikasi atau filter data
```

### 19. Apa itu request body?

Jawaban:

```text
Tulis jawaban di sini.
Request body adalah sebuah bentuk data yang dikirimkan kepada server oleh client untuk parameter controller yang dibutuhkan oleh server.
```

### 20. Apa tugas Controller?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai jembatan untuk menghubungkan client dengan server dan sebagai pusat pemanggilan logika service (pada beberapa methode orang menggunakan useCase yang memanggil useCase tersebut di controller dan melakukan sejumlah operasi disana)
```

### 21. Apa tugas Service?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai tonggak logika aplikasi untuk mengambil, menambah, menghapus, dan memperbaharui data yang diberikan atau dimiliki
```

### 22. Apa itu DTO?

Jawaban:

```text
Tulis jawaban di sini.
Data Transform Object adalah sebuah wadah dari untuk menerima request body sebelum dimasukkan ke service digunakan untuk mengolah data dan parsing sesuai kebutuhan logika bisnis sebelum diubah menjadi object
```

### 23. Apa itu Model?

Jawaban:

```text
Tulis jawaban di sini.
Entity sebuah aplikasi untuk menyimpan data dan merepresentasikan table atau data yang ada di database. Model juga bisa berfungsi sebagai data Holder dalam sebuah aplikasi
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
Tulis jawaban di sini.
Karena untuk menerapkan clean architecure dan membuat modularitas lebih mudah agar bila ada keperluan tambahan bisa tinggal mengganti logic yang ada dengan service yang diinginkan untuk kemudahan pengembangan dan fleksibilitas.
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
Tulis jawaban di sini.
Agar request atau response tidak langsung akses entity namun DTO terlebih dahulu dan agar lebih fleksibel untuk pengolahan data yang akan digunakan kepada service application
```
