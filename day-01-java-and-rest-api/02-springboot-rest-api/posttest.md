# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
sebuah framework backend java
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
membuat template untuk spring boot
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
karena memiliki build terstandarisasi seperti mvn dan dependensi yang otomatis
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
untuk pembuatan framework mvc dan pembuatan rest api
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
karena kita hanya menggunakan api dan masih disimpan ke dalam map
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
file yang berisi kumpulan dependency
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
berfungsi untuk menjalankan file projek
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```
penanda utama untuk memulai aplikasi spring boot
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
pada file utama klik tombol run java
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
mvn springboot:run
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
404 Not Found berarti server berjalan dengan baik dan merespons, tetapi URL atau path yang diakses tidak ada di dalam direktori aplikasi tersebut
```

### 12. Apa itu REST API?

Jawaban:

```text
sebuah protokol untuk jembatan komunikasi berbasis request dan response
```

### 13. Apa itu endpoint?

Jawaban:

```text
sebuah titik atau alamat untuk mengirim atau menerima request/response
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
reqeust meminta sesuatu dari server, dan response mengirim hasil kepada klien
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
JSON menggunakan snake_case agar format datanya dapat diakses dengan mudah secara universal, sedangkan Java menggunakan camelCase merupakan naming convention yang umum digunakan
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
digunakan untuk mengubah format Java (camelCase) menjadi JSON (snake_case)
```

### 17. Apa tugas Controller?

Jawaban:

```text
mengatur traffic komunikasi
```

### 18. Apa tugas Service?

Jawaban:

```text
logic bisnis
```

### 19. Apa itu DTO?

Jawaban:

```text
merupakan format atau template request dan response
```

### 20. Apa itu Model?

Jawaban:

```text
entity yang memiliki atribut yang merepresantiskan struktur database
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```
untuk menjaga keamanan, efisiensi, dan pemisahan tanggung jawab
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
agar hasil yang dikirimkan sesuai dengan request, sehingg data yang ditampilkan seperlunya saja
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
karena melanggar prinsip separation of concerns
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika suatu aksi berhasil dilakukan atau berhasil mengambil data
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
ketika sebuah data berhasil ditambahkan
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
path parameter digunakan untuk mengidentifikasi sumber daya (resource) yang unik, sedangkan query parameter digunakan untuk memodifikasi atau memfilter sumber daya tersebut
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
ketika post, dilakukan request berupa fullname,email dan phone number. proses ini dilakukan di dto sebagai format request dan response akan ditampilkan berupa id, fullname, email dan phone number dan akan memunculkan http response 210 created. proses ini dijembatani oleh controller. setelah request berhasil, maka akan disimpan ke dalam map
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
saya mengalami kesulitan di service karena kurang bisa membuat logic
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
saya mengalami error di format file java nya, cara saya mengatasinya adalah dengan me rename file java tersebut
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
saya ingin mendalami cara membuat logic di service
```
