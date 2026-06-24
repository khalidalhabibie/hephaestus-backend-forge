# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena testing membantu mencegah bug sebelum masuk ke production
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code cuma jalan, trusted code sudah diuji dan bisa diandalkan
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given kondisi awal, When aksi dilakukan, Then hasil yang diharapkan
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Karena business logic ada di situ dan bisa dites tanpa dependency luar
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 untuk menjalankan test, Mockito untuk mock dependency
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. Loan disetujui jika memenuhi syarat
2. Loan ditolak jika tidak valid
3. Handling error saat data tidak lengkap
```

7. Apa tujuan peer code review?

Jawaban:

```text
Untuk memastikan kualitas kode dan mengurangi bug
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Logging dengan format rapi supaya mudah dicari dan dianalisis
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Untuk melacak satu request dari awal sampai akhir
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Password, token, PIN, nomor KTP, nomor kartu
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Testing bersifat penting untuk cegah bug
2. Logging membantu untuk debugging
3. Code review meningkatkan kualitas
```

Apa 2 hal yang masih membingungkan?

```text
1. Strategi test yang lebih advance
2. Best practice untuk logging di production
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Apakah business logic yang di-coding sudah benar dan aman?
```
