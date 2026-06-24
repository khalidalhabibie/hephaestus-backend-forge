# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
authentication adalah proses memeriksa identitas siapa kita
```

### 2. Apa itu authorization?

Jawaban:

```text
authorization adalah proses memberikan siapa ke akses tertentu
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
authentication adalah memeriksa identitas sedangkan authorization adalah memberikan akses
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena user dibatas akses nya sesuai role nya
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Metode autentikasi di mana server memberikan token (biasanya setelah login) dan client menggunakan token tersebut untuk mengakses resource tanpa perlu kirim username/password setiap request
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Header HTTP yang digunakan untuk mengirim kredensial (seperti token) dari client ke server untuk proses autentikasi/otorisasi
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Token yang digunakan dengan skema "Bearer", artinya siapa pun yang memiliki token tersebut dianggap sebagai pemilik akses tanpa verifikasi tambahan
```

### 8. Apa itu JWT?

Jawaban:

```text
JSON Web Token adalah format token berbasis JSON yang berisi informasi (payload) dan dilindungi dengan signature untuk memastikan integritas data
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Data atau informasi yang disimpan dalam payload JWT, seperti user ID, role, atau expiry
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
- sub (subject / user ID)
- exp (expiration time)
- iat (issued at)
- iss (issuer)
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload bisa dimodifikasi oleh pihak lain; hanya signature yang memastikan bahwa data tidak diubah dan berasal dari sumber terpercaya
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, PIN, OTP, atau informasi pribadi rahasia
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Untuk membatasi waktu penggunaan token agar mengurangi risiko penyalahgunaan jika token bocor
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
- Access token: digunakan untuk akses API, biasanya short-lived.
- Refresh token: digunakan untuk mendapatkan access token baru, biasanya lebih lama masa berlakunya
```

### 15. Apa itu RBAC?

Jawaban:

```text
Role-Based Access Control, yaitu mekanisme kontrol akses berdasarkan peran (role) pengguna
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
- Role: kumpulan dari permission (misalnya admin, user).
- Permission: hak spesifik untuk melakukan suatu aksi (misalnya read, write).
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
- Customer
- Loan Officer
- Credit Analyst
- Admin
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
- Create loan application
- Approve loan
- Reject loan
- View loan details
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena tidak menjamin user hanya mengakses data yang boleh diakses; perlu pengecekan lebih detail seperti ownership atau resource-level.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Pengecekan izin akses berdasarkan resource tertentu, misalnya memastikan user hanya bisa melihat data miliknya sendiri.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Validasi bahwa resource yang diakses memang dimiliki oleh user yang sedang login.
```

### 22. Apa itu IDOR?

Jawaban:

```text
Insecure Direct Object Reference, yaitu vulnerability di mana user bisa mengakses data lain hanya dengan mengubah ID (misalnya di URL).
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan melakukan ownership check di backend dan tidak hanya mengandalkan parameter dari client.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum terautentikasi atau token tidak valid/missing.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah terautentikasi tapi tidak memiliki izin untuk mengakses resource.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
- 401: belum login atau token invalid.
- 403: sudah login tapi tidak punya akses.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```
Karena bisa memberikan informasi sensitif kepada attacker untuk mengeksploitasi sistem.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Prinsip memberikan akses minimum yang diperlukan untuk menjalankan tugas, tidak lebih.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Untuk audit, deteksi fraud, investigasi insiden, dan memenuhi regulasi keamanan.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
- User ID
- Timestamp
- Endpoint/API
- HTTP method
- IP address
- Status code
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Dengan mendefinisikan mekanisme autentikasi (misalnya Bearer token) dan requirement authorization pada setiap endpoint.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Dengan mendefinisikan security schemes dan menunjukkan endpoint mana yang memerlukan authentication serta jenis token yang digunakan.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
User bisa memanipulasi role (misalnya menjadi admin) sehingga mendapatkan akses yang tidak sah.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Jika token bocor, bisa digunakan selamanya tanpa batas waktu, meningkatkan risiko penyalahgunaan.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
membuat security dan logic di service
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. RBAC
2. authentication
3. authorization
```

Apa 2 hal yang masih membingungkan?

```text
1. bingung di bagian security
2.
```

Apa 1 pertanyaan untuk mentor?

```text
cara membuat auth security dan util
```
