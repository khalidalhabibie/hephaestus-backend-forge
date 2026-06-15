# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah framework Java untuk membuat aplikasi backend dengan cepat melalui konfigurasi otomatis dan server bawaan
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializr digunakan untuk membuat project Spring Boot secara cepat dengan memilih dependency dan konfigurasi awal
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Karena Maven memudahkan pengelolaan dependency, build project, dan struktur project yang standar
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Spring Web digunakan untuk membuat REST API, termasuk controller, routing, dan embedded server seperti Tomcat
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Agar fokus memahami dasar REST API terlebih dahulu sebelum masuk ke integrasi database
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
pom.xml digunakan untuk mengatur dependency, konfigurasi build, dan plugin dalam project Maven
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
File ini adalah entry point aplikasi yang digunakan untuk menjalankan Spring Boot
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Annotation ini mengaktifkan konfigurasi otomatis, component scanning, dan konfigurasi Spring Boot
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Klik tombol run pada class utama (main class) di IDE seperti IntelliJ atau VS Code
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Gunakan perintah: mvn spring-boot:run atau java -jar file.jar
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Tidak selalu error, bisa jadi karena belum ada endpoint yang dibuat pada root URL tersebut
```

### 12. Apa itu REST API?

Jawaban:

```text
REST API adalah metode komunikasi antara client dan server menggunakan HTTP dan biasanya menggunakan JSON
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL yang digunakan untuk mengakses resource atau fungsi tertentu pada API
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request adalah permintaan dari client ke server, sedangkan response adalah balasan dari server ke client
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena JSON mengikuti standar umum (snake_case) untuk interoperabilitas, sedangkan Java mengikuti konvensi penamaan camelCase
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JSONProperty digunakan untuk memetakan nama field JSON ke atribut di Java
```

### 17. Apa tugas Controller?

Jawaban:

```text
Controller menerima request, memanggil service, dan mengembalikan response
```

### 18. Apa tugas Service?

Jawaban:

```text
Service menjalankan business logic dan mengatur proses utama aplikasi
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO adalah objek untuk mentransfer data antara client dan server
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah representasi data yang biasanya sesuai dengan struktur database
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Agar tidak semua field model terekspos dan untuk validasi input
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Agar dapat mengontrol data yang dikirim ke client dan menjaga keamanan
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Agar kode terstruktur, mudah dirawat, dan mengikuti separation of concerns
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Saat request berhasil diproses dan data berhasil dikembalikan
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Saat berhasil membuat data baru di server
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Path parameter digunakan untuk identifikasi resource, sedangkan query parameter untuk filter atau optional parameter
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
Client mengirim request POST dengan body JSON ke endpoint → Controller menerima request → data diubah ke DTO → Service memproses business logic → data disimpan (jika ada database) → hasil dikembalikan ke controller → controller mengirim response JSON ke client
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Memahami alur antar layer (Controller, Service, DTO) dan mapping data
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Contoh/ error 404 karena endpoint salah, diselesaikan dengan mengecek mapping URL di controller
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Integrasi database (JPA), validation, dan authentication seperti JWT
```