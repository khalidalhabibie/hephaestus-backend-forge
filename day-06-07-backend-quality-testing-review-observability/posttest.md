# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
karena testing membantu mengurangi risiko bug masuk ke production, memastikan logic berjalan sesuai ekspektasi, dan mencegah impact yang lebih besar seperti error bisnis atau kerugian user
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
working code itu sekadar berjalan sesuai skenario tertentu, tapi belum tentu aman di semua kondisi. trusted code sudah teruji (misalnya lewat test) sehingga lebih bisa diandalkan dalam berbagai kondisi
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
given: kondisi awal atau setup data
when: aksi yang dilakukan
then: hasil yang diharapkan

pola ini membantu membuat test lebih jelas dan mudah dipahami karena sudah terstruktur
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
karena service layer berisi business logic utama yang biasanya tidak langsung bergantung ke external system, jadi lebih mudah diisolasi dan diuji
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
junit 5 digunakan sebagai framework untuk menjalankan test dan assertion

mockito digunakan untuk membuat mock object agar dependency eksternal bisa disimulasikan
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. pengajuan loan berhasil dengan data valid
2. pengajuan ditolak karena tidak memenuhi kriteria (misalnya skor kredit rendah)
3. validasi gagal karena data input tidak lengkap atau tidak valid
```

7. Apa tujuan peer code review?

Jawaban:

```text
untuk meningkatkan kualitas code, menemukan bug lebih awal, memastikan standar coding terpenuhi, dan berbagi knowledge antar tim
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
adalah logging dengan format terstruktur misalnya json sehingga mudah dibaca mesin, dicari, dan dianalisis. penting untuk debugging dan monitoring di sistem yang kompleks
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
digunakan untuk melacak satu request end-to-end antar service, sehingga memudahkan tracing saat terjadi error atau investigasi issue
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. password
2. nomor kartu kredit
3. aksestoken
4. nik
5. otp
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. testing itu bukan hanya cuma cek jalan atau tidak, tapi untuk mengurangi risiko yang tidak diinginkan
2. pentingnya isolation dalam unit test dengan mock
3. logging dan observability membantu debugging di production
```

Apa 2 hal yang masih membingungkan?

```text
1. batasan detail apa saja yang perlu dimock di unit test
2. best practice dalam menentukan coverage test yang cukup
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
apakah logic utama sudah tercover unit test dengan baik dan skenario edge case sudah dipertimbangkan
```
