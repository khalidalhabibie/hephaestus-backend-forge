# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
in-memory Map itu local menggunakan HashMap, database itu pakai server, bisa pake postgre
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
ya supaya deploy-able ga cm local aja```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
select mengambil data, insert menginput data, update mengupdate data, delete menghapus data```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
where itu query sql untuk menentukan posisi data yg mau diambil, ilike itu untuk mencari data yang cocok dengan tdk sensitif
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
primary key itu adalah variable utama di table itu, foreign key itu adalah primary key dari table lain yang dipakai di table tersebut
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA adalah spesifikasi standar Java untuk ORM (Object-Relational Mapping). Hibernate adalah implementasi konkret dari JPA. JPA seperti interface, Hibernate seperti class yang mengimplementasikannya.


```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
Entity itu framework spring yang memetakan class java ke database. Id itu untuk menandakan primary key, generated value itu untuk men-generate ID 
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table itu untuk memetakan entity ke nama tertentu, @Column itu memetakan field ke kolom tertentu dengan constraint.
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository adalah layer untuk akses database, JpaRepository adalah penyedia berbagai metode untuk CRUD, pagination, sorting otomatis
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
method yang di generate -sql kan oleh spring data jpa```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
Tulis jawaban di sini.
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah tool database migration. Migration penting untuk versioning schema database, tracking perubahan, dan menjaga konsistensi antar environment (dev, staging, prod).
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1__create_customers_table.sql: V1 = versi, __ = separator, deskripsi = nama file. Migration lama tidak diubah karena sudah di-apply di database. Mengubahnya bikin inconsistency antar environment.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
Customer (1) → Order (N). Satu customer punya banyak order. Order (N) → Customer (1). Banyak order dimiliki satu customer.
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
Tulis jawaban di sini.@ManyToOne: banyak entity ini milik satu entity lain (Order punya satu Customer). @OneToMany: satu entity punya banyak entity lain (Customer punya banyak Order). @JoinColumn: menentukan foreign key column untuk join.```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy: data relasi di-load hanya saat diakses. Eager: data relasi di-load langsung saat query utama. LAZY lebih aman karena mencegah load data tidak perlu yang bikin lambat dan boros memory.
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
Join menggabungkan data dari dua tabel. INNER JOIN: hanya baris yang match di kedua tabel. LEFT JOIN: semua baris tabel kiri + data kanan kalau match (null kalau tidak match).
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
Query 1 untuk ambil N data, lalu N query tambahan untuk ambil relasi masing-masing. Solusi: JOIN FETCH, Entity Graph, atau batch fetching.
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Entity tidak langsung dikembalikan karena: (1) expose struktur database, (2) risk infinite loop JSON, (3) tidak fleksibel. DTO memisahkan struktur internal dengan kontrak API, lebih aman dan fleksibel.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
Transactional untuk membungkus method dalam transaction (commit/rollback otomatis). read only itu untuk yg sifatnya non-write
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. cara konek database
2. 70% logic di training folder
3. query sql sederhana dan flyway
```

Apa 2 hal yang masih membingungkan?

```text
1. logic flyway
2. 30% logic bisnisnya
```

Apa 1 pertanyaan untuk mentor?

```text
Penasaran sama skema pembiayaan database system kek gini agar bisa sustain untuk waktu yang tidak ditentukan. Apalagi dari segi konsumsi pastinya besar
```
