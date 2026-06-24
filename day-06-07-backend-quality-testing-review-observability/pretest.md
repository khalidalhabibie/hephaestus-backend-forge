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
Working code adalah kode yang sudah bisa dijalankan tanpa error, sedangkan trusted code adalah kode yang tidak hanya berjalan, tetapi juga sudah teruji dan dapat dipercaya kualitas serta keamanannya.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Testing disebut sebagai risk reduction karena membantu menemukan dan memperbaiki bug sebelum sistem digunakan, sehingga mengurangi kemungkinan kegagalan atau dampak negatif di masa depan.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Given-When-Then adalah cara menulis test dengan mendeskripsikan kondisi awal (Given), aksi yang dilakukan (When), dan hasil yang diharapkan (Then).
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test menguji satu bagian kecil dari kode secara terpisah (misalnya satu fungsi), sedangkan integration test menguji bagaimana beberapa bagian kode bekerja bersama sebagai satu sistem.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Service layer biasanya cocok untuk unit test karena lapisan ini berisi logika bisnis inti yang dapat diuji secara terisolasi dengan mudah (misalnya dengan mock dependency seperti database atau API), sehingga pengujian menjadi cepat, fokus, dan tidak bergantung pada sistem eksternal.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
JUnit 5 berfungsi untuk membuat, mengelola, dan menjalankan test otomatis guna memastikan kode Java bekerja dengan benar.
```

7. Apa fungsi Mockito?

Jawaban:

```text
Mockito adalah library yang digunakan untuk membuat objek tiruan (mock) sehingga kita bisa menguji suatu kode tanpa bergantung pada dependency asli seperti database atau API.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Dependency seperti repository sering dimock saat unit test karena repository biasanya bergantung pada database, sehingga jika tidak dimock, test akan menjadi lebih lambat, kompleks, dan tidak terisolasi.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
1. Pengajuan loan berhasil
Kasus: data valid
Expected: loan berhasil dibuat
    Given valid loan request
    When applyLoan dipanggil
    Then loan berhasil disimpan
2. Pengajuan ditolak karena skor kredit rendah
Kasus: credit score di bawah minimum
Expected: aplikasi ditolak
    Given credit score rendah
    When applyLoan dipanggil
    Then exception / status ditolak
3. Pengajuan gagal karena data tidak lengkap
Kasus: field penting kosong
Expected: error validasi
    Given data tidak lengkap
    When applyLoan dipanggil
```

10. Apa tujuan peer code review?

Jawaban:

```text
Tujuan peer code review adalah untuk meningkatkan kualitas kode dengan memastikan kode tersebut benar, efisien, dan sesuai standar sebelum digunakan atau di-merge.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Logic & correctness → apakah logika bisnis sudah benar dan sesuai requirement
Readability & clean code → kode mudah dipahami, naming jelas, tidak redundant
Error handling → penanganan error sudah aman dan tidak menimbulkan bug baru
Security → tidak ada celah seperti SQL injection, data leakage, auth issues
Performance → query efisien, tidak ada bottleneck atau over-processing
Testing → sudah ada unit/integration test yang memadai
Dependency & structure → penggunaan layer (controller, service, repository) sudah tepat
Logging & monitoring → ada logging yang cukup untuk debugging
```

12. Apa itu structured logging?

Jawaban:

```text
Structured logging adalah cara mencatat log dalam format yang terstruktur (seperti JSON) sehingga mudah dibaca, dicari, dan dianalisis oleh sistem maupun manusia.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Correlation_id digunakan untuk mengidentifikasi dan menelusuri satu request secara end-to-end di dalam log, terutama pada sistem yang terdistribusi.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Info → digunakan untuk mencatat alur normal aplikasi (misalnya request masuk atau proses berhasil)
Warn → digunakan saat ada kondisi yang tidak biasa tapi belum menyebabkan kegagalan
Error → digunakan saat terjadi kegagalan atau error yang mengganggu proses sistem
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Password
2. Personal data sensitif (NIK, nomor KTP, alamat lengkap)
3. Informasi keuangan (nomor kartu kredit, rekening)
4. Token & credential (API key, access token, session ID)
5. Data authentication (OTP, PIN)
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       |     3     |
| Given-When-Then       |     2     |
| JUnit 5               |     2     |
| Mockito               |     2     |
| Service layer testing |     2     |
| Peer code review      |     2     |
| Structured logging    |     2     |
| Correlation ID        |     2     |
| PII safety            |     2     |
