# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
karena tujuan utamanya adalah mengurangi risiko kesalahan (bug), kegagalan sistem, dan dampak negatif di production. Dengan melakukan testing, kita bisa menemukan masalah lebih awal sehingga biaya perbaikan lebih murah dan kepercayaan terhadap sistem meningkat
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code adalah kode yang terlihat berjalan sesuai harapan saat dicoba secara manual. 

Trusted code adalah kode yang sudah divalidasi dengan test otomatis (unit test, integration test) sehingga dapat dipercaya hasilnya dalam berbagai kondisi, termasuk edge case.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
- Given: kondisi awal atau setup (data, state sistem)
- When: aksi atau proses yang dilakukan
- Then: hasil yang diharapkan
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
karena berisi logika bisnis utama. Layer ini biasanya tidak langsung bergantung pada UI atau database (bisa di-mock), sehingga lebih mudah diisolasi dan diuji secara fokus.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan sebagai framework untuk menulis dan menjalankan unit test. 

Mockito digunakan untuk membuat mock object sehingga dependency eksternal (seperti database atau API) bisa disimulasikan saat testing.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
1. Pengajuan loan berhasil dengan data valid.
2. Pengajuan loan gagal karena data tidak valid.
3. Pengajuan loan ditolak karena tidak memenuhi syarat.
```

7. Apa tujuan peer code review?

Jawaban:

```text
untuk meningkatkan kualitas kode, menemukan bug lebih awal, memastikan standar coding terpenuhi, serta berbagi knowledge antar developer.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah pencatatan log dalam format terstruktur (misalnya JSON) sehingga mudah diproses oleh sistem monitoring. Ini penting karena memudahkan pencarian, analisis, dan debugging di sistem yang kompleks.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
untuk melacak satu request yang sama di berbagai service atau log. Ini memudahkan debugging karena semua log terkait bisa dihubungkan dengan satu ID.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
1. Password
2. Nomor kartu kredit
3. Nomor identitas (KTP/NPWP)
4. Token atau API key
5. Data pribadi sensitif (alamat lengkap, tanggal lahir)
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Testing penting untuk mengurangi risiko kegagalan sistem.
2. Perbedaan antara kode yang hanya berjalan dan kode yang benar-benar dapat dipercaya.
3. Pentingnya logging dan tracing dalam debugging.
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara menentukan coverage test yang cukup.
2. Perbedaan detail antara unit test dan integration test.
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Apakah logika bisnis sudah memiliki unit test yang memadai.
```
