# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Spring Boot adalah framework Java yang membantu dalam development aplikasi dengan memanfaatkan REST API agar proses menjadi lebih cepat.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Spring Initializr berfungsi untuk membuat project Spring Boot dengan dependency automated.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Maven berfungsi untuk mempermudah proses development dengan menambahkan library secara otomatis.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Spring Web berfungsi untuk membuat REST API.
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
Day 1 masih memahami Java Fundamental tanpa menerapkan clean coding, serta data belum terhubung dengan REST API yang membantu menambahkan data langsung melalui API.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
pom.xml berfungsi untuk memberikan informasi project secara lebih detail (e.g. version Java dan Spring Boot).
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
TrainingApplication.java berfungsi untuk menjalankan aplikasi dengan mengimplementasikan Spring Boot.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
@SpringBootApplication berfungsi untuk memberitahu program bahwa Class tersebut merupakan aplikasi Spring Boot.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
Cara menjalankan Spring Boot dari IDE adalah dengan menjalankan tombol run yang ada di main serta terdapat @SpringBootApplication.
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
Cara menjalankan Spring Boot dari terminal adalah dengan menjalankan mvn spring-boot:run.
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Belum tentu karena sepengetahuan saya terdapat beberapa 404, seperti 404 Not Found yang menandakan program bisa berjalan normal namun terdapat URL yang belum di-assign.
```

### 12. Apa itu REST API?

Jawaban:

```text
REST API adalah cara aplikasi berkomunikasi melalui HTTP.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah URL yang menjadi alur komunikasi antardata maupun informasi pada API.
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request -> permintaan yang dikirim client ke server.
Response -> jawaban yang dikirim setelah server ke client sesuai dengan request yang dilakukan sebelumnya.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Hal tersebut adalah format penulisan yang umum digunakan, Java menggunakan camelCase karena sesuai dengan standar penulisan Java yang ada.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty digunakan untuk menghubungkan nama field dengan nama var di Java.
```

### 17. Apa tugas Controller?

Jawaban:

```text
Controller bertugas untuk menerima request dari client dan mengontrol alur tersebut.
```

### 18. Apa tugas Service?

Jawaban:

```text
Service bertugas untuk menjalankan business logic.
```

### 19. Apa itu DTO?

Jawaban:

```text
DTO adalah object yang digunakan untuk mengirim atau menerima data antara client dan server.
```

### 20. Apa itu Model?

Jawaban:

```text
Model adalah sekumpulan data utama yang digunakan.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Karena data yang dikirim client belum tentu sama dengan data yang disimpan di model.
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Karena tidak semua data perlu ditampilkan ke client.
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Karena business logic merupakan hal yang crucial, sehingga perubahan yang terjadi pada Controller tidak mengganggu business logic yang ada. Hal ini juga dilakukan agar kode lebih bisa dibaca dan lebih rapi.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Saag request berhasil diproses dan data berhasil diperbarui.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Saat request berhasil diproses dan data baru berhasil dibuat.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
Path parameter -> di dalam URL untuk mencari data tertentu (e.g. GET /customer/v1).

Query parameter -> setelah tanda tanya "?" dan digunakan untuk filter (e.g. GET /customers?id=01).
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
Client mengirim request POST beserta data Customer yang ingin dimasukkan, kemudian Controller menerima request dan meneruskan ke Service untuk memproses dan membaut data Customer sesuai dengan yang diminta. Setelah selesai, Service mengembalikan ke Controller untuk selanjutnya dikirim response dalam bentuk JSON.
```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Bagian yang paling sulit menurut saya adalah memahami logic ketika membuat code tersebut dan flow dari setiap job-nya.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Error yang sering saya temui addalah kesalahan penulisan dan alur yang sering terbalik. Cara penyelesaiannya adalah dengan membaca case dengan lebih teliti.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Saya ingin mempelajari bagaimana cara membuat API yang terhubung langsung ke database sungguhan karena saya belum pernah mendevelop project tersebut dari 0 dengan database yang saya buat sendiri.
```