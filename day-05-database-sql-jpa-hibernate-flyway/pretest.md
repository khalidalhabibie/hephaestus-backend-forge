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
Database atau basis data adalah tempat menyimpan, mengelola, dan mengatur data secara terstruktur agar lebih mudah untuk dikelola.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Kalau menyimpan data di map itu bersifat temporary, dan akan hilang ketika aplikasi di re run. Sedangkan di database, data tidak akan hilang jika aplikasi di re run, tapi data akan hilang jika database dihapus.
```

3. Apa itu table?

Jawaban:

```text
Tabel adalah struktur pada database. Tempat untuk menyimpan data dalam bentuk baris dan kolom.
```

4. Apa itu row?

Jawaban:

```text
Row pada tabel adalah satu data lengkap dalam sebuah tabel.
```

5. Apa itu column?

Jawaban:

```text
Kolum pada tabel adalah jenis data yang ada dalam sebuah tabel.
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah suatu value yang digunakan untuk mengidentifikasikan setiap row secara unik dalam suatu tabel.
```

7. Apa itu foreign key?

Jawaban:

```text
Kolom dalam suatu tabel, yang merujuk ke primary key di table lain.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Agar bisa terkoneksi ke suatu server database.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
Select adalah syntax pada query untuk mengambil atau menarik data.
```

10. Apa fungsi INSERT?

Jawaban:

```text
Insert adalah syntax pada query untuk bisa memasukkan data ke suatu tabel.
```

11. Apa fungsi UPDATE?

Jawaban:

```text
Update adalah syntax pada query untuk bisa mengupdate / memperbarui data di suatu tabel.
```

12. Apa fungsi DELETE?

Jawaban:

```text
Delete adalah syntax pada query untuk bisa menghapus data dari suatu tabel.
```

13. Apa fungsi WHERE?

Jawaban:

```text
Untuk memfilter data sehingga data tertentu saja yang diambil, diubah, diapus sesuai dengan kondisinya.
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE adalah query untuk mengambil data menggunakan filter.

Untuk perbedaannya terus terang saya belum tau.
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
Orderby berfungsi untuk mengurutkan data dari suatu tabel.
```

16. Apa fungsi LIMIT?

Jawaban:

```text
Untuk membatasi jumlah data yang ditampilkan dari harils query.
```

17. Apa itu JOIN?

Jawaban:

```text
Syntax pada query untuk menggabungkan data dari dua atau lebih tabel bedasarkan relasinya.
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
Inner join -> menampilkan data yang memiliki pasangan di kedua tabel.

Left join -> Menampilkan semua data dari tabel kiri saja.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Java Persitence API adalah framework pada java untuk mengelola data di database dengan cara menggunakan OOP.
```

20. Apa itu Hibernate?

Jawaban:

```text
Hibernate adalah ORM di java yang digunakan untuk menghubungkan object java dan tabel pada database.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
Jpa menjelaskan bagaimana cara transaksi ke database dari java.

Hibernate untuk mengimplementasikan JPA, koneksi ke database, generate sql, menjalani query.
```

22. Apa itu Entity?

Jawaban:

```text
Entitiy adalah class dalam java yang mempresentasikan tabel di database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
anotasi dalam springboot untuk menandakan bahwa suatu class itu relasi dengan tabel di database.
```

24. Apa fungsi @Table?

Jawaban:

```text
Menentukan nama dan konfigurasi tabel spesifik database yang dipetakan oleh class tersebut.
```

25. Apa fungsi @Id?

Jawaban:

```text
Anotasi untuk menentukan entitas id menjadi id pada tabel database.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Saya belum tahu.
```

27. Apa itu Repository?

Jawaban:

```text
Berfungsi sebagai jembatan untuk mengakses dan mengelola di database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
interface pada springboot yang menyediakan fungsi siap pakai.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Karena javax sudah tidak lagi dimaintain, sekarang versi barunya adalah jakarta.
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Adalah kueri bawaan dari spring data jpa yang memungkinkan membuat kueri otomatis dalam kelas repository.
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
Select * from users where email like "%risjad%"
```

32. Apa fungsi @Query?

Jawaban:

```text
@Query untuk mendefinisikan custom atau native kueri.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL itu query bawaan, sedangkan native query adalah query kustom.
```

34. Kapan menggunakan native query?

Jawaban:

```text
Ketika logika atau algoritmanya lebih rumit
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Proses perpindahan daatabase.
```

36. Apa itu Flyway?

Jawaban:

```text
Kurang tahu.
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Agar perubahan bisa ditracking.
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
Untuk mendokumentasikan perubahan database atau DDL database.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Menjadi tidak standar.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Hubungan atau relasi antar tabel yang saling berkaitan dengan primary key dan foreign key.
```

41. Apa itu one-to-many?

Jawaban:

```text
Relasi pada tabel, dimana satu entitias bisa memiliki banyak entitas lainnya.
```

42. Apa itu many-to-one?

Jawaban:

```text
Relasi pada tabel, dimana banyak entitias bisa memiliki satu entitas.
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Anotasi java spring boot untuk many to one.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Anotasi pada spring boot untuk relasi one to many.
```

45. Apa itu lazy loading?

Jawaban:

```text
Lazy loading adalah konsep dimana trigger hanya saat kita mengakses suatu resource.
```

46. Apa itu eager loading?

Jawaban:

```text
Eager loading adalah konsep dimana loading terjadi sebelum di trigger.
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Performa sistem bisa jelek.
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Masalah performance pada query
```

49. Apa itu join fetch?

Jawaban:

```text
Tidak tahu
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
karena 1 customer bisa memiliki hubungan many loan application. Dan agar tabel databse itu normalisasi dengan baik.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
tidak tahu
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
Select * from user_transaction where status = "active"
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
Select sum(payment) from transaction where users.id = 16
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        | 4         |
| SQL basic             | 4         |
| JPA                   | 4         |
| Hibernate             | 4         |
| Repository            | 4         |
| Flyway                | 1         |
| Relationship          | 4         |
| Join query            | 3         |
| Lazy loading          | 2         |
| Finance data modeling | 2         |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
