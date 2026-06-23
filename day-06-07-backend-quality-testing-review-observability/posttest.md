# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena membantu kita menemukan bug sebelum aplikasi dipakai user, jadi kita mengurangi risiko error di production yang bisa bikin sistem crash, data rusak, atau risiko lainnya yang merugikan. Misalnya kalau kita bikin fitur transfer uang tanpa testing bisa saja saldo tidak berkurang atau double transfer terjadi. Oleh karena itu testing dilakukan agar kita bisa cek skenario itu lebih awal sebelum live production.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code -> code yang jalan tanpa error saat dijalankan, tapi belum tentu aman atau benar di semua kondisi.
Trusted code -> code yang sudah diuji dan bisa dipercaya untuk digunakan di production karena sudah melewati testing dan review.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given user punya saldo 100000 -> When user transfer 50000 -> Then saldo tersisa 50000

Oleh karena itu Given-When-Then adalah pola untuk menulis test agar lebih jelas.
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Service layer cocok untuk unit test karena di sana biasanya logic bisnis berada, tidak terlalu tergantung UI atau database langsung sehingga kita bisa fokus testing logic tanpa harus jalanin seluruh aplikasi. Misalnya method approveLoan() bisa di-test apakah status berubah dari PENDING ke APPROVED tanpa perlu buka API atau frontend.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 -> digunakan untuk menjalankan test dan melihat hasil.
Mockito -> digunakan untuk mock dependency agar kita hanya fokus ke unit yang di-test.

E.g. Kita test LoanService, tapi repository kita mock supaya tidak benar-benar akses database.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
- Success create loan application -> input valid, di mana harus berhasil simpan data.
- Approve loan success -> status PENDING dan berubah jadi APPROVED.
- Reject invalid status transition -> misalnya APPROVED langsung ke REJECTED harus error.
```

7. Apa tujuan peer code review?

Jawaban:

```text
Tujuan peer code review adalah proses mengecek code oleh orang lain supaya lebih aman, rapi, dan minim bug sebelum masuk production.
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah format log yang terstruktur dalam format JSON.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Correlation_id berfungsi sebagai ID unik untuk melacak satu request dari awal sampai akhir di seluruh sistem, misalnya semua log punya correlation_id yang sama jadi gampang tracking errornya di mana.
```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
- Password
- Nomor CC
- PIN
- OTP
- NIK
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Testing itu penting untuk mencegah bug masuk production dan bikin code lebih aman dan terpercaya;
2. Unit test fokus ke logic di service layer, jadi tidak perlu jalanin full aplikasi atau database; dan;
3. Structured logging dan correlation_id sangat membantu untuk debugging dan tracking error di sistem besar.
```

Apa 2 hal yang masih membingungkan?

```text
1. Menentukan boundary antara unit test dan integration test; dan;
2. Bagaimana practice langsungnya.
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Tidak hanya cek apakah code jalan atau tidak, tapi juga apakah logic-nya sudah benar, aman, dan mudah dipahami oleh orang lain agar mempermudah development process.
```
