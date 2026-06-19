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
tempat penyimpanan data
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
map hanya sementara disimpan di memory sedangkan database menyimpan secara permanen
```

3. Apa itu table?

Jawaban:

```text
table berisi beberapa atribut data
```

4. Apa itu row?

Jawaban:

```text
value dari sebuah atribut
```

5. Apa itu column?

Jawaban:

```text
semacam objek yang berisi beberapa atribut
```

6. Apa itu primary key?

Jawaban:

```text
id unik sebuah table
```

7. Apa itu foreign key?

Jawaban:

```text
id dari table lain ketika ada di sebuah table, berfungsi sebagai reference
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
tidak tau
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
mengambil/menampilkan data
```

10. Apa fungsi INSERT?

Jawaban:

```text
menambahkan data
```

11. Apa fungsi UPDATE?

Jawaban:

```text
mengupdate data
```

12. Apa fungsi DELETE?

Jawaban:

```text
menghapus data secara permanen
```

13. Apa fungsi WHERE?

Jawaban:

```text
mencari lokasi data berada di table mana
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
tidak tau
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
mengurutkan output query
```

16. Apa fungsi LIMIT?

Jawaban:

```text
tidak tau
```

17. Apa itu JOIN?

Jawaban:

```text
menggabungkan tabel
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
inner join mengembalikan data yang cocok atau beririsan di kedua tabel. sedangkan left join mengembalikan seluruh baris dari tabel utama (kiri) ditambah data dari tabel kanan yang cocok.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
tidak tau
```

20. Apa itu Hibernate?

Jawaban:

```text
tidak tau
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
tidak tau
```

22. Apa itu Entity?

Jawaban:

```text
representasi data di database
```

23. Apa fungsi @Entity?

Jawaban:

```text
penanda yang menyatakan bahwa sebuah kelas Java mewakili tabel di dalam basis data (database)
```

24. Apa fungsi @Table?

Jawaban:

```text
digunakan untuk memetakan kelas Java (Entity) ke tabel spesifik di dalam database
```

25. Apa fungsi @Id?

Jawaban:

```text
untuk menandai sebuah variabel atau field sebagai Primary Key
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
tidak tau
```

27. Apa itu Repository?

Jawaban:

```text
komponen (atau lapisan/layer) yang bertugas sebagai penghubung antara aplikasi dan database
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
tidak tau
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
karena adanya perubahan versi, javax merupakan versi lama
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
tidak tau
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
tidak tau
```

32. Apa fungsi @Query?

Jawaban:

```text
digunakan untuk mendefinisikan kueri khusus (custom query) agar aplikasi dapat berinteraksi dengan database
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
tidak tau
```

34. Kapan menggunakan native query?

Jawaban:

```text
tidak tau
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Database migration adalah proses memindahkan data dari satu sistem database ke sistem database lainnya.
```

36. Apa itu Flyway?

Jawaban:

```text
tools untuk migrasi database
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
memastikan konsistensi data, mencegah konflik antar tim, dan memungkinkan rollback yang aman jika terjadi kegagalan deployment
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
file sql ini merupakan versi 1
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
kehilangan catatan kronologis perubahan struktur (schema)
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
hubungan atau keterkaitan antara dua atau lebih tabel dalam sebuah database
```

41. Apa itu one-to-many?

Jawaban:

```text
1 tabel dapat terhubung ke banyak tabel anak sedangkan anak hanya satu terhubung ke induk tabel
```

42. Apa itu many-to-one?

Jawaban:

```text
banyak tabel dapat terhubung ke 1 tabel anak
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
 untuk memetakan relasi basis data many to one
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
 untuk memetakan relasi basis data one to many
```

45. Apa itu lazy loading?

Jawaban:

```text
teknik optimasi web yang menunda pemuatan sumber daya (seperti gambar, video, atau skrip) hingga benar-benar dibutuhkan
```

46. Apa itu eager loading?

Jawaban:

```text
teknik pemrograman untuk memuat data terkait (relasi) atau modul secara bersamaan di awal, segera setelah data atau program utama diminta
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
performa loading awal yang justru lebih lambat dan penurunan user experience
```

48. Apa itu N+1 query problem?

Jawaban:

```text
tidak tau
```

49. Apa itu join fetch?

Jawaban:

```text
untuk menggabungkan (join) tabel sekaligus memuat (fetch) data relasi dari database dalam satu kali query
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
mencegah duplikasi data, kemudahan pembaruan data, memfasilitasi hubungan 1 to many
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
karena setiap pembayaran bulanan memiliki porsi alokasi yang berbeda untuk pokok pinjaman dan bunga, meskipun jumlah angsuran yang dibayar nasabah terlihat sama
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT loan_id, name, amount, status 
FROM loans 
WHERE status = 'Active';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT 
    customer_id, 
    SUM(amount) AS total_pembayaran
FROM 
    payments
GROUP BY 
    customer_id;
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic |3|
| SQL basic |3|
| JPA |1|
| Hibernate |1|
| Repository |1|
| Flyway |1|
| Relationship |3|
| Join query |3|
| Lazy loading |1|
| Finance data modeling |2|

## Notes

```text
Tulis bagian yang masih membingungkan.
```

