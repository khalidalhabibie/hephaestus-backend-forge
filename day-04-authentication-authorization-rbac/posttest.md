# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Proses memastikan siapa user yang sedang menggunakan sistem.
```

### 2. Apa itu authorization?

Jawaban:

```text
Proses mengecek apakah user boleh melakukan suatu tindakan atau tidak.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication untuk memastikan siapa usernya, sedangkan authorization untuk menentukan apa yang boleh dilakukan user tersebut.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena login hanya membuktikan identitas, sedangkan hak aksesnya tetap harus dicek.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Cara login yang menggunakan token sebagai bukti bahwa user sudah berhasil login.
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Untuk mengirim token ke server saat meminta data atau menjalankan suatu proses.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Format pengiriman token pada request ke server.
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT adalah token digital yang digunakan sebagai bukti bahwa user sudah login.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah informasi yang disimpan di dalam JWT.
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
userId, username, role, dan expiry time.
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena isi JWT bisa dilihat dan diubah, jadi signature harus dicek terlebih dahulu untuk memastikan datanya masih asli.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, data pribadi, dan informasi sensitif lainnya.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Agar token tidak bisa digunakan terus-menerus jika jatuh ke tangan yang salah.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk mengakses layanan, sedangkan refresh token digunakan untuk meminta access token baru.
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC adalah pengaturan hak akses berdasarkan role yang dimiliki user.
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role adalah peran user, sedangkan permission adalah hak untuk melakukan suatu tindakan.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
Admin, Staff, dan Approver.
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Melihat loan, membuat loan, mengubah data customer, dan approve loan.
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena user bisa saja memiliki role yang benar tetapi tetap tidak boleh mengakses data milik orang lain.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Pengecekan akses berdasarkan data tertentu yang sedang diakses.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Pengecekan untuk memastikan user hanya bisa mengakses data miliknya sendiri.
```

### 22. Apa itu IDOR?

Jawaban:

```text
Celah keamanan yang membuat seseorang bisa mengakses data milik orang lain hanya dengan mengganti ID.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan mengecek pemilik data sebelum data ditampilkan.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum login atau token tidak valid.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah login tetapi tidak memiliki izin untuk melakukan tindakan tersebut.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 berarti belum login atau token bermasalah, sedangkan 403 berarti sudah login tetapi tidak memiliki akses.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Karena bisa memberikan informasi yang dapat dimanfaatkan oleh orang yang ingin menyerang sistem.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Memberikan hak akses seperlunya saja sesuai kebutuhan user.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Untuk mengetahui siapa yang melakukan akses, kapan dilakukan, dan apa yang dilakukan.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
User ID, waktu, endpoint, method, status, dan IP address.
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Dengan menjelaskan apakah endpoint memerlukan login, token, dan role tertentu.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger menunjukkan endpoint mana yang membutuhkan login dan token untuk diakses.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
User bisa memalsukan role dan mendapatkan akses yang tidak seharusnya.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Token bisa digunakan selamanya jika bocor atau dicuri.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Memahami JWT, role, dan pengecekan akses pada setiap endpoint.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan authentication dan authorization.
2. Cara kerja JWT dan token.
3. Pentingnya role dan pengecekan akses pada backend.
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara kerja signature pada JWT.
2. Implementasi authorization yang lebih kompleks pada project nyata.
```

Apa 1 pertanyaan untuk mentor?

```text
Kapan sebaiknya menggunakan role check saja dan kapan harus menambahkan ownership check?
```
