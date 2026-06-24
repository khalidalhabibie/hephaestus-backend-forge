# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Tulis jawaban di sini.
Authentication adalah proses verifikasi identitas user untuk memastikan bahwa user yang mengakses sistem adalah benar-benar siapa yang dia klaim. Contohnya: login dengan username dan password, lalu sistem memverifikasi kecocokan credential tersebut.
```

### 2. Apa itu authorization?

Jawaban:

```text
Tulis jawaban di sini.
Authorization adalah proses menentukan apa saja yang boleh dan tidak boleh dilakukan oleh user yang sudah terautentikasi setelah identitasnya terverifikasi. Authorization mengatur hak akses terhadap resource atau action tertentu.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Tulis jawaban di sini.
Authentication menjawab pertanyaan "Siapa kamu?" (verifikasi identitas), sedangkan authorization menjawab pertanyaan "Apa yang boleh kamu lakukan?" (verifikasi hak akses). Authentication terjadi pertama kali, baru kemudian authorization.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Tulis jawaban di sini.
Karena setiap user memiliki role dan permission yang berbeda. User yang sudah login (terautentikasi) belum tentu memiliki hak untuk melakukan action tertentu. Contoh: staff boleh membuat loan, tapi tidak boleh approve loan.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Tulis jawaban di sini.
Token-based authentication adalah metode autentikasi di mana server mengeluarkan token (seperti JWT) setelah user berhasil login. Token ini kemudian dikirimkan oleh client di setiap request berikutnya untuk membuktikan identitas tanpa perlu mengirim ulang username/password.
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Tulis jawaban di sini.
Authorization header digunakan client untuk mengirimkan credential atau token ke server pada setiap HTTP request. Fungsinya membuktikan bahwa request tersebut berasal dari user yang sudah terautentikasi dan berhak mengakses resource.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Tulis jawaban di sini.
Bearer token adalah skema autentikasi di HTTP header di mana client mengirimkan token dengan format "Bearer <token>". Artinya siapa pun yang "membawa" (bearer) token ini dianggap memiliki hak akses yang melekat pada token tersebut.
```

### 8. Apa itu JWT?

Jawaban:

```text
Tulis jawaban di sini.
JWT (JSON Web Token) adalah token berbasis JSON yang terdiri dari 3 bagian: header, payload, dan signature. JWT digunakan untuk pertukaran informasi antar pihak secara aman dan stateless, umumnya untuk autentikasi dan authorization.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Tulis jawaban di sini.
Claim adalah pasangan key-value yang berisi informasi/data dalam payload JWT. Claim menyimpan data seperti identitas user, role, waktu pembuatan token, dan waktu kedaluwarsa.
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
Tulis jawaban di sini.
1. sub (subject) - identitas user, biasanya username atau user ID
2. iat (issued at) - waktu token dibuat
3. exp (expiration) - waktu token kedaluwarsa
4. role - peran/role user (contoh: STAFF, ADMIN, MANAGER)
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Tulis jawaban di sini.
Karena payload JWT hanya di-encode Base64, bukan di-encrypt. Siapa pun bisa membaca dan memodifikasi payload. Tanpa validasi signature, attacker bisa membuat token palsu. Signature memastikan token benar-benar dibuat oleh server yang memiliki secret key.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Tulis jawaban di sini.
Data sensitif seperti password, nomor KTP, nomor kartu kredit, informasi rekening bank, data kesehatan, atau data pribadi lainnya yang bersifat rahasia. JWT bisa di-decode oleh siapa saja yang memiliki token.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Tulis jawaban di sini.
Untuk membatasi masa berlaku token sehingga jika token dicuri atau bocor, attacker tidak bisa menggunakannya selamanya. Expiry juga memaksa user untuk login ulang secara periodik untuk memastikan identitasnya masih valid.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Tulis jawaban di sini.
Access token adalah token dengan masa berlaku pendek (menit/jam) untuk mengakses API. Refresh token adalah token dengan masa berlaku lebih panjang (hari/minggu) yang digunakan untuk mendapatkan access token baru tanpa login ulang.
```

### 15. Apa itu RBAC?

Jawaban:

```text
Tulis jawaban di sini.
RBAC (Role-Based Access Control) adalah model kontrol akses di mana permission diberikan berdasarkan role. User diberi role tertentu, dan role tersebut menentukan apa saja yang boleh dilakukan user dalam sistem.
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Tulis jawaban di sini.
Role adalah label/jabatan yang dimiliki user (contoh: ADMIN, STAFF). Permission adalah hak akses spesifik untuk melakukan action tertentu (contoh: CREATE_LOAN, APPROVE_LOAN). Role adalah kumpulan dari beberapa permission.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
Tulis jawaban di sini.
1. STAFF - membuat customer dan loan application
2. APPROVAL - menyetujui atau menolak loan application
3. ADMIN - melakukan semua action termasuk delete dan manage user
4. MANAGER - approve loan dengan nominal di atas threshold tertentu
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Tulis jawaban di sini.
1. CUSTOMER_CREATE - membuat data customer baru
2. LOAN_CREATE - membuat pengajuan pinjaman
3. LOAN_APPROVE - menyetujui pengajuan pinjaman
4. LOAN_REJECT - menolak pengajuan pinjaman
5. CUSTOMER_DELETE - menghapus data customer
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Tulis jawaban di sini.
Karena role check hanya memastikan user memiliki jabatan tertentu, tapi tidak memverifikasi apakah user berhak mengakses data/resource spesifik. Contoh: staff boleh melihat loan, tapi tidak boleh melihat loan milik customer lain (ownership check).
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Tulis jawaban di sini.
Resource-level authorization adalah pemeriksaan hak akses terhadap data/resource spesifik, bukan hanya endpoint. Contoh: user A boleh mengedit profil user A, tapi tidak boleh mengedit profil user B meskipun keduanya memiliki role yang sama.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Tulis jawaban di sini.
Ownership check adalah verifikasi bahwa user yang mengakses data adalah pemilik data tersebut atau memiliki hak atas data tersebut. Contoh: customer hanya boleh melihat data loan miliknya sendiri, bukan milik customer lain.
```

### 22. Apa itu IDOR?

Jawaban:

```text
Tulis jawaban di sini.
IDOR (Insecure Direct Object Reference) adalah celah keamanan di mana attacker bisa mengakses data milik user lain dengan memanipulasi ID parameter pada URL/request, karena sistem tidak melakukan ownership check.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Tulis jawaban di sini.
Dengan melakukan ownership check: setiap kali ada request untuk mengakses data customer, sistem harus memverifikasi bahwa customer_id pada request sesuai dengan customer_id yang terdapat pada token/JWT user yang sedang login.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Tulis jawaban di sini.
401 digunakan ketika user belum login atau token tidak valid/expired. Artinya user tidak terautentikasi dan perlu menyertakan credential yang valid untuk mengakses resource.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Tulis jawaban di sini.
403 digunakan ketika user sudah login (terautentikasi) tetapi tidak memiliki permission/role yang diperlukan untuk mengakses resource atau melakukan action tertentu.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
Tulis jawaban di sini.
401 berarti "belum login atau token salah" (autentikasi gagal). 403 berarti "sudah login tapi tidak punya hak akses" (autorization gagal). 401 = who are you?, 403 = I know who you are, but you can't do this.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Tulis jawaban di sini.
Karena error message yang terlalu detail bisa memberikan informasi kepada attacker tentang struktur sistem, data yang ada, atau alasan kegagalan. Contoh: jangan tunjukkan "user admin tidak ada" vs "password salah", cukup "username atau password invalid".
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Tulis jawaban di sini.
Prinsip di mana user atau proses hanya diberikan permission minimum yang diperlukan untuk menyelesaikan tugasnya. Tujuannya mengurangi surface area serangan dan dampak jika credential user bocor.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Tulis jawaban di sini.
Karena access log digunakan untuk audit trail, deteksi anomali, investigasi fraud, memenuhi regulasi compliance (seperti OJK/BI), dan forensik jika terjadi keamanan incident.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
Tulis jawaban di sini.
1. Timestamp
2. User ID / Username
3. IP Address
4. HTTP Method
5. Endpoint / URL
6. Request/Response Status Code
7. Action yang dilakukan
8. Resource ID yang diakses
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Tulis jawaban di sini.
Ditulis dengan menyebutkan skema autentikasi (Bearer Token), role yang diizinkan, dan contoh header Authorization. Contoh: "Requires Bearer token. Role: ADMIN, STAFF."
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Tulis jawaban di sini.
Swagger/OpenAPI bisa menambahkan security scheme (Bearer JWT) dan menampilkan icon gembok pada endpoint yang membutuhkan autentikasi. Developer bisa langsung test endpoint dengan memasukkan token di Authorize modal.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Tulis jawaban di sini.
Attacker bisa memanipulasi role di request body menjadi ADMIN dan mendapatkan hak akses penuh. Role harus selalu ditentukan oleh server (backend) berdasarkan data di database, bukan dari input client.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Tulis jawaban di sini.
Jika token dicuri atau bocor, attacker bisa menggunakannya untuk selamanya (permanent access). Tidak ada cara untuk mencabut atau invalidate token tanpa mengubah secret key server.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Tulis jawaban di sini.
(Tulis pengalaman pribadi, contoh:) Menentukan kombinasi role dan permission yang tepat untuk loan system, serta memastikan resource-level authorization (ownership check) berjalan benar tanpa merusak UX.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan authentication dan authorization serta implementasinya di Spring Boot
2. Cara kerja JWT dan pentingnya signature validation serta expiry
3. Implementasi RBAC dengan role-based access control dan resource-level authorization
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara terbaik mengimplementasikan refresh token tanpa menambah kompleksitas berlebihan
2. Strategi ownership check yang efisien untuk relasi data yang kompleks (many-to-many)
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice menggabungkan RBAC dengan ABAC (Attribute-Based Access Control) dalam sistem loan yang memiliki banyak cabang/regional dengan aturan approval berbeda?
```
