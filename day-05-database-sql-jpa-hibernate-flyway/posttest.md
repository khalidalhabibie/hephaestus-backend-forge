# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In-memory Map menyimpan data di RAM dan data hilang saat aplikasi mati, sedangkan database menyimpan data secara permanen di disk dan mendukung query serta multi-user.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Database diperlukan untuk menyimpan data secara permanen, menjaga konsistensi.
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
SELECT: mengambil data
INSERT: menambah data  
UPDATE: mengubah data  
DELETE: menghapus data
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
WHERE untuk memfilter data, ILIKE untuk pencarian string tanpa case-sensitive di PostgreSQL.
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
Primary key adalah identitas unik tiap row, foreign key adalah penghubung antar tabel.
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA adalah spesifikasi ORM, Hibernate adalah implementasi JPA.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text

Entity adalah representasi tabel.

@Entity: menandai class entity  
@Id: primary key  
@GeneratedValue: auto generate ID

```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table menentukan nama tabel, @Column mengatur mapping kolom.
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository adalah layer akses database, JpaRepository menyediakan CRUD otomatis.
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text

Derived query method adalah method yang otomatis jadi query.

Contoh:
Customer findByEmail(String email);

```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query untuk custom query.

JPQL: pakai entity  
Native query: pakai SQL langsung

```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway untuk versioning database. Migration penting agar perubahan database tercatat dan konsisten.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1 = versi, nama = deskripsi.
Migration lama tidak boleh diubah agar tidak merusak history database.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
One-to-many: satu customer punya banyak order  One-to-many: satu customer punyaMany-to-one: banyak order milik satu customer
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text

@ManyToOne: banyak ke satu  
@OneToMany: satu ke banyak  
@JoinColumn: foreign key

```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text

Lazy: load saat dibutuhkan  
Eager: langsung load semua

LAZY lebih aman untuk performa.

```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text

JOIN untuk gabung tabel

INNER JOIN: data matching saja  
LEFT JOIN: semua data kiri ditambah yang cocok di kanan

```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 adalah banyak query tambahan akibat relasi.

Solusi: gunakan JOIN FETCH atau query optimization.
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Entity tidak langsung digunakan karena bisa expose data sensitif.

DTO membuat response lebih aman dan terkontrol.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@Transactional memastikan operasi database konsisten. @Transactional(readOnly = true) memastikan operasi database.

```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text

1. Cara kerja JPA dan Hibernate
2. Relationship antar entity
3. Flow docker & database dari query sampai API
```

Apa 2 hal yang masih membingungkan?

```text
1. kendala image di docker terkadang masih tersimpan yang lama sehingga harus di remove dulu
2. membuat logic
```

Apa 1 pertanyaan untuk mentor?

```text
tidak ada
```
