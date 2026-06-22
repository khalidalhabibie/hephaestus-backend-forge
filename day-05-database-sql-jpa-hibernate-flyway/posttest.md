# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In memory Map menyimpan data secara temporary(sementara) sedangkan database menyimpan data secara persisten untuk jangka panjang, sehingga tidak ter-reset ketika kita re-run program.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Untuk menyimpan data secara persisten dan memisahkan layer data dengan layer logic.
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
SELECT -> untuk memilih data yang ingin diambil / diakses
INSERT -> untuk menambahkan data ke dalam database
UPDATE -> untuk melakukan update data pada database
DELETE -> untuk menghapus data pada database
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
WHERE berfungsi untuk menambahkan kondisi saat ingin melakukan select data, sedangkan ILIKE berfungsi untuk mencari teks yang mengandung value yang di-specify
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
Primary key -> adalah kunci unik yang berfungsi sebagai uniqie identifier
Foreign key -> attribute yang ada di lebih dari 1 tabel yang berfungsi untuk menghubungkan kedua tabel tsb.
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA (Java Persistence API) adalah standar di Java untuk mengelola data ke database, sedangkan Hibernate adalah framework (implementasi JPA) untuk ORM (Object Relational Mapping). Jadi JPA seperti aturan atau standar yang harus diikuti, dan Hibernate adalah implementasi dari standar tsb.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
@Entity -> untuk mendefinisikan suatu class sebagai entit
@Id -> untuk mendefinisikan suatu attribute sebagai Id
@GeneratedValue -> untuk menunjukkan bahwa value di bawahnya merupakan value yang di-generate oleh system
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table -> untuk menentukan nama tabel di database
@Column -> untuk memetakan nama kolom di database 
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository adalah layer yang digunakan untuk mengakses database. JpaRepository berguna untuk menyediakan semua operasi database siap pakai sehingga tidak perlu query semuanya dari nol.
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Derived query adalah query yang dijalankan dari nama method, tanpa menulis SQL atau JPQL secara manual. Contoh: Customer findByEmail(String email);
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query berguna untuk menjalankan teks di dalamnya sebagai query dalam database.
JPQL -> Query berbasis object (entity), bukan tabel database, sehingga menggunakan nama entity bukan tabel
Native Query -> Query yang menggunakan SQL database asli, menggunakan nama tabel  
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah tools untuk database migration yang digunakan untuk mengelola perubahan struktur database secara terstruktur dan versioned. Database migration penting agar semua database konsisten, dan lebih tracable untuk setiap perubahan yang terjadi, serta meminimalisir human error.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1 → Versi 1 (harus urut)
__ (double underscore) → separator wajib
create_customers_table → deskripsi
Migration lama sebaiknya tidak diubah setelah dijalankan untuk menjaga konsistensi.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
One to many berarti satu data pada satu tabel bisa terhubung pada lebih dari 1 data di tabel yang lain. Contoh: 1 customer bisa membuat banyak order, sehingga relationship one to many

Many to one berarti lebih dari 1 data di suatu tabel terhubung pada hanya 1 data pada tabel yang lain. Contoh: Beberapa order bisa milik 1 customer
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne -> menggambarkan relationship many to one
@OneToMany -> menggambarkan relationship one to many 
@JoinColumn -> menggambarkan join antara 2 tabel
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy Loading berarti data relasi tidak langsung diambil dari database, tetapi baru di-load saat dibutuhkan, sedangkan eager Loading berarti data relasi langsung diambil bersamaan dengan entity utama. FetchType.LAZY lebih aman karena menghindari mengambil data terlalu banyak yang memberatkan sistem sehingga menjaga performa.
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
SQL Join adalah operasi join untuk menggabungkan atau menghubungkan dua tabel. Inner join mengambil data yang ada di kedua tabel, sedangkan left join mengambil data yang ada di tabel kiri dan data di tabel kanan yang match dengan tabel kiri.
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 query problem adalah masalah ketika satu query utama diikuti oleh banyak query tambahan (N query), sehingga membuat performa jadi lambat.
Cara sederhana menguranginya adalah dengan menggunakan JOIN FETCH, entity graph, atau query khusus (@Query) agar data relasi diambil dalam satu query saja.
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Karena bisa mengekspos data sensitif, menimbulkan masalah performa (seperti lazy loading), dan membuat API terikat pada struktur database.
DTO digunakan untuk mengontrol data yang dikirim, meningkatkan keamanan, serta memisahkan struktur internal (entity) dari kebutuhan response API.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@Transactional digunakan untuk mengelola transaksi database agar operasi seperti insert, update, dan delete.
@Transactional(readOnly = true) digunakan saat hanya melakukan operasi baca (select) karena lebih ringan dan dapat meningkatkan performa.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Penggunaan database dibandingkan Map
2. Menerima input timestamp
3. Mapping database tabel & column
```

Apa 2 hal yang masih membingungkan?

```text
1. Pengerjaan service untuk API yang membutuhkan JOIN
2. 500 Internal Server Error karena bisa memiliki banyak penyebab
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara menemukan root cause error?
```
