# Pretest - Authentication, Authorization & RBAC

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang authentication, authorization, JWT, RBAC, dan access control pada backend finance.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Security Mindset

### 1. Kenapa access control penting dalam sistem finance?

Jawaban:

```text
Karena sistem finance nyimpen data sensitif kayak saldo dan transaksi. Kalau ga ada pembatasan, siapapun bisa ngintip atau ngubah data orang lain.```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Data sensitif bocor, manipulasi saldo oleh pihak tidak berhak, dan pelanggaran privasi nasabah.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Data limit kredit, persetujuan pinjaman, atau akses ke data pribadi nasabah lain
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Frontend bisa dimanipulasi dengan mudah, backend adalah pertahanan terakhir untuk validasi data.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Sistem menolak semua akses secara otomatis kecuali secara eksplisit diberikan izin khusus.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Proses pembuktian identitas pengguna (siapa kamu?).
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login menggunakan username dan password atau OTP.```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Metode akses menggunakan token digital yang diberikan server setelah user berhasil login.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Tempat menyimpan token akses agar server bisa mengenali identitas pengirim request.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Standar penulisan token di header (contoh: Authorization: Bearer <token>).
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Memvalidasi signature, masa berlaku, dan isi (claim) token tersebut.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Server menolak permintaan karena tidak ada identitas yang dikenali (401 Unauthorized).
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Server menolak karena token tidak sesuai atau sudah dirusak (401 Unauthorized).
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Server menolak akses karena masa berlaku token sudah habis (401 Unauthorized).
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
Standar untuk mengirim informasi (seperti data user) secara aman dalam format terenkripsi.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Kumpulan data atau informasi user yang dimasukkan ke dalam payload JWT.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
ID user, username, peran (role), dan waktu kedaluwarsa token.
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload mudah didecode siapa saja, signature menjamin bahwa data belum diubah.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, PIN, atau data sangat sensitif lainnya.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Membatasi durasi akses agar jika token dicuri, akses hanya berlaku sementara.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access untuk akses API, Refresh untuk membuat access token baru saat sudah habis.
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Proses menentukan apakah pengguna boleh melakukan tindakan tertentu (apa yang bisa kamu lakukan?).
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
AuthN membuktikan identitas, AuthZ membuktikan izin akses.
```

### 24. Apa itu RBAC?

Jawaban:

```text
Sistem pembatasan akses berdasarkan peran (role) yang dimiliki pengguna.
```

### 25. Apa itu role?

Jawaban:

```text
Kumpulan hak akses atau tanggung jawab yang diberikan ke pengguna (misal: Admin, Staf).
```

### 26. Apa itu permission?

Jawaban:

```text
Izin spesifik untuk melakukan tindakan tertentu (misal: baca data, hapus data).
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Nasabah, Officer Pinjaman, Admin Sistem.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
approve_loan, view_my_balance, create_loan_application.
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena login hanya membuktikan dia siapa, bukan berarti dia punya hak akses ke semua fitur.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Pembatasan akses berdasarkan kepemilikan spesifik pada sebuah data (bukan sekadar role).
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena user dengan role yang sama tidak boleh mengakses data milik user lain.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Proses verifikasi bahwa pengguna yang meminta data adalah pemilik sah data tersebut.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Customer mengubah ID pinjaman di URL untuk mengakses detail pinjaman milik orang lain.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Data milik user lain bocor atau terubah akibat akses yang tidak dibatasi di level resource.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Memastikan ID user di database cocok dengan pemilik resource sebelum menampilkan data.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum login atau tokennya sudah tidak valid.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah login tapi tidak punya izin (role/akses) untuk fitur tersebut.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 berarti "Siapa kamu?", 403 berarti "Kamu bukan siapa-siapa yang punya izin".
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk melacak siapa melakukan apa dan kapan, terutama saat terjadi masalah keamanan.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
Timestamp, ID User, IP Address, Endpoint yang diakses, dan status respon.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication |4|
| Authorization |4|
| JWT |3|
| RBAC |1|
| Resource ownership |1|
| 401 vs 403 |3|
| Audit log |2|

## Notes

```text
lets see, tp sepertinya di auth ini akan agak bingung.
```
