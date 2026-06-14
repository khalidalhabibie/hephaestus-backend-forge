# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah salah satu framework/kerangka kerja dari bahasa pemrograman Java sebagai service BackEnd untuk membuat API dan koneksi ke database dengan lebih mudah
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializr digunakan untuk membuat kerangka awal project Spring Boot dengan dependency apa saja yang dibutuhkan
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Maven dipilih ialah karena memudahkan dalam manajemen dependency dan proses build project yang terstruktur
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Spring Web digunakan untuk membuat REST API dan menangani request serta response HTTP
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Karena fokus awal ialah memahami dasar REST API sebelum masuk ke integrasi database
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
pom.xml sendiri berfungsi untuk mengatur dependency, konfigurasi project, dan juga build lifecycle project Maven
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
File tersebut merupakan entry point untuk menjalankan aplikasi Spring Boot
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Annotation tersebut menandai class utama dan mengaktifkan konfigurasi dari Spring Boot
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Spring Boot dijalankan dengan menekan tombol Run pada class utama di IDE
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Spring Boot dijalankan dengan perintah `mvn spring-boot:run` di terminal
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Tidak selalu error, karena bisa saja belum ada endpoint yang dipetakan ke root path (/)
```

### 12. Apa itu REST API?

Jawaban:

```text
REST API adalah cara komunikasi antar sistem menggunakan HTTP dengan prinsip REST
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint ialah URL yang digunakan untuk mengakses suatu fungsi pada REST API
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request merupakan permintaan dari client ke server, sedangkan response adalah balasan dari server ke client
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena JSON mengikuti konvensi umum API, sedangkan Java mengikuti standar penamaan variabel bahasa Java
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty digunakan untuk memetakan nama field JSON ke atribut Java yang berbeda
```

### 17. Apa tugas Controller?

Jawaban:

```text
Controller bertugas menerima request dari client dan mengarahkan ke service yang sesuai
```

### 18. Apa tugas Service?

Jawaban:

```text
Service bertugas menjalankan business logic aplikasi
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO adalah object khusus untuk membawa data antara request atau response tanpa logika bisnis
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah representasi tabel dalam database yang ditulis dalam bentuk kode Java
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Agar struktur data request lebih aman dan tidak bergantung langsung pada entity
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Supaya data yang dikirim ke client bisa dikontrol dan tidak membocorkan field yang sensitif
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Karena akan membuat kode sulit dirawat dan maintenance
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Status 200 OK digunakan saat request berhasil diproses tanpa membuat data baru
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Status 201 Created digunakan saat berhasil membuat data baru melalui request POST
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Path parameter adalah bagian dari URL, sedangkan query parameter ada setelah tanda "?"
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
Request masuk ke controller, diproses oleh service, lalu hasilnya dikembalikan sebagai response
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Bagian tersulit adalah memahami alur data dan pemisahan tanggung jawab antar layer
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Error yang sering muncul adalah mapping endpoint, dan solusinya dengan mengecek annotation dan URL
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Saya ingin mempelajari terkait integrasi database dan JPA di Spring Boot
```
