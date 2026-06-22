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
Working code : code yang tampak berjalan saat dicoba manual

Trusted code : code dengan behavior penting yang sudah diverifikasi sehingga lebih aman diubah
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
membantu menurunkan kemungkinan terjadinya masalah (risk) dan dampaknya terhadap sistem atau bisnis
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Given : menyiapkan kondisi awal
When : menjalankan action
Then : memverifikasi hasil
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
- unit test : menguji logic kecil (Method/service kecil)

- integration test : menguji beberapa komponen bersama (Beberapa layer)
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
karena mengandung business logic inti (tapi tidak terlalu tergantung pada detail teknis seperti database atau UI), mudah diisolasi dengan mock, cepat, tidak tergantung infrastruktur
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
untuk menulis dan menjalankan unit test secara lebih modern, fleksibel, dan powerful
```

7. Apa fungsi Mockito?

Jawaban:

```text
untuk membuat objek tiruan dalam unit testing
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
karena cepat, stabil, dan benar-benar fokus pada logic yang sedang diuji, bukan pada database atau dependensi lain
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
mengecek pada decision logic dan mengecek apakah sesuai mengcover semua aturan bisnis utama
```

10. Apa tujuan peer code review?

Jawaban:

```text
untuk meningkatkan kualitas, mengurangi risiko, dan memastikan code yang masuk ke sistem sudah layak digunakan
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
- Apakah logic sudah benar?
- Apakah securitynya sudah sesuai dan aman
- Apakah kodenya readability dan maintainability
```

12. Apa itu structured logging?

Jawaban:

```text
cara mencatat log dalam format terstruktur agar mudah dianalisis, dicari, dan diproses oleh sistem
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
untuk menandai dan melacak satu request di seluruh sistem
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
- `info` : untuk mencatat kejadian normal dalam aplikasi
- `warn` : ada kondisi tidak ideal, tapi sistem masih bisa berjalan
- `error` : ada error yang menyebabkan proses gagal atau tidak berjalan sesuai harapan
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
- Password
- Data pribadi
- Informasi rekening
- Token & authentication data
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |3|
| Given-When-Then |3|
| JUnit 5 |3|
| Mockito |3|
| Service layer testing |3|
| Peer code review |3|
| Structured logging |3|
| Correlation ID |3|
| PII safety |3|
