# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses mengenali atau memverifikasi siapa user tersebut.
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses menentukan apa yang boleh dilakukan oleh user tersebut.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication menjawab siapa user, sedangkan authorization menjawab apa yang boleh dilakukan oleh user.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena authorization harus mengecek role, permission, dan konteks resource dari user tersebut. Sistem finance menerapkan deny by default untuk action berisiko.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Proses mengenali user secara stateless menggunakan token yang dikirim pada setiap request API.
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Digunakan oleh client untuk mengirim token ke backend pada setiap request API.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Digunakan oleh client untuk mengirim token ke backend pada setiap request API.
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah token yang membawa informasi atau claim tentang user untuk membantu backend mengenali user secara stateless.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah informasi payload di dalam JWT yang membantu backend mengenali user.
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
user_id, role, expiry, dan issuer.
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload bisa dibaca/dimodifikasi, jadi signature harus divalidasi untuk memastikan token tersebut asli dan belum diubah.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Password, sensitive customer data, full customer profile, internal secret atau API key, dan data yang sering berubah.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Untuk mengurangi risiko token digunakan secara tidak sah jika token tersebut dicuri.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token berdurasi pendek dan digunakan untuk akses API, sedangkan refresh token berdurasi lebih panjang dan digunakan untuk meminta access token baru.
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC (Role-Based Access Control) adalah mekanisme di mana akses ditentukan berdasarkan role yang merepresentasikan tanggung jawab user.
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role merepresentasikan tanggung jawab user, sedangkan permission menentukan action spesifik yang boleh dilakukan oleh role tersebut.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
customer, agent, credit_analyst, supervisor, dan admin.
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Create Loan, Approve Loan, dan View Report.
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena tidak cukup hanya punya akses ke endpoint, backend harus mengecek resource scope untuk mencegah data leakage.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Pengecekan apakah sebuah resource atau data memang berada di dalam lingkup user tersebut.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Pemeriksaan apakah data atau resource tersebut benar-benar milik user atau berada dalam scope-nya.
```

### 22. Apa itu IDOR?

Jawaban:

```text
Insecure Direct Object Reference (IDOR) adalah kerentanan keamanan di mana backend tidak melakukan ownership check, sehingga user bisa mengakses resource milik orang lain (misalnya mengganti ID di URL).
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan melakukan resource-level check atau ownership check di sisi backend sebelum mengembalikan data.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika token tidak ada, tidak valid, expired, atau backend belum bisa mengenali identitas user.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Ketika user sudah login dan identitas valid, tetapi tidak punya izin akses (misal role tidak cukup atau resource bukan miliknya).
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 Unauthorized menandakan kegagalan authentication (user belum dikenali), sedangkan 403 Forbidden menandakan kegagalan authorization (user dikenali tapi tidak punya hak akses).
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Karena bisa membocorkan detail internal backend kepada client atau pihak eksternal.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Prinsip di mana user hanya diberikan hak akses paling minimal yang dibutuhkan sesuai dengan tugas dan tanggung jawabnya.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Karena sistem harus bisa melacak siapa melakukan apa demi keperluan investigasi dan audit log.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
user_id, role, endpoint, result (allow/deny), dan correlation_id.
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Dituliskan secara jelas endpoint mana yang butuh token dan mencantumkan role requirement jika relevan.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger membantu menjelaskan endpoint mana yang membutuhkan bearer token.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Client dapat dengan mudah mengubah role-nya sendiri (misalnya menjadi admin), sehingga user biasa bisa mendapatkan akses penuh ke sistem secara tidak sah.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Risiko security besar karena token bisa dipakai secara bebas terlalu lama dan berpotensi besar untuk token tersebut dicuri.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Implementasi RBAC pada code.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan spesifik antara Authentication dan Authorization.
2. RBAC
3. Perbedaan 401 dan 403
```

Apa 2 hal yang masih membingungkan?

```text
1. Penggunaan middleware dalam implementasi code.
2. Penggunaan bearer token di Swagger UI dan Postman
```

Apa 1 pertanyaan untuk mentor?

```text
Mengapa output tes di Postman dan Swagger UI bisa berbeda padahal endpoint, header, authorization, dan body sama?
```
