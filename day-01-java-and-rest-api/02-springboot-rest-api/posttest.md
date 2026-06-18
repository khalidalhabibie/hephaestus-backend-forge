# Posttest - Spring Boot REST API

Jawab pertanyaan berikut setelah membaca materi dan membuat Customer REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
Framework java dalam membuat dan mengembangkan aplikasi backend.
```

### 2. Apa fungsi Spring Initializr?

Jawaban:

```text
Berfungsi untuk membuat menginisiasi projek springboot, baik itu ; memilih builder yang ingin digunakan, versi java, versi springboot, penamaan project, maupun dependencynya.
```

### 3. Saat membuat project, kenapa memilih Maven?

Jawaban:

```text
Karena strukturnya yang standar dan kaku sehingga mudah dipahami secara instan oleh developer tanpa konfigurasi yang rumut.
```

### 4. Apa fungsi dependency Spring Web?

Jawaban:

```text
Untuk membuat dan mengembangkan aplikasi backend, kususnya pada web. Dependency ini memuat ; Staterkit dalam pembuatan REST API.
```

### 5. Kenapa Day 1 belum menambahkan dependency database?

Jawaban:

```text
karena database yang digunakaan sebelumnya hanya disimpan di map/ram, selain itu kita perlu memahami konsep fundamental terlebih dahulu baik itu tentang java dan springboot, agar nantinya ketika sudah terbiasa baru naik level untuk menambahkan dependency database.
```

### 6. Apa fungsi file pom.xml?

Jawaban:

```text
Berfungsi sebagai dependency injecton ketika kita memerlukan dependency tambahan yang tidak kita masukan pada awal pembuatan project springboot di Spring initialzer.
```

### 7. Apa fungsi TrainingApplication.java?

Jawaban:

```text
Sebagai main/root yang akan dijalankan oleh springboot, class ini yang akan mmendeteksi dan menjalankan semua code yang kita buat di folder lain.
```

### 8. Apa fungsi @SpringBootApplication?

Jawaban:

```text
Anotasi yang menandakan bahwa code yang ada dibawahnya adalah root/main untuk springboot menjalankan project.
```

### 9. Bagaimana cara menjalankan Spring Boot dari IDE?

Jawaban:

```text
bisa langsung klik run / menggunakan command mvn spring-boot:run
```

### 10. Bagaimana cara menjalankan Spring Boot dari terminal?

Jawaban:

```text
menggunakan command mvn spring-boot:run
```

### 11. Jika membuka http://localhost:8080 dan hasilnya 404, apakah selalu error? Jelaskan.

Jawaban:

```text
Karena tidak ada service / controller yang terceteksi pada root server. tidak akan selalu eror, jika kita sudah menyiapkan endpointnya/APInya, yang di return pasti response controllernya.
```

### 12. Apa itu REST API?

Jawaban:

```text
API yang menjalankan event berdasarkan request dan response dari sisi client dan server, REST sendiri adalah standar dalam penulisan API.
```

### 13. Apa itu endpoint?

Jawaban:

```text
Alamat / url API spesifik untuk mengakses fungsi atau data tertentu
```

### 14. Apa perbedaan request dan response?

Jawaban:

```text
Request adalah kegiatan yang dilakukan/diberikan oleh client kepada server, sedangkan Response adalah balikan yang diberikan oleh server ke client.
```

### 15. Kenapa JSON menggunakan snake_case, sedangkan Java menggunakan camelCase?

Jawaban:

```text
Karena keduanya memiliki standar berbeda yang sudah disepakati untuk diikuti, sehingga ketika dalam mengembangkan aplikasi bisa scallable dan teratur.
```

### 16. Apa fungsi @JsonProperty?

Jawaban:

```text
untuk mapping hasil json yang akan menjadi requestbody atau response body, biasanya untuk convert camelCase java menjadi snake_case json.
```

### 17. Apa tugas Controller?

Jawaban:

```text
menjadi jembatan/pintu antara client dan server dalam memberikan request maupun menerima response yang akan di olah oleh bisnis logic yang ada di service dan akan diteruskan ke presistance layer/databse.  
```

### 18. Apa tugas Service?

Jawaban:

```text
Menjalankan business logic dari data yang diterima / dikirimkan oleh controller.
```

### 19. Apa itu DTO?

Jawaban:

```text
Data transfer objek, berfungsi sebagai mapping requestbody/responsebody agar yang diakses/dikirimkan ke client tidak class aslinya namun dtonya saja. agar lebih aman.
```

### 20. Apa itu Model?

Jawaban:

```text
Representasi model database/entity yang ada dalam code.
```

### 21. Kenapa request body tidak langsung menggunakan model?

Jawaban:

```text
Agar client tidak mengakses langsung model dan menjaga keamanan data ketika terjadi hal yang tidak diinginkan atau bugs.
```

### 22. Kenapa response tidak langsung menggunakan model?

Jawaban:

```text
Menjaga agar data yang penting tidak bocor ke client, misal password tidak perlu di return namun hanya Responsebody yang sudah disiapkan saja. 
```

### 23. Kenapa business logic tidak boleh ditaruh di Controller?

Jawaban:

```text
Menjaaga arsitektur agar lebih jelas dan clean sehingga code lebih scallable dan mudah dibaca oleh developer lainnya, sehingga ketika mengembangkan sistem lagi tidak bingung.
```

### 24. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika service berhasil/sukses dieksekusi.
```

### 25. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika data baru tertambahkan ke dalam database.
```

### 26. Apa bedanya path parameter dan query parameter?

Jawaban:

```text
path paramater berfungsi untuk menunjuk suatu data secara spesifik berdasarkan pathnya, biasanya ditandai dengan id dibelakang apinya "customers/1" -> cari customer dengan id 1

Query Parameer berfungsi untuk mencari data yang memiliki character yang mirip dengan parameter yang diberikan, biasanyai ditandai dengan ? key&value yang ada di belakang api
"customer/name?Steven" -> cari customer dengan nama yang mengandung steven
```

### 27. Jelaskan flow POST /api/v1/customers dari request sampai response.

Jawaban:

```text
- Client mengirimkan data yang sesuai dengan RequestBody yang sudah disiapkan 
- Data diterima oleh controller dan disimpan dalam RequestBody request
- data dimasukan ke database ram/map -> Service.createCustomer
    - service menerima data dari controller
    - validasi apakah name null? jika null trow illegalarg exception
    - mapping clean username,email,phonenumber dengan -> Trim(), toLowerCase()
    - membuat instanece objek pada model cusomer dengan data yg udah di cleaning
    - masukan ke database ram
    - insert ke CustomerRespionse
    - return response sesuai CustomerResponse

- selain di simpan di datbase ram, data juga di masukan ke variabel response yang berbentuk CustomerResponse -> ini untuk dto response
- setelah itu response kemabali diberikan ke client dengan status 201 ok, message : customer created, dan payload Customer response

```

### 28. Bagian mana yang paling sulit saat membuat Spring Boot REST API?

Jawaban:

```text
Membiasakan diri dengan anotasi yang ada di springboot, sintaksis for, query param yg masih belom ngerti. handling eror karena kadang panik saat eror.
```

### 29. Error apa yang kamu temui dan bagaimana cara menyelesaikannya?

Jawaban:

```text
Saat membuat query param bingung sintaksnya, solve pakai bantuan ai untuk membiming stepbystep agar paham.
```

### 30. Apa yang ingin kamu pelajari berikutnya?

Jawaban:

```text
Membuat backend app dengan database dan juga migration yang bisa digunakan dalam production dan bisnis yg nyata.
```
