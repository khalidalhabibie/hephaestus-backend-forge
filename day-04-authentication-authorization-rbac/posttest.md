# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses untuk memastikan bahwa seseorang atau sistem benar-benar memiliki identitas yang diklaim sebelum diberikan akses.
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses untuk menentukan hak atau izin yang dimiliki oleh seseorang atau sistem terhadap sumber daya setelah identitasnya berhasil diverifikasi.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Perbedaan authentication dan authorization adalah bahwa authentication digunakan untuk memastikan identitas pengguna (siapa kamu), sedangkan authorization digunakan untuk menentukan hak atau akses pengguna tersebut terhadap suatu sistem atau data (apa yang boleh kamu lakukan).
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
User yang sudah login belum tentu boleh melakukan semua action karena login (authentication) hanya memastikan siapa pengguna tersebut, sedangkan hak untuk melakukan aksi tertentu ditentukan oleh authorization sesuai dengan peran atau izin yang dimilikinya.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah metode autentikasi yang menggunakan token (sebuah string unik) sebagai bukti bahwa pengguna sudah berhasil login, sehingga pengguna tidak perlu mengirimkan username dan password berulang kali dalam setiap permintaan ke server.
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header adalah bagian dari HTTP request yang digunakan untuk mengirimkan kredensial atau token autentikasi dari client ke server agar server dapat memverifikasi identitas dan menentukan hak akses pengguna.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Bearer token adalah jenis token autentikasi yang berarti siapa pun yang “membawa” token tersebut dianggap sebagai pengguna yang sah dan dapat mengakses sistem tanpa perlu verifikasi tambahan.
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT (JSON Web Token) adalah sebuah format token berbasis JSON yang digunakan untuk mengirimkan informasi secara aman antara client dan server dalam proses authentication atau authorization.
JWT biasanya berisi data seperti identitas pengguna (user ID), role, dan informasi lainnya, serta dilengkapi dengan signature untuk memastikan data tidak diubah.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim pada JWT adalah informasi atau data yang disimpan di dalam token yang menjelaskan sesuatu tentang pengguna atau konteks autentikasi.
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
4 claim yang umum pada JWT adalah iss (issuer) yang menunjukkan pihak pembuat token, sub (subject) yang menunjukkan identitas pengguna, aud (audience) yang menunjukkan pihak yang menjadi tujuan token, dan exp (expiration time) yang menunjukkan waktu kadaluarsa token.
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
JWT payload tidak boleh dipercaya sebelum signature divalidasi karena isi payload dapat dengan mudah dibaca dan dimanipulasi oleh pihak lain, sehingga hanya signature yang memastikan bahwa data tersebut benar-benar asli dan tidak diubah.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data yang tidak boleh disimpan di JWT adalah informasi sensitif seperti password, nomor kartu kredit, PIN, data pribadi rahasia, dan private key, karena payload JWT dapat dengan mudah dibaca oleh siapa saja yang memiliki token tersebut meskipun sudah di-encode, sehingga berisiko disalahgunakan jika bocor.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Token perlu expiry agar membatasi masa berlaku akses pengguna, sehingga jika token bocor atau dicuri, tidak bisa digunakan selamanya dan risiko penyalahgunaan dapat diminimalkan.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Perbedaan access token dan refresh token adalah bahwa access token digunakan untuk mengakses resource atau API dan memiliki masa berlaku yang singkat, sedangkan refresh token digunakan untuk mendapatkan access token baru tanpa perlu login ulang dan biasanya memiliki masa berlaku lebih lama.
Provide your feedback on BizChat
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC (Role-Based Access Control) adalah metode pengaturan hak akses di mana izin pengguna ditentukan berdasarkan perannya (role) dalam sistem, sehingga setiap pengguna hanya dapat melakukan aksi sesuai dengan role yang dimilikinya.
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Perbedaan role dan permission adalah bahwa role merupakan kumpulan dari beberapa permission yang dikelompokkan berdasarkan jabatan atau fungsi pengguna, sedangkan permission adalah izin spesifik untuk melakukan suatu aksi tertentu dalam sistem.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
Contoh role dalam loan system adalah loan officer yang bertugas memproses pengajuan, credit analyst yang menilai kelayakan kredit, customer service yang melayani nasabah, dan admin yang mengelola data serta sistem.
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
Contoh permission dalam loan system adalah melihat data pengajuan pinjaman, membuat pengajuan baru, menyetujui atau menolak pinjaman, mengubah data nasabah, serta menghapus atau mengekspor data pinjaman.
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Role check saja tidak cukup karena role hanya bersifat umum dan tidak selalu mencerminkan izin spesifik yang dibutuhkan untuk setiap aksi, sehingga tetap diperlukan permission yang lebih detail untuk mengontrol akses secara lebih tepat dan aman.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Resource-level authorization adalah proses penentuan hak akses yang dilakukan tidak hanya berdasarkan role atau permission, tetapi juga mempertimbangkan kepemilikan atau keterkaitan pengguna dengan resource tertentu, sehingga pengguna hanya dapat mengakses atau mengubah data yang menjadi miliknya atau yang diizinkan secara spesifik.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah proses untuk memastikan bahwa pengguna hanya dapat mengakses atau melakukan aksi pada resource yang memang menjadi miliknya atau yang berhak ia kelola.
```

### 22. Apa itu IDOR?

Jawaban:

```text
IDOR (Insecure Direct Object Reference) adalah kerentanan keamanan di mana aplikasi memberikan akses langsung ke suatu resource berdasarkan ID atau parameter tanpa melakukan pengecekan otorisasi yang memadai, sehingga pengguna dapat mengakses data milik orang lain hanya dengan mengubah nilai tersebut.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Cara mencegah customer melihat data customer lain adalah dengan menerapkan authorization yang ketat seperti ownership check dan resource-level authorization, sehingga setiap request harus divalidasi bahwa pengguna hanya boleh mengakses data yang memang menjadi miliknya.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
401 Unauthorized digunakan ketika request tidak memiliki kredensial autentikasi yang valid atau belum login, sehingga server tidak dapat mengidentifikasi pengguna yang melakukan request tersebut.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
403 Forbidden digunakan ketika pengguna sudah terautentikasi (sudah login), tetapi tidak memiliki izin untuk mengakses resource atau melakukan aksi yang diminta.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
Perbedaan 401 dan 403 adalah bahwa 401 Unauthorized digunakan ketika pengguna belum terautentikasi atau kredensial tidak valid, sedangkan 403 Forbidden digunakan ketika pengguna sudah terautentikasi tetapi tidak memiliki izin untuk mengakses resource yang diminta.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Error message pada keamanan tidak boleh terlalu detail karena dapat memberikan informasi sensitif kepada attacker yang bisa dimanfaatkan untuk menemukan celah atau menyerang sistem.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Principle of least privilege adalah prinsip keamanan yang menyatakan bahwa setiap pengguna atau sistem hanya boleh diberikan akses minimum yang diperlukan untuk menjalankan tugasnya, tidak lebih.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Access log penting dalam finance backend karena digunakan untuk mencatat semua aktivitas pengguna sehingga dapat membantu audit, mendeteksi penyalahgunaan, serta memastikan keamanan dan kepatuhan terhadap regulasi.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
Field penting dalam access log adalah timestamp (waktu kejadian), user ID atau identitas pengguna, IP address, endpoint atau resource yang diakses, metode request (GET, POST, dll), serta status response (misalnya 200, 401, 403).
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Auth requirement dalam API contract ditulis dengan menjelaskan mekanisme autentikasi dan otorisasi yang harus dipenuhi oleh client, seperti penggunaan token pada header (misalnya Authorization: Bearer <token>), serta aturan akses yang menentukan siapa saja yang boleh mengakses endpoint tersebut.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger/OpenAPI membantu dokumentasi endpoint yang protected dengan cara mendefinisikan skema autentikasi (seperti Bearer token) dan menandai endpoint mana saja yang memerlukan autentikasi, sehingga pengguna API dapat dengan jelas mengetahui cara mengaksesnya dan izin apa yang dibutuhkan.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Risikonya adalah client dapat memanipulasi role tersebut, sehingga pengguna bisa berpura-pura memiliki hak akses lebih tinggi (misalnya menjadi admin) dan mendapatkan akses yang seharusnya tidak diizinkan.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Risiko token tanpa expiry adalah token dapat digunakan selamanya jika bocor atau dicuri, sehingga attacker bisa mengakses sistem tanpa batas waktu dan meningkatkan potensi penyalahgunaan secara serius.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Bagian yang paling sulit dari Day 4 adalah memastikan implementasi authorization dilakukan secara konsisten dan aman di semua endpoint, terutama dalam menangani detail seperti permission, ownership check, dan mencegah celah seperti IDOR.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Authentication memastikan identitas pengguna
2. Authorization menentukan hak akses pengguna
3. Keamanan membutuhkan kontrol detail seperti permission, ownership check, dan penggunaan token yang aman
```

Apa 2 hal yang masih membingungkan?

```text
1. Perbedaan implementasi antara role-based dan permission-based secara praktis di sistem nyata
2. Cara menerapkan resource-level authorization dan ownership check secara konsisten di semua endpoint
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana best practice menerapkan authorization (role, permission, dan ownership check) agar konsisten dan scalable di sistem backend nyata?
```
