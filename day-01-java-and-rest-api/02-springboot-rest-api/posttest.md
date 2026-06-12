## Posttest - Spring Boot REST API
  
Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

#### 1. Apa itu Spring Boot?
  
Jawaban:

```text
Spring Boot adalah framework dari Java yang membantu kita membuat aplikasi dengan lebih cepat. Biasanya dipakai untuk membuat REST API atau web application tanpa perlu konfigurasi yang terlalu banyak.
```

#### 2. Apa fungsi Spring Initializr?
  
Jawaban:

```text
Spring Initializr digunakan untuk membuat project Spring Boot baru dengan mudah. Di sana kita bisa memilih nama project, Maven/Gradle, versi Java, dan dependency yang dibutuhkan.
```

#### 3. Saat membuat project, kenapa memilih Maven?
  
Jawaban:

```text
Maven dipilih karena membantu mengatur dependency dan build project. Jadi kalau butuh library seperti Spring Web, kita cukup tambahkan dependency dan Maven akan mengurusnya.
```

#### 4. Apa fungsi dependency Spring Web?
  
Jawaban:z

```text
Spring Web digunakan supaya aplikasi bisa membuat REST API. Dengan dependency ini kita bisa memakai annotation seperti @RestController, @GetMapping, @PostMapping, dan menerima request HTTP.
```

#### 5. Kenapa Day 1 belum menambahkan dependency database?
  
Jawaban:

```text
Karena di awal fokusnya belajar dasar REST API dulu. Data masih bisa disimpan sementara menggunakan Map atau List, jadi belum perlu database agar tidak terlalu kompleks.
```

#### 6. Apa fungsi file pom.xml?
  
Jawaban:

```text
File pom.xml berfungsi untuk mengatur konfigurasi project Maven. Di dalamnya ada informasi project, dependency, versi Java, dan plugin untuk menjalankan atau build aplikasi.
```

#### 7. Apa fungsi TrainingApplication.java?
  
Jawaban:

```text
TrainingApplication.java adalah file utama untuk menjalankan aplikasi Spring Boot. Biasanya di dalamnya ada method main yang menjalankan SpringApplication.run().
```

#### 8. Apa fungsi @SpringBootApplication?
  
Jawaban:

```text
@SpringBootApplication menandakan bahwa class tersebut adalah class utama Spring Boot. Annotation ini juga membantu Spring membaca konfigurasi, component, controller, dan service secara otomatis.
```

#### 9. Bagaimana cara menjalankan Spring Boot dari IDE?
  
Jawaban:

```text
Dari IDE, kita bisa buka file utama seperti TrainingApplication.java lalu klik tombol Run. Setelah itu aplikasi akan berjalan dan biasanya muncul log di console.
```

#### 10. Bagaimana cara menjalankan Spring Boot dari terminal?
  
Jawaban:

```text
Dari terminal bisa menjalankan perintah mvn spring-boot:run. Bisa juga build dulu dengan mvn clean package lalu menjalankan file jar yang sudah dibuat.
```

#### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.
  
Jawaban:

```text
Tidak selalu error. 404 bisa terjadi karena memang belum ada endpoint yang dibuat untuk path /. Kalau endpoint yang dibuat misalnya /api/v1/customers, maka yang harus dibuka adalah endpoint tersebut.
```

#### 12. Apa itu REST API?
  
Jawaban:

```text
REST API adalah cara komunikasi antara client dan server menggunakan HTTP. Biasanya menggunakan method seperti GET, POST, PUT, dan DELETE, lalu data dikirim atau diterima dalam bentuk JSON.
```

#### 13. Apa itu endpoint?
  
Jawaban:

```text
Endpoint adalah alamat URL tertentu yang digunakan untuk mengakses fitur API. Contohnya /api/v1/customers untuk mengambil atau membuat data customer.
```

#### 14. Apa perbedaan request dan response?
  
Jawaban:

```text
Request adalah permintaan yang dikirim client ke server, misalnya meminta data customer. Response adalah jawaban dari server, misalnya data customer dalam bentuk JSON atau status berhasil.
```

#### 15. Kenapa JSON menggunakan snake\_case, sedangkan Java menggunakan camelCase?
  
Jawaban:

```text
Karena biasanya standar penulisan JSON banyak memakai snake_case, sedangkan di Java standar penamaan variabel menggunakan camelCase. Jadi kadang perlu mapping supaya nama field tetap sesuai di masing-masing sisi.
```

#### 16. Apa fungsi @JsonProperty?
  
Jawaban:

```text
@JsonProperty digunakan untuk mengatur nama field saat data Java diubah menjadi JSON atau JSON diubah menjadi object Java. Contohnya field fullName di Java bisa dibuat menjadi full_name di JSON.
```

#### 17. Apa tugas Controller?
  
Jawaban:

```text
Controller bertugas menerima request dari client dan mengembalikan response. Controller biasanya memanggil service untuk menjalankan proses atau logic yang dibutuhkan.
```

#### 18. Apa tugas Service?
  
Jawaban:

```text
Service bertugas menyimpan business logic aplikasi. Misalnya proses membuat customer, mencari customer, update customer, atau delete customer.
```

#### 19. Apa itu DTO?
  
Jawaban:

```text
DTO atau Data Transfer Object adalah object yang digunakan untuk mengirim data antara client dan server. Contohnya CreateCustomerRequest untuk request dan CustomerResponse untuk response.
```

#### 20. Apa itu Model?
  
Jawaban:

```text
Model adalah representasi data utama di aplikasi. Contohnya Customer yang punya id, fullName, email, dan phoneNumber.
```

#### 21. Kenapa request body tidak langsung menggunakan model?
  
Jawaban:

```text
Karena data yang dikirim dari client belum tentu sama dengan model. Misalnya saat membuat customer, client tidak perlu mengirim id karena id dibuat oleh server.
```

#### 22. Kenapa response tidak langsung menggunakan model?
  
Jawaban:

```text
Karena tidak semua data di model perlu dikirim ke client. Dengan response DTO, kita bisa mengatur data mana saja yang aman dan perlu ditampilkan.
```

#### 23. Kenapa business logic tidak boleh ditaruh di Controller?
  
Jawaban:

```text
Supaya kode lebih rapi dan mudah dirawat. Controller cukup mengatur request dan response, sedangkan logic utama diletakkan di service.
```

#### 24. Kapan menggunakan 200 OK?
  
Jawaban:

```text
200 OK digunakan ketika request berhasil diproses. Contohnya berhasil mengambil data customer, update data, atau delete data dan server mengembalikan response sukses.
```

#### 25. Kapan menggunakan 201 Created?
  
Jawaban:

```text
201 Created digunakan ketika data baru berhasil dibuat. Contohnya saat POST /api/v1/customers berhasil membuat customer baru.
```

#### 26. Apa bedanya path parameter dan query parameter?
  
Jawaban:

```text
Path parameter adalah nilai yang menjadi bagian dari URL, contohnya /customers/1 untuk mengambil customer dengan id 1. Query parameter adalah nilai setelah tanda tanya, contohnya /customers?name=budi untuk mencari customer berdasarkan nama.
```

#### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.
  
Jawaban:

```text
Client mengirim request POST ke /api/v1/customers dengan body JSON berisi data customer. Controller menerima request tersebut lalu mengirimkannya ke service. Service membuat data customer baru, menyimpannya sementara, lalu membuat CustomerResponse. Setelah itu controller mengembalikan response ke client, biasanya dengan status 201 Created.
```

#### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?
  
Jawaban:

```text
Bagian yang cukup sulit adalah memahami perbedaan Controller, Service, DTO, dan Model. Selain itu juga perlu latihan untuk membedakan penggunaan @PathVariable dan @RequestParam.
```

#### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?
  
Jawaban:

```text
Error yang ditemui adalah ambiguous mapping karena ada dua @GetMapping dengan path yang sama. Cara menyelesaikannya adalah menggabungkan endpoint get all dan search menggunakan @RequestParam(required = false), atau membuat path yang berbeda untuk search.
```

#### 30. Apa yang ingin kamu pelajari berikutnya?
  
Jawaban:

```text
Saya ingin belajar menggunakan database dengan Spring Boot, misalnya memakai Spring Data JPA. Selain itu saya juga ingin belajar validasi request, error handling, dan membuat API yang lebih rapi.
```