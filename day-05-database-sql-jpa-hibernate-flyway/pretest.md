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
Database atau basi data adalah kumpulan data yang disimpan secara terstruktur dan dapat diakses/dikelola
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Map disimpan di memory (sementara), database persistent dan bisa diakses banyak user (multiple user)
```

3. Apa itu table?

Jawaban:

```text
Table adalah struktur penyimpanan data berbentuk baris dan kolom
```

4. Apa itu row?

Jawaban:

```text
Row adalah satu record/baris data dalam table
```

5. Apa itu column?

Jawaban:

```text
Column adalah atribut/field dari data dalam table
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah identifier unik untuk tiap row
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah kolom yang mereferensikan primary key di table lain
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Untuk menghubungkan aplikasi dengan database (komunikasi & query execution)
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
SELECT untuk mengambil data
```

10. Apa fungsi INSERT?

Jawaban:

```text
INSERT untuk menambah data
```

11. Apa fungsi UPDATE?

Jawaban:

```text
UPDATE untuk mengubah data
```

12. Apa fungsi DELETE?

Jawaban:

```text
DELETE untuk menghapus data
```

13. Apa fungsi WHERE?

Jawaban:

```text
WHERE untuk filter data spesifik
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE case-sensitive, ILIKE case-insensitive
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
JOIN untuk menggabungkan data antar table
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN hanya data yang match, LEFT JOIN ambil semua kiri + yang match
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Salah satu opsi ORM di Java
```

20. Apa itu Hibernate?

Jawaban:

```text
Implementasi dari JPA
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA = interface, Hibernate = implementasi
```

22. Apa itu Entity?

Jawaban:

```text
Representasi table dalam class
```

23. Apa fungsi @Entity?

Jawaban:

```text
@Entity menandai class sebagai entity
```

24. Apa fungsi @Table?

Jawaban:

```text
Menentukan nama table
```

25. Apa fungsi @Id?

Jawaban:

```text
Menandai primary key
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Untuk auto generate ID
```

27. Apa itu Repository?

Jawaban:

```text
Sebagai layer akses data
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
JpaRepository menyediakan CRUD otomatis
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Karena migrasi dari Java EE ke Jakarta EE
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Method query otomatis berdasarkan nama method
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findByEmail(String email)
```

32. Apa fungsi @Query?

Jawaban:

```text
Untuk menulis query custom
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL pakai entity, native query pakai SQL langsung
```

34. Kapan menggunakan native query?

Jawaban:

```text
Saat butuh query yang kompleks atau spesifik DB
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Proses perubahan struktur database (schema)
```

36. Apa itu Flyway?

Jawaban:

```text
Tool untuk versioning dan migration DB
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Agar perubahan terkontrol dan tentunya menjaga ke-konsisten-an
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
Versi 1 untuk create table customers
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Risiko mismatch antar environment dan error
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Hubungan antar table melalui forign key
```

41. Apa itu one-to-many?

Jawaban:

```text
Satu data punya banyak data lain
```

42. Apa itu many-to-one?

Jawaban:

```text
Banyak data mengarah ke satu data
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Relasi many ke one
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Relasi one ke many
```

45. Apa itu lazy loading?

Jawaban:

```text
Data di-load saat dibutuhkan saja (biasanya pada saat sudah masuk/kelihatan di view aplikasi)
```

46. Apa itu eager loading?

Jawaban:

```text
Data langsung di-load
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Bisa menyebabkan error
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Query yang berulang secara berlebihan
```

49. Apa itu join fetch?

Jawaban:

```text
Join + Fetch data sekaligus
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Agar tidak duplikasi dan lebih scalable
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena banyak jadwal per loan
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM loan WHERE status = 'APPROVED';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT SUM(p.amount) FROM payment p WHERE p.customer_id = 1;
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        | 5         |
| SQL basic             | 5         |
| JPA                   | 4         |
| Hibernate             | 4         |
| Repository            | 4         |
| Flyway                | 3         |
| Relationship          | 4         |
| Join query            | 4         |
| Lazy loading          | 4         |
| Finance data modeling | 4         |

## Notes

```text
Bagian relationship dan lazy loading masih perlu diperdalam.
```
