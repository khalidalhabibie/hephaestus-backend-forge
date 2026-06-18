# Pretest - Authentication, Authorization & RBAC

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang authentication, authorization, JWT, RBAC, dan access control pada backend finance.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Security Mindset

### 1. Kenapa access control penting dalam sistem finance?

Jawaban:

```text
Agar keamanan data terjaga, karena bersangkutan dengan uang/finansial.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
User bisa melakukan hal hal yang tidak diinginkan, misal; mentrasnfer uang, menghapus sesuatu, update sesuatu dll
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
data data sensitif seperti ; nik, nama ibu kandung, nama pasangan, service service yang menyangkut data customer dan loansewrvice dll.
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena jika ada orang yang tidak bertanggung jawab bisa langsung masuk ke server, aplikasi bisa di retas dan mengakibatkan kebocoran data, hacker bisa melakukan hal hal yang tidak diinginkan.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
ditolak karena tidak memiliki role / hak akses.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Proses backend mengidentifikasi user yang ingin masuk ke aplikasi ; baik itu mengidentifikasi apakah user ada di database, role dan aksesnya apa dll.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Autentikasi menggunakan JWT -> token JWT ini akan menjadi penanda user memili akses ke aplikasi, aksesnya kemana saja, dan aksesnya sampai kapan.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Untuk membawa akses token yang di generate pada proses autentikasi awal, agar backend bisa memverifikasi user bisa mengakses apa aja.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer token adalah kunci digital berupa string acak yang memberikan akses otomatis ke sumber daya sistem kepada siapa pun yang memegangnya
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Memvalidasi apakah token benar, akses timenya masih ada, role nya apa, agar backend bisa menentukan user masih bisa menggunakan aplikasi dan mengakses apa aja.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
User tidak bisa mengakses aplikasi
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
User tidak bisa mendapatkan akses tertentu diluar haknya. role base akses dll
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Akses user untuk menggunakan aplikasi berakhir.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
Json Web Token : biasanya berisi data data customer yang di enkripsi, role, dan juka akses session.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Claim pada JWT adalah bagian payload yang berisi pernyataan atau informasi tentang pengguna dan metadata token yang digunakan untuk proses autentikasi dan otorisasi.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
jti (JWT ID) → ID unik untuk token
exp (expiration time) → waktu kedaluwarsa token
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Krena jwt bisa di dekripsi jika orang lain punya tokennya, oleh karena itu signature perlu di validasi.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Secret key / signature, password, informasi rehasisa lainnua
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
supaya token tidak bisa digunakan selamanya dan untuk menjaga keamanan sistem, misalnya bisa aja jwtnya bocor dan hacker bisa menggunakan berkali kali kalo g ada session/expirynya
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
akses token : token baru yang dikirimkan ke user untuk menjadi penanda akses

refresh token : token yang digenerate untuk memperbarui akses token secara otomatis
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
memberikan akses tertentu kepada user tertentu - intinya sih akses user ke service tertentu
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication adalah proses untuk memverifikasi identitas pengguna (siapa kamu), sedangkan authorization adalah proses menentukan hak akses pengguna (apa yang boleh kamu lakukan).
```

### 24. Apa itu RBAC?

Jawaban:

```text
role base akses control : akses user ke service tertentu yang di atur berdasarkan role.
```

### 25. Apa itu role?

Jawaban:

```text
peran/kekuatan yang dimiliki oleh user tertentu
```

### 26. Apa itu permission?

Jawaban:

```text
perizinan untuk mengakses service tertentu
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
customer, credit, disbursement dll
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
- melihat pinjaman
- approce pinjaman
- mengajukan pinjaman dll
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena user dibatasi dengan role - role ini yang akan menandakan user bisa mengakses service apa aja
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Proses pemberian hak akases untuk role tertentu -> apakah user bisa mengakses service miliknya sendiri atau role lain.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena role hanya memberikan akses secara umum, sedangkan tidak mengontrol akses terhadap data atau resource spesifik milik pengguna.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Karena role hanya memberikan akses secara umum, sedangkan tidak mengontrol akses terhadap data atau resource spesifik milik pengguna.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
autorizationnya salah/bugs -> karena seharusnya data user/customer hanya bisa di akses oleh customer itu sendiri
```

### 34. Apa risiko IDOR?

Jawaban:

```text
berisiko menyebabkan pengguna dapat mengakses atau memanipulasi data milik orang lain tanpa izin hanya dengan mengubah identifier (misalnya ID) pada request.```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Backend harus melakukan authorization check yang ketat dengan memastikan pengguna hanya bisa mengakses resource yang memang menjadi haknya.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum terautentikasi atau token tidak valid (misalnya token tidak ada, expired, atau salah).
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah terautentikasi, tetapi tidak memiliki izin untuk mengakses resource tersebut.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
- 401 : unauthorized -> saat autorization awal/ cek apakah user identitasnya valid atau tidak
- 403 : forbidden -> saat user ingin mengakses service yang bukan miliknya
```

### 39. Kenapa access log penting?

Jawaban:

```text
Agar ketika ada fraud bisa di tack dan di audtit lewat log
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
- user_id
- event nya apa
```

## Self Assessment

| Area               | Score 1-5 |
| ------------------ | --------- |
| Authentication     |      3     |
| Authorization      |       3    |
| JWT                |       3    |
| RBAC               |       3    |
| Resource ownership |       3    |
| 401 vs 403         |       3    |
| Audit log          |       3    |

## Notes

```text
Keselurhan proses autorization dan autentication in code
```
