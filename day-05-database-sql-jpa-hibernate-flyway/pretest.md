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
Database adalah tempat atau wadah untuk menyimpan, mengelola, dan mengorganisir data agar bisa diakses, dicari, dan diubah dengan mudah oleh aplikasi.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Map -> menyimpan data di memory aplikasi dan biasanya hilang saat aplikasi diberhentikan.
Database -> menyimpan data secara permanen di media penyimpanan, sehingga data tetap ada walaupun aplikasi dimatikan (e.g. products yang kita simpan di keranjang e-commerce).
```

3. Apa itu table?

Jawaban:

```text
Table adalah struktur penyimpanan data di database.
```

4. Apa itu row?

Jawaban:

```text
Row adalah satu data dalam sebuah table (e.g. pada table Customer satu row bisa berisi data satu Customer).
```

5. Apa itu column?

Jawaban:

```text
Column adalah atribut atau field yang menjelaskan data yang disimpan (e.g. id, name, email, etc).
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah kolom yang digunakan untuk mengidentifikasi setiap row secara unik dalam sebuah table (e.g. id_customer).
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah kolom yang menghubungkan suatu table dengan table lain melalui primary key, di mana dapat membangun relasi antar table (e.g. id_customer pada table Order untuk melihat order dari masing-masing Customer).
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Karena database driver berfungsi sebagai penghubung antara aplikasi dan database, sehingga aplikasi dapat mengirim query dan menerima hasil dari database.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
SELECT digunakan untuk mengambil atau membaca data dari database (e.g. SELECT * FROM customer).
```

10. Apa fungsi INSERT?

Jawaban:

```text
INSERT digunakan untuk menambahkan data baru ke table (e.g. INSERT INTO customer(name, email) VALUES ('Edith', 'edith@gmail.com')).
```

11. Apa fungsi UPDATE?

Jawaban:

```text
UPDATE digunakan untuk mengubah data yang sudah ada (e.g. UPDATE customer SET email = 'edith@gmail.com').
```

12. Apa fungsi DELETE?

Jawaban:

```text
DELETE digunakan untuk menghapus data (e.g. DELETE FROM customer).
```

13. Apa fungsi WHERE?

Jawaban:

```text
WHERE digunakan untuk memberikan kondisi pada query (e.g. UPDATE customer SET email = 'edith@gmail.com' WHERE salary = 5000000).
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
LIKE -> membedakan huruf besar dan kecil.
ILIKE -> tidak membedakan huruf besar dan kecil.
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
ORDER BY digunakan untuk mengurutkan data.
```

16. Apa fungsi LIMIT?

Jawaban:

```text
LIMIT digunakan untuk membatasi jumlah data.
```

17. Apa itu JOIN?

Jawaban:

```text
JOIN digunakan untuk menggabungkan data dari beberapa table yang saling berhubungan.
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN -> hanya menampilkan data yang memiliki relation.
LEFT JOIN -> menampilkan semua data dari table kiri meskipun tidak memiliki relation.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Tulis jawaban di sini.
```

20. Apa itu Hibernate?

Jawaban:

```text
Tulis jawaban di sini.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
Tulis jawaban di sini.
```

22. Apa itu Entity?

Jawaban:

```text
Entity adalah sebuah class Java yang merepresentasikan sebuah table di database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
@Entity berfungsi untuk menandai bahwa sebuah Class adalah Entity dan akan dipetakan ke table database.
```

24. Apa fungsi @Table?

Jawaban:

```text
Tulis jawaban di sini.
```

25. Apa fungsi @Id?

Jawaban:

```text
Tulis jawaban di sini.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Tulis jawaban di sini.
```

27. Apa itu Repository?

Jawaban:

```text
Tulis jawaban di sini.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Tulis jawaban di sini.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Tulis jawaban di sini.
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
Tulis jawaban di sini.
```

32. Apa fungsi @Query?

Jawaban:

```text
Tulis jawaban di sini.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
Tulis jawaban di sini.
```

34. Kapan menggunakan native query?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Tulis jawaban di sini.
```

36. Apa itu Flyway?

Jawaban:

```text
Tulis jawaban di sini.
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Tulis jawaban di sini.
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
File migration versi 1 yang berisi code untuk membuat table customers.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Relationship antar table adalah hubungan antara data pada satu table dengan data pada table lain.
```

41. Apa itu one-to-many?

Jawaban:

```text
Satu data pada table A dapat memiliki banyak data terkait pada table B.
```

42. Apa itu many-to-one?

Jawaban:

```text
Banyak data pada table A terhubung ke satu data pada table B.
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Tulis jawaban di sini.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Tulis jawaban di sini.
```

45. Apa itu lazy loading?

Jawaban:

```text
Tulis jawaban di sini.
```

46. Apa itu eager loading?

Jawaban:

```text
Tulis jawaban di sini.
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Tulis jawaban di sini.
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Tulis jawaban di sini.
```

49. Apa itu join fetch?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Karena satu customer bisa mengajukan banyak pinjaman, sehingga data customer tidak perlu diulang pada setiap loan dan data menjadi lebih terstruktur dengan memisahkan table.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena satu pinjaman biasanya memiliki banyak jadwal pembayaran.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM loan_application
WHERE status = 'APPROVED';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT customer_id,
SUM(amount) AS total_payment
FROM repayment
GROUP BY customer_id;
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic | 4 |
| SQL basic | 4 |
| JPA | 1 |
| Hibernate | 1 |
| Repository | 1 |
| Flyway | 1 |
| Relationship | 3 |
| Join query | 4 |
| Lazy loading | 1 |
| Finance data modeling | 3 |

## Notes

```text
- Perbedaan detail JPQL dan native query;
- Cara kerja Hibernate di belakang layar; dan;
- Lazy loading, eager loading, dan N+1 query problem.
```

