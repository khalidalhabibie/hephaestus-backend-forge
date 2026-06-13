# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
merupakan framework untuk mempermudah dan mempercepat proses membuat aplikasi web berbasis Java dengan mempermudah setelan dasar agar tidak dibuat secara manual
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
web yang membantu membuat template awal project spring boot secara instan, kita tinggal memilih versi Java dan dependency yang mau dipakai
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
karena maven menstandarisasi proses development secara otomatis dalam mengunduh, mengatur, dan merapikan semua dependency yang kita butuhkan di dalam project
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
untuk meningkatkan development aplikasi web karena menyediakan fitur rest api, dan otomatis memasang server internal di dalam project kita
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
karena di awal kita fokus belajar bikin alur data secara map terlebih dahulu sehingga datanya masih disimpan sementara di memori komputer dan belum ke database asli
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
sebagai file konfigurasi utama yang isinya informasi project dan dependency apa saja yang dipakai aplikasi.

```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
sebagai file utama yang punya fungsi main(), gunanya sebagai pemicu utama untuk menjalankan seluruh aplikasi spring boot

```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
sebagai penanda kelas utama aplikasi dan juga yang menjalankan auto configuration dan component scan
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
cari file utama, lalu klik ikon segitiga hijau (run)
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
masuk ke terminal, lalu ketik perintah `mvnw spring-boot:run`, pastikan sudah di folder utama kita
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Tidak selalu error, 404 itu aplikasinya sudah menyala dengan baik, tapi kita saja yang belum membuat halaman utama atau endpoint khusus di alamat '/' tersebut
```

### 12. Apa itu REST API?

Jawaban:

```text
cara komunikasi standar berbasis HTTP agar aplikasi frontend bisa minta atau kirim data ke backend (spring boot)
```

### 13. Apa itu endpoint?

Jawaban:

```text
alamat URL di server yang dapat diakses untuk meminta data ke server
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
request adalah permintaan yang dikirim oleh user ke server untuk meminta sesuai kebutuhanya

response adalah jawaban dari server setelah menerima request dari user
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
karena itu aturan best practice masing-masing bahasa. json mudah dibaca di javascript, sedangkan java memang standarnya pakai camelCase untuk penamaan variabel
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
sebagai penerjemah otomatis antara nama variabel camelCase di java (contoh: customerName) menjadi snake_case di json (contoh: customer_name) saat dikirim atau diterima
```

### 17. Apa tugas Controller?

Jawaban:

```text
tugasnya menerima request masuk lalu mengarahkan ke bagian yang sesuai requestnya
```

### 18. Apa tugas Service?

Jawaban:

```text
sebagai tempat untuk memproses logika bisnis, misalnya menghitung diskon atau melakukan cek terhadap password
```

### 19. Apa itu DTO?

Jawaban:

```text
wadah menaruh data yang dipakai untuk menukar informasi antar sistem biar rapi dan aman sesuai kebutuhan
```

### 20. Apa itu Model?

Jawaban:

```text
gambaran bentuk data yang akan disimpan di dalam database
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
supaya lebih aman dan fleksibel karena kita tidak ingin pengguna mengirim data sembarangan yang bisa langsung merusak struktur tabel database asli dan juga menghindari terjadinya manipulasi data
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
untuk menjaga keamanan data supaya kita bisa menyembunyikan data sensitif (seperti password atau pin) agar tidak ikut terkirim dan bocor ke layar user
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
supaya controller bisa berfokus dalam terima request dan memberikan response saja, dan urusan bisnis logic bisa di handle di service. hal ini untuk memudahkan dalam debugging, memaintenance code, maupun development kedepanya
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika prosesnya sukses dan data berhasil diambil atau dikirim tanpa ada masalah
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
ketika kita berhasil membuat atau memasukkan data baru ke dalam server
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
path parameter adalah bagian dari link URL yang dipakai buat menunjuk sebuah data spesifik, contohnya nomor ID di ujung link. sedangkan query parameter adalah teks tambahan di ujung link URL (biasanya diletakan setelah tanda "?") untuk memfilter data tertentu

```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
1. pengguna mengirim data JSON berisi nama, email, dan nomor HP ke URL /api/v1/customers
2. method createCustomer di CustomerControllerV1 menangkap data tersebut dan mengubahnya menjadi objek java CreateCustomerRequest
3. controller meneruskan data request tersebut ke metode customerService.createCustomer(request)
4. di dalam Service, sistem membuat data Customer baru dan otomatis memberikan nomor ID unik
5. data Customer baru tersebut disimpan ke dalam memori (customerStorage yang berbentuk HashMap)
6. angka sequence ditambah 1 (sequence++) agar siap dipakai untuk data customer berikutnya
7. service mengubah data Customer tadi menjadi CustomerResponse lewat fungsi pembantu (helper) toResponse
8. controller menerima data CustomerResponse dari Service, lalu mengirimkannya kembali ke pengguna berupa JSON dengan status 201 Created

```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
memahami alur pemisahan data antara model, dto, service, dan controller serta mengingat syntax untuk handle service ke map
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
merumuskan data dari map untuk bisa dimasukan kedalam proses createCustomer, getCostumerById, dan getCostumer
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
membuat sistem yang bener bener terhubung langsung ke database
```
