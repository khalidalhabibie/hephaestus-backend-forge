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
karena sistem finance menyimpan data sensitif seperti saldo, transaksi, dan pinjaman sehingga tanpa access control, data bisa disalahgunakan dan menimbulkan kerugian finansial maupun reputasi perasahaan
```

### 2. Apa risiko jika semua user bisa mengakses semua data?

Jawaban:

```text
risikonya kebocoran data, fraud, manipulasi transaksi, dan pelanggaran privacy karena user bisa melihat atau mengubah data yang bukan miliknya
```

### 3. Apa contoh data atau action yang harus dibatasi pada sistem loan?

Jawaban:

```text
melihat data pinjaman orang lain, update status loan, serta akses ke data financial seperti jumlah pinjaman
```

### 4. Kenapa backend tidak boleh hanya mengandalkan frontend untuk membatasi akses?

Jawaban:

```text
karena frontend bisa dimanipulasi karena sifatnya bisa dilihat melalui inspect element sehingga rentan dengan direct api call. jadi validasi akses harus tetap dilakukan di backend
```

### 5. Apa maksud deny by default?

Jawaban:

```text
semua akses ditolak secara default, dan hanya diizinkan jika memang ada rule yang memperbolehkan
```

## Section B - Authentication

### 6. Apa itu authentication?

Jawaban:

```text
proses untuk memastikan identitas user
```

### 7. Apa contoh proses authentication?

Jawaban:

```text
login menggunakan username/email dan password yang diverifikasi ke database
```

### 8. Apa itu token-based authentication?

Jawaban:

```text
metode authentication yang menggunakan token sebagai bukti bahwa user sudah login
```

### 9. Apa fungsi Authorization header?

Jawaban:

```text
header ini digunakan untuk membuktikan identitas user dan menentukan apakah mereka memiliki izin untuk mengakses data atau fitur tertentu
```

### 10. Apa arti format Bearer token?

Jawaban:

```text
sebuah tanda pengenal digital berupa token untuk memberi akses keamanan pada user saat mengakes aplikasi
```

### 11. Apa yang harus dilakukan backend saat menerima token?

Jawaban:

```text
backend membaca token, memeriksa keasliannya tokenya, lalu mengecek waktu kedaluwarsa. jika token sah, backend memberikan akses data. jika tidak sah atau sudah melewati waktu nya, backend langsung menolaknya
```

### 12. Apa yang terjadi jika token tidak ada?

Jawaban:

```text
request ditolak dengan status 401 unauthorized
```

### 13. Apa yang terjadi jika token invalid?

Jawaban:

```text
request ditolak karena token tidak valid
```

### 14. Apa yang terjadi jika token expired?

Jawaban:

```text
request ditolak dan user harus login ulang
```

## Section C - JWT

### 15. Apa itu JWT?

Jawaban:

```text
token berbentuk json yang digunakan untuk authentication
```

### 16. Apa itu claim pada JWT?

Jawaban:

```text
data yang disimpan dalam payload yang diberikan jwt
```

### 17. Sebutkan contoh claim yang umum ada di JWT.

Jawaban:

```text
user_id, role, email
```

### 18. Kenapa payload JWT tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
karena payload masih bisa di manipulasi jika tidak ada validasi signature
```

### 19. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
password dan pin
```

### 20. Kenapa JWT harus punya expiry?

Jawaban:

```text
untuk menghindari data bocor jika disimpan terus menerus
```

### 21. Apa perbedaan access token dan refresh token?

Jawaban:

```text
access token digunakan untuk akses api (biasanya untu waktu jangka pendek), sedangkan refresh token untuk mendapatkan access token baru (jangka waktunya lebih panjang)
```

## Section D - Authorization and RBAC

### 22. Apa itu authorization?

Jawaban:

```text
proses menentukan apakah user boleh melakukan suatu action
```

### 23. Apa perbedaan authentication dan authorization?

Jawaban:

```text
authentication memastikan siapa user
authorization menentukan apa yang boleh dia lakukan
```

### 24. Apa itu RBAC?

Jawaban:

```text
role based access control, yaitu pembagian akses berdasarkan role
```

### 25. Apa itu role?

Jawaban:

```text
ekumpulan permission yang dimiliki user
```

### 26. Apa itu permission?

Jawaban:

```text
hak untuk melakukan action tertentu seperti read dan write
```

### 27. Apa contoh role dalam loan system?

Jawaban:

```text
customer, loan officer, admin, risk analyst
```

### 28. Apa contoh permission dalam loan system?

Jawaban:

```text
create loan, approve loan, view loan, update status loan
```

### 29. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena ketika sudah masuk ya hanya diberikan autentikasi tapi masih ada authorization yang belum tentu diberikan
```

## Section E - Resource-Level Authorization

### 30. Apa itu resource-level authorization?

Jawaban:

```text
pembatasan akses berdasarkan ownership, bukan hanya role
```

### 31. Kenapa role check saja tidak cukup?

Jawaban:

```text
karena user dengan role yang sama belum tentu punya akses yang sama
```

### 32. Apa itu ownership check?

Jawaban:

```text
pengecekan apakah user adalah pemilik resource yang diakses
```

### 33. Apa contoh kasus customer melihat loan milik customer lain?

Jawaban:

```text
user mengganti loan_id di request dan bisa melihat data orang lain jika tidak divalidasi
```

### 34. Apa risiko IDOR?

Jawaban:

```text
tidak tahu
```

### 35. Bagaimana cara backend mencegah data leakage karena salah akses resource?

Jawaban:

```text
dengan validasi ownership, filtering berdasarkan user_id, dan tidak percaya input dari client sepenuhnya
```

## Section F - 401, 403, and Audit

### 36. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
saat user belum login atau token tidak valid
```

### 37. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
saat user sudah login tapi tidak punya akses
```

### 38. Apa bedanya 401 dan 403?

Jawaban:

```texts
401: belum authenticated
403: sudah authenticated tapi tidak authorized
```

### 39. Kenapa access log penting?

Jawaban:

```text
untuk monitoring, audit, dan investigasi jika terjadi masalah atau fraud
```

### 40. Field apa saja yang penting dalam access log?

Jawaban:

```text
user_id, endpoint, method, timestamp, status code, ip address, dan action
```

## Self Assessment

| Area               | Score 1-5 |
| ------------------ | --------- |
| Authentication     | 3         |
| Authorization      | 3         |
| JWT                | 3         |
| RBAC               | 3         |
| Resource ownership | 3         |
| 401 vs 403         | 3         |
| Audit log          | 3         |

## Notes

```text
apa resiko idor
```
