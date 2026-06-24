# Pretest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang database, SQL, JPA, Hibernate, Flyway, dan relationship antar table.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Database Basic

1. Apa itu database?

Jawaban:

```text
Kumpulan data atau informasi.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Data di Map
- Data tersimpan sementara
- Kapasitasi dibatasi oleh ukuran RAM
- Data lebih cepat diproses

Data di Database
- Data tersimpan permanen
- Kapasitas lebih besar daibandingkan di Map
- Data lebih lama diproses
```

3. Apa itu table?

Jawaban:

```text
Susuanan data terstruktur dalam bentuk baris dan kolom.
```

4. Apa itu row?

Jawaban:

```text
Row adalah deretan sel, data, atau informasi yang disusun secara horizontal (dari kanan ke kiri).
```

5. Apa itu column?

Jawaban:

```text
Column adalah deretan sel, data, atau informasi yang disusun secara vertikal (dari atas ke bawah).
```

6. Apa itu primary key?

Jawaban:

```text
Kolom yang berisik data unik digunakan untuk mengidentifikasi setiap data.
```

7. Apa itu foreign key?

Jawaban:

```text
Kolom yang menghubungkan satu tabel dengan tabel lainnya.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Karema driver digunakan sebagai penghubung antara aplikasi dan database.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
SELECT digunakan untuk mengambil, memilih, dan menampikan data dari sesuatu tabel berdasarkan ketentuan tertentu.
```

10. Apa fungsi INSERT?

Jawaban:

```text
INSERT digunakan untuk menambahkan data baru ke dalam tabel.
```

11. Apa fungsi UPDATE?

Jawaban:

```text
UPDATE digunakan untuk mengubah dan memperbaharui data yang sudah ada dalam suatu tabel.
```

12. Apa fungsi DELETE?

Jawaban:

```text
DELETE digunakan untuk menghapus data dari tabel.
```

13. Apa fungsi WHERE?

Jawaban:

```text
WHERE digunakan untuk memfilter data berdasarkan kondisi tertentu.
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE bersifat case-sensitive (membedakan huruf besar dan kecil), sedangkan ILIKE bersifat case-insensitive (mengabaikan perbedaan huruf besar dan kecil).
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
ORDER BY digunakan untuk mengurutkan data hasil query berdasarkan kolom tertentu.
```

16. Apa fungsi LIMIT?

Jawaban:

```text
LIMIT digunakan untuk membatasi jumlah data yang ingin ditampilkan.
```

17. Apa itu JOIN?

Jawaban:

```text
JOIN digunakan untuk menggabungkan data dari beberapa tabel.
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN hanya menampilkan data yang cocok di kedua table.
LEFT JOIN menampilkan semua data dari table kiri meskipun tidak ada pasangan di table kanan.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
JPA adalah bagian dari Java ynag digunakan untuk mengelola data ke database dengan menggunakan oobjek.
```

20. Apa itu Hibernate?

Jawaban:

```text
Framework yang digunakan untuk membantu proses mapping dari objek ke database.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA adalah spesifikasi, Hibernate adalah implementasinya.
```

22. Apa itu Entity?

Jawaban:

```text
Class Java yang merepresentasikan tabel di database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
Menandai class sebagai entitas yang akan dipetakan ke database.
```

24. Apa fungsi @Table?

Jawaban:

```text
Menentukan nama tabel yang digunakan oleh entitas.
```

25. Apa fungsi @Id?

Jawaban:

```text
Menandai suatu field atau variabel sebagai primary key.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Menghasilkan nilai primary key secara otomatis.
```

27. Apa itu Repository?

Jawaban:

```text
Komponen yang digunakan untuk mengakses data dari database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Menyediakan operasi CRUD dan query dasar tanpa menulis SQL secara manual.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Karena Jakarta EE menggantikan namespace javax menjadi jakarta.
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Method query yang dibuat berdasarkan nama method.
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findByEmail(String email)
```

32. Apa fungsi @Query?

Jawaban:

```text
Menuliskan query secara langsung pada repository.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL menggunakan entity dan field Java.
Native query menggunakan SQL asli database.
```

34. Kapan menggunakan native query?

Jawaban:

```text
Saat query kompleks atau membutuhkan fitur khusus database.
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Proses mengelola perubahan struktur database secara terkontrol.
```

36. Apa itu Flyway?

Jawaban:

```text
Tools yang mengelola dan menjalankan database migration.
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Agar setiap perubahan yang dilakukan dalam database dalam terlacak.
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
Migration versi 1 yang digunkan untuk membuat tabel customers.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Kemungkinan perubahan dari database sulit dilacak.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Hubungan antara data di satu tabel dengan tabel lainnya.
```

41. Apa itu one-to-many?

Jawaban:

```text
Satu data pada tabel pertama memiliki banyak data pada tabel kedua yang saling berhubungan.
```

42. Apa itu many-to-one?

Jawaban:

```text
Banyak data pada tabel pertama memiliki satu data pada tabel lainnya yang saling berhubungan.
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Untuk mendefinisikan hubungan many-to-one pada suatu entitas.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Untuk mendefinisikan hubungan one-to-many pada suatu entitas.
```

45. Apa itu lazy loading?

Jawaban:

```text
Data relasi diambil hanya saat diperlukan saja.
```

46. Apa itu eager loading?

Jawaban:

```text
Data relasi langsung diambil bersama entitas utama.
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Dapat menyebabkan error dan memnungkinakna performa aplikasi menjadi lambat.
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Masalah ketika satu query utama memicu banyak query tambahan yang tidak diperlukan.
```

49. Apa itu join fetch?

Jawaban:

```text
Teknik mengambil data utama dan relasinya dalam satu query.
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Karena tabel-tabel dalam database harus menerapkan normalisasi dan 1 customer bisa memiliki banyak loan application (one-to-many).
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena satu pinjaman memiliki banyak jadwal pembayaran cicilan.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```sql
SELECT * FROM loan_application 
WHERE status = 'APPROVED';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```sql
SELECT customer_id, SUM (amount) AS total_pembayaran 
WHERE customer_id = 1 
FROM transaksi 
GROUP BY customer_id;
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic |3|
| SQL basic |3|
| JPA |2|
| Hibernate |2|
| Repository |2|
| Flyway |2|
| Relationship |3|
| Join query |3|
| Lazy loading |2|
| Finance data modeling |3|

## Notes

```text
Bagaimana implementasi cara menghubungkan Service Layer yang sebelumnya sudah dibuat ke Data Layer?
```
