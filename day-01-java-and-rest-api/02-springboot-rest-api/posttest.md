# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah framework berbasis Java yang digunakan untuk membuat aplikasi web dan layanan backend dengan cepat dan mudah karena menyediakan konfigurasi otomatis dan fitur bawaan siap pakai.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializr adalah alat (tool) yang digunakan untuk membuat proyek awal (starter project) Spring Boot secara otomatis dengan konfigurasi dasar yang sudah siap dipakai.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Memilih Maven saat membuat project (misalnya di Spring Initializr) biasanya karena fungsinya sebagai build automation dan dependency management tool.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Dependency Spring Web berfungsi untuk membangun aplikasi web atau REST API serta menangani komunikasi HTTP (request dan response) dengan mudah di Spring Boot.
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Karena pada Day 1 fokusnya masih pada setup dasar aplikasi dan memahami konsep Spring Boot (seperti controller dan API), sehingga belum perlu menggunakan database agar pembelajaran tidak terlalu kompleks di awal.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
File pom.xml berfungsi sebagai file konfigurasi utama di Maven yang digunakan untuk mengatur dependency, build project, dan informasi proyek dalam aplikasi Spring Boot.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
TrainingApplication.java berfungsi sebagai class utama (entry point) untuk menjalankan aplikasi Spring Boot serta mengaktifkan konfigurasi otomatis melalui anotasi seperti @SpringBootApplication.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Anotasi @SpringBootApplication berfungsi untuk menggabungkan konfigurasi utama Spring Boot seperti auto-configuration, component scanning, dan konfigurasi aplikasi agar aplikasi dapat berjalan secara otomatis.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Spring Boot dapat dijalankan dari IDE dengan cara menjalankan class utama yang memiliki method main() seperti TrainingApplication.java.
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Spring Boot dapat dijalankan dari terminal dengan perintah mvn spring-boot:run pada folder project yang menggunakan Maven.
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Tidak selalu error, karena status 404 (Not Found) hanya berarti tidak ada endpoint atau halaman yang terdaftar di /, bukan berarti aplikasinya gagal berjalan.
```

### 12. Apa itu REST API?

Jawaban:

```text
REST API adalah antarmuka yang memungkinkan aplikasi saling berkomunikasi melalui protokol HTTP dengan menggunakan metode seperti GET, POST, PUT, dan DELETE untuk mengakses atau mengelola data.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL atau alamat spesifik dalam sebuah API yang digunakan untuk mengakses layanan atau data tertentu.
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Perbedaan request dan response adalah request merupakan permintaan dari client ke server, sedangkan response adalah hasil atau jawaban yang diberikan server kembali ke client.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Perbedaan itu terjadi karena JSON mengikuti konvensi umum lintas bahasa (sering memakai snake_case untuk keterbacaan dan konsistensi), sedangkan Java menggunakan camelCase sesuai standar penamaan variabel dalam bahasa tersebut.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty berfungsi untuk menentukan nama field pada JSON agar bisa dipetakan ke properti di Java, terutama jika nama keduanya berbeda.
```

### 17. Apa tugas Controller?

Jawaban:

```text
Controller bertugas untuk menerima request dari client, memprosesnya, lalu mengembalikan response yang sesuai.
```

### 18. Apa tugas Service?

Jawaban:

```text
Service bertugas untuk menangani logika bisnis atau proses utama aplikasi sebelum hasilnya dikembalikan ke controller.
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek yang digunakan untuk membawa atau mentransfer data antar layer dalam aplikasi, seperti dari controller ke service atau sebaliknya.
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah representasi struktur data dalam aplikasi yang biasanya mencerminkan entitas atau tabel di database.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Request body tidak langsung menggunakan model karena DTO digunakan untuk membatasi, menyesuaikan, dan mengontrol data yang masuk agar tidak langsung terikat ke struktur database (model).
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Response tidak langsung menggunakan model karena untuk mengontrol, menyembunyikan, dan menyesuaikan data yang dikirim ke client agar tidak mengekspos seluruh struktur atau informasi sensitif dari model.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Business logic tidak boleh ditaruh di Controller karena Controller hanya bertugas menangani request/response, sedangkan logika bisnis harus dipisahkan ke Service agar kode lebih rapi, mudah dikelola, dan mengikuti prinsip clean architecture.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Status 200 OK digunakan ketika request berhasil diproses oleh server dan data atau respon dikembalikan dengan sukses.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Status 201 Created digunakan ketika request berhasil membuat resource baru di server, biasanya setelah operasi POST berhasil.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Perbedaannya adalah path parameter digunakan sebagai bagian dari URL untuk menunjuk resource tertentu, sedangkan query parameter digunakan untuk mengirim data tambahan atau filter melalui URL.
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
Flow POST /api/v1/customers adalah client mengirim request ke Controller, diproses oleh Service (dan disimpan ke database jika ada), lalu hasilnya dikembalikan sebagai response ke client.
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Bagian yang paling sulit saat membuat Spring Boot REST API biasanya adalah mengatur struktur kode (Controller, Service, DTO, Model) dan memastikan alur data serta business logic berjalan dengan benar dan rapi.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Error yang sering ditemui adalah dependency belum ter-download atau konfigurasi tidak sesuai (misalnya endpoint 404 atau port bentrok), dan biasanya diselesaikan dengan sync ulang Maven, cek mapping endpoint, dan memastikan konfigurasi aplikasi sudah benar.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Saya ingin mempelajari integrasi database (Spring Data JPA) dan pembuatan CRUD yang terhubung ke database agar aplikasi menjadi lebih lengkap dan realistis.
```
