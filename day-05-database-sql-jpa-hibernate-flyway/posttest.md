# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
- In-memory map: Data disimpan di memory aplikasi dan bersifat sementara
- Database: Data disimpan secara permanen di disk database, dan juga bisa diakses oleh banyak aplikasi
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
- Sebagai penyimpanan data persisten
- Untuk menjaga konsistensi data
- Agar mendukung banyak user secara bersamaan
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
- SELECT: Mengambil data
- INSERT: Menambah data
- UPDATE: Mengubah data
- DELETE: Menghapus data
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
- WHERE: Untuk filter kondisi data
- ILIKE: Untuk pencarian teks case-insensitive
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
- Primary Key: Sebagai kunci utama/identifier dalam sebuah tabel, digunakan untuk identitas per row data
- Foreign Key: Sebagai kunci penghubung antar tabel 1 ke tabel lainnya, digunakan untuk relasi
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
- JPA: Spesifikasi tabel (interface)
- Hibernate: Implementasi dari JPA itu sendiri
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
Entity ialah representasi tabel
- @Entity: Untuk menandai class sebagai entity
- @Id: Untuk menandai primary key
- @GeneratedValue: Untuk auto-generate ID
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
- @Table: Untuk memberikan penamaan custom ke tabel
- @Column: Untuk mapping field ke kolom
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
- Repository: Sebagai layer akses ke data
- JpaRepository: Untuk menyediakan CRUD siap pakai
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
- Derived query method ialah query yang dibuat dari nama method
- Contoh: findByEmail(String email)
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query: Untuk membuat custom query
- JPQL (Java Persistence Query Language): Query ke object yang ingin di-populate
- Native: Query SQL secara langsung
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
- Flyway ialah tool versioning database
- Sedangkan Migration itu sendiri penting untuk konsistensi schema
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
- V1__create_table.sql maksudnya version + deskripsi dari query-nya
- Sedangkan migration lama idak boleh diubah untuk menjaga histori dan konsistensi
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
1 Customer punya banyak Orders. Lalu, banyak Order milik 1 Customer
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
- @ManyToOne: Anotasi untuk mendefinisikan banyak data ke satu data
- @OneToMany: Anotasi untuk mendefinisikan satu data ke banyak data
- @JoinColumn: Sebagai anotasi untuk mendefinisikan "foreign key"
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
- Lazy loading ialah load saat dibutuhkan, sedangkan Eager loading ialah load langsung
- Karena Lazy lebih aman untuk performa
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
INNER JOIN ialah ke hanya data yang match, sedangkan LEFT JOIN: semua data kiri + matching kanan
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
- N+1 query problem ialah query berulang (1 + N query)
- Cara menyederhakanannya, salah satunya ialah dengan cara "fetch join"
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
- Entity tidak langsung dijadikan API response karena bisa mengekspos data sensitif dan bisa saja akan menyulitkan maintenance
- Sedangkan DTO (Data Transfer Object) berfungsi sebagai class yang mendefinisikan request dan response API yang berinteraksi langsung dengan controller
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
- @Transactional berfungsi untuk mengatur transaksi
- readOnly=true digunakan untuk jenis operasi baca saja
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Konsep database & SQL, serta flyway juga
2. Cara kerja JPA
3. Konsep relasi antar tabel di database
```

Apa 2 hal yang masih membingungkan?

```text
1. Optimasi query kompleks
2. N+1 problem yang lebih advance mendalam
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara agar menghindari N+1 problem di sistem yang jauh lebih kompleks?
```
