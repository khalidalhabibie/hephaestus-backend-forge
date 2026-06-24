# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Testing disebut sebagai risk reduction karena proses testing membantu menemukan dan mengurangi kemungkinan terjadinya kesalahan atau kegagalan pada sistem sebelum sistem tersebut digunakan oleh pengguna.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code adalah kode program yang dapat berjalan dan menghasilkan output sesuai fungsi dasar yang diharapkan, tetapi belum tentu telah melalui pengujian menyeluruh.
Sementara itu, trusted code adalah kode yang tidak hanya berjalan dengan benar, tetapi juga telah diuji secara mendalam dan terbukti andal, sehingga dapat dipercaya untuk digunakan dalam berbagai kondisi tanpa menimbulkan kesalahan yang signifikan.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Pola Given-When-Then adalah cara penulisan skenario pengujian yang digunakan untuk menjelaskan perilaku sistem secara jelas dan terstruktur, dimana Given menjelaskan kondisi awal atau konteks, When menyatakan aksi yang dilakukan, dan Then menunjukkan hasil atau output yang diharapkan dari aksi tersebut.
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Service layer cocok untuk unit test karena berisi logika bisnis yang terpisah dari detail implementasi seperti database atau antarmuka pengguna, sehingga lebih mudah diuji secara terisolasi tanpa bergantung pada komponen lain.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 berperan sebagai framework untuk menjalankan dan mengelola pengujian unit, termasuk menyediakan anotasi dan struktur untuk menulis test case, sedangkan Mockito digunakan untuk membuat objek tiruan (mock) sehingga dependensi eksternal dapat disimulasikan dan pengujian dapat dilakukan secara terisolasi.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
Pertama, menguji pengajuan pinjaman dengan data valid untuk memastikan bahwa aplikasi pinjaman berhasil diproses dan statusnya sesuai dengan yang diharapkan.
Kedua, menguji pengajuan pinjaman dengan data tidak valid (misalnya jumlah pinjaman negatif atau data pemohon tidak lengkap) untuk memastikan sistem menolak pengajuan tersebut dan memberikan respon error yang tepat.
Ketiga, menguji logika persetujuan atau penolakan pinjaman berdasarkan kriteria tertentu (seperti skor kredit atau batas maksimal pinjaman) untuk memastikan keputusan yang dihasilkan sesuai dengan aturan bisnis yang telah ditetapkan.
```

7. Apa tujuan peer code review?

Jawaban:

```text
Tujuan peer code review adalah untuk meningkatkan kualitas kode dengan cara mendeteksi kesalahan, memastikan kesesuaian dengan standar coding, serta berbagi pengetahuan antar anggota tim sehingga kode menjadi lebih mudah dipahami dan dipelihara.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah metode pencatatan log dalam format terstruktur (seperti JSON) yang memuat informasi dalam bentuk key-value sehingga mudah dibaca, dicari, dan dianalisis oleh sistem maupun manusia.
Structured logging penting karena memudahkan proses debugging, monitoring, dan analisis sistem secara lebih cepat dan akurat, terutama dalam aplikasi berskala besar atau terdistribusi.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Fungsi correlation_id pada log dan error response adalah untuk mengidentifikasi dan melacak satu permintaan (request) secara unik di seluruh sistem, terutama ketika melibatkan banyak layanan atau komponen.
Dengan adanya correlation_id, developer dapat menelusuri alur proses dari awal hingga akhir dengan lebih mudah saat melakukan debugging atau analisis masalah, karena semua log dan error yang terkait dengan satu request akan memiliki identifier yang sama.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Minimal lima data yang tidak boleh ditulis mentah di log adalah data pribadi sensitif seperti kata sandi (password), nomor kartu kredit atau informasi pembayaran, nomor identitas (seperti KTP atau paspor), data kesehatan, serta token autentikasi atau API key karena data-data tersebut dapat disalahgunakan jika terekspos.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Saya memahami bahwa testing berperan penting sebagai risk reduction karena membantu menemukan kesalahan lebih awal sebelum sistem digunakan.
2. Saya memahami perbedaan antara working code dan trusted code, dimana trusted code sudah melalui pengujian dan lebih dapat diandalkan.
3. Saya memahami pentingnya praktik seperti unit testing, structured logging, dan penggunaan correlation_id untuk meningkatkan kualitas, keandalan, dan kemudahan debugging sistem.
```

Apa 2 hal yang masih membingungkan?

```text
1. Saya masih merasa membingungkan dalam menentukan batas antara unit test dan integration test dalam praktik nyata.
2. Saya masih belum sepenuhnya memahami bagaimana merancang test yang benar-benar mencakup semua edge case secara efektif.
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Saya akan mengecek apakah logika bisnis yang ditulis sudah benar dan sesuai dengan requirement serta mudah dipahami oleh developer lain.
```
