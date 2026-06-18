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
Agar hanya user yang berhak yang bisa mengakses data dan melakukan transaksi
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Data bisa bocor, disalahgunakan, dan terjadi fraud
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Melihat data pinjaman, approve/reject loan, ubah status pembayaran
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena frontend bisa dimanipulasi oleh user
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Semua akses ditolak kecuali yang diizinkan secara eksplisit
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Proses verifikasi identitas user
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login dengan username dan password
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Autentikasi menggunakan token sebagai bukti login
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Mengirim token dari client ke backend
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Format pengiriman token: "Bearer <token>"
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Memvalidasi token dan mengambil informasi user
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Request ditolak (401 Unauthorized)
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Request ditolak karena token tidak valid
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
User harus login ulang atau refresh token
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
Token berbentuk JSON yang digunakan untuk autentikasi
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Informasi yang disimpan di dalam JWT
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
id, role, expired token
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena bisa dimodifikasi oleh pihak yang tidak berwenang
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, data sensitif, informasi rahasia
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Agar token tidak bisa digunakan selamanya
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token untuk akses API, refresh token untuk mendapatkan token baru
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Proses menentukan hak akses user
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication = siapa user, authorization = apa yang boleh dilakukan
```

### 24. Apa itu RBAC?

Jawaban:

```text
Role-Based Access Control, akses berdasarkan role
```

### 25. Apa itu role?

Jawaban:

```text
Peran user dalam sistem
```

### 26. Apa itu permission?

Jawaban:

```text
Hak untuk melakukan aksi tertentu
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Admin, officer, customer
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
View loan, approve loan, update loan
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena tiap user punya role dan permission berbeda
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
```

### 32. Apa itu ownership check?

Jawaban:

```text
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Data bocor karena akses tanpa validasi resource
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Validasi ownership dan permission di backend
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum login atau token tidak valid
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user tidak punya izin meskipun sudah login
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 = belum terautentikasi, 403 = tidak punya akses
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk monitoring dan audit aktivitas user
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
user_id, waktu, endpoint, status, IP address
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication | 3|
| Authorization | 3|
| JWT | 3|
| RBAC |2 |
| Resource ownership | 3|
| 401 vs 403 | 4|
| Audit log | 3|

## Notes

```text
Bagaimana membuat sistem authorization token bisa centralize sehingga tidak harus di parsing di tiap service?
```
