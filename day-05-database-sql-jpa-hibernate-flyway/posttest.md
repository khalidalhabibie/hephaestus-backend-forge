# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In-memory Map itu nyimpen data di RAM, jadi cepat tapi kalau aplikasi mati datanya ikut hilang.

Database nyimpen data di storage (disk), jadi datanya tetap ada walaupun aplikasi dimatikan.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Karena kita butuh data yang tersimpan permanen, bisa dipakai banyak user, dan aman. Jika cuma pakai memory, datanya hilang dan tidak cocok untuk aplikasi real.
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
SELECT: ambil data
INSERT: tambah data  
UPDATE: ubah data  
DELETE: hapus data  
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
WHERE: untuk filter data sesuai kondisi
ILIKE: untuk cari text tanpa peduli huruf besar/kecil  
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
- Primary key merupakan field dalam suatu tabel yang sifatnya unik dan sebagai identitas pembeda suatu row data, 
- Foreign key: penghubung ke tabel lain (relasi)
 
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA itu standar (aturan), sedangkan Hibernate itu implementasinya. Sehingga Hibernate yang benar-benar dijalankan, JPA cuma konsepnya.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
Entity itu representasi tabel di Java.
- @Entity: tandain class sebagai tabel  
- @Id: primary key  
- @GeneratedValue: ID dibuat otomatis  
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
- @Table: nama tabel di database
- @Column: nama kolom di tabel  
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository itu tempat akses database. JpaRepository memudahkan karena sudah ada CRUD tanpa harus dibuat manual

```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Query yang dibuat dari nama method, Contoh: findByEmail(String email)
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query dipakai kalau query custom.

JPQL pakai entity (Java object) sedangkan native query pakai SQL asli database  
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway untuk ngatur perubahan database. Penting supaya semua environment punya struktur database yang sama.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
(V1) = versi, (create_table) = deskripsi
Migration lama tidak diubah karena bisa bikin error di environment lain.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
Satu Customer bisa punya banyak Order (one-to-many). Satu Order cuma milik satu Customer (many-to-one)
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne: banyak ke satu
@OneToMany: satu ke banyak  
@JoinColumn: kolom foreign key  
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy: data diambil saat dibutuhkan
Eager: langsung diambil semua  

Lazy biasanya lebih aman biar tidak ambil data berlebihan
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
Join untuk gabung tabel

- INNER JOIN: ambil yang cocok saja  
- LEFT JOIN: ambil semua dari kiri + yang cocok dari kanan  
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
Terjadi saat query utama diikuti banyak query tambahan.
Cara sederhana: pakai join fetch atau optimasi query
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Karena bisa membocorkan data yang tidak perlu. DTO dipakai supaya data yang keluar lebih aman dan sesuai kebutuhan
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
Untuk mengatur transaksi database. readOnly = true dipakai kalau cuma baca data supaya lebih ringan.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Perbedaan antara penyimpanan data di memory dan database
2. Cara kerja dasar JPA, Hibernate, dan repository
3. Konsep relasi antar tabel seperti one-to-many dan many-to-one
```

Apa 2 hal yang masih membingungkan?

```text
1. Cara mengatasi N+1 problem secara optimal di project real
2. Kapan sebaiknya pakai JPQL dibanding native query
```

Apa 1 pertanyaan untuk mentor?

```text
Dalam project production, lebih sering pakai JPQL atau native query untuk performa yang lebih baik?
```
