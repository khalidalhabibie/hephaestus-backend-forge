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
Access control sangat penting dalam sistem finance karena sistem ini mengelola data dan transaksi yang sensitif, bernilai tinggi, dan berisiko
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Jika semua user bisa mengakses semua data, maka risiko yang muncul adalah terjadinya kebocoran informasi, penyalahgunaan data, meningkatnya potensi fraud, serta hilangnya integritas dan kepercayaan terhadap sistem keuangan.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Data seperti informasi pribadi nasabah, detail pinjaman, dan dokumen sensitif, serta action seperti approval loan, pencairan dana, perubahan limit, dan restrukturisasi pinjaman harus dibatasi agar mencegah penyalahgunaan, menjaga keamanan data, dan mengurangi risiko fraud dalam sistem loan.
Provide your feedback on BizChat
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Backend tidak boleh hanya mengandalkan frontend untuk membatasi akses karena frontend bisa dengan mudah dimanipulasi oleh user, sehingga tanpa validasi di backend, user dapat melewati pembatasan dan mengakses atau mengubah data yang seharusnya tidak diizinkan.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Deny by default adalah prinsip keamanan di mana semua akses otomatis ditolak terlebih dahulu, kecuali jika secara eksplisit diizinkan.
Dengan kata lain, sistem hanya akan memberikan akses jika ada aturan yang secara jelas memperbolehkannya, bukan sebaliknya.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses memastikan identitas user (misalnya melalui username dan password) sebelum diberikan akses ke sistem.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Contoh proses authentication adalah saat user login menggunakan username dan password yang kemudian diverifikasi oleh sistem untuk memastikan identitasnya valid.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah metode authentication di mana setelah user berhasil login, sistem akan memberikan token (semacam kunci digital) yang digunakan untuk mengakses sistem tanpa perlu login ulang setiap kali request.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header adalah bagian dari HTTP request yang digunakan untuk mengirim informasi kredensial (seperti token) ke server agar bisa mengakses resource yang dilindungi.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer token adalah format di mana token dikirim di Authorization header dengan kata “Bearer” sebagai penanda bahwa token tersebut adalah bukti akses.
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Saat backend menerima token, hal yang harus dilakukan adalah memvalidasi token tersebut untuk memastikan keaslian, keabsahan, dan hak akses user sebelum memproses request.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Jika token tidak ada, maka backend akan menolak request tersebut karena tidak memiliki bukti identitas atau hak akses dari user.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Jika token invalid, maka backend akan menolak request tersebut karena token tidak bisa dipercaya sebagai bukti identitas yang sah.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Jika token expired, maka backend akan menolak request tersebut karena masa berlaku token sudah habis dan tidak lagi dianggap valid.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah format token berbasis JSON yang digunakan untuk mengirim informasi identitas dan hak akses secara aman antara client dan server.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Claim pada JWT adalah informasi atau data yang disimpan di dalam payload token yang digunakan untuk menyampaikan identitas dan atribut user.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
Contoh claim yang umum di JWT antara lain iss, sub, exp, serta custom claim seperti userId, email, dan role yang berisi informasi identitas dan hak akses user.
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Payload JWT tidak boleh dipercaya sebelum signature divalidasi karena isinya bisa diubah, dan hanya signature yang memastikan bahwa data tersebut asli dan tidak dimodifikasi.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, PIN, data kartu kredit, dan informasi rahasia lainnya tidak boleh disimpan di JWT karena dapat dengan mudah dibaca jika token bocor.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
JWT harus memiliki expiry agar token tidak berlaku selamanya dan membatasi risiko akses tidak sah jika token jatuh ke pihak yang tidak berwenang.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk mengakses API secara langsung, sedangkan refresh token digunakan untuk mendapatkan access token baru tanpa perlu login ulang.
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses untuk menentukan apakah user yang sudah terautentikasi memiliki izin untuk melakukan suatu akses atau aksi tertentu.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication adalah proses memverifikasi identitas user, sedangkan authorization adalah proses menentukan hak akses user setelah identitasnya terverifikasi.
```

### 24. Apa itu RBAC?

Jawaban:

```text
RBAC (Role-Based Access Control) adalah metode pengelolaan akses di mana hak akses diberikan berdasarkan role (peran) yang dimiliki oleh user.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah sekumpulan hak akses (permissions) yang diberikan kepada user berdasarkan peran atau tanggung jawabnya dalam sistem.
```

### 26. Apa itu permission?

Jawaban:

```text
Permission adalah hak akses tertentu yang menentukan aksi apa yang bisa dilakukan user, seperti membaca, membuat, mengubah, atau menghapus data.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Contoh role dalam sistem loan antara lain Customer, Credit Analyst, Approver, Finance, dan Admin, yang masing-masing memiliki tanggung jawab dan hak akses berbeda dalam proses pinjaman.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Contoh permission dalam sistem loan meliputi CREATE_LOAN, APPROVE_LOAN, DISBURSE_LOAN, dan VIEW_CUSTOMER_DATA, yang mengatur aksi spesifik yang dapat dilakukan user dalam proses pinjaman.
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
User yang sudah login belum tentu boleh melakukan semua action karena authorization tetap diperlukan untuk menentukan hak akses sesuai role atau permission yang dimiliki.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Resource-level authorization adalah metode authorization yang memastikan bahwa user hanya bisa mengakses resource tertentu yang memang menjadi haknya, bukan hanya berdasarkan role secara umum.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Role check saja tidak cukup karena role hanya menentukan jenis akses secara umum, tetapi tidak membatasi akses terhadap data spesifik yang dimiliki user.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah mekanisme untuk memastikan bahwa suatu resource (data) benar-benar dimiliki oleh user yang sedang mengaksesnya.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Contoh kasusnya adalah ketika customer mengubah loanId pada API dan sistem tidak melakukan ownership check, sehingga ia bisa melihat data loan milik customer lain yang seharusnya tidak boleh diakses.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Risiko IDOR (Insecure Direct Object Reference) sangat serius karena memungkinkan user mengakses resource yang bukan miliknya.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Backend mencegah data leakage dengan memvalidasi permission dan melakukan ownership check untuk memastikan user hanya bisa mengakses resource yang menjadi haknya.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
401 Unauthorized digunakan ketika request tidak memiliki kredensial yang valid, sehingga user dianggap belum terautentikasi.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
403 Forbidden digunakan ketika user sudah berhasil diidentifikasi, tetapi tidak memiliki hak akses terhadap resource atau action yang diminta.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 Unauthorized berarti user belum terautentikasi atau token tidak valid, sedangkan 403 Forbidden berarti user sudah terautentikasi tetapi tidak memiliki izin untuk mengakses resource.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Access log penting karena digunakan untuk mencatat semua aktivitas akses yang terjadi di sistem, sehingga membantu dalam monitoring, audit, dan investigasi.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
Field penting dalam access log meliputi timestamp, user ID, endpoint, method, status code, serta informasi tambahan seperti IP address dan action untuk memastikan aktivitas dapat dilacak secara lengkap.
```

## Self Assessment

| Area               | Score 1-5 |
| ------------------ | --------- |
| Authentication     |     2     |
| Authorization      |     2     |
| JWT                |     2     |
| RBAC               |     2     |
| Resource ownership |     2     |
| 401 vs 403         |     2     |
| Audit log          |     2     |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
