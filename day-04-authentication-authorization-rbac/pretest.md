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
Berguna untuk melindungi data keuangan yang bersifat sensitif dan memastikan hanya pihak tertentu saja yang dapat mengakses maupun memodifikasi data
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Terjadi kebocoran data dan bisa saja penyalahgunaan informasi
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Data peminjaman, approval loan, update status loan, dan melihat detail nasabah
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena FrontEnd sendiri bisa saja dimanipulasi oleh user
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Semua akses ditolak secara bawaan/default
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
Login menggunakan username dan password
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Metode autentikasi menggunakan token sebagai identitas/data user setelah login
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Untuk mengirim token dari client ke server pada tiap request yang dikirim
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Menunjukkan bahwa token yang dikirim adalah token akses bertipe Bearer
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Memvalidasi token
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Request ditolak dengan status unauthorized
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Request ditolak karena token sudah tidak valid, biasanya dilempar kembali ke halaman login
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Request ditolak dan user harus login ulang ataupun refresh token
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) ialah token berbasis JSON yang digunakan untuk autentikasi dan pertukaran informasi secara aman karena di-enkripsi
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Data atau informasi yang disimpan di dalam payload JWT
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
token_expired, role, user_id, username
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload JWT bisa saja dimanipulasi jika signature-nya tidak diverifikasi
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, data sensitif seperti nomor kartu ataupun informasi bersifat rahasia lainnya
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Untuk membatasi masa berlaku token demi security
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk akses ke API, refresh token digunakan untuk mendapatkan access token baru
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Proses menentukan apakah user memiliki izin untuk mengakses resource atau halaman, ataupun izin untuk melakukan aksi tertentu
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication untuk memverifikasi identitas, authorization untuk menentukan hak akses
```

### 24. Apa itu RBAC?

Jawaban:

```text
RBAC (Role Based Access Control) ialah metode pengaturan akses berdasarkan role pengguna
```

### 25. Apa itu role?

Jawaban:

```text
Sekumpulan hak akses yang diberikan ke user
```

### 26. Apa itu permission?

Jawaban:

```text
Izin spesifik untuk melakukan aksi tertentu ke API
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Admin, Loan Officer, Customer
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Create loan, approve loan, view loan, update loan
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena hak akses ditentukan oleh role dan permission masing-masing untuk user-nya
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Kontrol akses berdasarkan kepemilikan atau atribut resource atau API tertentu
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena user dengan role sama belum tentu boleh mengakses semua resource atau API
```

### 32. Apa itu ownership check?

Jawaban:

```text
Validasi apakah resource atau API dimiliki oleh user yang mengakses
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Customer mengakses API dengan ID loan milik orang lain
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Kebocoran data sebab akses langsung ke resource tanpa validasi kepemilikan
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Melakukan validasi user dan permission di setiap request
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum terautentikasi atau token tidak valid
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah login tetapi tidak memiliki izin
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 berarti belum login/invalid token, 403 berarti tidak memiliki izin
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk monitoring, audit, dan tentunya investigasi keamanan
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
User ID, endpoint, timestamp, status code, IP address
```

## Self Assessment

| Area               | Score 1-5 |
| ------------------ | --------- |
| Authentication     | 4         |
| Authorization      | 5         |
| JWT                | 4         |
| RBAC               | 4         |
| Resource ownership | 4         |
| 401 vs 403         | 4         |
| Audit log          | 3         |

## Notes

```text
Best practice dalam mengirimkan token JWT
```
