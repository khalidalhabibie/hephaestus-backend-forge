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
tempat untuk menyimpan, mengolah dan mengambil/memanggil data secara terstruktur, supaya mudah digunakan
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Map : disimpan secara sementara (memori)

database : disimpan secara permanen 
```

3. Apa itu table?

Jawaban:

```text
table di dalam database yang terdiri baris dan kolom
```

4. Apa itu row?

Jawaban:

```text
satu baris data yang merepresentasikan 1 data (pada bagian bawah (kebawah))
```

5. Apa itu column?

Jawaban:

```text
atribut dari table (pada bagian atas (kesamping))
```

6. Apa itu primary key?

Jawaban:

```text
atribut/kolom unik yang pasti ada disetiap table untuk mengidentifikasi setiap row
```

7. Apa itu foreign key?

Jawaban:

```text
atribut/kolom untuk menghubungkan data antar table
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
agar aplikasi bisa berkomunikasi dan menjalankan query ke database
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
untuk mengambil data dari database
```

10. Apa fungsi INSERT?

Jawaban:

```text
untuk menambahkan data baru ke table
```

11. Apa fungsi UPDATE?

Jawaban:

```text
untuk mengubah data yang sudah ada
```

12. Apa fungsi DELETE?

Jawaban:

```text
untuk menghapus data
```

13. Apa fungsi WHERE?

Jawaban:

```text
untuk memfilter data berdasarkan kondisi
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE case-sensitive, ILIKE tidak case-sensitive
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
untuk mengurutkan hasil query sesuai kondisi
```

16. Apa fungsi LIMIT?

Jawaban:

```text
untuk membatasi jumlah data yang ditampilkan
```

17. Apa itu JOIN?

Jawaban:

```text
untuk mengambil data dari beberapa table
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN hanya data yang cocok

LEFT JOIN semua data kiri ditampilkan
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
spesifikasi untuk ORM di Java
```

20. Apa itu Hibernate?

Jawaban:

```text
implementasi JPA
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA : standar
Hibernate : framework implementasinya
```

22. Apa itu Entity?

Jawaban:

```text
representasi table dalam Java object
```

23. Apa fungsi @Entity?

Jawaban:

```text
menandai class sebagai entity JPA
```

24. Apa fungsi @Table?

Jawaban:

```text
untuk mapping entity ke table tertentu
```

25. Apa fungsi @Id?

Jawaban:

```text
menandai primary key entity
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
untuk generate nilai id secara otomatis
```

27. Apa itu Repository?

Jawaban:

```text
interface untuk database
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
menyediakan CRUD dan query otomatis
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
karena Jakarta EE menggantikan javax (karena versi terbaru)
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Query otomatis berdasarkan nama method
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findByEmail(String email)
```

32. Apa fungsi @Query?

Jawaban:

```text
untuk menulis query custom
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
- JPQL berbasis entity

- native query berbasis SQL
```

34. Kapan menggunakan native query?

Jawaban:

```text
ketika query sudah cukup kompleks
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
proses versioning perubahan schema database
```

36. Apa itu Flyway?

Jawaban:

```text
tools untuk migration database
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
supaya konsisten antar environment
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
script migration versi 1 untuk membuat table
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
data tidak konsisten dan sulit di tracking
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
hubungan data antar table
```

41. Apa itu one-to-many?

Jawaban:

```text
satu data berelasi dengan banyak data (table)
```

42. Apa itu many-to-one?

Jawaban:

```text
banyak data mengacu ke satu data (table)
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
mapping relasi banyak data ke satu data (table)
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Mmpping relasi satu data ke banyak data (table)
```

45. Apa itu lazy loading?

Jawaban:

```text
data relasi dimuat saat dibutuhkan
```

46. Apa itu eager loading?

Jawaban:

```text
data relasi langsung dimuat
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
bisa error atau performa akan buruk
```

48. Apa itu N+1 query problem?

Jawaban:

```text
terlalu banyak query akibat loading relasi
```

49. Apa itu join fetch?

Jawaban:

```text
mengambil data relasi dalam satu query
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
agar data terstruktur dan tidak duplikat
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
karena satu loan punya banyak cicilan
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM loan WHERE status = 'ACTIVE';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT customer_id, SUM(amount) FROM payment GROUP BY customer_id;
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic |3|
| SQL basic |3|
| JPA |3|
| Hibernate |3|
| Repository |3|
| Flyway |3|
| Relationship |3|
| Join query |3|
| Lazy loading |3|
| Finance data modeling |3|

## Notes

```text
Tulis bagian yang masih membingungkan.
```