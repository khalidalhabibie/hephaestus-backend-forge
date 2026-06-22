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
Working code yang penting kode bekerja sesuai dengan apa yang diharapkan. Sedangkan Trusted Code menjamin keamanan, keandalan, dan bebas dari kerentanan. Jadi trusted code tidak hanya bekerja sesuai dengan harapan, tapi juga aman, dan andal.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena dengan testing, kita bisa identifikasi celah, bug, atau kegagalan lebih awal. Dengan menemukan dan memperbaiki masalah tersebut sebelum produk atau sistem dirilis.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Given when then adalah mendeskripsikan sebuah sistem.
Given -> Kondisi awal
When -> Ketika ada peristiwa
Then -> Menjelaskan hasil perubahan yang dilihat akibat dari when.
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit Test -> Test yang dilakukan satu komponen fungsi method class. Integration Testing -> Testing yang dilakukan untuk memastikan kerja dari beberapa fungsi / class / method yang bekerja sama.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena semua logika bisnis biasanya terisolasi di service layer.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Framework untuk testing automated secara terstruktur di java.
```

7. Apa fungsi Mockito?

Jawaban:

```text
Untuk membuat objek dummy untuk mengisolasi komponen yang sedang di test dari dependencies eksternal.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Agar terisolasi tanpa dipengaruhi oleh komponen eksternal.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Pengujian Happy Path ketika Status Loan di Approved, apakah akan generate tabel yang diharapkan atau tidak.
```

10. Apa tujuan peer code review?

Jawaban:

```text
Proses dimana code yang ditulis oleh seorang developer, di cek oleh developer lainnya sebelum code tersebut di merge atau di publish.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Saat melakukan code review backend :
1. Logic Bisnis
2. Clean Code & Readability
3. Struktur dan arsitektur
4. Validasi input
5. Error Handling
6. security
7. Database query
8. Performance
9. Unit Test
```

12. Apa itu structured logging?

Jawaban:

```text
Log yang dilakukan secara terstruktur agar lebih mudah di analisis, dicari, dan dimanfaatkan dalam sistem monitoring modern.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Untuk melacak satu request atau transaksi n to n dalam sistem.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
INFO -> untuk logging alur normal aplikasi
WARN -> untuk logging kondisi tidak ideal, tapi aplikasi tetap jalan
ERROR -> untuk logging ketika kegagalan terjadi yang menghambat proses berlanjut.
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Data pribadi customer PDP
2. Data Sensitif
3. Data keuangan
4. Token dan kredentials
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Testing mindset       | 4         |
| Given-When-Then       | 4         |
| JUnit 5               | 2         |
| Mockito               | 3         |
| Service layer testing | 4         |
| Peer code review      | 4         |
| Structured logging    | 3         |
| Correlation ID        | 4         |
| PII safety            | 2         |
