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
Supaya hanya orang yang benar-benar boleh yang bisa lihat atau ubah data.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Data bisa bocor, disalahgunakan, atau orang bisa melihat informasi yang seharusnya tidak bisa mengakses datanya
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Melihat status pinjaman, mengubah data customer, approve loan, dan lihat informasi rekening dan data pribadi.
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena pada frontend bisa dimodifikasi atau dipalsukan sehingga backend harus tetap cek izinnya sendiri.
```

### 5. Apa maksud deny by default?

Jawaban:

```text
artinya semua permintaan ditolak dulu, baru dibuka kalau memang sudah jelas boleh.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Proses memastikan siapa user itu.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Login pakai username dan password, atau OTP yang dikirim ke hp.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Login yang membuat token, lalu token itu dipakai untuk minta data dari server.
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Untuk mengirim token ke server supaya server tahu siapa yang minta data.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
format pengiriman token pada request ke server
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Server harus cek tokennya benar dan belum kadaluarsa.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Server harus tolak akses dan biasanya jawab 401 Unauthorized.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Server juga menolak akses karena tokennya tidak bisa dipercaya.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Server tolak dan minta user login lagi atau pakai refresh token.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT adalah token digital yang dipakai sebagai tanda kalau user sudah login.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Claim adalah informasi dalam token, seperti id user atau peran user.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
tidak tahu
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Karena isi payload JWT bisa dibaca dan diubah oleh siapa saja. Oleh karena itu, sistem harus memeriksa signature terlebih dahulu untuk memastikan data JWT tidak diubah dan benar dibuat oleh pihak yang berwenang. Jika signature belum divalidasi, isi payload tidak boleh dipercaya.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Jangan simpan password, data pribadi, atau info sensitif yang bisa disalahgunakan.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
Agar token tidak bisa dipakai selamanya kalau jatuh ke tangan yang salah.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token dipakai untuk akses layanan sedangkan refresh token dipakai untuk minta token baru kalau yang lama sudah habis.
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Proses cek apakah user boleh melakukan suatu proses atau activity tertentu.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication: proses mengecek siapa pengguna yang sedang masuk ke sistem.
Authorization: proses mengecek apa saja yang boleh dilakukan oleh pengguna tersebut.
```

### 24. Apa itu RBAC?

Jawaban:

```text
RBAC adalah kontrol akses berdasarkan role yang dimiliki user.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah peran atau kategori user, seperti admin atau customer.
```

### 26. Apa itu permission?

Jawaban:

```text
Permission adalah hak untuk melakukan action tertentu.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Contohnya customer, loan officer, dan manager.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Contohnya lihat loan, ajukan loan, approve loan, dan edit data customer.
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena login hanya bukti identitas; harus dicek lagi apakah ada akses pada activity tertentu.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Cek akses berdasarkan data tertentu, bukan hanya berdasarkan role.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Karena role bisa saja punya izin umum tapi tidak boleh lihat data orang lain.
```

### 32. Apa itu ownership check?

Jawaban:

```text
proses untuk memastikan bahwa pengguna hanya bisa melihat, mengubah, atau menghapus data yang memang miliknya sendiri.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Kalau customer A bisa lihat detail loan B, itu contoh akses yang salah.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Data orang lain bisa bocor karena kontrol ID yang salah.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Backend harus cek owner, validasi id resource, dan batasi query sesuai user.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
Saat user belum login atau token tidak valid.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
Saat user sudah login tapi tidak punya izin untuk action itu.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 berarti belum login atau token salah, 403 berarti sudah login tapi tidak boleh melakukan itu.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Untuk tahu siapa akses apa, kapan, dan mendeteksi masalah atau serangan.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
User id, waktu, endpoint, method, status, dan IP address.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication | 3 |
| Authorization | 2 |
| JWT | 1 |
| RBAC | 1 |
| Resource ownership | 2 |
| 401 vs 403 | 4 |
| Audit log | 3 |

## Notes

```text
saya belum pernah membuat JWT dsbnya itu
```
