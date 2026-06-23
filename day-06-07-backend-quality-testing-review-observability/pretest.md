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
Working code hanya berjalan sesuai skenario tertentu, sedangkan trusted code sudah teruji sehingga dapat dipercaya benar di berbagai kondisi.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena testing mengurangi risiko bug di production dengan menemukan masalah lebih awal sebelum deploy.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Format penulisan test: Given (kondisi awal), When (aksi dilakukan), Then (hasil yang diharapkan).
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test menguji satu komponen secara terisolasi, sedangkan integration test menguji interaksi antar komponen (misalnya service dengan database)
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena berisi business logic utama dan bisa diuji tanpa bergantung ke external system dengan cara mock dependency.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Framework untuk menulis dan menjalankan unit test di Java.
```

7. Apa fungsi Mockito?

Jawaban:

```text
Library untuk membuat mock object agar dependency bisa disimulasikan saat testing.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Agar test fokus ke logic yang diuji dan tidak tergantung ke database atau sistem eksternal.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
- Pengajuan loan valid berhasil
- Pengajuan ditolak jika data tidak sesuai format
- response sesuai
```

10. Apa tujuan peer code review?

Jawaban:

```text
Untuk meningkatkan kualitas code, menemukan bug, dan memastikan standar coding terpenuhi.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
- Logic correctness
- Error handling
- Security (PII, injection)
- Performance
- Readability & maintainability
- Test coverage
```

12. Apa itu structured logging?

Jawaban:

```text
Logging dengan format terstruktur (misalnya JSON) sehingga mudah dibaca dan dianalisis oleh sistem.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Untuk melacak satu request end-to-end di berbagai service dalam sistem terdistribusi.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
- info: proses normal (misalnya request masuk)
- warn: kondisi tidak normal tapi masih berjalan
- error: kegagalan yang mengganggu proses
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
- Password
- Token / API key
- Nomor KTP / NIK
- Nomor rekening / kartu kredit
- Data pribadi sensitif
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |3|
| Given-When-Then |2|
| JUnit 5 |2|
| Mockito |2|
| Service layer testing |3|
| Peer code review |3|
| Structured logging |3|
| Correlation ID |2|
| PII safety |2|
