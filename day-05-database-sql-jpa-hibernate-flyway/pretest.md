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
termpat penyimpanan data yang diatur
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Kalau map itu di memori kalau berhenti data hilang, kalau database datanya disimpan terus
```

3. Apa itu table?

Jawaban:

```text
tempat di database yang berbentuk seperti tabel (baris dan kolom) untuk menyimpan data
```

4. Apa itu row?

Jawaban:

```text
satu baris data dalam tabel, biasanya mewakili satu data atau satu item.
```

5. Apa itu column?

Jawaban:

```text
kolom dalam tabel yang berisi jenis data tertentu, misalnya nama, umur, atau alamat.
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah penanda unik untuk setiap data di tabel, jadi tidak ada yang sama.
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah kolom yang menghubungkan satu tabel dengan tabel lain.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Agar aplikasi backend bisa terhubung dan berkomunikasi dengan database untuk mengambil atau menyimpan data.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
untuk memilih
```

10. Apa fungsi INSERT?

Jawaban:

```text
untuk memasukkan
```

11. Apa fungsi UPDATE?

Jawaban:

```text
untuk mengupdate data
```

12. Apa fungsi DELETE?

Jawaban:

```text
untuk menghapus
```

13. Apa fungsi WHERE?

Jawaban:

```text
untuk tahu letaknya dimana
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE untuk mencari data yang mirip dan sensitif terhadap snake case dan camel case kalau ILIKE lebih flesibel
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
Mau desc dan asc
```

16. Apa fungsi LIMIT?

Jawaban:

```text
lupa
```

17. Apa itu JOIN?

Jawaban:

```text
ambil lebih dari satu tabel
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text

INNER JOIN = hanya yang sama-sama ada
LEFT JOIN = ambil semua dari kiri, yang tidak ada di kanan tetap ikut

```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
JPA adalah Java Persistence API
```

20. Apa itu Hibernate?

Jawaban:

```text
alat yang menjalankan aturan JPA
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
kalau JPA itu konsepnya, hibernate itu alat yang menjalankan JPA
```

22. Apa itu Entity?

Jawaban:

```text
Entity adalah kelas di Java yang mewakili tabel di database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
@Entity digunakan untuk menandai bahwa suatu kelas adalah entity dan akan terhubung ke tabel di database
```

24. Apa fungsi @Table?

Jawaban:

```text
@Table digunakan untuk menentukan nama tabel di database yang akan dipakai oleh entity tersebut.
```

25. Apa fungsi @Id?

Jawaban:

```text
@Id digunakan untuk menandai kolom sebagai primary key (penanda unik data).
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
@GeneratedValue digunakan agar nilai primary key dibuat otomatis oleh database.
```

27. Apa itu Repository?

Jawaban:

```text
Repository adalah tempat (interface) untuk mengakses dan mengelola data di database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
membantu kita melakukan operasi database (seperti save, update, delete, ambil data) tanpa harus menulis query sendiri.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
wah kurang tahu saya 
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
BELUM TAHU
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findByEmail(String email)
```

32. Apa fungsi @Query?

Jawaban:

```text
digunakan untuk menulis query secara manual jika tidak bisa menggunakan query method biasa.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
kalau jpql langsung dari JPA kalau native kita tulis sendiri querynya (CMIIW)
```

34. Kapan menggunakan native query?

Jawaban:

```text
saat butuh cepat performance
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
database perpindahan
```

36. Apa itu Flyway?

Jawaban:

```text
Belum tahu
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
belum tahu
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
untuk menggatur miggration
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
akan banyak data yang error atau bahkan hilang
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
hubungan antar table
```

41. Apa itu one-to-many?

Jawaban:

```text
satu ke banyak
```

42. Apa itu many-to-one?

Jawaban:

```text
banyak ke satu
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
tidak tahu
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
tidak tahu
```

45. Apa itu lazy loading?

Jawaban:

```text
loading lama atau malas contohnya di beranda shopee dipanggil kalau diperlukan misalnya di scroll
```

46. Apa itu eager loading?

Jawaban:

```text
loading yang utama misalnya header
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
tidak tahu
```

48. Apa itu N+1 query problem?

Jawaban:

```text
belum tahu
```

49. Apa itu join fetch?

Jawaban:

```text
belum tahu
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text

Karena satu customer bisa punya banyak loan (pinjaman). Kalau dipisah, data jadi lebih rapi dan tidak berulang-ulang.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena satu pinjaman bisa punya banyak jadwal cicilan.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM loan WHERE status = 'APPROVED';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text

SELECT customer_id, SUM(amount) 
FROM repayment 
GROUP BY customer_id;

```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic |2,5|
| SQL basic | 3|
| JPA | 3|
| Hibernate |1|
| Repository |1|
| Flyway |1|
| Relationship |1|
| Join query |3|
| Lazy loading |3|
| Finance data modeling |2|

## Notes

```text
JPA karena belum mengimplementasikan secara langsung dan cara menghubungkan ke database 
```

