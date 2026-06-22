# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In-memory Map menyimpan data di memori aplikasi sehingga data akan hilang saat aplikasi berhenti. Database menyimpan data secara permanen sehingga tetap ada meskipun aplikasi dimatikan.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Karena database dapat menyimpan data secara aman, terstruktur, dan dapat digunakan oleh banyak pengguna secara bersamaan dan tidak hilang jika aplikasinya mati
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
SELECT untuk mengambil data.
INSERT untuk menambahkan data.
UPDATE untuk mengubah data.
DELETE untuk menghapus data.
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
WHERE digunakan untuk memfilter data berdasarkan kondisi tertentu. ILIKE digunakan untuk pencarian teks tanpa membedakan huruf besar dan kecil.
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
Primary key adalah identitas unik setiap data pada tabel. Foreign key adalah kolom yang menghubungkan satu tabel dengan tabel lainnya.
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA adalah standar untuk mengelola data di Java. Hibernate adalah implementasi dari JPA yang menjalankan proses tersebut.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
Entity adalah class yang mewakili tabel database.
@Entity menandai class sebagai entity.
@Id menandai primary key.
@GeneratedValue membuat nilai id otomatis.
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table digunakan untuk menentukan nama tabel. @Column digunakan untuk menentukan nama dan pengaturan kolom pada database.
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository adalah tempat untuk mengakses data database. JpaRepository menyediakan fungsi CRUD sehingga tidak perlu membuat query dasar secara manual.
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Derived query method adalah method yang otomatis dibuat menjadi query berdasarkan nama method.
Contohnya adalah Customer findByEmail(String email);
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query digunakan untuk membuat query secara manual. JPQL menggunakan nama Entity dan atribut Java. Native query menggunakan SQL asli sesuai database.
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah tools untuk mengelola perubahan database, kalau Migration penting agar struktur database tetap konsisten di setiap lingkungan aplikasi.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1 menunjukkan versi migration pertama dan create_customers_table menjelaskan isi migration. Migration lama tidak boleh diubah agar riwayat perubahan database tetap konsisten dan tidak menimbulkan error.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
One-to-many berarti satu Customer dapat memiliki banyak Order, Many-to-one berarti banyak Order dapat dimiliki oleh satu Customer.
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne menunjukkan banyak data terhubung ke satu data lain.
@OneToMany menunjukkan satu data terhubung ke banyak data.
@JoinColumn menentukan kolom penghubung antar tabel.
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy loading mengambil data saat dibutuhkan. Eager loading langsung mengambil semua data terkait.
LAZY lebih aman karena mengurangi pengambilan data yang tidak diperlukan sehingga aplikasi lebih efisien.
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
SQL join digunakan untuk menggabungkan data dari beberapa tabel.
INNER JOIN hanya menampilkan data yang memiliki pasangan kalau LEFT JOIN menampilkan semua data dari tabel kiri meskipun tidak memiliki pasangan.
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 terjadi ketika aplikasi menjalankan terlalu banyak query untuk mengambil data yang saling berhubungan, cara menguranginya adalah mengambil data yang dibutuhkan sekaligus menggunakan join atau fetch join.
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Karena Entity bisa berisi data yang tidak perlu ditampilkan. DTO membantu mengatur data yang dikirim ke client agar lebih aman dan sesuai kebutuhan.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@Transactional digunakan agar proses database berjalan sebagai satu kesatuan.
@Transactional(readOnly = true) digunakan saat hanya membaca data tanpa melakukan perubahan.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Cara melakukan operasi data menggunakan SQL dan JPA.
2. Cara menghubungkan tabel menggunakan relationship.
3. Pentingnya migration database menggunakan Flyway.
```

Apa 2 hal yang masih membingungkan?

```text
1. Kapan sebaiknya menggunakan Join Fetch dibandingkan DTO Projection untuk menghindari masalah N+1 query.
2. Cara menentukan kapan menggunakan Derived Query, JPQL, atau Native Query pada kasus yang lebih kompleks di project nyata.
```

Apa 1 pertanyaan untuk mentor?

```text
Apa praktik terbaik yang biasanya digunakan tim backend untuk mengelola relationship antar entity agar tetap mudah dikembangkan dan memiliki performa yang baik?
```
