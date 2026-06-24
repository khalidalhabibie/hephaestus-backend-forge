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
Working code adalah kode yang bisa berjalan, sedangkan trusted code adalah kode yang sudah diuji dan dipercaya aman serta benar.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Testing disebut risk reduction karena membantu mengurangi risiko bug, error, dan kesalahan sebelum aplikasi digunakan.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Given-When-Then adalah pola penulisan test yang menjelaskan kondisi awal, aksi yang dilakukan, dan hasil yang diharapkan.
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test menguji bagian kecil kode secara terpisah, sedangkan integration test menguji apakah beberapa bagian sistem bisa bekerja bersama.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Service layer cocok untuk unit test karena biasanya berisi logic utama aplikasi yang bisa diuji tanpa menjalankan seluruh sistem.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
JUnit 5 berfungsi untuk membuat dan menjalankan test pada aplikasi Java.
```

7. Apa fungsi Mockito?

Jawaban:

```text
Mockito berfungsi untuk membuat mock object agar dependency bisa digantikan saat unit test.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Repository sering dimock agar test fokus ke logic service tanpa bergantung langsung ke database.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Contoh test case penting adalah memastikan loan application berhasil dibuat ketika data valid dan gagal ketika data tidak valid.
```

10. Apa tujuan peer code review?

Jawaban:

```text
Peer code review bertujuan untuk menemukan kesalahan, meningkatkan kualitas kode, dan memastikan kode mudah dipahami tim.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Area yang perlu dicek adalah logic bisnis, validasi, error handling, security, struktur kode, naming, dan performa.
```

12. Apa itu structured logging?

Jawaban:

```text
Structured logging adalah cara menulis log dengan format terstruktur seperti JSON agar mudah dicari dan dianalisis.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Correlation_id berfungsi untuk melacak satu request dari awal sampai akhir di beberapa service atau proses.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Info digunakan untuk aktivitas normal, warn untuk kondisi mencurigakan, dan error untuk masalah yang menyebabkan proses gagal.
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Data yang tidak boleh ditulis mentah di log adalah password, token, NIK, nomor kartu, PIN, dan data pribadi sensitif lainnya.
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       | 2         |
| Given-When-Then       | 2         |
| JUnit 5               | 2         |
| Mockito               | 2         |
| Service layer testing | 2         |
| Peer code review      | 2         |
| Structured logging    | 2         |
| Correlation ID        | 2         |
| PII safety            | 2         |
