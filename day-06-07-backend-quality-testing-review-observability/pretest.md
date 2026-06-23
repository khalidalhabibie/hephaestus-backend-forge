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
Working code -> code yang terlihat berjalan dan menghasilkan output yang diharapkan (e.g. program loan berhasil dijalankan).

Trusted code -> code yang tidak hanya berjalan, tetapi juga sudah dilakukan pengujian sehingga kita yakin dalam menggunakan programnya di berbagai situasi even terdapat perubahan di masa depan (e.g. program berhasil membuat data pinjaman dan sudah memiliki unit test untuk berbagai skenario sukses maupun gagal → trusted code).
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena testing membantu mengurangi risiko terjadinya bug maupun error masuk ke production walaupun tidak selamanya menjamin aplikasi terbebas dari masalah tersebut, tetapi dengan menerapkan testing kita dapat meningkatkan kepercayaan bahwa fitur bekerja sesuai dengan harapan.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Tulis jawaban di sini.
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test -> menguji satu bagian kecil code, seperti biasanya satu method atau satu class saja.
Integration test-> menguji interaksi beberapa komponen sekaligus, seperti koneksi service dengan database.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena sebagian besar business logic berada di service layer.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Tulis jawaban di sini.
```

7. Apa fungsi Mockito?

Jawaban:

```text
Tulis jawaban di sini.
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Karena tujuan unit test adalah menguji logika pada class yang sedang diuji, bukan menguji database atau komponen lain.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
- Pengajuan pinjaman berhasil dibuat;
- Customer tidak ditemukan;
- Nominal pinjaman melebihi batas maksimum; dan;
- Data berhasil tersimpan ke database.
```

10. Apa tujuan peer code review?

Jawaban:

```text
Peer code review bertujuan untuk memeriksa kualitas code sebelum digabungkan ke branch utama.
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
- Apakah logic sudah benar;
- Apakah ada potensi bug; dan;
- Apakah kode mudah dibaca dan dipahami.
```

12. Apa itu structured logging?

Jawaban:

```text
Structured logging adalah cara menulis log dalam format yang terstruktur dan konsisten sehingga mudah dicari dan dipahami oleh semua team.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Correlation ID berfungsi untuk mencari satu request dari awal sampai akhir proses.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Tulis jawaban di sini.
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Data sensitif atau personal.
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset | 2 |
| Given-When-Then | 1 |
| JUnit 5 | 1 |
| Mockito | 1 |
| Service layer testing | 1 |
| Peer code review | 1 |
| Structured logging | 1 |
| Correlation ID | 1 |
| PII safety | 1 |
