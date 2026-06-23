# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
proses untuk membuktikan atau memverifikasi identitas asli dari seorang pengguna, sistem, atau perangkat
```

### 2. Apa itu authorization?

Jawaban:

```text
proses menentukan hak akses atau izin yang dimiliki oleh pengguna yang identitasnya sudah terverifikasi
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication: Berfokus pada identitas. Terjadi di awal proses sebelum authorization.
Authorization: Berfokus pada hak akses. Terjadi setelah pengguna berhasil melewati tahap autentikasi.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap pengguna memiliki peran (role) dan tingkat kepentingan yang berbeda di dalam sistem demi menjaga keamanan
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Metode autentikasi di mana setelah pengguna berhasil login, server akan menghasilkan sebuah token
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Bagian dari HTTP request header yang digunakan oleh client untuk mengirimkan kredensial autentikasi (seperti token) ke server agar server dapat memverifikasi identitas dan hak akses request tersebut
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Sebuah skema autentikasi di dalam Authorization header (Authorization: Bearer <token>) yang berarti "siapa pun pemegang (bearer) token ini, berikan ia akses."
```

### 8. Apa itu JWT?

Jawaban:

```text
sebuah standar terbuka (RFC 7519) yang digunakan untuk berbagi informasi atau klaim secara aman antara dua pihak (client dan server) dalam bentuk objek JSON
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah potongan-potongan informasi berupa pasangan key-value yang disimpan di dalam bagian Payload JWT, yang menyatakan informasi tentang pengguna (subjek) serta metadata tambahan tentang token tersebut
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
1. sub (Subject): ID pengguna yang unik.
2. exp (Expiration Time): Waktu kapan token kedaluwarsa.
3. iat (Issued At): Waktu kapan token dibuat.
4. iss (Issuer): Pihak yang menerbitkan token.
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena bagian payload JWT hanya di-encode menggunakan Base64Url (bukan dienkripsi secara rahasia), sehingga siapa saja bisa mengubah isinya dengan mudah di sisi client
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif atau rahasia, seperti password, nomor kartu kredit, PIN
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Untuk membatasi jendela waktu penyalahgunaan jika token tersebut dicuri atau bocor ke pihak yang tidak bertanggung jawab
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access Token: Digunakan langsung untuk mengakses protected resources, berumur pendek (misal: 15 menit), dan dikirimkan pada setiap API request.
Refresh Token: Digunakan khusus untuk meminta access token baru ketika yang lama kedaluwarsa, berumur panjang (misal: 7 hari), dan disimpan sangat aman di sisi client tanpa dikirim pada setiap request resource.
```

### 15. Apa itu RBAC?

Jawaban:

```text
: Role-Based Access Control (RBAC) adalah metode pembatasan akses sistem di mana hak istimewa (permissions) dikelompokkan ke dalam peran-peran tertentu (roles)
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Permission: Tingkat instruksi aksi yang sangat spesifik terhadap suatu resource (contoh: create:customer, approve:loan).
Role: Kumpulan dari satu atau beberapa permission (contoh: Role STAFF memiliki permission create:customer dan read:customer)
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
ADMIN, STAFF, APPROVER, dan CUSTOMER
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
loan:create, loan:view-all, loan:approve, dan loan:reject
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena role check hanya memeriksa kelompok besar pengguna secara vertikal (kasar/broad), tetapi tidak bisa menangani otorisasi granular secara horizontal, seperti membatasi agar seorang CUSTOMER hanya bisa melihat data miliknya sendiri dan bukan milik CUSTOMER lain
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Mekanisme pemeriksaan hak akses yang memvalidasi apakah pengguna saat ini berhak melakukan tindakan pada entitas data (resource) spesifik yang diminta, bukan hanya sekadar memeriksa hak akses ke endpoint-nya secara umum.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Bagian dari resource-level authorization yang memastikan bahwa pengguna yang meminta operasi (seperti read, update, delete) pada suatu data adalah pemilik sah (owner) dari data tersebut (misal: mencocokkan user_id dari JWT dengan customer_id di baris database tabel loans).
```

### 22. Apa itu IDOR?

Jawaban:

```text
Insecure Direct Object Reference (IDOR) adalah kerentanan keamanan di mana aplikasi menerima input parameter berbasis ID objek langsung dari client (seperti /api/v1/loans/105) untuk mengakses data tanpa melakukan validasi otorisasi apakah pengguna tersebut berhak mengakses objek dengan ID tersebut.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan mengimplementasikan ownership check di sisi backend. Jangan pernah mengandalkan filter di frontend. Sisi backend wajib mengekstrak ID pengguna dari JWT token yang valid, lalu memvalidasi di query database bahwa resource yang dicari memang berelasi langsung dengan ID pengguna tersebut.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Ketika request yang masuk tidak memiliki kredensial autentikasi yang valid atau token tidak disertakan/kedaluwarsa (Pengguna belum terbukti siapa dia).
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Ketika pengguna sudah berhasil login (autentikasi sukses), namun berdasarkan aturan bisnis/RBAC, mereka tidak memiliki hak istimewa atau izin untuk mengakses resource yang diminta.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 Unauthorized: Masalah identitas (You don't log in/Who are you?).
403 Forbidden: Masalah hak akses (I know who you are, but you don't have permission to do this).
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Untuk mencegah kebocoran informasi (Information Disclosure) yang dapat dimanfaatkan oleh penyerang untuk memetakan arsitektur sistem, struktur database, atau menebak username mana yang valid di sistem (User Enumeration).
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Prinsip keamanan informasi di mana setiap pengguna, program, atau proses hanya diberikan hak akses seminimal mungkin yang mutlak diperlukan untuk menyelesaikan tugas atau fungsi kerjanya saja.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Penting untuk kebutuhan audit, pelacakan aktivitas mencurigakan (fraud detection), investigasi jika terjadi kebocoran atau manipulasi data, serta kepatuhan terhadap regulasi finansial nasional/internasional.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
timestamp, user_id/client_ip, http_method, request_url, status_code, dan user_agent.
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Ditulis dengan mendefinisikan skema keamanan (security schemes) pada dokumentasi API contract, menentukan endpoint mana saja yang membutuhkan token, jenis tokennya (misal: Bearer JWT), serta cakupan scope atau role yang diizinkan untuk memanggil endpoint tersebut.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger/OpenAPI menyediakan komponen securitySchemes (seperti bearerAuth) yang memungkinkan pengembang untuk memasukkan token JWT secara langsung melalui UI tombol "Authorize". Hal ini membuat pengujian API yang terproteksi menjadi mudah dan terdokumentasi rapi tanpa perlu alat pihak ketiga.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Sangat berbahaya karena client-side data bisa dimanipulasi dengan mudah oleh penyerang menggunakan intercepting tools (seperti Burp Suite) atau mengubah payload di HTTP request body/header. Penyerang bisa dengan mudah mengganti rolenya dari STAFF menjadi ADMIN untuk menguasai sistem jika backend tidak melakukan validasi ulang.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Jika token tersebut pernah bocor atau dicuri sekali saja, penyerang akan memiliki akses selamanya ke sistem secara ilegal tanpa bisa dihentikan kecuali kunci penandatangan JWT di-reset global (yang berakibat semua pengguna ter-logout).
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text

```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. TOKEN
2. MIDDLEWARE 
3. RBAC
```

Apa 2 hal yang masih membingungkan?

```text
1.
2.
```

Apa 1 pertanyaan untuk mentor?

```text
```
