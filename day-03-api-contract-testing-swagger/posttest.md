# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
Sebuah dokumen kesepakatan formal antara tim Backend dan tim Client (Frontend/Mobile/QA) yang mendefinisikan bagaimana cara bertukar data antar sistem. Dokumen ini menjadi acuan tunggal (Single Source of Truth) selama proses pengembangan aplikasi agar tidak terjadi miskomunikasi.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
Isi komponen utama dalam API contract meliputi:
- HTTP Method (GET, POST, PUT, PATCH, DELETE)
- URL Path / Endpoints (misal: /api/v2/customers)
- Header Requirements (misal: Content-Type, Authorization)
- Request Params / Query Params / Path Variables
- Request Body (struktur JSON beserta tipe datanya)
- Response Body (struktur JSON untuk skenario sukses dan error)
- HTTP Status Codes (misal: 200, 201, 400, 404, 500)
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Karena memungkinkan Frontend/Mobile developer bekerja secara paralel tanpa harus menunggu Backend selesai dibuat. Berdasarkan struktur JSON yang disepakati di API contract, developer client bisa langsung membuat komponen UI, mengikat data (data binding), atau membuat data tiruan (mock data/mock API).
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah sebuah objek/class Java yang dirancang khusus untuk membawa data antar proses atau layer di dalam aplikasi, terutama dari dan menuju client melalui jaringan internet. DTO murni hanya berisi properti data, getter, setter, dan anotasi validasi tanpa memiliki logika bisnis terikat.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
- Request DTO: Objek yang menampung dan memvalidasi data masukan dari client (payload input), biasanya digunakan pada method POST, PUT, dan PATCH.
- Response DTO: Objek yang memformat data dari server untuk dikirim kembali ke client sebagai hasil keluaran (payload output) agar sesuai dengan struktur kontrak API.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Untuk menjaga keamanan dan fleksibilitas (Separation of Concerns). Model/Entity mencerminkan struktur tabel database secara langsung dan seringkali berisi data sensitif (seperti password, token, atau flag internal). DTO digunakan sebagai tameng agar kita hanya mengekspos field yang memang diizinkan dan dibutuhkan oleh client.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk memetakan nama properti di Java (camelCase) ke nama field JSON (misal snake_case atau UPPERCASE) saat proses serialisasi (Java ke JSON) dan deserialisasi (JSON ke Java). Properti ini juga bisa mengabaikan atau mengganti representasi nama field tanpa mengubah kode di database.
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
Untuk memberi tahu Spring Boot agar mengekstrak payload JSON yang dikirim oleh client di dalam tubuh HTTP request, lalu mengubahnya secara otomatis (deserialisasi) menjadi objek Java/DTO yang diletakkan pada parameter method di Controller.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
Untuk mengambil nilai dinamis yang disematkan langsung di dalam segmen URL path, umumnya digunakan untuk mentarget ID spesifik dari suatu resource. Contoh: Mengambil nilai `1` dari URL `/api/v2/customers/{id}` (diakses via `/api/v2/customers/1`).
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
Untuk mengaktifkan proses validasi otomatis dari JSR-383 / Jakarta Bean Validation (seperti anotasi @NotBlank, @Email, @Size) pada objek Request DTO sebelum data tersebut masuk dan diproses lebih jauh oleh logika di Controller atau Service.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
- POST: Digunakan untuk membuat/mengirimkan data baru ke server. Datanya diletakkan di dalam Request Body, bersifat tidak idempoten (mengeksekusi berulang kali akan menambah data baru terus-menerus), dan lebih aman untuk data sensitif.
- GET: Digunakan untuk mengambil/membaca data dari server. Parameter dikirim melalui URL (Query Params), bersifat idempoten (dipanggil berulang kali tidak mengubah status server), dan datanya dapat di-cache oleh browser.
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
- PUT: Digunakan untuk memperbarui data secara menyeluruh (Replace/Overwrite). Client wajib mengirimkan seluruh field objek baru untuk menggantikan objek lama di database.
- PATCH: Digunakan untuk memperbarui data sebagian secara parsial (Modify). Client hanya mengirimkan field-field yang ingin diubah saja, sedangkan field yang absen tetap mempertahankan nilai lama di database.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Digunakan ketika sebuah HTTP Request (umumnya POST) berhasil dieksekusi dan server sukses membuat sebuah data/resource baru di database.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Digunakan untuk menandakan bahwa HTTP Request berhasil diproses secara umum, seperti sukses mengambil data (GET), sukses memperbarui data (PUT/PATCH), atau sukses menghapus data (DELETE).
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Digunakan ketika server tidak bisa memproses request karena kesalahan dari sisi client, seperti sintaks JSON salah (malformed JSON), field mandatory kosong, atau gagal lolos aturan validasi data (@Valid).
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Digunakan ketika server tidak dapat menemukan resource yang diminta oleh client, contohnya mencari data customer dengan ID atau alamat email yang tidak terdaftar di database.
```

### 17. Apa itu API testing?

Jawaban:

```text
Proses pengujian perangkat lunak berfokus pada pengiriman request ke API endpoints untuk memastikan logika bisnis, performa, keamanan, serta keandalan pertukaran datanya berjalan sesuai dengan kriteria fungsional dan kontrak API yang disepakati.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Komponen yang harus diverifikasi meliputi:
- HTTP Status Code (apakah sesuai skenario sukses/error)
- Struktur skema JSON respon dan tipe datanya
- Validitas nilai data di dalam JSON data body
- Kecepatan respon (Response time)
- Penanganan error message saat dikirimkan input tidak valid
- Perubahan kondisi data aktual di database setelah API ditembak
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
- Expected Response: Hasil respon ideal (status code, struktur, data) yang tertulis di dokumen kontrak API/skenario uji sebagai target acuan kebenaran.
- Actual Response: Hasil respon nyata yang dikeluarkan dan diterima langsung dari server ketika API ditembak saat pengujian dijalankan.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Rangkaian alat (tools) open-source yang dibangun di sekitar OpenAPI Specification (OAS) untuk membantu developer dalam merancang, membangun, mendokumentasikan, dan menguji RESTful API secara interaktif.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
Spesifikasi standar industri berbentuk format deskripsi API standar (biasanya ditulis dalam bentuk JSON atau YAML) untuk mendefinisikan RESTful API tanpa bergantung pada bahasa pemrograman backend yang digunakan.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Menyediakan halaman visual berbasis web (GUI) yang interaktif yang digenerate otomatis dari kode backend. Memudahkan developer atau QA melakukan uji coba langsung (Try it out) untuk mengirim request dan melihat response tanpa alat tambahan seperti Postman.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
Pada konfigurasi standar pustaka Springdoc OpenAPI di Spring Boot, URL default yang diakses adalah:
http://localhost:8080/swagger-ui/index.html atau http://localhost:8080/swagger-ui.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
Secara default, skema OpenAPI dalam format JSON mentah dapat diakses melalui URL:
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
Anotasi Swagger/OpenAPI untuk mengelompokkan (grouping) operasional endpoint controller ke dalam satu kategori atau modul besar di tampilan Swagger UI, serta memberikan deskripsi singkat dari modul tersebut.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
Anotasi Swagger/OpenAPI untuk memberikan ringkasan (summary) serta deskripsi mendetail mengenai fungsi dari suatu endpoint API tertentu agar mudah dipahami oleh pembaca dokumentasi.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
Anotasi Swagger/OpenAPI untuk mendokumentasikan skenario HTTP Status Code yang mungkin dikembalikan oleh suatu endpoint, lengkap dengan penjelasan kondisi error/sukses beserta tipe skema representasi objek datanya.```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI: Terikat langsung dengan kode program backend (auto-generated), selalu sinkron dengan perubahan kode, fungsinya fokus pada visualisasi dokumentasi lokal aplikasi tersebut, dan tidak memerlukan instalasi aplikasi eksternal.Postman: Alat pengujian API independen (terpisah dari backend), bisa menyimpan riwayat request, membuat automation test suites (mocking, environment switching, CI/CD integration), dan bisa menembak API mana pun secara fleksibel tanpa perlu akses ke kode sumbernya.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Apakah dependency springdoc-openapi-starter-webmvc-ui sudah terpasang dengan benar di pom.xml.Apakah aplikasi Spring Boot sudah berjalan dengan sukses (tidak crash saat startup).Apakah penulisan URL alamat port, format v2/v3, atau path suffix-nya sudah tepat.Apakah endpoint Swagger tidak sengaja terblokir oleh konfigurasi keamanan (Spring Security).
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Memahami bagian pemisahan logika validasi input yang dinamis pada endpoint PATCH. Karena kita harus membiarkan field bernilai null saat validasi DTO, namun harus teliti menangani merge data parsial di tingkat Service Layer agar data lama di database H2 tidak tertimpa menjadi null secara tidak sengaja.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1.Alur pembuatan REST API terstruktur mulai dari pendefinisian API Contract, pembuatan Request & Response DTO, pemisahan model Entity database, hingga manipulasi data di Service Layer.
2. Mekanisme validasi otomatis menggunakan anotasi di tingkat DTO seperti @NotBlank, @Email, dan @Size yang terintegrasi langsung dengan penanganan Global Exception Handler.
3. Cara memanfaatkan Springdoc OpenAPI (Swagger) untuk menghasilkan dokumentasi API interaktif secara otomatis langsung dari anotasi kode Spring Boot.
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara terbaik menyusun spesifikasi Swagger @ApiResponse jika tipe datanya berupa generic class yang bertumpuk (seperti WebResponse membungkus Page yang membungkus CustomerResponse).
2. Penerapan validasi tingkat lanjut (seperti custom validation logic) yang melibatkan pengecekan data ke database langsung sebelum melewati lapisan Controller.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana strategi terbaik (best practice) untuk mengimplementasikan mekanisme Idempotency Key pada endpoint POST (Create) di Spring Boot untuk mencegah duplikasi data akibat double-submit dari client, dan bagaimana cara mengamankan (security) endpoint PATCH agar tidak terjadi mass-assignment vulnerability jika client mencoba mengirimkan field rahasia yang tidak terdaftar di DTO?
```
