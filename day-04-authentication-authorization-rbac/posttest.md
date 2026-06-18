# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication ialah proses verifikasi identitas user sebelum diberikan akses ke sistem
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization merupakan proses menentukan hak akses atau izin yang dimiliki user setelah terautentikasi
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication memastikan siapa user, sedangkan Authorization menentukan apa yang boleh dilakukan user (hak)
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap user memiliki role dan permission yang berbeda meskipun sudah login
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication ialah metode autentikasi menggunakan token sebagai bukti login
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header digunakan untuk mengirimkan token autentikasi ke server/BackEnd
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Bearer token berarti siapa pun yang memegang token dapat mengakses resource
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah format token berbasis JSON yang digunakan untuk autentikasi dan pertukaran informasi
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim pada JWT adalah informasi atau data yang disimpan di dalam payload token
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
Contoh claim umum seperti adalah sub, exp, email, dan role
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload JWT bisa dimodifikasi sehingga harus divalidasi signature-nya terlebih dahulu
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, PIN, dan informasi rahasia lainnya
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Token perlu expiry agar mengurangi risiko penyalahgunaan jika token bocor
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk akses API, sedangkan refresh token digunakan untuk mendapatkan token baru
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC adalah mekanisme pengaturan akses berdasarkan role yang dimiliki user
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role adalah kelompok hak akses, sedangkan permission adalah izin spesifik terhadap suatu aksi
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
Contoh role adalah ADMIN, STAFF, dan CUSTOMER
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Contoh permission adalah create-loan, approve-loan, dan view-customer
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena role tidak cukup granular untuk mengontrol akses secara detail
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Resource-level authorization adalah pengaturan akses berdasarkan resource tertentu
```

### 21. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah pengecekan apakah user memiliki resource yang diakses
```

### 22. Apa itu IDOR?

Jawaban:

```text
IDOR adalah celah keamanan ketika user dapat mengakses resource milik user lain melalui manipulasi ID
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan melakukan ownership check dan validasi user terhadap data yang diakses
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
401 Unauthorized digunakan ketika user belum terautentikasi atau token tidak valid
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
403 Forbidden digunakan ketika user tidak memiliki izin untuk mengakses resource
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
Kalau 401 berkaitan dengan autentikasi, sedangkan 403 berkaitan dengan otorisasi
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Karena dapat membuka celah informasi bagi attacker untuk mengeksploitasi sistem
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Principle of least privilege adalah memberikan akses minimum yang diperlukan bagi user
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Access log penting untuk audit, monitoring, dan investigasi keamanan dalam sistem finance
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
Field penting meliputi timestamp, user_id, endpoint, method, dan status code
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Auth requirement ditulis dalam API contract dengan menyebutkan kebutuhan token atau role
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger/OpenAPI membantu menandai endpoint yang membutuhkan autentikasi dan mempermudah testing
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Karena client bisa memanipulasi role sehingga berisiko privilege escalation
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Token tanpa expiry berisiko digunakan selamanya jika bocor
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Bagian tersulit mungkin pada saat implementasi /me dan pengaturan security config, util, jwt, dan sebagainya
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan Authentication dan Authorization
2. Cara kerja JWT dalam sistem autentikasi
3. Pentingnya otorisasi di BackEnd
```

Apa 2 hal yang masih membingungkan?

```text
1. Best practice security config
2. Cara handle Authorization dengan baik dan benar
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice security config di Spring Java?
```
