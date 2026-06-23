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
Working code bisa jalan, trusted code sudah terbukti aman dan stabil lewat testing
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena testing membantu menemukan masalah sebelum sampai ke production
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Pola penulisan test: kondisi awal, aksi yang dilakukan, dan hasil yang diharapkan.
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test menguji satu bagian kecil, integration test menguji kerja sama antar bagian
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena di situlah business logic berada dan mudah diuji secara terpisah
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Framework untuk menulis dan menjalankan unit test di Java
```

7. Apa fungsi Mockito?

Jawaban:

```text
Untuk membuat mock dependency saat melakukan unit test
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Supaya test fokus ke logic tanpa tergantung database
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Test pengajuan loan diterima dan ditolak sesuai aturan bisnis
```

10. Apa tujuan peer code review?

Jawaban:

```text
Menjaga kualitas kode dan saling belajar antar developer
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Logic, readability, error handling, security, dan testing
```

12. Apa itu structured logging?

Jawaban:

```text
Logging dengan format rapi dan konsisten agar mudah dianalisis
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Untuk melacak satu request yang sama di berbagai log atau service
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Info untuk proses normal, warn untuk kondisi tidak ideal, error untuk kegagalan
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Password, token, PIN, dan data pribadi sensitif (PII)
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       | 4         |
| Given-When-Then       | 4         |
| JUnit 5               | 4         |
| Mockito               | 3         |
| Service layer testing | 4         |
| Peer code review      | 4         |
| Structured logging    | 3         |
| Correlation ID        | 3         |
| PII safety            | 3         |
