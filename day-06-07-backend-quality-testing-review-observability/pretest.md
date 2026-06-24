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
Working code cuma bisa jalan, tapi belum tentu benar sedangkan trusted code sudah lebih bisa dipercaya karena sudah dites
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena testing membantu mengurangi kemungkinan error saat aplikasi dipakai
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Cara menulis test: Given (kondisi), When (aksi), Then (hasil)
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test fokus ke satu fungsi, sedangkan integration test cek gabungan beberapa bagian.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena di situ ada logic utama aplikasi.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Untuk membuat dan menjalankan test di Java
```

7. Apa fungsi Mockito?

Jawaban:

```text
Untuk membuat data atau dependency palsu saat testing
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Supaya tidak perlu akses database asli saat test
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
- Data valid
- Data tidak valid
- Loan disetujui atau ditolak
```

10. Apa tujuan peer code review?

Jawaban:

```text
Supaya code dicek oleh orang lain sebelum dipakai
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

Logic, error handling, dan apakah code mudah dibaca

````

12. Apa itu structured logging?

Jawaban:

```text
Logging yang terstruktur supaya mudah dibaca dan dicari
````

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Untuk melacak satu request yang sama di log
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Info: normal
Warn: agak bermasalah
Error: terjadi error
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Password, nomor kartu, dan data pribadi
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       |     4     |
| Given-When-Then       |     3     |
| JUnit 5               |     3     |
| Mockito               |     4     |
| Service layer testing |     3     |
| Peer code review      |     3     |
| Structured logging    |     3     |
| Correlation ID        |     3     |
| PII safety            |     2     |
