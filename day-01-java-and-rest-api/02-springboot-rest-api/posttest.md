# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Tulis jawaban di sini.
Spring Boot adalah framework berbasis Spring yang digunakan untuk mempermudah pengembangan aplikasi Java. Spring Boot menyediakan konfigurasi otomatis (auto-configuration), embedded server, dan dependency management sehingga developer dapat membuat aplikasi dengan lebih cepat dan mudah.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Tulis jawaban di sini.
Spring Initializr adalah tools untuk membuat project Spring Boot secara otomatis. Dengan Spring Initializr, developer dapat memilih dependency, build tool, dan konfigurasi awal project tanpa perlu membuat struktur project secara manual.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Tulis jawaban di sini.
Maven digunakan sebagai build automation dan dependency management tool. Maven membantu mengelola library yang dibutuhkan aplikasi, proses build, testing, dan packaging project secara otomatis melalui file pom.xml.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Tulis jawaban di sini.
Dependency Spring Web digunakan untuk membuat aplikasi web dan REST API. Dependency ini menyediakan fitur seperti controller, request mapping, response handling, serta embedded Tomcat server.
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Tulis jawaban di sini.
Karena fokus Day 1 adalah memahami dasar Spring Boot dan REST API terlebih dahulu. Data masih dapat disimpan sementara menggunakan collection seperti List atau Map sehingga belum memerlukan koneksi database.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
Tulis jawaban di sini.
pom.xml adalah file konfigurasi utama Maven yang berisi informasi project, dependency yang digunakan, plugin, serta konfigurasi build aplikasi.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
Tulis jawaban di sini.
TrainingApplication.java merupakan class utama (main class) yang digunakan untuk menjalankan aplikasi Spring Boot. Class ini menjadi entry point saat aplikasi dijalankan.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Tulis jawaban di sini.
@SpringBootApplication adalah anotasi utama Spring Boot yang menggabungkan @Configuration, @EnableAutoConfiguration, dan @ComponentScan untuk mengaktifkan konfigurasi otomatis dan scanning bean.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Tulis jawaban di sini.
Aplikasi dapat dijalankan dengan membuka class utama yang memiliki method main() kemudian menekan tombol Run pada IDE seperti IntelliJ IDEA atau Eclipse.
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Tulis jawaban di sini.
Menggunakan perintah:

mvn spring-boot:run

atau setelah build:

java -jar target/nama-file.jar
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Tulis jawaban di sini.
Tidak selalu error. Status 404 berarti endpoint yang diakses tidak ditemukan. Jika aplikasi berjalan tetapi belum memiliki endpoint pada path "/", maka 404 adalah hal yang normal.
```

### 12. Apa itu REST API?

Jawaban:

```text
Tulis jawaban di sini.
REST API adalah arsitektur komunikasi antara client dan server menggunakan protokol HTTP. REST API memungkinkan pertukaran data dalam format seperti JSON melalui endpoint tertentu.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Tulis jawaban di sini.
Endpoint adalah URL yang disediakan oleh REST API untuk menerima request dan mengirimkan response. Contohnya: /api/v1/customers.
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Tulis jawaban di sini.
Request adalah permintaan yang dikirim client ke server, sedangkan response adalah balasan yang dikirim server kepada client setelah memproses request tersebut.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Tulis jawaban di sini.
Karena masing-masing memiliki konvensi penulisan yang berbeda. JSON sering menggunakan snake_case agar lebih mudah dibaca lintas bahasa pemrograman, sedangkan Java mengikuti standar penamaan camelCase untuk variabel dan method.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Tulis jawaban di sini.
@JsonProperty digunakan untuk memetakan nama field JSON ke atribut Java yang berbeda namanya. Anotasi ini membantu proses serialisasi dan deserialisasi data JSON.
```

### 17. Apa tugas Controller?

Jawaban:

```text
Tulis jawaban di sini.
Controller bertugas menerima request dari client, memvalidasi input sederhana, memanggil service yang sesuai, dan mengembalikan response kepada client.
```

### 18. Apa tugas Service?

Jawaban:

```text
Tulis jawaban di sini.
Service bertugas menjalankan business logic aplikasi. Service menjadi penghubung antara Controller dan layer data atau repository.
```

### 19. Apa itu DTO?

Jawaban:

```text
Tulis jawaban di sini.
DTO (Data Transfer Object) adalah objek yang digunakan untuk mengirim dan menerima data antara client dan server tanpa mengekspos model atau entity secara langsung.
```

### 20. Apa itu Model?

Jawaban:

```text
Tulis jawaban di sini.
Model adalah representasi data atau objek bisnis dalam aplikasi yang berisi atribut dan perilaku yang berkaitan dengan data tersebut.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Tulis jawaban di sini.
Karena request dari client belum tentu membutuhkan semua field yang ada pada model. Menggunakan DTO membuat validasi lebih mudah, lebih aman, dan menghindari perubahan data yang tidak diinginkan.
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Tulis jawaban di sini.
Karena model mungkin memiliki data internal yang tidak perlu ditampilkan kepada client. DTO response membantu membatasi data yang dikirim sehingga lebih aman dan fleksibel.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Tulis jawaban di sini.
Agar kode lebih rapi, mudah diuji, dan mengikuti prinsip separation of concerns. Controller hanya menangani request-response sedangkan business logic berada di Service.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Tulis jawaban di sini.
Status 200 OK digunakan ketika request berhasil diproses dan server berhasil mengembalikan data yang diminta.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Tulis jawaban di sini.
Status 201 Created digunakan ketika request berhasil membuat resource baru, misalnya saat melakukan POST untuk menambahkan customer baru.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Tulis jawaban di sini.
Path parameter merupakan bagian dari URL yang menunjukkan resource tertentu.

Contoh:
GET /customers/1

Query parameter digunakan untuk filtering, sorting, atau pencarian.

Contoh:
GET /customers?name=John
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
Tulis jawaban di sini.
1. Client mengirim HTTP POST ke endpoint /api/v1/customers beserta data JSON.
2. Controller menerima request tersebut.
3. Request body dikonversi menjadi DTO Request.
4. Controller memanggil Service.
5. Service menjalankan business logic dan membuat data customer.
6. Service mengembalikan hasil ke Controller.
7. Controller membuat DTO Response.
8. Spring Boot mengubah DTO Response menjadi JSON.
9. Server mengirim response ke client dengan status 201 Created.
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Tulis jawaban di sini.
Bagian yang paling sulit adalah memahami alur data dari Controller, DTO, Service, hingga Response serta memahami pemisahan tanggung jawab antar layer agar struktur aplikasi tetap rapi dan scalable.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Tulis jawaban di sini.
Salah satu error yang ditemui adalah HTTP 404 Not Found karena endpoint belum dibuat atau URL yang diakses salah. Solusinya adalah memastikan path pada @RequestMapping atau @PostMapping sesuai dengan URL yang diakses.

Selain itu, pernah terjadi error dependency Maven yang dapat diselesaikan dengan melakukan Maven Reload atau menjalankan perintah mvn clean install.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Tulis jawaban di sini.
Saya ingin mempelajari integrasi database menggunakan Spring Data JPA, validasi data menggunakan Bean Validation, exception handling, authentication dan authorization menggunakan Spring Security, serta pembuatan REST API yang terhubung dengan database secara penuh.
```
