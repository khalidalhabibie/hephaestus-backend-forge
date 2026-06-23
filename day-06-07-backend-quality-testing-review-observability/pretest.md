# Pretest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Total pertanyaan: 15.
- Estimasi waktu: 15-20 menit.

1. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Tulis jawaban di sini.
Working code: Kode yang sekadar berjalan dan menghasilkan output benar pada skenario normal saat dicoba oleh developer.
Trusted code: Kode yang keandalannya terbukti karena memiliki automasi pengujian (automated tests), siap menghadapi edge cases, aman dari regresi, dan dipahami dengan baik oleh tim.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Tulis jawaban di sini.
Karena testing mendeteksi bug dan cacat logika lebih awal di lingkungan development, sehingga mengurangi risiko kegagalan sistem di production yang dapat berdampak pada kerugian finansial, keamanan data, atau runtuhnya kepercayaan pengguna.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Tulis jawaban di sini.
Struktur penulisan test case berbasis Behavior-Driven Development (BDD):
Given: Kondisi awal atau persiapan konteks/data dummy.
When: Aksi atau eksekusi fungsi yang sedang diuji.
Then: Verifikasi atau assertion terhadap hasil akhir yang diharapkan.
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Tulis jawaban di sini.
Unit Test: Menguji satu komponen terkecil (seperti satu fungsi/metode) secara terisolasi dengan mengisolasi/mocking ketergantungan luar. Eksekusinya sangat cepat.
Integration Test: Menguji bagaimana beberapa komponen bekerja sama (misal: Service terhubung ke Database nyata atau API pihak ketiga). Eksekusinya lebih lambat.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Tulis jawaban di sini.
Karena service layer adalah tempat berkumpulnya inti bisnis logika (business logic) dan aturan validasi aplikasi, yang tidak seharusnya bergantung pada bagaimana data disimpan (Database) atau bagaimana data diterima (Controller).
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai testing framework standar di ekosistem Java untuk menstrukturkan, mengelompokkan, memicu eksekusi test case, serta menyediakan fitur assertions (seperti assertEquals, assertThrows).
```

7. Apa fungsi Mockito?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai mocking framework untuk membuat objek tiruan (mock) dari dependency nyata, sehingga kita bisa mengatur perilaku tiruan tersebut (when().thenReturn()) demi mengisolasi unit yang sedang diuji.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Tulis jawaban di sini.
Agar pengujian tidak menyentuh database nyata. Hal ini mencegah efek samping (seperti manipulasi data asli), menghindari ketergantungan jaringan yang membuat test lambat, dan memastikan test fokus 100% pada logika kode di Service.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Tulis jawaban di sini.
Skenario Positif: Pengajuan pinjaman berhasil disetujui jika skor kredit kustomer memenuhi syarat.
Skenario Negatif: Pengajuan ditolak otomatis jika kustomer memiliki outstanding loan yang melebihi batas limit.
Skenario Validasi: Melempar exception jika nominal pinjaman yang dimasukkan bernilai minus atau nol.
```

10. Apa tujuan peer code review?

Jawaban:

```text
Tulis jawaban di sini.
Menjaga standar kualitas kode, mendeteksi bug atau celah keamanan yang terlewat oleh penulis kode, serta sarana berbagi pengetahuan (knowledge sharing) antar anggota tim.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Tulis jawaban di sini.
Kebenaran logika bisnis, celah keamanan (seperti SQL injection atau bocornya PII), performa kode (seperti potensi N+1 query problem), keterbacaan kode (clean code), cakupan testing (test coverage), dan penanganan error (exception handling).
```

12. Apa itu structured logging?

Jawaban:

```text
Tulis jawaban di sini.
Praktik menulis log aplikasi ke dalam format yang terstruktur dan konsisten (biasanya JSON) bukan teks biasa (plain text), agar log tersebut mudah diparsing, diindeks, dan dianalisis oleh log management tools seperti ELK Stack atau Grafana Loki.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Tulis jawaban di sini.
Sebuah string unik yang dilekatkan pada satu request sejak awal masuk sistem untuk melacak alur request tersebut saat melewati berbagai fungsi, service, atau microservices. Berguna mempermudah debugging log yang saling terpisah.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Tulis jawaban di sini.
INFO: Informasi jalannya aplikasi secara normal (misal: "Aplikasi berhasil startup", "Transaksi berhasil diproses").
WARN: Terjadi sesuatu yang tidak biasa atau potensi masalah, namun aplikasi masih berjalan (misal: "Koneksi lambat", "Percobaan login gagal").
ERROR: Terjadi kegagalan sistem yang membutuhkan perhatian serius segera (misal: "Database down", "NullPointerException saat memproses pembayaran").
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Tulis jawaban di sini.
Data Pribadi/PII (Personally Identifiable Information) dan data kredensial, seperti: Nomor NIK/KTP, nomor kartu kredit/CVV, kata sandi (password), alamat email pribadi, nomor telepon kustomer, dan access token.
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |5|
| Given-When-Then |5|
| JUnit 5 |5|
| Mockito |5|
| Service layer testing |5|
| Peer code review |5|
| Structured logging |5|
| Correlation ID |5|
| PII safety |5|
