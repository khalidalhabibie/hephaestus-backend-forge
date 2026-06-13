# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```
Spring boot merupakan salah satu framework back end yang menggunakan bahasa java yang biasa digunakan untuk pengembangan aplikasi.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```
membantu developer dalam memulai suatu project menggunakan spring boot dengan lebih cepat tanpa perlu melakukan konfigurasi secara manual 
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```
dapat mempermudah developer, karena dengan menggunakan maven kita tidak perlu mengunduh library satu satu selain itu pada maven memiliki struktur project yang sudah terstandarisasi dan terintegrasi dengan spring boot
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```
untuk menyediakan fitur pengembangan aplikasi web dan REST API, termasuk pengelolaan request-response HTTP, pembuatan endpoint, serialisasi JSON, serta web server bawaan sehingga aplikasi dapat dijalankan dan diakses melalui browser atau API client seperti Postman
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```
pada hari pertama belum diperlukan depedency karena masih pengenalan awal dan belum kompleks dan belum masuk ke konfigurasi database
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```
digunakan Maven untuk mengetahui bagaimana proyek dibangun, dependensi apa saja yang dibutuhkan, serta plugin yang harus dijalankan.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```
berfungsi sebagai class utama yang menjadi titik awal menjalankan aplikasi Spring Boot. Class ini menginisialisasi Spring Framework, memuat konfigurasi aplikasi, melakukan component scanning, serta menjalankan embedded web server melalui method SpringApplication.run().
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```
berfungsi sebagai annotation utama yang menggabungkan @Configuration, @EnableAutoConfiguration, dan @ComponentScan.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```
1. Buka `TrainingApplication.java`. atau kalau di tempat saya adalah "Exerciseday02Application.java"
2. Klik tombol Run.
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```
mvn spring-boot:run
mvnw.cmd spring-boot:run
./mvnw spring-boot:run
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```
itu tidak selalu berarti aplikasi gagal. Pada awal project, root path `/` memang belum punya endpoint. Artinya server sudah berjalan, tetapi belum ada route untuk URL tersebut.
```

### 12. Apa itu REST API?

Jawaban:

```
REST API adalah suatu metode untuk menghubungkan satu aplikasi dengan aplikasi lain agar bisa berkomunikasi dengan aturan tertentu
```

### 13. Apa itu endpoint?

Jawaban:

```
alamat URL di aplikasi web atau API tempat client mengirim request
```

### 14. Apa perbedaan request dan response?

Jawaban:

```
kalau request merupakan permintaan yang dikirim kalau response adalah jawaban yang diterima sesuai dengan yang diminta 
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```
Java menggunakan camelCase karena merupakan standar penamaan variabel dan atribut dalam bahasa Java. Sementara itu, JSON sering menggunakan snake_case karena lebih umum digunakan dalam pertukaran data API dan dianggap lebih mudah dibaca.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```
berfungsi untuk menentukan nama field yang digunakan pada JSON saat proses serialisasi dan deserialisasi.
```

### 17. Apa tugas Controller?

Jawaban:

```
penghubung antara request dari client dan logic aplikasi yaitu service/model
```

### 18. Apa tugas Service?

Jawaban:

```
Mengolah data dan menjalankan logika bisnis aplikasi
```

### 19. Apa itu DTO?

Jawaban:

```
DTO adalah Data Transfer Object. DTO digunakan untuk data yang masuk dan keluar dari API.
```

### 20. Apa itu Model?

Jawaban:

```
representasi data tabel yang ditulis dalam bentuk java
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```
Untuk menjaga keamanan, memudahkan validasi, dan mengurangi ketergantungan antara API dan database,
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```
karena model merepresentasikan struktur database dan dapat mengandung data sensitif atau informasi yang tidak perlu ditampilkan.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```
karena controller seharusnya hanya bertugas untuk menangani request dan response saja. Jika business logic ditempatkan di controller, maka kode akan menjadi sulit dibaca, sulit dirawat (maintain), dan sulit digunakan kembali (reusable).
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```
digunakaan saat server berhasil memproses request dan mengembalikannya dengan berhasil
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```
saat server berhasil membuat data baru
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```
Path parameter digunakan untuk mengidentifikasi resource tertentu dan menjadi bagian dari URL, misalnya /customers/123. Sedangkan query parameter digunakan untuk filter, pencarian, atau parameter opsional yang ditambahkan setelah tanda ?, misalnya /customers?full_name=francis. Pada Spring Boot, path parameter diakses menggunakan @PathVariable, sedangkan query parameter menggunakan @RequestParam
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```
saat melakukan request post /api/v1/customers, json mengubah object CreateCustomerRequest melalui @RequestBody yang kemudian controller akan memanggil CustomerService.createCustomer() yang dimana akan digenerate IDnya lalu buat object Customer dan setelah itu disimpan dalam hashmap yang selanjutnya data customer akan di konversi menjadi CustomerResponse yang direturn ke controller. kemudian controller akan mengirim response status 201 Created dengan data customer yang baru dibuat dalam format json
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```
saat mulai ngodingnya inisiasi awal kebutuhan programnnya apa, namun dengan materi yang diberikan pelan-pelan lebih terbayang apa yang harus dilakukan dan proses selanjutnya bagaimana
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```
tidak bisa melakukan testing di postman karena inisiasi API yang masih kurang sesuai, cara memperbaikinya adalah mencari tahu root cause nya ada dimana dan diperbaiki pelan-pelan meskipun masih banyak dibantu temen yang lebih jago 
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```
ingin mematangkan fundamental terlebih dahulu karena sebenarnya banyak yang masih dibingungkan tp saya juga bingung bagaimananya 
```
