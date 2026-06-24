# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Testing disebut risk reduction karena membantu mengurangi risiko bug dan kesalahan sebelum aplikasi digunakan.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code adalah kode yang bisa berjalan, sedangkan trusted code adalah kode yang sudah diuji dan dipercaya benar.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given-When-Then adalah pola test yang menjelaskan kondisi awal, aksi yang dilakukan, dan hasil yang diharapkan.
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Service layer cocok untuk unit test karena biasanya berisi logic utama aplikasi yang bisa diuji secara terpisah.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan untuk membuat dan menjalankan test, sedangkan Mockito digunakan untuk membuat mock dependency.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
Tiga test case penting adalah berhasil membuat loan application dengan data valid, gagal saat customer tidak ditemukan, dan gagal saat data pinjaman tidak valid.
```

7. Apa tujuan peer code review?

Jawaban:

```text
Peer code review bertujuan untuk menemukan kesalahan, meningkatkan kualitas kode, dan memastikan kode mudah dipahami tim.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah log dengan format terstruktur agar lebih mudah dicari, dianalisis, dan dilacak saat terjadi masalah.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Correlation_id berfungsi untuk melacak satu request dari awal sampai akhir agar error lebih mudah dicari di log.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Data yang tidak boleh ditulis mentah di log adalah password, token, PIN, NIK, nomor kartu, dan data pribadi sensitif.
```


## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. fungsi coorelation id walaupun masih ngawang ngawang
2. Menulsikan logger dengan package bawaan spring
3. pentingnya peercode review
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara membuat testcase yang efisien dan tidak buang buang waktu
2. bagaimana cara menangani kalo code programer sebelumnya terlalu berantakan dan tanpa dokumentasi
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Dokumentasi Code overall dan API terlebih dahulu
```
