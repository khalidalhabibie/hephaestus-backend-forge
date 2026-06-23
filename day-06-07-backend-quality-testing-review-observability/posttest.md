# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena testing membantu menemukan bug lebih awal sehingga mengurangi risiko masalah saat aplikasi digunakan.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code berjalan dengan baik, sedangkan trusted code sudah terbukti benar melalui testing.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given: kondisi awal.
When: aksi yang dilakukan.
Then: hasil yang diharapkan.
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Karena service layer berisi logika bisnis utama yang perlu dipastikan berjalan dengan benar.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan untuk membuat dan menjalankan test. Mockito digunakan untuk membuat mock dependency.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. Berhasil membuat loan application.
2. Gagal karena customer tidak ditemukan.
3. Gagal karena data request tidak valid.
```

7. Apa tujuan peer code review?

Jawaban:

```text
Untuk menemukan kesalahan, meningkatkan kualitas kode, dan berbagi pengetahuan dalam tim.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah pencatatan log dengan format yang terstruktur sehingga lebih mudah dicari dan dianalisis.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Untuk melacak satu proses atau request yang sama di berbagai log dan error.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Password.
2. PIN.
3. Token atau API key.
4. Nomor kartu kredit.
5. Data pribadi sensitif (NIK atau nomor identitas).
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Testing adalah risk reduction untuk memastikan business rule tetap aman dan mengurangi risiko bug saat perubahan atau refactor.
2. Unit test paling cocok dilakukan pada service layer karena berisi business rule dan dependency dapat dimock menggunakan Mockito.
3. Structured logging dengan correlation_id membantu tracing, monitoring, dan audit tanpa membocorkan PII atau data sensitif.
```

Apa 2 hal yang masih membingungkan?

```text
1. Kapan harus menggunakan verify pada Mockito dan kapan tidak perlu agar test tidak terlalu bergantung pada implementasi.
2. Perbedaan batasan penggunaan unit test, integration test, dan E2E test dalam proyek nyata. dan menyusun unit test yang cepat dan akurat bagaimana?
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Belum melakukan code review karena hari ini mengerjakan logging dan unit testing
```
