# Posttest - Validation & Error Handling

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Validation & Error Handling.

### 1. Apa itu validasi request?

Jawaban:

```text
proses periksa data yang dikirim user untuk memastikan data tersebut sudah sesuai dengan aturan yang ditentukan sebelum diproses lebih lanjut oleh sistem
```

### 2. Kenapa backend tetap perlu validasi walaupun frontend sudah validasi?

Jawaban:

```text
karena validasi di frontend bisa dibypass dengan mudah oleh user dengan mengirim request langsung ke backend menggunakan postman tanpa lewat frontend, maka dari itu perlu divalidasi kembali oleh backend
```

### 3. Apa fungsi @Valid?

Jawaban:

```text
untuk memeriksa aturan validasi yang ada pada objek data sebelum diproses sama controller
```

### 4. Apa fungsi @NotBlank?

Jawaban:

```text
untuk memastikan bahwa sebuah data (biasanya teks) tidak boleh kosong, tidak boleh null, dan tidak boleh hanya berisi spasi
```

### 5. Apa perbedaan @NotBlank dan @NotNull?

Jawaban:

```text
@NotNull hanya memastikan data tidak bernilai null tapi teks kosong seperti "" atau " " masih dianggap sah

@NotBlank memastikan data tidak null, tidak kosong, dan tidak hanya berisi spasi
```

### 6. Apa fungsi @Email?

Jawaban:

```text
untuk memastikan bahwa input email yang dimasukkan user memiliki format alamat email yang sesuai, seperti tanda @
```

### 7. Apa fungsi @Size?

Jawaban:

```text
untuk membatasi jumlah karakter teks atau ukuran elemen tidak kurang dari batas minimal atau tidak lebih dari batas maksimal yang ditentukan
```

### 8. Apa yang terjadi jika request gagal validasi?

Jawaban:

```text
spring boot akan menolak request tersebut lalu menghentikan proses program selanjutnya dan otomatis melemparkan error
```

### 9. Apa itu MethodArgumentNotValidException?

Jawaban:

```text
jenis error khusus yang dilemparkan ketika data yang dikirim ke controller gagal melewati pemeriksaan validasi dari anotasi @Valid
```

### 10. Apa itu standard error response?

Jawaban:

```text
format message error yang dibuat seragam dan rapi di seluruh program agar user atau aplikasi lain yang terhubung mudah memahami isi pesan error tersebut
```

### 11. Kenapa error response perlu konsisten?

Jawaban:

```text
supaya frontend atau aplikasi lain yang terhubung dan membaca backend kita bisa mengerti pesan error dengan mudah tanpa harus membuat kode handling yang berbeda-beda untuk setiap error
```

### 12. Apa itu field-level error?

Jawaban:

```text
pesan error yang menjelaskan secara spesifik bagian data mana yang salah beserta alasan kesalahannya, misalnya pesan bahwa data "email" tidak boleh kosong
```

### 13. Apa itu custom exception?

Jawaban:

```text
error buatan kita sendiri yang dinamai secara khusus sesuai dengan jenis masalah tertentu yang terjadi di dalam aplikasi kita
```

### 14. Kenapa CustomerNotFoundException lebih baik daripada RuntimeException biasa?

Jawaban:

```text
karena namanya lebih spesifik menggambarkan masalah yang terjadi, sehingga kita bisa menangani error tersebut dengan pesan dan tindakan yang lebih tepat
```

### 15. Apa fungsi @ControllerAdvice?

Jawaban:

```text
sebagai komponen utama yang bertugas mengumpulkan dan menangani semua error yang terjadi di seluruh bagian controller di aplikasi kita
```

### 16. Apa fungsi @ExceptionHandler?

Jawaban:

```text
anotasi yang diset di atas sebuah method untuk menandakan bahwa method tersebut bertugas menangani jenis error tertentu secara otomatis saat error itu muncul
```

### 17. Kenapa error handling sebaiknya centralized?

Jawaban:

```text
agar code program menjadi lebih rapi dan tidak terjadi penumpukan kode error di setiap controller dan juga semua penanganan kesalahan diatur di satu tempat yang sama
```

### 18. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
saat user mengirimkan data request yang tidak sesuai format atau tidak lengkap sesuai aturan validasi yang sudah kita buat
```

### 19. Kapan menggunakan 404 Not Found?

Jawaban:

```text
saat data yang dicari oleh user tidak ditemukan di dalam database atau bisa juga alamat url yang dituju memang tidak ada di sistem
```

### 20. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
saat terjadi kesalahan atau kerusakan mendadak dari dalam sisi server
```

### 21. Kenapa stack trace tidak boleh dikirim ke client?

Jawaban:

```text
karena stack trace menampilkan alur baris code program secara detail yang bisa aja disalahgunakan oleh pihak yang tidak bertanggung jawab untuk mencari celah keamanan aplikasi kita
```

### 22. Jelaskan flow ketika POST /api/v1/customers menerima email invalid.

Jawaban:

```text
1. data dikirim ke sistem
2. anotasi @Valid mendeteksi format email tidak sesuai aturan @Email
3. spring boot melemparkan MethodArgumentNotValidException
4. error ditangkap oleh @ControllerAdvice
5. sistem mengubah error menjadi response status 400 Bad Request dengan pesan kesalahan sesuai untuk dikirim balik ke client
```

### 23. Jelaskan flow ketika GET /api/v1/customers/999 tidak menemukan data.

Jawaban:

```text
1. request masuk mencari customer id 999
2. aplikasi mencari ke database
3. data tidak ditemukan
4. aplikasi sengaja melemparkan CustomerNotFoundException
5. error ditangkap oleh @ControllerAdvice
6. sistem mengubahnya menjadi response status 404 Not Found untuk dikirim balik ke client
```

### 24. Apa perbedaan validation error, business error, dan system error?

Jawaban:

```text
validation error adalah kesalahan format input data dari user

business error adalah pelanggaran aturan jalannya logika bisnis di aplikasi

system error adalah kerusakan teknis dari dalam server atau database yang mati

```

### 25. Bagian mana yang paling sulit dari exercise Day 2?

Jawaban:

```text
menyusun globalexceptionhandler
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. memahami validasi data untuk meningkatkan keamanan backend
2. Cara mengelompokkan penanganan error di satu tempat terpusat menggunakan @ControllerAdvice.
3. Cara membedakan kode status HTTP seperti 400, 404, dan 500 sesuai dengan jenis masalah yang terjadi
```

Apa 2 hal yang masih membingungkan?

```text
1. cara mengambil pesan error bawaan dari anotasi validasi agar bisa dimasukkan ke dalam objek response buatan kita sendiri
2. menentukan kapan sebuah masalah harus dianggap sebagai business error atau langsung dilempar sebagai validation error
```

Apa 1 pertanyaan untuk mentor?

```text
apakah untuk globalexceptionhandling itu template untuk semua program kah atau kapan kita harus pesifik terhadap errornya
```
