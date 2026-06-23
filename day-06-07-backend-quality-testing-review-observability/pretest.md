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
working code adalah kode yang berjalan dan menghasilkan output sesuai harapan tapi belum tentu bisa dipercayai

trusted code adalah kode yang tidak hanya berjalan, tetapi juga sudah memiliki pengujian sehingga lebih dapat dipercaya dan lebih aman ketika dilakukan perubahan
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
testing membantu mengurangi risiko bug, error, atau perubahan yang tidak diinginkan pada aplikasi. testing tidak menjamin aplikasi bebas bug, tetapi dapat meningkatkan tingkat kepercayaan terhadap kualitas kode
```

3. Apa itu Given-When-Then?

Jawaban:

```text
given-when-then adalah pola penulisan test yang memisahkan kondisi awal, aksi yang dilakukan, dan hasil yang diharapkan

given = kondisi awal
when = aksi yang dijalankan
then = hasil yang diharapkan
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
unit test menguji satu unit kode secara terpisah, seperti method atau service tertentu

integration test menguji interaksi antar komponen, misalnya service dengan database atau api lain
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
karena sebagian besar business logic berada di service layer sehingga perilaku aplikasi dapat diuji tanpa harus bergantung langsung pada database atau komponen eksternal
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
junit 5 digunakan untuk membuat dan menjalankan unit test pada aplikasi java
```

7. Apa fungsi Mockito?

Jawaban:

```text
mockito digunakan untuk membuat mock object sehingga dependency dapat disimulasikan saat proses testing
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
agar fokus pengujian hanya pada business logic yang ada di service serta menghindari ketergantungan terhadap database atau sistem eksternal
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
contohnya adalah memastikan pengajuan pinjaman berhasil dibuat ketika data valid, serta memastikan sistem menolak pengajuan jika data yang diberikan tidak memenuhi syarat
```

10. Apa tujuan peer code review?

Jawaban:

```text
untuk meningkatkan kualitas kode, menemukan potensi bug lebih awal, menjaga konsistensi coding standard, dan berbagi pengetahuan antar anggota tim
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
logic bisnis, keamanan, performa, penanganan error, kualitas kode, testing, dan kepatuhan terhadap coding standard
```

12. Apa itu structured logging?

Jawaban:

```text
pencatatan log dalam format yang terstruktur, misalnya json, sehingga lebih mudah dicari, difilter, dan dianalisis oleh sistem monitoring
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
correlation_id digunakan untuk melacak satu alur request yang melewati beberapa service atau komponen sehingga proses troubleshooting menjadi lebih mudah
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
info digunakan untuk mencatat aktivitas normal aplikasi

warn digunakan ketika ada kondisi yang tidak ideal tetapi aplikasi masih dapat berjalan

error digunakan ketika terjadi kegagalan yang menyebabkan proses tidak berjalan sebagaimana mestinya
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
password, token, pin, nomor kartu kredit, data identitas pribadi, dan informasi sensitif lainnya yang dapat menimbulkan risiko keamanan atau privasi
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       | 3         |
| Given-When-Then       | 3         |
| JUnit 5               | 2         |
| Mockito               | 2         |
| Service layer testing | 3         |
| Peer code review      | 3         |
| Structured logging    | 2         |
| Correlation ID        | 2         |
| PII safety            | 3         |
