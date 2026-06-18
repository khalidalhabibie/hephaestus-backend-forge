# Posttest - Authentication, Authorization & RBAC

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Authentication, Authorization, JWT, RBAC, dan resource-level authorization.

### 1. Apa itu authentication?

Jawaban:

```text
proses untuk memverifikasi identitas user, misalnya dengan username/password, untuk mengecek keaslian identitas
```

### 2. Apa itu authorization?

Jawaban:

```text
proses untuk menentukan apakah user yang sudah terverifikasi boleh melakukan suatu action
```

### 3. Apa perbedaan authentication dan authorization?

Jawaban:

```text
authentication lebih ke arah "siapa kamu?"

authorization lebih ke arah "apa yang boleh kamu lakukan?"
```

### 4. Kenapa user yang sudah login belum tentu boleh melakukan semua action?

Jawaban:

```text
karena setiap user punya role dan permission yang berbeda, jadi tidak semua action boleh dilakukan oleh semua user
```

### 5. Apa itu token-based authentication?

Jawaban:

```text
metode authentication yang menggunakan token sebagai bukti identitas setelah login, biasanya dikirim di setiap request
```

### 6. Apa fungsi Authorization header?

Jawaban:

```text
untuk mengirimkan credential (seperti token) dari client ke server sebagai bukti bahwa request tersebut sudah terautentikasi
```

### 7. Apa arti Bearer token?

Jawaban:

```text
artinya siapa pun yang memiliki token tersebut dianggap sebagai pemiliknya dan bisa menggunakannya untuk akses
```

### 8. Apa itu JWT?

Jawaban:

```text
token berbentuk string yang berisi payload dan ditandatangani secara digital
```

### 9. Apa itu claim pada JWT?

Jawaban:

```text
adalah informasi/data yang disimpan di dalam payload jwt
```

### 10. Sebutkan 4 claim yang umum ada pada JWT.

Jawaban:

```text
sub, iss, exp, iat
```

### 11. Kenapa JWT payload tidak boleh dipercaya sebelum signature divalidasi?

Jawaban:

```text
karena payload bisa dimodifikasi oleh pihak luar, jadi harus diverifikasi dulu signature nya untuk memastikan keasliannya
```

### 12. Data apa saja yang tidak boleh disimpan di JWT?

Jawaban:

```text
data sensitif seperti password, pin, nomor kartu, atau informasi rahasia lainnya
```

### 13. Kenapa token perlu expiry?

Jawaban:

```text
untuk membatasi waktu penggunaan token sehingga bisa mencegah terjadinya kebocoran data
```

### 14. Apa perbedaan access token dan refresh token?

Jawaban:

```text
access token digunakan untuk akses API dan biasanya waktunya pendek, refresh token digunakan untuk mendapatkan access token baru dan biasanya lebih lama masa berlakunya
```

### 15. Apa itu RBAC?

Jawaban:

```text
role based access control, yaitu metode pengaturan akses berdasarkan role user
```

### 16. Apa perbedaan role dan permission?

Jawaban:

```text
role adalah kumpulan permission

permission adalah hak untuk melakukan aksi tertentu.
```

### 17. Berikan contoh role dalam loan system.

Jawaban:

```text
admin, loan officer, customer
```

### 18. Berikan contoh permission dalam loan system.

Jawaban:

```text
create loan, approve loan, view loan, reject loan
```

### 19. Kenapa role check saja tidak cukup?

Jawaban:

```text
karena perlu memastikan juga akses ke resource tertentu, karena kalau hanya role check saja bisa rentan kepemilikan data yang seharusnya setiap orang berbeda beda jadi bisa dimiliki banyak orang
```

### 20. Apa itu resource-level authorization?

Jawaban:

```text
authorization yang memastikan user hanya bisa mengakses resource tertentu, misalnya data miliknya sendiri
```

### 21. Apa itu ownership check?

Jawaban:

```text
pengecekan apakah suatu resource benar-benar dimiliki oleh user yang melakukan request
```

### 22. Apa itu IDOR?

Jawaban:

```text
insecure direct object reference, yaitu celah ketika user bisa mengakses resource orang lain dengan mengganti id
```

### 23. Bagaimana cara mencegah customer melihat data customer lain?

Jawaban:

```text
dengan melakukan ownership check dan memastikan query selalu difilter berdasarkan user yang login
```

### 24. Kapan menggunakan 401 Unauthorized?

Jawaban:

```text
ketika user belum login atau token tidak sesuai
```

### 25. Kapan menggunakan 403 Forbidden?

Jawaban:

```text
ketika user sudah login tetapi tidak punya izin untuk akses resource tersebut
```

### 26. Apa perbedaan 401 dan 403?

Jawaban:

```text
401 terkait authentication gagal
403 terkait authorization ditolak.
```

### 27. Kenapa error message security tidak boleh terlalu detail?

Jawaban:

```text
agar tidak memberikan informasi sensitif yang bisa dimanfaatkan oleh pihak luar
```

### 28. Apa itu principle of least privilege?

Jawaban:

```text
memberikan akses minimum yang dibutuhkan user untuk menjalankan tugas
```

### 29. Kenapa access log penting dalam finance backend?

Jawaban:

```text
untuk audit, tracking aktivitas, dan mendeteksi potensi fraud atau penyalahgunaan
```

### 30. Sebutkan field penting dalam access log.

Jawaban:

```text
user id, endpoint, method, timestamp, ip address, status code
```

### 31. Bagaimana auth requirement ditulis di API contract?

Jawaban:

```text
dituliskan di dokumentasi endpoint, seperti required header authorization dan jenis token yang digunakan
```

### 32. Bagaimana Swagger/OpenAPI membantu dokumentasi endpoint yang protected?

Jawaban:

```text
dengan mendefinisikan security scheme dan menandai endpoint mana yang membutuhkan authentication
```

### 33. Apa risiko jika role dikirim dari client lalu langsung dipercaya?

Jawaban:

```text
client bisa memanipulasi role sehingga mendapatkan akses yang tidak seharusnya
```

### 34. Apa risiko token tanpa expiry?

Jawaban:

```text
token bisa digunakan dengan bebas tanpa batas waktu yang bisa membuat resiko keamanan meningkat karena bisa dimanipulasi datanya
```

### 35. Bagian mana yang paling sulit dari Day 4?

Jawaban:

```text
membuat proses authentication dan authorization berdasar role untuk di implementasikan di seluruh endpoint
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. perbedaan jelas antara authentication dan authorization
2. pentingnya rbac dan resource level authorization untuk keamanan
3. konsep teori dalam cara menggunakan token
```

Apa 2 hal yang masih membingungkan?

```text
1. implementasikan hash code
2. implementasikan best practice authentication (authUtil,AuthContext)
```

Apa 1 pertanyaan untuk mentor?

```text
bagaimana cara terbaik menggabungkan rbac dengan attribute based access control di sistem yang kompleks?
```
