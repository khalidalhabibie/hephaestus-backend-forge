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
Karena tidak semua user boleh mengakses data atau melakukan tindakan tertentu dalam sistem finance.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Dapat menyebabkan kebocoran data, penyalahgunaan akses, dan risiko fraud.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Persetujuan pinjaman, data limit kredit, akses data pribadi nasabah, dan laporan keuangan.
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena frontend dapat dimanipulasi sehingga backend tetap harus memeriksa hak akses setiap request.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Secara default semua akses ditolak sampai ada izin yang jelas untuk mengaksesnya.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses mengenali atau memverifikasi identitas user.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login menggunakan username dan password.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Authentication adalah proses mengenali atau memverifikasi identitas user dengan akses token.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Digunakan untuk mengirim token atau informasi autentikasi ke backend.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer berarti client membawa token yang digunakan sebagai bukti identitas saat mengakses API.
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Memvalidasi token sebelum memproses request (signature, masa berlaku, dan isi token).
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Request seharusnya ditolak karena user tidak dikenali.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Request ditolak karena user bukan orang yang seharusnya mengakses.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Request ditolak dan token perlu diperbaharui oleh user supaya dapat mengakses.
```

# Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah token yang berisi informasi atau claim tentang user.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah informasi yang disimpan di dalam JWT.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
user_id, role, issuer, dan expiry.
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload dapat dimodifikasi sehingga keasliannya harus diverifikasi terlebih dahulu.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, API key, dan data sensitif customer.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Untuk mengurangi risiko penyalahgunaan jika token dicuri.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk mengakses API, sedangkan refresh token digunakan untuk mendapatkan access token baru.
```

---

# Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses menentukan apakah user memiliki izin untuk melakukan suatu aksi.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication menentukan siapa user, sedangkan authorization menentukan apa yang boleh dilakukan user.
```

### 24. Apa itu RBAC?

Jawaban:

```text
RBAC (Role Based Access Control) adalah pengaturan akses berdasarkan role user.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah kategori atau posisi user dalam sistem yang menentukan hak aksesnya.
```

### 26. Apa itu permission?

Jawaban:

```text
Permission adalah izin untuk melakukan suatu tindakan tertentu.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Customer, agent, credit analyst, supervisor, dan admin.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Create loan, approve loan, view report, dan manage user.
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap user memiliki role dan permission yang berbeda.
```

---

# Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Pemeriksaan apakah user boleh mengakses resource atau data tertentu.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena user juga harus memiliki akses terhadap resource yang diminta.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Pemeriksaan apakah resource tersebut memang milik atau berada dalam cakupan user yang mengaksesnya.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Customer mengganti ID loan pada URL dan berhasil melihat data milik customer lain.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Kebocoran data dan akses tidak sah terhadap resource milik user lain.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Dengan memeriksa ownership, scope akses, dan permission sebelum mengembalikan data.
```

---

# Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika user belum terautentikasi atau token tidak valid.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Ketika user sudah terautentikasi tetapi tidak memiliki izin akses.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 berarti identitas user belum valid atau tidak dikenali, sedangkan 403 berarti user dikenali tetapi tidak memiliki hak akses.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk mengetahui siapa melakukan apa dan membantu audit maupun investigasi.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
user_id, role, endpoint, result, timestamp, dan correlation_id.
```

---

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication |2|
| Authorization |1|
| JWT |2|
| RBAC |1|
| Resource ownership |1|
| 401 vs 403 |2|
| Audit log |1|

## Notes

```text
Cara membuat authentication dan authorization.
``
