# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication adalalah proses autentifikasi untuk memverifikasi identitas user ketika mengakses sistem.
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization adalah akses yang menentukan apa saja yang bisa dilakukan oleh user.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication berfokus pada verifikasi identitas sedangkan authorization lebih berfokus pada mengontrol akses.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena semua user memiliki kebutuhan dan tugas yang berbeda.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah metode autentikasi yang memberikan token kepada user yang bisa digunakan sebagai identifier user ketika user membutuhkan akses.
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header digunakan untuk mengirimkan kredensial (biasanya token) ke backend sebagai bukti identitas.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Bearer token adalah token yang sudah menjamin bahwa user yang membawanya adalah user yang valid.
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah format token yang digunakan untuk authentication dan authorization dengan cara menyimpan data user dalam bentuk yang aman dan terstruktur.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah potongan informasi tentang sebuah entitas yang dikemas dalam bentuk objek JSON.
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
- iss (Issuer) -> pihak yang menerbitkan token
- sub (Subject) -> subjek atau pengguna yang menjadi pemilik token
- exp (Expiration Time) -> waktu kedaluwarsa token. Setelah waktu ini, token tidak lagi valid.
- iat (Issued At) -> waktu kapan token tersebut dibuat atau diterbitkan
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena kita harus memverifikasi signature untuk memastikan bahwa payload itu asli agar payload tidak bisa dimanipulasi oleh siapa saja.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti data identitas, password, API key, dsb.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Agar akses yang diberikan tidak berlaku selamanya yang meningkatkan risiko kebocoran data.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access Token digunakan untuk akses API dan dipakai di setiap request, sedangkan refresh token digunakan untuk mendapatkan access token baru dan dipakai saat token expired.
```

### 15. Apa itu RBAC?

Jawaban:

```text
Role Based Access Control adalah pengendalian akses yang dibedakan berdasarkan role user yang masuk, misalnya role admin memiliki akses yang berbeda dengan role user biasa.
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role adalah peran yang dimiliki oleh user yang menentukan akses dan aktivitas yang bisa dilakukan oleh user, sedangkan permission adalah kontrol akses atau izin untul melakukan aktivitas tertentu sesuai dengan role user.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
customer, analyst, HO, admin, super admin, dsb.
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Contoh permission untuk customer:
- Registrasi & login
- Mengajukan pinjaman
- Melihat status pengajuan
- Melihat riwayat pembayaran
- Melakukan pembayaran
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Role check tidak cukup karena hanya memeriksa role user, tapi tidak memeriksa data atau akses apa saja yang diizinkan untuk dilakukan oleh user tsb.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Resource-level authorization adalah mekanisme authorization yang memastikan user hanya bisa mengakses resource tertentu yang diizinkan.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah bagian dari authorization yang memastikan bahwa user adalah pemilik dari resource yang ingin diakses.
```

### 22. Apa itu IDOR?

Jawaban:

```text
IDOR (Insecure Direct Object Referenc) adalah jenis kerentanan keamanan pada aplikasi web yang terjadi ketika sebuah aplikasi mengakses objek/data berdasarkan input langsung dari user (seperti ID) tanpa melakukan pengecekan hak akses dengan benar sehingga pengguna bisa mengakses data milik orang lain hanya dengan mengganti ID di URL atau request.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Kita harus menerapkan authorization (otorisasi) yang benar di backend dengan cara melakukan ownership check, menerapkan RBAC, serta melakukan validasi di backend, dsb.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika user yang ingin masuk tidak ada token atau token invalid / expired.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Ketika user tidak punya izin di halaman tsb (tidak punya permission meskipun identitasnya valid dan sudah bisa login).
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 ditampilkan ketika user belum terautentikasi.
403 ditampilkan ketika sudah terautentikasi tapi tidak diizinkan.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Error message security tidak boleh terlalu detail karena mengungkap struktur sistem dan membocorkan informasi sensitif sehingga bisa membantu attacker memahami sistem dan menemukan celah.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Principle of Least Privilege adalah prinsip keamanan di mana setiap user, sistem, atau proses hanya boleh memiliki akses minimum yang diperlukan untuk menjalankan tugasnya.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Untuk mencatat semua aktivitas request yang masuk sehingga lebih tracable apabila ada masalah. Hal ini sangat penting terutama dalam industri finance yang besifat high risk dan sangat sensitif.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
Timestamp, userId, method, endpoint, status code, IP address
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Contoh:
paths:
  /orders/{id}:
    get:
      summary: Get order detail
      security:
        - BearerAuth: []
      description: |
        Authorization:
        - User must be owner of the order
        - Role: CUSTOMER or ADMIN

      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string

      responses:
        200:
          description: Success
        403:
          description: Forbidden (not owner)
        401:
          description: Unauthorized (missing/invalid token)
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger/OpenAPI membantu dokumentasi dengan mendefinisikan auth, menandai endpoint yang protected, menjelaskan role & permission, membedakan 401 vs 403, dan menyediakan UI untuk testing auth.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Maka ada potensi attacker bisa dengan mudah memanipulasinya untuk mendapatkan akses lebih tinggi (privilege escalation) dan mengontrol sistem secara tidak sah.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Token tanpa expiry berisiko karena token bisa digunakan selamanya jika bocor atau dicuri.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Validasi token.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Authentication vs. Authorization
2. 401 vs. 403
3. Semakin memantapkan pembuatan DTO dan penambahan dummy data di Service Layer
```

Apa 2 hal yang masih membingungkan?

```text
1. Validasi token
2. Pembuatan helper untuk membaca token
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana alur validasi token yang sebaiknya dilakukan?
```
