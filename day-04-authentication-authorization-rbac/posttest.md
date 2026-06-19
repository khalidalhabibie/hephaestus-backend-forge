# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses memastikan kebeneran, seperti membuktikan bahwa data-data yang nasabah masukan memang milik mereka sesuai dengan identitas mereka sendiri.
```

### 2. Apa itu authorization?

Jawaban:

```text
Authorization adalah proses untuk menentukan hak akses user setelah dia berhasil login, yaitu apa saja yang boleh dan tidak boleh dilakukan user.
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication -> in practice menunjukkan identitas.
Authorization -> in practice menjawab apa saja yang boleh dilakukan.
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap user memiliki role dan permission yang berbeda, sehingga akses dibatasi sesuai dengan tanggung jawabnya.
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah proses autentikasi menggunakan token (e.g. user mendapatkan token untuk proses log in, terkadang ada setiap sesi log in satu token tapi ada juga yang dibatasi oleh waktu).
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
Authorization header berfungsi untuk mengirim token dari client ke server agar server bisa memverifikasi akses user.
```

### 7. Apa arti Bearer token?

Jawaban:

```text
Bearer token memiliki format untuk autentikasi pemilik token.
```

### 8. Apa itu JWT?

Jawaban:

```text
JWT  dalah token yang berisi data user (tidak sensitif)dan sudah ditandatangani secara digital untuk keamanan.
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah data atau informasi yang disimpan di dalam JWT (e.g. user ID, role, dan expiry time).
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
- role;
- scope;
- etc.
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena payload JWT bisa dimodifikasi oleh pihak lain jika signature tidak dicek, sehingga harus diverifikasi terlebih dahulu untuk memastikan keasliannya.
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Data sensitif seperti password, PIN, atau data sensitif lainnya karena JWT bisa di-decode, jadi bukan tempat aman untuk menyimpan data rahasia.
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
Token perlu expiry agar token tidak bisa dipakai selamanya jika token bocor. Dengan expiry, token hanya berlaku dalam waktu tertentu lalu harus diperbarui.
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token -> token utama untuk akses API, biasanya expiry pendek.
Refresh token -> dipakai untuk mendapatkan access token baru tanpa login ulang.
```

### 15. Apa itu RBAC?

Jawaban:

```text
RBAC adalah sistem kontrol akses berdasarkan role. Jadi akses user ditentukan dari perannya (e.g. admin, customer, atau staff).
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
Role -> “jabatan” atau kategori user.
Permission -> aksi yang boleh dilakukan dari setiap jabatan user.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
- Admin;
- Customer;
- Reviewer; dan;
- etc.
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
- Melihat data loan;
- Membuat loan application;
- Approval loan;
- etc.
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena kadang tidak semua data boleh diakses dalam satu role, jadi tetap perlu pengecekan tambahan seperti siapa pemilik datanya.
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
Authorization yang melihat data spesifik, misalnya user hanya boleh akses data miliknya sendiri, bukan milik orang lain.
```

### 21. Apa itu ownership check?

Jawaban:

```text
Ownership check adalah pengecekan apakah data yang mau diakses memang milik user tersebut, misalnya user hanya boleh lihat loan information miliknya sendiri.
```

### 22. Apa itu IDOR?

Jawaban:

```text
IDOR adalah celah keamanan ketika user bisa mengakses data orang lain hanya dengan mengganti ID di URL atau request.
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
Dengan memastikan backend selalu mengecek kepemilikan data berdasarkan user dari token, bukan dari input user.
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
401 Unauthorized digunakan ketika user belum login atau token tidak valid.
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
403 Forbidden digunakan ketika user sudah login tapi tidak punya izin untuk akses melakukan sesuatu.
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 -> belum login karena token salah.
403 -> sudah login tapi tidak boleh akses atau melakukan suatu activity.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
Error message security tidak boleh terlalu detail agar detail sistem kita tidak dapat dimanfaatkan oleh pihak-pihak tidak bertanggung jawab.
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
Principle of least privilege adalah proses memberikan akses seminimal mungkin sesuai kebutuhan user, jadi user hanya bisa melakukan hal yang dia butuhkan saja.
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
Access log penting dalam finance backend karena semua aktivitas user harus bisa dilacak untuk keamanan dan audit. Kalau ada masalah, kita bisa lihat siapa yang melakukan apa.
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
- User ID, endpoint;
- Waktu akses;
- Status request; dan;
- IP address;
- etc.
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
Biasanya ditulis bahwa endpoint butuh login, misalnya “harus menyertakan token di Authorization header”.
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
Swagger membantu menjelaskan endpoint mana yang butuh login dan bagaimana cara aksesnya, jadi seluruh developer dapat mudah memahami.
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
Risikonya adalah user bisa memalsukan role jadi admin dan mendapatkan akses ilegal. Hal ini dapat membahayakan karena terdapat banyak informasi-informasi penting yang bisa dimanfaatkan.
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
Risiko token tanpa expiry adalah bisa dipakai selamanya tanpa batas waktu, sehingga akan sangat berbahaya ketika user tidak log out dan device-nya dipakai orang lain maka seluruh informasinya dapat dilihat begitu saja oleh pihak tidak bertanggung jawab.
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
Bagian yang paling sulit biasanya memahami bagaimana authorization bekerja di backend, terutama saat menggabungkan role dan ownership check pada JWT.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Saya memahami perbedaan authentication dan authorization serta cara kerjanya;
2. Saya mulai memahami konsep RBAC dan pentingnya ownership check; dan;
3. Saya memahami kenapa 401 dan 403 itu berbeda dan penting di backend security.
```

Apa 2 hal yang masih membingungkan?

```text
1. Saya masih belum terlalu memahami bagaimana cara refresh token secara nyata di backend; dan;
2. Saya masih perlu latihan supaya lebih paham flow security secara keseluruhan.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara best practice implementasi JWT dan RBAC di project nyata supaya tetap aman tapi tidak ribet?
```
