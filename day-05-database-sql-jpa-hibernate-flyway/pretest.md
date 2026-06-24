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
Database adalah tempat untuk menyimpan data secara terstruktur agar dikelola.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Table adalah struktur untuk menyimpan data dalam bentuk baris dan kolom
```

3. Apa itu table?

Jawaban:

```text
Row adalah satu data/record dalam table
```

4. Apa itu row?

Jawaban:

```text
Column adalah atribut atau field dari data dalam table
```

5. Apa itu column?

Jawaban:

```text
Column adalah atribut atau field dari data dalam table
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah penanda unik untuk setiap row di table
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah kolom yang menghubungkan ke primary key di table lain
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Database driver dibutuhkan agar aplikasi bisa berkomunikasi dengan database
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
SELECT digunakan untuk mengambil data dari database
```

10. Apa fungsi INSERT?

Jawaban:

```text
INSERT digunakan untuk menambahkan data baru
```

11. Apa fungsi UPDATE?

Jawaban:

```text
UPDATE digunakan untuk mengubah data
```

12. Apa fungsi DELETE?

Jawaban:

```text
DELETE digunakan untuk menghapus data
```

13. Apa fungsi WHERE?

Jawaban:

```text
WHERE digunakan untuk memfilter data
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE case-sensitive, ILIKE tidak case-sensitive
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
ORDER BY untuk mengurutkan data
```

16. Apa fungsi LIMIT?

Jawaban:

```text
LIMIT untuk membatasi jumlah hasil
```

17. Apa itu JOIN?

Jawaban:

```text
JOIN digunakan untuk menggabungkan data dari beberapa table
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN hanya ambil data yang cocok di kedua table, LEFT JOIN ambil semua dari kiri meskipun tidak ada pasangan
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
JPA adalah spesifikasi untuk mengelola database menggunakan Java
```

20. Apa itu Hibernate?

Jawaban:

```text
Hibernate adalah implementasi dari JPA
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA adalah aturan, Hibernate adalah tools yang menjalankan aturan itu
```

22. Apa itu Entity?

Jawaban:

```text
Entity adalah class Java yang merepresentasikan table
```

23. Apa fungsi @Entity?

Jawaban:

```text
@Entity menandai class sebagai entity
```

24. Apa fungsi @Table?

Jawaban:

```text
@Table menentukan nama table di database
```

25. Apa fungsi @Id?

Jawaban:

```text
@Id menandai primary key.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
@GeneratedValue untuk generate nilai ID otomatis
```

27. Apa itu Repository?

Jawaban:

```text
Repository adalah interface untuk akses data
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
JpaRepository menyediakan fungsi CRUD otomatis
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Karena sudah pindah dari Java EE ke Jakarta EE
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Method query yang dibuat otomatis dari nama method
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findByEmail(String email)
```

32. Apa fungsi @Query?

Jawaban:

```text
@Query digunakan untuk menulis query manual
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL pakai entity, native query pakai SQL langsung
```

34. Kapan menggunakan native query?

Jawaban:

```text
Saat butuh query kompleks atau fitur khusus database
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Proses mengubah struktur database secara bertahap
```

36. Apa itu Flyway?

Jawaban:

```text
Flyway adalah tools untuk mengatur migration database
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Agar perubahan database terkontrol dan tidak bentrok
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
Versi 1 untuk membuat table customers
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Bisa menyebabkan error dan data tidak konsisten
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Hubungan antar table di database.
```

41. Apa itu one-to-many?

Jawaban:

```text
Satu data punya banyak data lain di beda tabel
```

42. Apa itu many-to-one?

Jawaban:

```text
Banyak data dari beberapa tabel mengarah ke satu data
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Menentukan relasi many-to-one
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Menentukan relasi one-to-many
```

45. Apa itu lazy loading?

Jawaban:

```text
Data diambil hanya saat dibutuhkan
```

46. Apa itu eager loading?

Jawaban:

```text
Data langsung diambil saat query awal
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Bisa menyebabkan error atau performa buruk
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Query berulang banyak kali (boros)
```

49. Apa itu join fetch?

Jawaban:

```text
Mengambil data relasi dalam satu query
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Agar data lebih rapi dan bisa digunakan ulang (tidak duplikat)
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena satu loan punya banyak jadwal pembayaran
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM loan WHERE status = 'ACTIVE';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT SUM(amount) FROM payment WHERE customer_id = 1;
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        |     3     |
| SQL basic             |     3     |
| JPA                   |     3     |
| Hibernate             |     3     |
| Repository            |     3     |
| Flyway                |     2     |
| Relationship          |     3     |
| Join query            |     3     |
| Lazy loading          |     3     |
| Finance data modeling |     3     |

## Notes

```text
Bagaimana menentukan case mana yang perlu menggunakan lazy loading?
```
