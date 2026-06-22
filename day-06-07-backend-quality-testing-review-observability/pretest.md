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
bekerja dengan code dan code yang dipercayai
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
kerena dapat menghindari error yang berdampak ke prod yang merugikan, dan dari testing jadi gerbang pertama dalam memastikan program berjalan sesuai yang diharapkan
```

3. Apa itu Given-When-Then?

Jawaban:

```text
belum tahu
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
kalau unit test bisa dilakukan sendiri oleh developer atau bisa dilakukan oleh QA kalau integrated Test pasti di test oleh QA untuk mencari dan mencoba segala bentuk scenarion
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
karena bisa ditembak testingnya dengan API karena jika di service layer sudah aamn harusnya meminimalisir error atau ketidaktepatan kode
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
belum tahu
```

7. Apa fungsi Mockito?

Jawaban:

```text
belum tahu
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
belum tahu alasan pastinya
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
1. Create loan berhasil → customer ditemukan, status awal SUBMITTED.
2. Create loan gagal → customer tidak ditemukan.
3. Find loan by ID berhasil.
4. Find loan by ID gagal → loan tidak ditemukan.
5. Update status valid → status berhasil berubah.
6. Update status tidak valid → menghasilkan error.
7. Loan dengan status final tidak bisa diubah lagi.
8. APPROVED → DISBURSED → repayment schedule otomatis dibuat.
9. DISBURSED → CLOSED berhasil jika semua cicilan PAID.
10. DISBURSED → CLOSED gagal jika masih ada cicilan yang belum PAID.
```

10. Apa tujuan peer code review?

Jawaban:

```text
Memastikan kode berkualitas, mudah dipahami, sesuai standar, dan mengurangi bug sebelum di-merge.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
1. Logika program.
2. Validasi input.
3. Keamanan kode.
4. Penanganan error.
5. Query database dan performa.
6. Keterbacaan serta konsistensi kode.
```

12. Apa itu structured logging?

Jawaban:

```text
belum tahu
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
belum tahu
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
belum tahu
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Data sensitif seperti password, token, API key, PIN, OTP, dan informasi pribadi pengguna (misalnya NIK atau nomor kartu kredit).
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |3|
| Given-When-Then |1|
| JUnit 5 |1|
| Mockito |1|
| Service layer testing |2|
| Peer code review |2|
| Structured logging |1|
| Correlation ID |1|
| PII safety |1|
