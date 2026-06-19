# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring adalah framework java yang memudahkan programmer untuk membuat aplikasi java.

Spring Boot adalah salah satu jenis framework dari Spring. Spring boot memudahkan developer untuk menjalankan Spring Framework.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializr adalah tools yang digunakan untuk membantu develoepr untuk membuat Spring Boot Project dengan mudah, men-generate struktur project seperti configuration files, build tools, dan dependensi.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Karena maven memudahkan developer dalam memanage dependencies. Maven juga bantu automisasi builds, test, dan download library.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Spring web berfungsi untuk menyediakan komponen yang diperlukan untuk membangun aplikasi web, seperti REST API dan pemrorsesan request dan response
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Karena, di day 1 kita masih menggunakan database didalam memory, didalam service.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
pom.xml ini berguna untuk mengelola dependensi, menentukan versi java, serta informasi mengenai projek projek.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
TrainingApplication.java ini memuat class main untuk run aplikasi java springboot ini.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Anotasi SpringBootApplication berguna untuk masuk titik utama kedalam aplikasi SpringBoot.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
1. Buka TrainingApplication.java
2. Klik Run Java runner.
3. Aplikasi berhasil dijalankan
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
1. buka directory root yang terdapat pom.xml
2. mvnw spring-boot:run
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Belum tentu, bisa jadi root API yang belum di definisikan yang akan menjalanu api nya.
```

### 12. Apa itu REST API?

Jawaban:

```text
REST adalah arsitektur standar untuk membangun sistem berbasis web.
REST API adalah penerapan nyata dari arsitektur tersebut agar memudahkan komunikasi antara aplikasi.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah alamat URL yang digunakan REST API untuk mengakses server. Seperti titik masuk untuk mengakses server / resource pada server.
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request adalah permintaan yang dikirim oleh Client ke server.

Response adalah pesan balasan yang dikirim oleh server untuk memproses dari request yang diterima.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena JSON sudah menggunakan standar seperti itu, standar komunitas.
Dan Java menggunakan camelCase juga sudah didefinisikan pada code conventions.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty adalah anotasi dari library pada java untuk mengatur bagaimana variabel pada objek Java agar diubah menjadi format JSON.
```

### 17. Apa tugas Controller?

Jawaban:

```text
Controller adalah gerbang masuk API, komponen yang langsung berinteraksi dengan clien. Menerima request dari clien dan mengembalikan response dari server.
```

### 18. Apa tugas Service?

Jawaban:

```text
Service adalah tempat dimana programmer menuliskan logika bisnis, aturan sistem, atau perhitungan aplikasi.
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO adalah objek yang khusus dibuat untuk membungkus dan mengangkut data untuk tranfer data.
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah representasi data dari tabel yang ada didalam database. Cerminan langsung dari struktur tabel pada database.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Karena masalah keamanan data, untuk menghindari kebocoran data maka harus melalui DTO atau perantara dulu saja. Baik itu request dan response direkomendasi menggunakan DTO Object. Agar data yang tidak seharusnya diisin oleh frontend bisa dijaga aksesnya.
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Karena masalah keamanan data, untuk menghindari kebocoran data maka harus melalui DTO atau perantara dulu saja. Baik itu request dan response direkomendasikan menggunakan DTO Object. Agar data yang tidak seharusnya diisin oleh frontend bisa dijaga aksesnya.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Karena jika ditaruh di controller, akan menyebabkan kode lebih susah untuk di maintenance, di test, dan di reusable. Business logic lebih dirokemendasikan untuk di taruh di service.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika sebuah request berhasil di proses dan tidak membuat resource baru.

Contoh -> Ketika mengirimkan request GET /api/v1/users. Api akan mereturn data users tanpa mencreate resource apapun.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika sebuah request berhasil di proses dan menghasilkan resource baru.

Contoh -> Ketika mengirimkan request POST /api/v1/users dan request body { "username" : "risjadshidqi", "email" : "risjad@gmail.com", "password" : "testpassword"}. API akan mereturn success dan membuat resource user baru dengan data yang dikirim.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Path Paramater akan menggunakan paramater yang ditulis di path URL. Contoh -> /api/v2/users/2. 2 Disini adalah path parameter.

Query parameter digunakan untuk memfilter data, sorting data, pagination. Contoh -> GET /api/v2/users?name=tony.
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
1. Client me hit request API POST /api/v1/customers
2. Request masuk ke controller yang menghandle api tersebut.
3. Controller memanggil service yang akan menghandle request tersebut.
4. Service melakukan proses yang telah ditentukan, dalam hal ini adalah create Customer dan menambahkan resource customer.
5. Service akan membuat response dan mengirimkan ke controller.
6. Controller akan mengirim response yang telah dibuat ke client.
7. Client akan menghandle response yang telah di return oleh server.
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Tidak ada yang sulit untuk membuat spring boot jika kita ingin belajar dan membaca dokumentasi.
Yang agak challenging adalah ketika kita inisiasi projek baru, dependencies, dan mengatur struktur folder sesuai dengan Clean Code.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Error yang saya temui saat saya develop adalah error endpoint not found, dan itu diakibatkan karena saya belum re run aplikasi java spring bootnya, jadi code yang terupdate belum ter run.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Saya ingin belajar lebih dengan clean architecture pada Java Spring Boot, agar code yang ditulis tidak hanya jalan sebagai fungsional tapi juga future proof.
```
