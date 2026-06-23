# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena testing membantu menemukan bug lebih awal sehingga mengurangi risiko kegagalan sistem di production
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code hanya berjalan sesuai harapan, sedangkan trusted code sudah diuji dan terbukti benar serta aman untuk digunakan
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given: kondisi awal,
When: aksi yang dilakukan,
Then: hasil yang diharapkan.
Digunakan untuk membuat test lebih terstruktur dan mudah dipahami.
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Karena berisi business logic utama dan lebih mudah diisolasi tanpa ketergantungan langsung ke DB atau external system
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan untuk menjalankan dan struktur testing, Mockito digunakan untuk membuat mock/dependency palsu agar test fokus pada logic.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. Pengajuan loan valid → berhasil diproses.
2. Pengajuan dengan data tidak valid → ditolak.
3. Pengajuan dengan user tidak ditemukan / error dependency → handle error dengan benar.
```

7. Apa tujuan peer code review?

Jawaban:

```text
Untuk meningkatkan kualitas kode, menemukan bug, memastikan best practice, dan berbagi pengetahuan antar developer
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah log dalam format terstruktur (misalnya JSON). Hal ini penting karena memudahkan pencarian, analisis, dan monitoring otomatis
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Untuk melacak satu request end-to-end di berbagai service sehingga memudahkan debugging dan tracing
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Password
2. Nomor kartu kredit
3. PIN / OTP
4. NIK / KTP
5. Token / API key
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Testing adalah untuk mengurangi risiko, bukan hanya mencari bug.
2. Unit testing fokus pada isolasi business logic.
3. Observability (logging & correlation id) penting untuk debugging di production.
```

Apa 2 hal yang masih membingungkan?

```text
1. Batasan detail logging yang aman tapi tetap informatif.
2. Strategi test terbaik untuk dependency kompleks.
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Apakah logika bisnis sudah tertutup oleh unit test yang cukup dan meaningful?
```
