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
agar role tertentu saja bisa mengakses sesuai kewenangannya
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
maka akan terjadi kebocoran data dan celah keamanan
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
semisal kasir hanya bisa mengakses data customer nama, alamat, nik sedangkan untuk credit analyst bisa mengakses data customer yang lebih detail seperti gaji dll
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
agar keamanan berlapis dan backend memiliki keamanannya sendiri
```

### 5. Apa maksud deny by default?

Jawaban:

```text
 secara default menolak semua akses, permintaan, atau koneksi kecuali yang secara spesifik telah diizinkan sebelumnya
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
proses verifikasi untuk membuktikan apakah seseorang benar sesuai yang di claim
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
proses login dan input password
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
sebuah token yang didapatkan ketika client berhasil verifikasi identitas
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
berfungsi untuk mengirim token dari klien ke server agar bisa memiliki akses ke data tertentu
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
kredensial untuk keamanan akses
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
backend harus memprosesnya melalui langkah-langkah verifikasi keamanan sebelum memberikan akses ke endpoint
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
backend harus segera menghentikan proses dan menolak akses
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
menolak akses karena token invalid
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
backend akan menolak permintaan tersebut karena masa berlaku di dalam token sudah lewat
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JSON web token digunakan untuk proses autentikasi berbasis bearer token
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
atribut seperti id role berbasis key dan value
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
id, email , nama, role,
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
karena JWT bisa dilihat oleh siapa aja
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
password
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
supaya tidak bisa diakses sembarangan
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
access token didapat setelah login, sedangkan refresh token didapat ketika token expired
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
perizinan akses terhadap service tertentu
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
authentication adalah proses untuk membuktikan siapa Anda, sedangkan authorization adalah proses untuk menentukan apa saja yang boleh dilakukan
```

### 24. Apa itu RBAC?

Jawaban:

```text
metode pengaturan hak akses sistem berdasarkan role
```

### 25. Apa itu role?

Jawaban:

```text
label jabatan yang mengelompokkan terhadap akses tertentu
```

### 26. Apa itu permission?

Jawaban:

```text
hak atau otoritas spesifik untuk melakukan suatu tindakan tertentu
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
credit analyst, kasir, marketing officer, sales
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
perizinan untuk melihat detail customer detail seperti gaji, tanggungan dll
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena role nya terbatas dalam melakukan suatu aksi tertentu
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
memeriksa apakah Anda memiliki hak atas data spesifik
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
role check saja tidak cukup karena peran hanya mendefinisikan kategori jabatan, bukan kepemilikan atau batasan data spesifik
```

### 32. Apa itu ownership check?

Jawaban:

```text
memastikan bahwa pengguna yang meminta suatu data adalah pemilik sah dari data tersebut
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
IDOR (Insecure Direct Object Reference) atau BOLA (Broken Object Level Authorization)
```

### 34. Apa risiko IDOR?

Jawaban:

```text
kegagalan total dalam menjaga kerahasiaan dan integritas data
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
gunakan UUID
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
ketika request gagal masuk karena masalah autentikasi
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
ketika request gagal karena masalah otorisasi
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 untuk autentikasi sedangkan 403 untuk otorisasi
```

### 39. Kenapa access log penting?

Jawaban:

```text
Log ini mencatat setiap request HTTP yang masuk ke backend, memberikan rekam jejak digital 
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
tidak tau
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication |3|
| Authorization |3|
| JWT |2|
| RBAC |3|
| Resource ownership |2|
| 401 vs 403 |3|
| Audit log |1|

## Notes

```text
Tulis bagian yang masih membingungkan.
```
