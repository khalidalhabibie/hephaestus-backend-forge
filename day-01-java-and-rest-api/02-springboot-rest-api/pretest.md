# Pretest - Spring Boot REST API

Jawab pertanyaan berikut dengan bahasa sendiri sebelum membaca materi Spring Boot REST API.

### 1. Apa itu Spring Boot?

Jawaban:

```text
merupakan framework untuk mempermudah dan mempercepat proses membuat aplikasi web berbasis Java dengan mempermudah setelan dasara agar tidak dibuat secara manual
```

### 2. Apa itu REST API?

Jawaban:

```text
merupakan cara komunikasi agar web dan server bisa saling bertukar data
```

### 3. Apa itu HTTP?

Jawaban:

```text
aturan standar yang digunakan komputer untuk proses kirim dan terima data ke server di internet
```

### 4. Apa itu JSON?

Jawaban:

```text
format tulisan untuk bisa mengirim, menerima, dan menyimpan data antara server dan aplikasi web
```

### 5. Apa itu endpoint?

Jawaban:

```text
alamat URL di server yang dapat diakses untuk meminta data ke server
```

### 6. Apa itu request?

Jawaban:

```text
permintaan yang dikirim oleh user ke server untuk meminta sesuai kebutuhanya
```

### 7. Apa itu response?

Jawaban:

```text
jawaban dari server setelah menerima request dari user
```

### 8. Apa fungsi GET?

Jawaban:

```text
untuk mengambil atau melihat data dari server
```

### 9. Apa fungsi POST?

Jawaban:

```text
untuk mengirim atau menambahkan data baru ke server
```

### 10. Apa fungsi PUT?

Jawaban:

```text
untuk mengubah atau memperbarui data lama yang sudah ada di server secara keseluruhan
```

### 11. Apa fungsi DELETE?

Jawaban:

```text
untuk menghapus data yang ada di server
```

### 12. Kapan menggunakan 200 OK?

Jawaban:

```text
ketika prosesnya sukses dan data berhasil diambil atau dikirim tanpa ada masalah
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
ketika kita berhasil membuat atau memasukkan data baru ke dalam server
```

### 14. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
ketika server tidak bisa proses request karena data yang dikirim user salah (salah dalam mengetik, tidak lengkap, atau tidak sesuai aturan)
```

### 15. Kapan menggunakan 404 Not Found?

Jawaban:

```text
ketika halaman atau data yang dicari user memang tidak ada di server
```

### 16. Kapan menggunakan 500 Internal Server Error?

Jawaban:

```text
ketika sistem atau code di dalam servernya sedang error padahal user tidak melakukan kesalahan
```

### 17. Apa itu path parameter?

Jawaban:

```text
bagian dari link URL yang dipakai buat menunjuk sebuah data spesifik, contohnya nomor ID di ujung link
```

### 18. Apa itu query parameter?

Jawaban:

```text
teks tambahan di ujung link URL (biasanya diletakan setelah tanda "?") untuk memfilter data tertentu
```

### 19. Apa itu request body?

Jawaban:

```text
berisi data lengkap yang biasanya memiliki format JSON yang kita berikan ketika mengirim request ke server
```

### 20. Apa tugas Controller?

Jawaban:

```text
tugasnya menerima request masuk lalu mengarahkan ke bagian yang sesuai requestnya
```

### 21. Apa tugas Service?

Jawaban:

```text
sebagai tempat untuk memproses logika bisnis, misalnya menghitung diskon atau melakukan cek terhadap password
```

### 22. Apa itu DTO?

Jawaban:

```text
wadah menaruh data yang dipakai untuk menukar informasi antar sistem biar rapi dan aman sesuai kebutuhan
```

### 23. Apa itu Model?

Jawaban:

```text
gambaran bentuk data yang akan disimpan di dalam database
```

### 24. Kenapa business logic tidak ditaruh di Controller?

Jawaban:

```text
supaya controller bisa berfokus dalam terima request dan memberikan response saja, dan urusan bisnis logic bisa di handle di service. hal ini untuk memudahkan dalam debugging, memaintenance code, maupun development kedepanya
```

### 25. Kenapa request/response perlu DTO?

Jawaban:

```text
supaya kita bisa memilih data apa saja yang boleh diperlihatkan ke luar jadi data rahasia di database tidak ikut terekspose ke luar
```
