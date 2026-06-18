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
Untuk membatasi hak akses setiap pengguna. Agar pengguna tidak bisa mengakses role atau akses yang salah yang tidak seharusnya.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
User bisa salah mengakses file yang tidak seharusnya, dan bisa terjadi kebocoran data. Sistem bisa terjadi kesalahan akses, dan potensi kerusakan data karena semua user bisa memiliki hak akses penuh.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Sistem yang mengelola data pribadi nasabah seperti ktp, salary, dan rekening. Serta action penting seperti approval loan, perubahamn limit pinjaman, dan penghapusan data.
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena frontend bisa dimanipulasi oleh user seperti lewat inspect element dan api tools, sehingga pembatasan akses bisa dibypass dengan mudah. Makanya backend harus tetap melakukan validasi dan kontrol akses agar keamanan sistem terjamin.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Secara default, kita tidak bisa mengakses suatu resource atau suatu data jikalau belum diberikan aksesnya. Jadi secara default, semua akses akan ditolak secara automatis kecuali secara eksplisit diizinkan.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Authentification adalah proses meverifikasi siapa kita.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Contoh proses otentifikasi adalah ketika login dengan username dan password. User input username dan password -> sistem memverifikasi ke database, jika cocok maka user berhasil login.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Metode autentikasi dimana setelah user login, sistem memberikan sebuah token sebagai bukti identitas, dan token tersebut digunakan untuk mengakses sistem tanpa perlu login ulang.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header digunakan untuk mengirimkan informasi autentikasi (token) dari client ke server agar server bisa memverifikasi identitas user dan menentukan hak aksesnya.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer adalah format autentikasi dimana token digunakan sebagai bukti akses, dan setiap client yang membawa token itu dianggap sebagai user yang sah.
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Mengecek apakah token masih valid dan belum expired, dan token tersebut valid atau tidak. Setelah itu backend memberikan akses ke suatu resource.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Jika token tidak ada, server tidak dapa mengidentifikasi user sehingga request akan ditolak dengan 401 unauthorized.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Jika token tidak valid, server akan menolak request karena token tidak bisa diverifikasi.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Jika token expired, server akan menolak request karna sudah expired, sehingga user harus login ulang untuk mendapatkan token baru.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
Format token berbasis json yang digunakan untuk mengirim informasi secara aman antara client dan server. Digunakan untuk authentication dan authorization.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Informasi atau data yang disimpan dalam jwt biasanya tentang user dan token.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
- identitas user
- expired date
- role
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payloard jwt bisa dibaca dan di modif siapa saja, sehingga harus diverifikasi dulu menggunakan signature untuk memastikan data tersebut valid.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, pin, nomor kartu kredit, data rahasia.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
JWT memiliki expiry date untuk mengurangi risiko penyalahgunaan jika token bocor.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan mengakses api dan biasanya memiliki active date yang pendek. Refresh token digunakan untuk mendapatkan access token baru tanpa login ulang.
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses menentukan apa yang boleh kamu lakukan.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentification adalah untuk verifikasi identitas user (kamu siapa ?), dan authorization adalah proses menentukan hak akses user (apa yang boleh kamu lakukan ?)
```

### 24. Apa itu RBAC?

Jawaban:

```text
RBAC atau role based access control adalah pembatasan akses sesuai dengan role suatu user. Bukan per orang satu per satu.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah sekumpulan hak akses yang diberikan kepada user untuk menentukan apa saja yang boleh dilakukan didalam sistem.
```

### 26. Apa itu permission?

Jawaban:

```text
Izin atau hak akses spesifik yang menentukan aksi apa saja yang diizinkan.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
1. SuperAdmin
2. Customer
3. Credit Analyst
4. Disbursement Officer
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
1. View customer data
2. create loan
3. approve loan
4. reject loan
5. update loan status
6. disburse fund
7. upload_document
8. verify document
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Untuk membatasi hak akses setiap user yang akan memiliki role nya yang berbeda beda.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Kontrol akses yang menentukan user boleh mengakses resource teretentu bedasarkan kepimilikan atau konteks data tersebut.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
User dengan role yang sama bisa mengakses data milik user lain yang seharusnya tidak boleh diakses.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Proses memastikan bahwa user hanya dapat mengakses data yang memang dimilikinya.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Sistem tetap mengembalikan data walaupun loan tersebut milik customer lain karena tidak ada pengecekan kepemilikan.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
User dapat mengakses data milik orang lain hanya dengan mengunah id pada request, kebocoran data dan pelanggaran keamanan.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Backend melakukan validasi authorization, seperti cek role dan ownership data, tidak hanya dari id request, memastikan setiap resource diverifikasi apakah user berhak mengakses data tersebut.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika user belum terautentikasi atau tidak mengirimkan token yang valid, sehingga server tidak mengenali identitas user.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
User sudah login, tapi tidak memiliki izin untuk akses resource atau aksi tertentu.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 unauthorized itu berarti belum login atau token tidak valid, sedangkan 403 forbidden berarti user sudah login tapi tidak miliki hak akses yang benar.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Memantau aktivitas user, membantu audit keamanan, mendeteksi penyalahgunaan role, dan memudahkan ketika debugging.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
- Timestamp
- userid
- endpoint yang diakses
- http method
- status code
- request id
```

## Self Assessment

| Area               | Score 1-5 |
| ------------------ | --------- |
| Authentication     | 4         |
| Authorization      | 4         |
| JWT                | 4         |
| RBAC               | 5         |
| Resource ownership | 3         |
| 401 vs 403         | 5         |
| Audit log          | 2         |

## Notes

```text
Audit Log, resource ownership
```
