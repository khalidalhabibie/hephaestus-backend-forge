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
menurut saya, working code ini itu kode yg bisa running tp blm tentu bener logicnya. kalo trusted code ini dia udah running, logicnya jg udh tested dan verified.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
karena pada testing itu kita bs melihat error, menemukan bug, dsb nya dan mengurangi risiko after deploy
```

3. Apa itu Given-When-Then?

Jawaban:

```text
given itu kondisi awal apa, when itu apa yang dilakukan, then itu hasil yang diharapkan
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test itu nguji 1 komponen kecil, integration test itu nguji multi komponen yg nyambung bareng```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
ya karena isinya pure business logic, ibarat kata kalo business logic aja udh bug, error, dsb nya, yg lain pasti salah. Selain itu service ini nggak konek db jd ga ngefek ke program yg lg run
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
framework java untuk nulis dan running test
```

7. Apa fungsi Mockito?

Jawaban:

```text
Library untuk bikin object palsu (mock). Jadi bisa uji Service tanpa panggil database beneran. Bisa juga verify "method ini dipanggil berapa kali".```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Unit test fokus ke logic Service, bukan ke logic database. Kalau panggil repository beneran, jadi integration test. Mock bikin test lebih cepat dan isolasi.```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Create loan dengan customer valid → success
Create loan dengan customer tidak ada → throw CustomerNotFoundException
Update status SUBMITTED → APPROVED → success
Update status SUBMITTED → DISBURSED → gagal (harus lewat APPROVED dulu)
Update status DISBURSED → CLOSED kalau belum semua schedule PAID → gagal
```

10. Apa tujuan peer code review?

Jawaban:

```text
Bukan cari salah, tapi: (1) bagi knowledge, (2) cek apakah kode mudah dipahami orang lain, (3) tangkap bug/edge case yang terlewat, (4) pastikan konsisten standar tim.

```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
Business logic benar nggak? (sesuai requirement)
Exception handling lengkap?
Nggak ada N+1 query?
DTO dipakai, bukan return Entity langsung?
Naming jelas? Method nggak terlalu panjang?
Transactional dipakai di write operation?

```

12. Apa itu structured logging?

Jawaban:

```text
Log yang format-nya rapi dan bisa di-parse mesin (biasanya JSON). Bisa difilter, dicari, di-aggregate. Beda sama log teks bebas yang susah dianalisis.

```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
ID unik yang dibawa sepanjang request (misal dari API Gateway → Service A → Service B → Database). Jadi kalau ada error, bisa trace "request ini lewat mana aja" walaupun lewat banyak service.
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
Info: alur normal, untuk observasi (request masuk, proses selesai)
Warn: ada yang aneh tapi nggak fatal (retry ke-3, timeout tapi masih bisa fallback)
Error: gagal total, perlu diperbaiki (exception, database down, data corrupt)

```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
NIK / KTP
Password / token / API key
Nomor kartu kredit
Alamat lengkap
Nomor HP
Data kesehatan
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |4|
| Given-When-Then |4|
| JUnit 5 |1|
| Mockito |1|
| Service layer testing |2|
| Peer code review |3|
| Structured logging |2|
| Correlation ID |2|
| PII safety |2 |
