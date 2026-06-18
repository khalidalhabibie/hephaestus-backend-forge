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
Karena data keuangan bersifat sensitif, dan berkaitan dengan banyak data pribadi seperti NIK, slip gaji, dll. sehingga dibutuhkan pengamanan yang ketat agar data tsb tidak tersebar.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Maka bisa terjadi risiko kebocoran data yang dapat menurunkan kepercayaan customer terhadap perusahaan atau merusak reputasi perusahaan.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban: 

```text
Data sensitif (seperti NIK / NPWP, nomor rekening, slip gaji, alamat lengkap), rincian pinjaman (seperti outstanding balance, riwayat pembayaran cicilan, dll), sistem penilaian / skoring, serta aksi-aksi dengan risiko tinggi, seperti proses approval pinjaman, proses disbursement atau pencairan dana, dsb.
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena frontend bersifat client-facing sehingga lebih mudah di-bypass. Maka dari itu, dibutuhkan security yang berlapis-lapis dari frontend dan backend.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Deny by default berarti semua akses di-deny atau ditutup secara default, kecuali ada user yang diberikan akses.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Authentication adalalah proses autentifikasi untuk memverifikasi identitas user ketika mengakses sistem.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login menggunakan username dan password, di mana akan input user akan diverifikasi agar sistem bisa memutuskan apakah user bisa diberikan akses masuk atau tidak.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah metode autentikasi yang memberikan token kepada user yang bisa digunakan sebagai identifier user ketika user membutuhkan akses.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header digunakan untuk mengirimkan kredensial (biasanya token) ke backend sebagai bukti identitas.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer token merujuk pada token yang sudah menjamin bahwa user yang membawanya adalah user yang valid.
Format: Bearer <token> di mana Bearer adalah jenis authentication scheme dan <token> berisi nilai token (biasanya JWT).
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Backend akan mengambil token dari header kemudian melakukan validasi apakah token tersebut berada dalam bentuk yang valid, mengekstrak data user dari token, kemudian mengecek apakah user tersebut boleh diberi akses atau tidak, dan melanjkutkan request apabila user tsb memang memiliki akses.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Jika token tidak ada, maka backend tidak bisa mengidentifikasi siapa user tersebut sehingga dianggap unauthenticated dan request ditolak.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Maka user dianggap unauthenticated dan request ditolak.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Maka akses yang diberikan akan ditarik kembali oleh sistem sehingga user kehilangan akses.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah format token yang digunakan untuk authentication dan authorization dengan cara menyimpan data user dalam bentuk yang aman dan terstruktur.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah potongan informasi tentang sebuah entitas yang dikemas dalam bentuk objek JSON.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
Clain untuk menyimpan ID unik pengguna (misal: 1234567890 atau usr_jhon_doe)
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena kita harus memverifikasi signature untuk memastikan bahwa payload itu asli agar payload tidak bisa dimanipulasi oleh siapa saja.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti data identitas, password, dsb.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Agar akses yang diberikan tidak berlaku selamanya yang meningkatkan risiko kebocoran data.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access Token digunakan untuk akses API dan dipakai di setiap request, sedangkan refresh token digunakan untuk mendapatkan access token baru dan dipakai saat token expired.
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Authorization adalah akses yang menentukan apa saja yang bisa dilakukan oleh user.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication berfokus pada verifikasi identitas sedangkan authorization lebih berfokus pada mengontrol akses.
```

### 24. Apa itu RBAC?

Jawaban:

```text
Role Based Access Control adalah pengendalian akses yang dibedakan berdasarkan role user yang masuk, misalnya role admin memiliki akses yang berbeda dengan role user biasa.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah peran yang dimiliki oleh user yang menentukan akses dan aktivitas yang bisa dilakukan oleh user.
```

### 26. Apa itu permission?

Jawaban:

```text
Permission adalah izin untuk melakukan akses atau aktivitas tertentu.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
user (nasabah), analyst, disbursement, admin, super admin, dsb.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Contoh permission untuk user:
- Registrasi & login
- Mengajukan pinjaman
- Melihat status pengajuan
- Melihat riwayat pembayaran
- Melakukan pembayaran
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena semua user memiliki kebutuhan dan tugas yang berbeda.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Resource-level authorization adalah mekanisme authorization yang memastikan user hanya bisa mengakses resource tertentu yang diizinkan.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Role check tidak cukup karena hanya memeriksa role user, tapi tidak memeriksa data atau akses apa saja yang diizinkan untuk user tsb.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah bagian dari authorization yang memastikan bahwa user adalah pemilik dari resource yang ingin diakses.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Bisa menimbulkan kebocoran data karena customer bisa melihat loan customer lain, padahal seharusnya role user tidak memiliki akses tsb.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Tulis jawaban di sini.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Backend harus menerapkan beberapa lapisan proteksi, seperti role check, permission check, resource check, ownership check, dto, dsb.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika user yang ingin masuk tidak ada token atau token invalid / expired.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Ketika user tidak punya izin di halaman tsb.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 ditampilkan ketika user belum terautentikasi.
403 ditampilkan ketika sudah terautentikasi tapi tidak diizinkan.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk mencatat semua aktivitas request yang masuk sehingga lebih tracable apabila ada masalah.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
Timestamp, userId, method, endpoint, status code, IP address
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication |1|
| Authorization |1|
| JWT |1|
| RBAC |1|
| Resource ownership |1|
| 401 vs 403 |2|
| Audit log |1|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
