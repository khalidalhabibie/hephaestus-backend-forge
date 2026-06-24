# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
proses verifikasi identitas user
```

### 2. Apa itu authorization?

Jawaban:

```text
proses menentukan hak akses user terhadap resource
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
authentication memverifikasi siapa user, authorization menentukan apa yang boleh dilakukan user
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena setiap user punya role dan hak akses yang berbeda
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
metode login menggunakan token sebagai bukti identitas setelah berhasil login
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
untuk mengirim token sebagai bukti autentikasi ke server
```

### 7. Apa arti Bearer token?

Jawaban:

```text
token yang digunakan oleh siapa saja yang membawanya sebagai akses autentikasi
```

### 8. Apa itu JWT?

Jawaban:

```text
JSON Web Token adalah format token yang berisi data user dan signature
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
informasi atau data yang disimpan di dalam payload JWT
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
sub (subject), exp (expiry), iat (issued at), iss (issuer)
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
karena payload bisa dimodifikasi jika tidak divalidasi signature-nya
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
password, data sensitif, informasi pribadi yang rahasia
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
untuk membatasi waktu penggunaan agar lebih aman
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
access token untuk akses API, refresh token untuk mendapatkan token baru
```

### 15. Apa itu RBAC?

Jawaban:

```text
Role-Based Access Control adalah sistem pembatasan akses berdasarkan role
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
role adalah kategori user, permission adalah aksi yang boleh dilakukan
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
ADMIN, STAFF, APPROVER
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
create loan, approve loan, view data
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
karena perlu kontrol lebih detail seperti akses data tertentu
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
pembatasan akses berdasarkan resource tertentu
```

### 21. Apa itu ownership check?

Jawaban:

```text
pengecekan apakah user memiliki resource tersebut
```

### 22. Apa itu IDOR?

Jawaban:

```text
Insecure Direct Object Reference adalah celah akses data tanpa validasi
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
dengan mengecek ownership dan membatasi akses berdasarkan user
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
ketika user belum login atau token tidak valid
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
ketika user sudah login tapi tidak punya akses
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 untuk belum login, 403 untuk tidak punya izin
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
agar tidak memberikan informasi ke attacker
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
memberikan akses seminimal mungkin sesuai kebutuhan
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
untuk audit dan melacak aktivitas user
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
timestamp, user, endpoint, method, status
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
dengan mencantumkan header Authorization dan role yang diperbolehkan
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
menampilkan requirement auth dan menyediakan input token
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
user bisa memanipulasi role dan mendapatkan akses ilegal
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
token bisa digunakan terus menerus jika bocor
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
implementasi RBAC dan validasi token di setiap endpoint
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. perbedaan authentication dan authorization
```

Apa 2 hal yang masih membingungkan?

```text
1. pada bagian membuat API
```

Apa 1 pertanyaan untuk mentor?

```text
Tidak Ada
```
