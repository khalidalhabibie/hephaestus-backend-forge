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
Access control penting dalam sistem finance karena sistem finance menyimpan data transaksi dan nasabah yang sangat sensitif. Access control akan membatasi akses orang yang dapat melihat atau mengubah data.
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
Risikonya adalah data bisa disalahgunakan, bocor ke pihak yang merugikan, diubah tanpa izin, bahkan kerugiannya bisa sangat berdampak ke masalah finansial bisnis.
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
Pada sistem loan terdapat data yang seharusnya dibatasi, seperti data nasabah, data pinjaman, dan informasi pembayaran yang berisikan informasi sensitif (e.g. rekening).
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
Karena frontend bisa dimanipulasi oleh pengguna (e.g. XSS injection yang bisa ngambil akses akun nasabah). Oleh karena itu, backend masih harus memeriksa akses dari setiap data agar meminimalisir risiko yang muncul di masa mendatang. 
```

### 5. Apa maksud deny by default?

Jawaban:

```text
Menurut pendapat saya, deny by default adalah proses di mana akses dibatasi, sehingga user haru memiliki akses terlebih dahulu untuk mengakses data.
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
Authentication adalah proses memastikan kebeneran, seperti membuktikan bahwa data-data yang nasabah masukan memang milik mereka sesuai dengan identitas mereka sendiri.
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
Contoh proses authentication adalah registrasi akun dengan face recognition untuk validate kebenaran identitias nasabah tersebut. Contoh lainnya adalah log in menggunakan password.
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
Token-based authentication adalah proses autentikasi menggunakan token (e.g. user mendapatkan token untuk proses log in, terkadang ada setiap sesi log in satu token tapi ada juga yang dibatasi oleh waktu).
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
Tulis jawaban di sini.
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
Bearer token memiliki format untuk autentikasi pemilik token.
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
Tulis jawaban di sini.
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
Akses ditolak dan biasanya terdapat status error.
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
Permintaan akses ditolak karena token tidak benar.
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
Token yang ada tidak bisa digunakan lagi dan user harus log in ulang atau menggunakan token baru.
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
JWT adalah tools untuk generate token secara otomatis yang akan digunakan untuk autentikasi.
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
Tulis jawaban di sini.
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
Tulis jawaban di sini.
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
Tulis jawaban di sini.
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
Codes penting, password, nomor rekening atau CC, dan data sensitif lainnya.
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
JWT harus punya expiry agar menghindari pencurian dan penyalahgunaan token. Hal ini dapat memicu pencurian akses.
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
Access token -> expiry cepat
Refresh token -> mendapatkan access token baru tanpa log in ulang
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
Authorization adalah pembuktian bahwa user memiliki izin untuk melakukan suatu hal.
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
Authentication -> in practice menunjukkan identitas
Authorization -> in practice menjawab apa saja yang boleh dilakukan
```

### 24. Apa itu RBAC?

Jawaban:

```text
Tulis jawaban di sini.
```

### 25. Apa itu role?

Jawaban:

```text
Role adalah kategori user dalam sebuah sistem, di mana mengatur apa saja yang bisa dilakukan user pada sistem tersebut.
```

### 26. Apa itu permission?

Jawaban:

```text
Permission adalah izin untuk melakukan suatu tindakan.
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
Customer, Supervisor, Admin, etc.
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
Melihat data loan, acceptance loan, change data loan, dan delete data loan.
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
Karena setiap user memiliki role dan permission yang berbeda-beda, sehingga satu user tidak bisa mengerjakan hal yang tidak termasuk dalam tanggung jawabnya.
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
Tulis jawaban di sini.
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
Tulis jawaban di sini.
```

### 32. Apa itu ownership check?

Jawaban:

```text
Tulis jawaban di sini.
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
Customer mengganti ID sebagai primary key mereka, sehingga dapat mengakses data sensitif dan kursial milik CUstomer lain.
```

### 34. Apa risiko IDOR?

Jawaban:

```text
Tulis jawaban di sini.
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
401 Unauthorized digunakan ketika user memiliki token akses yang salah.
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
403 Forbidden digunakan ketikan user tidak memiliki izin untuk melakukan suatu hal di dalam sistem setelah berhasil log in.
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```text
401 -> identitas tidak valid, sehingga tidak bisa log in.
403 -> identitas valid, tetapi akses tidak diizinkan.
```

### 39. Kenapa access log penting?

Jawaban:

```text
Access log sangat penting untuk tracking activities setiap user yang nantinya membantu proses IT audit.
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
ID, activity yang dilakukan, IP address, etc.
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Authentication | 2 |
| Authorization | 2 |
| JWT | 1 |
| RBAC | 1 |
| Resource ownership | 1 |
| 401 vs 403 | 2 |
| Audit log | 1 |

## Notes

```text
Saya masih belum memahami semuanay secara lebih detail dan praktik langsungnya dalam mengimplementasikan security di sebuah sistem.
```
