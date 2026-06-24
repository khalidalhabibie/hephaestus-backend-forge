# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses untuk memastikan identitas user, misalnya dengan login menggunakan username dan password
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses untuk menentukan apa saja yang boleh dan tidak boleh dilakukan oleh user setelah dia ter authenticate
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication fokus ke siapa user tersebut, sedangkan authorization fokus ke hak akses atau izin apa yang dimiliki user
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap user punya role dan permission yang berbeda, jadi walaupun sudah login, belum tentu punya izin untuk semua action
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah metode authentication yang menggunakan token (misalnya JWT) sebagai bukti bahwa user sudah login
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header digunakan untuk mengirim token dari client ke server saat mengakses API yang protected
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Bearer token berarti siapa pun yang membawa token tersebut dianggap sebagai user yang sah
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah format token yang berisi informasi user dan digunakan untuk authentication dan authorization
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah data atau informasi yang disimpan di dalam JWT payload, seperti user ID atau role
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text

iss (Issuer), sub (Subject), exp (Expiration Time) → Waktu kedaluwarsa token, iat (Issued At) 
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload bisa dimodifikasi oleh client, jadi harus divalidasi signature-nya untuk memastikan data tidak diubah
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, PIN, nomor kartu, atau data rahasia lainnya
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Untuk membatasi waktu penggunaan token dan mengurangi risiko jika token bocor
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token digunakan untuk mengakses API dan biasanya short-lived, sedangkan refresh token digunakan untuk mendapatkan access token baru
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC (Role-Based Access Control) adalah sistem pengaturan akses berdasarkan role yang dimiliki user
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role adalah kumpulan permission, sedangkan permission adalah izin untuk melakukan action tertentu
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
Staff, Admin, dan Approver
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
create_loan, approve_loan, view_loan
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena user dengan role yang sama belum tentu boleh mengakses semua resource, misalnya data milik user lain
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Authorization yang mengecek apakah user boleh mengakses resource tertentu, bukan hanya berdasarkan role
```

### 21. Apa itu ownership check?

Jawaban:

```text
Pengecekan apakah resource tersebut memang dimiliki oleh user yang sedang login
```

### 22. Apa itu IDOR?

Jawaban:

```text
IDOR (Insecure Direct Object Reference) adalah celah keamanan saat user bisa mengakses data lain hanya dengan mengganti ID
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan melakukan ownership check dan memastikan user hanya bisa mengakses data miliknya sendiri
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum login atau token tidak valid
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah login tapi tidak punya izin untuk mengakses resource tersebut
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 berarti belum ter-authenticate, sedangkan 403 berarti sudah login tapi tidak punya permission
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Karena bisa dimanfaatkan attacker untuk mengetahui celah keamanan sistem
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Prinsip memberikan akses seminimal mungkin sesuai kebutuhan user
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Untuk audit, monitoring aktivitas, dan mendeteksi penyalahgunaan atau fraud
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
user_id, endpoint, method, timestamp, status_code
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Dengan menjelaskan jenis authentication, token yang dibutuhkan, dan role atau permission yang diperlukan
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Dengan menampilkan security scheme dan menandai endpoint mana yang membutuhkan authorization
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Client bisa memanipulasi role dan mendapatkan akses yang seharusnya tidak dimiliki
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Token bisa digunakan selamanya jika bocor, sehingga berisiko tinggi terhadap keamanan
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Memahami perbedaan role check, permission, dan resource-level authorization
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan authentication dan authorization
2. Cara kerja JWT dan token-based authentication
3. Pentingnya RBAC dan ownership check
```

Apa 2 hal yang masih membingungkan?

```text
1. Kapan sebaiknya menggunakan permission vs resource-level authorization
2. Implementasi RBAC yang rapi di backend
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice implementasi RBAC dan resource-level authorization di project nyata seperti finance?
```
