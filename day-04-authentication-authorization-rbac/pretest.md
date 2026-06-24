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
untuk melindungi data sensitif (saldo, transaksi) agar hanya bisa diakses oleh pihak yang berhak dan mencegah fraud
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
terjadi kebocoran data, penyalahgunaan, fraud, dan pelanggaran privasi
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
data pinjaman, approval loan, ubah status loan, dan melihat data nasabah
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
karena frontend bisa dimanipulasi, sehingga backend harus tetap melakukan validasi akses
```

### 5. Apa maksud deny by default?

Jawaban:

```text
semua akses ditolak secara default kecuali yang secara eksplisit diizinkan
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
proses verifikasi identitas user
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
login dengan menggunakan username dan password
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
metode autentikasi menggunakan token sebagai pengganti session
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
untuk mengirim token dari client ke backend
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
menunjukkan bahwa token yang dikirim adalah access token (Bearer <token>)
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
memvalidasi keaslian, signature, dan expiry token
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
request ditolak dengan status 401 Unauthorized
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
request ditolak karena token tidak valid (401 Unauthorized)
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
request ditolak dan user harus login ulang (otomatis refresh token)
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JSON Web Token, token berbentuk JSON yang berisi informasi user dan signature
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
data atau informasi yang disimpan dalam payload JWT
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
userId, username, role, exp
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
karena payload bisa dimodifikasi jika signature tidak diverifikasi
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
password, PIN, data sensitif seperti nomor kartu atau data pribadi penting
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
agar token tidak bisa digunakan selamanya dan mengurangi risiko penyalahgunaan
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
- access token untuk akses API (jangka pendek)
- refresh token untuk mendapatkan token baru (jangka panjang)
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
proses menentukan apakah user boleh melakukan sesuatu di dalam sistem
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
- authentication menentukan siapa user
- authorization menentukan apa yang boleh dilakukan
```

### 24. Apa itu RBAC?

Jawaban:

```text
role-Based Access Control, akses ditentukan berdasarkan role user
```

### 25. Apa itu role?

Jawaban:

```text
peran user dalam sistem, yang memiliki beberapa akses yang berbeda
```

### 26. Apa itu permission?

Jawaban:

```text
hak akses spesifik untuk melakukan sesuatu
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Loan Officer, Customer, Auditor
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
createLoan, approval, viewLoan, updateLoan
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena setiap user punya role dan permission yang berbeda
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
pembatasan akses berdasarkan resource tertentu (misalnya data milik siapa)
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
karena user dengan role sama belum tentu boleh akses semua data (belum tentu punya akses yang sama)
```

### 32. Apa itu ownership check?

Jawaban:

```text
pengecekan apakah resource tersebut milik user yang mengakses
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
customer mencoba mengakses loan dengan ID milik user lain melalui URL
```

### 34. Apa risiko IDOR?

Jawaban:

```text
kebocoran data sensitif dan data integrity rusak
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
validasi ownership dan authorization di setiap request
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
saat user belum login atau token tidak valid
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
saat user sudah login tapi tidak punya izin
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
- 401: belum terautentikasi
- 403: tidak punya akses
```

### 39. Kenapa access log penting?

Jawaban:

```text
untuk audit, monitoring, dan mendeteksi aktivitas yang mencurigakan
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
User ID, timestamp, endpoint, method, status code, IP address
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication |3|
| Authorization |3|
| JWT |3|
| RBAC |3|
| Resource ownership |3|
| 401 vs 403 |3|
| Audit log |3|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
