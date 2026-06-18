# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah salah satu framework Java.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializer adalah suatu tool untuk membantu membuat project Spring Boot yang sudah di-setup sesuai dengan pengaturan di web tsb.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Karena dapat mempermudah build project dan mengelola dependency secara otomatis, dengan struktur standar dan mudah dipahami.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Dependency Spring Web di Spring Boot digunakan untuk membuat aplikasi web dan REST API seperti Request Mapping (GET, POST, PUT, etc).
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Agar bisa fokus memahami cara kerja API dan Spring Boot dan memahami fundamentalnya terlebih dahulu, sebelum masuk ke database yang lebih kompleks.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
Untuk mengatur seluruh aspek build dan dependency project.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
File TrainingApplication.java adalah seperti class main atau class utama yang merupakan titik awal saat aplikasi dijalankan.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Sebagai anotasi untuk mengaktifkan dan melakukan konfigurasi aplikasi secara otomatis.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Buka project Spring Boot dan menjalankan file mmain (TrainingApplication.java) sebagai titik awal aplikasi.
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Bisa menggunakan Maven (mvn spring-boot:run).
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Belum tentu, bisa saja karena endpoint / belum dibuat, misalnya pada code hanya dibuat route @GetMapping("/hello"), maka apabila membuka http://localhost:8080/ maka hasilnya akan 404 karena belum dibuat routenya.
```

### 12. Apa itu REST API?

Jawaban:

```text
REST API adalah suatu layanan yang memungkinkan client untuk berinteraksi dengan server menggunakan URL dan HTTP method.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah alamat (URL) spesifik pada REST API yang digunakan untuk mengakses data tertentu di server.
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request adalah permintaan yang dikirim dari client ke server, sedangkan response adalah hasil yang dikembalikan dari server ke client sebagai balasan.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
JSON menggunakan snake_case agar lebih fleksibel dan dapat dipahami oleh berbagai bahasa sedangkan Java menggunakan camelCase untuk naming conventionnya agar lebih mudah dibaca dan lebih terstruktur.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk menyesuaikan nama field di JSON dengan nama field di Java tapi tetap mengikuti naming convention di JSON (snake_case).
```

### 17. Apa tugas Controller?

Jawaban:

```text
Untuk melakukan routing saat request masuk dan mengarahkan request ke business logic yang sesuai dan mengembalikan response ke client.
```

### 18. Apa tugas Service?

Jawaban:

```text
Untuk menjalankan business logic dalam aplikasi, misalnya CustomerService berisi business logic yang berhubungan dengan entity Customer.
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah object yang digunakan untuk mengirim dan menerima data dari client agar hanya data yang dibutuhkan saja yang di-transfer.
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah representasi dari database aplikasi.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Agar controller tidak dapat langsung mengakses database sehingga data dan struktur database aplikasi lebih aman dan terkendali.
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Karena Model akan mengembalikan semua data pada entity kepada client, padahal tidak semua data diperlukan pada business logic.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Agar setiap lapisan lebih rapi dan terstruktur sehingga lebih scalable dan lebih mudah mengidentifikasi error, serta business logic di Controller bisa digunakan kembali (reusable), yang sesuai dengan best practice di industri.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika server berhasil menerima, memproses, dan mengembalikan hasil sesuai request.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika server berhasil membuat data baru sebagai hasil dari request.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Tulis jawaban di sini.Path parameter berguna untuk menentukan rute atau alamat yang dituju, sedangkan query parameter berfungsi untuk melakukan filtering data yang ingin ditampilkan.
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
POST /api/v1/customers (client mengirim request) -> Request masuk ke controller dan mengarahkan request sesuai dengan mapping yang sudah dibuat -> Request body terbuat -> Request yang diikirimkan controller masuk ke service -> Service menjalankan business logic untuk create Customer -> Data Customer disimpan di database berdasarkan model yang sudah dibuat -> Service mengembalikan response body ke controller -> Controller melanjutkan response ke client -> Client menerima response
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Menentukan penempatan coding/class menjadi beberapa layer (dto / model, service, controller) dan kompleksitas coding dengan beberapa layer.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Response yang tidak muncul karena tidak melakukan setAttribute pada response body.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Memperkuat fundamental pembuatan REST API.
```
