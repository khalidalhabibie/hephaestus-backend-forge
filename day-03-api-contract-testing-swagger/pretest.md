# Pretest - API Contract, API Testing & Swagger

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang API contract, API testing, dan Swagger.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - API Contract

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah kesepakatan formal antara client dan server tentang bagaimana sebuah API digunakan dan apa yang dijanjikan oleh API tersebut.
```

### 2. Kenapa API contract penting?

Jawaban:

```text
API contract penting karena menjadi acuan yang jelas tentang bagaimana dua sistem atau layanan saling berkomunikasi, termasuk format data, endpoint, dan aturan penggunaan, sehingga semua pihak memiliki pemahaman yang sama. Dengan adanya API contract, tim developer dapat bekerja lebih terstruktur dan paralel tanpa saling bergantung, sekaligus mengurangi kesalahan seperti perbedaan format data atau nama field. Selain itu, API contract juga membantu proses testing, memudahkan dokumentasi, serta menjaga konsistensi dan stabilitas sistem ketika terjadi perubahan atau pengembangan lebih lanjut.
```

### 3. Apa saja isi API contract?

Jawaban:

```text
API contract berisi informasi lengkap yang menjelaskan bagaimana sebuah API dapat digunakan, mulai dari alamat endpoint yang bisa diakses, metode HTTP yang digunakan, hingga format data yang dikirim dan diterima. Di dalamnya juga terdapat penjelasan mengenai struktur request dan response, termasuk field yang digunakan, tipe data, serta mana yang wajib atau opsional. Selain itu, API contract mencakup kode status yang menunjukkan hasil dari permintaan, mekanisme keamanan seperti authentication, serta bagaimana sistem menangani error. API contract juga biasanya menyertakan aturan tambahan seperti validasi data, batasan penggunaan, dan versi API, sehingga semua pihak yang menggunakan API memiliki panduan yang jelas dan konsisten.
```

### 4. Apa itu endpoint?

Jawaban:

```text
Endpoint adalah alamat atau URL tertentu dalam sebuah API yang digunakan untuk mengakses fungsi atau data tertentu.
```

### 5. Apa itu HTTP method?

Jawaban:

```text
HTTP method adalah jenis instruksi atau perintah yang digunakan oleh client untuk memberi tahu server apa yang ingin dilakukan terhadap suatu resource melalui API.
```

### 6. Apa itu request body?

Jawaban:

```text
Request body adalah bagian dari HTTP request yang berisi data yang dikirim oleh client ke server, biasanya digunakan saat ingin membuat atau memperbarui data.
```

### 7. Apa itu response body?

Jawaban:

```text
Response body adalah bagian dari HTTP response yang berisi data yang dikirim oleh server kembali ke client sebagai hasil dari request.
Response body biasanya berisi informasi yang diminta atau hasil dari suatu aksi, dan umumnya ditampilkan dalam format seperti JSON.
```

### 8. Apa itu HTTP status code?

Jawaban:

```text
HTTP status code adalah kode angka yang dikirim oleh server dalam response untuk menunjukkan hasil dari request yang dilakukan oleh client.
Kode ini membantu client memahami apakah permintaan berhasil, gagal, atau terjadi masalah tertentu. HTTP status code biasanya terdiri dari tiga digit dan memiliki arti berdasarkan kategorinya.
```

### 9. Kenapa request dan response perlu ditulis jelas?

Jawaban:

```text
Request dan response perlu ditulis dengan jelas agar semua pihak yang menggunakan API memiliki pemahaman yang sama tentang data yang dikirim dan diterima. Dengan definisi yang jelas, developer dapat menghindari kesalahan seperti format data yang tidak sesuai, field yang tidak lengkap, atau tipe data yang salah. Selain itu, kejelasan ini juga membantu proses pengembangan, testing, dan debugging menjadi lebih cepat dan terarah, serta memastikan komunikasi antara client dan server berjalan konsisten dan tanpa miskomunikasi.
```

### 10. Apa risiko jika API contract tidak jelas?

Jawaban:

```text
Jika API contract tidak jelas, ada beberapa risiko yang bisa terjadi dalam pengembangan dan penggunaan sistem.
Pertama, miskomunikasi antar tim dapat terjadi karena frontend dan backend memiliki pemahaman yang berbeda tentang data yang dikirim dan diterima, sehingga integrasi menjadi bermasalah. Kedua, bug atau error lebih sering muncul, misalnya karena perbedaan nama field, tipe data yang tidak sesuai, atau struktur response yang tidak konsisten.
Selain itu, proses development bisa menjadi lebih lambat karena tim harus sering melakukan klarifikasi atau memperbaiki kesalahan yang sebenarnya bisa dihindari. Risiko lain adalah testing menjadi sulit dan tidak konsisten, karena tidak ada acuan yang jelas untuk memvalidasi request dan response.
Terakhir, jika API terus dikembangkan tanpa contract yang jelas, maka perubahan yang dilakukan bisa merusak sistem yang sudah berjalan (breaking changes), sehingga berdampak pada user atau layanan lain yang bergantung pada API tersebut.
Secara keseluruhan, API contract yang tidak jelas dapat menyebabkan sistem menjadi tidak stabil, sulit dikembangkan, dan rawan kesalahan.
Provide your feedback on BizChat
```

## Section B - DTO

### 11. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek atau struktur data yang digunakan untuk membawa data antar layer dalam aplikasi, terutama antara client dan server.
DTO biasanya digunakan untuk merepresentasikan data yang akan dikirim melalui API, baik sebagai request maupun response, tanpa membawa logika bisnis. Dengan menggunakan DTO, data yang dikirim bisa lebih terstruktur, aman, dan sesuai dengan kebutuhan.
```

### 12. Apa itu request DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah objek atau struktur data yang digunakan untuk membawa data antar layer dalam aplikasi, terutama antara client dan server.
DTO biasanya digunakan untuk merepresentasikan data yang akan dikirim melalui API, baik sebagai request maupun response, tanpa membawa logika bisnis. Dengan menggunakan DTO, data yang dikirim bisa lebih terstruktur, aman, dan sesuai dengan kebutuhan.
```

### 13. Apa itu response DTO?

Jawaban:

```text
Response DTO adalah Data Transfer Object yang digunakan khusus untuk membentuk dan mengirim data dari server ke client sebagai hasil dari request.
Response DTO berfungsi untuk menentukan data apa saja yang akan dikembalikan ke client dan bagaimana strukturnya, sehingga tidak semua data dari database atau internal sistem langsung diekspos.
```

### 14. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
DTO dan model sebaiknya dipisah karena keduanya memiliki tujuan yang berbeda dalam aplikasi.
Model biasanya merepresentasikan struktur data di dalam database atau domain bisnis, sehingga sering kali mengandung banyak field dan kadang memiliki logika tertentu. Sementara itu, DTO digunakan untuk mengirim data ke luar (ke client) atau menerima data dari client, sehingga hanya berisi data yang benar-benar diperlukan.
```

### 15. Kenapa JSON biasanya menggunakan snake_case sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena masing-masing mengikuti standar konvensi yang berbeda, dan perbedaan ini dijembatani dengan tools atau library agar tetap bisa saling terintegrasi dengan baik.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
@JsonProperty adalah anotasi dalam Java (biasanya dari library Jackson) yang digunakan untuk mengatur nama field JSON saat proses konversi antara object Java dan JSON.
Fungsi utamanya adalah untuk mapping nama field yang berbeda antara Java dan JSON.
```

## Section C - HTTP Method

### 17. Apa fungsi POST?

Jawaban:

```text
Fungsi POST adalah untuk mengirim data dari client ke server dengan tujuan membuat resource baru.
Biasanya, POST digunakan ketika kita ingin menambahkan data ke sistem, seperti membuat user baru, membuat pesanan, atau mengirim form.
```

### 18. Apa fungsi GET?

Jawaban:

```text
Fungsi GET adalah untuk mengambil atau membaca data dari server tanpa mengubah data tersebut.
GET digunakan ketika client ingin melihat atau mengambil informasi, seperti daftar data atau detail suatu resource.
```

### 19. Apa fungsi PUT?

Jawaban:

```text
Fungsi PUT adalah untuk memperbarui atau mengganti data yang sudah ada di server secara keseluruhan.
PUT biasanya digunakan ketika client ingin mengupdate sebuah resource dengan data baru, dan data lama akan digantikan sepenuhnya oleh data yang dikirim.
```

### 20. Apa fungsi PATCH?

Jawaban:

```text
Fungsi PATCH adalah untuk memperbarui sebagian data yang sudah ada di server tanpa harus mengganti seluruh data.
Berbeda dengan PUT yang mengganti seluruh isi resource, PATCH hanya mengubah field tertentu saja sesuai yang dikirim di request.
```

### 21. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
Perbedaan utama antara PUT dan PATCH terletak pada cara mereka memperbarui data.
PUT digunakan untuk mengganti seluruh data pada sebuah resource. Artinya, semua field harus dikirim ulang, dan data lama akan digantikan sepenuhnya oleh data baru. Jika ada field yang tidak dikirim, biasanya akan dianggap kosong atau hilang.
Sementara itu, PATCH digunakan untuk memperbarui sebagian data saja. Hanya field yang ingin diubah yang perlu dikirim, dan field lain yang tidak disebutkan akan tetap seperti sebelumnya.
```

### 22. Kapan menggunakan 201 Created?

Jawaban:

```text
201 Created digunakan ketika request berhasil membuat resource baru di server.
Status ini biasanya digunakan pada request dengan method POST (atau kadang PUT jika membuat resource baru), untuk menunjukkan bahwa data yang dikirim sudah berhasil diproses dan disimpan.
```

### 23. Kapan menggunakan 200 OK?

Jawaban:

```text
200 OK digunakan ketika request berhasil diproses dengan sukses dan server mengembalikan hasil yang diminta.
```

### 24. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
400 Bad Request digunakan ketika request dari client tidak valid atau tidak sesuai dengan aturan yang ditentukan oleh API.
```

### 25. Kapan menggunakan 404 Not Found?

Jawaban:

```text
04 Not Found digunakan ketika resource atau data yang diminta oleh client tidak ditemukan di server.
Artinya, server sudah menerima request dengan benar, tetapi data yang diminta memang tidak ada atau tidak tersedia.
```

## Section D - API Testing

### 26. Apa itu API testing?

Jawaban:

```text
API testing adalah proses untuk menguji apakah sebuah API bekerja dengan benar sesuai dengan API contract yang sudah ditentukan.
```

### 27. Kenapa API perlu dites?

Jawaban:

```text
API perlu dites agar dapat memastikan bahwa sistem bekerja dengan benar, stabil, dan sesuai dengan API contract yang telah ditentukan.
```

### 28. Tool apa yang biasa digunakan untuk API testing?

Jawaban:

```text
Postman, Swagger, cURL, dll
```

### 29. Apa yang perlu dicek saat melakukan API testing?

Jawaban:

```text
Saat melakukan API testing, kita perlu memastikan bahwa endpoint dan HTTP method yang digunakan sudah benar, request yang dikirim sesuai dengan format dan aturan yang ditentukan, serta response yang diterima memiliki struktur, isi, dan tipe data yang sesuai dengan API contract. Selain itu, kita juga perlu mengecek apakah HTTP status code yang diberikan sudah tepat, memastikan data yang dikembalikan akurat, menguji bagaimana API menangani error, serta memastikan performa dan keamanan API berjalan dengan baik agar API dapat digunakan secara stabil dan andal.
```

### 30. Apa itu expected response?

Jawaban:

```text
Expected response adalah hasil atau output yang diharapkan ketika sebuah API dipanggil berdasarkan API contract yang telah ditentukan.
```

## Section E - Swagger

### 31. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools yang digunakan untuk mendokumentasikan, melihat, dan mencoba API secara interaktif.
```

### 32. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah standar atau spesifikasi yang digunakan untuk mendefinisikan dan mendokumentasikan API secara terstruktur dan mudah dibaca oleh manusia maupun mesin.
```

### 33. Apa manfaat Swagger UI?

Jawaban:

```text
Swagger UI memiliki manfaat utama untuk menampilkan dokumentasi API secara interaktif dan memudahkan pengguna dalam memahami serta mencoba API.
```

### 34. Apa bedanya Postman dan Swagger UI?

Jawaban:

```text
Perbedaan antara Postman dan Swagger UI terletak pada fungsi utama dan cara penggunaannya dalam API development.
Postman adalah tool yang digunakan untuk melakukan API testing secara manual maupun automated. Dengan Postman, developer bisa membuat berbagai request (GET, POST, PUT, dll), mengatur header, body, dan authentication, lalu memeriksa response secara detail. Postman juga mendukung collection, environment, dan automation testing, sehingga lebih cocok digunakan untuk pengujian API yang lebih kompleks dan fleksibel.
Sementara itu, Swagger UI adalah tool yang digunakan untuk menampilkan dokumentasi API secara interaktif berdasarkan OpenAPI specification. Di Swagger UI, developer bisa melihat daftar endpoint, struktur request/response, dan mencoba API langsung dari browser, tetapi fitur testing-nya tidak selengkap Postman. Swagger UI lebih fokus pada dokumentasi dan eksplorasi API.
```

### 35. Menurut kamu, apakah Swagger bisa menggantikan dokumentasi API manual? Jelaskan.

Jawaban:

```text
Menurut saya, Swagger tidak sepenuhnya bisa menggantikan dokumentasi API manual, tetapi bisa sangat mengurangi kebutuhan dokumentasi manual dan bahkan menjadi sumber utama dokumentasi jika digunakan dengan baik.
Swagger (melalui OpenAPI dan Swagger UI) dapat secara otomatis menampilkan informasi penting seperti endpoint, request, response, dan status code dengan format yang konsisten dan interaktif. Hal ini membuat dokumentasi menjadi lebih mudah dipahami, selalu up-to-date (karena terhubung langsung dengan spesifikasi), dan bisa langsung digunakan untuk testing. Dalam banyak kasus, ini sudah cukup untuk kebutuhan teknis developer.
Namun, dokumentasi manual masih diperlukan untuk melengkapi hal-hal yang tidak tercakup sepenuhnya oleh Swagger, seperti penjelasan bisnis (business logic), contoh use case, alur penggunaan API, best practice, atau konteks penggunaan yang lebih luas. Swagger lebih fokus pada aspek teknis, bukan narasi atau penjelasan mendalam.
```

## Self Assessment

| Area         | Score 1-5 |
| ------------ | --------- |
| API contract |     2     |
| DTO          |     3     |
| HTTP method  |     3     |
| API testing  |     3     |
| Swagger UI   |     2     |
| OpenAPI      |     2     |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
