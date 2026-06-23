# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena testing membantu mengurangi risiko bug, error produksi, dan dampak negatif terhadap bisnis dengan memastikan kode berjalan sesuai ekspektasi sebelum dirilis.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code hanya berjalan tanpa error, sedangkan trusted code sudah diverifikasi dengan testing sehingga dapat dipercaya  dalam berbagai kondisi.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given: kondisi awal atau setup data
When: aksi atau method yang diuji
Then: hasil atau output yang diharapkan
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Karena service layer berisi business logic dan tidak bergantung langsung pada HTTP atau database sehingga mudah diisolasi dan diuji menggunakan mocking.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan untuk membuat dan menjalankan test, sedangkan Mockito digunakan untuk membuat mock dependency agar test fokus ke logic utama tanpa bergantung pada komponen lain.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. Create loan berhasil dengan data valid
2. Update status valid (misalnya SUBMITTED → APPROVED)
3. Update status invalid (misalnya DISBURSED → REJECTED)
```

7. Apa tujuan peer code review?

Jawaban:

```text
Untuk meningkatkan kualitas kode, menemukan bug lebih awal, memastikan best practice diterapkan, dan berbagi knowledge antar developer.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah format log yang konsisten (biasanya key-value atau JSON) sehingga mudah dibaca, dicari, dan dianalisis oleh manusia maupun sistem monitoring.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Correlation_id digunakan untuk melacak satu request secara end-to-end sehingga memudahkan debugging dan tracing antar service.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Password
2. Nomor KTP/NIK
3. Nomor kartu kredit
4. Email (tanpa masking)
5. Nomor telepon (tanpa masking)
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. logging (level log)
2. service unit test
3. controller unit test
```

Apa 2 hal yang masih membingungkan?

```text
1. memanggil method bawaan dari mockito dan junit
2. cara menguji logging secara optimal di project besar
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
cek apakah logic berjalan sesuai, lalu coba tes dengan log dan unit test, lalu memastikan controller dan exception berjalan dengan lancar
```
