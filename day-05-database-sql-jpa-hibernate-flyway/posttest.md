# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
Map : data disimpan secara sementara (memori)

database : data disimpan secara permanen 
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
karena untuk penyimpanan data utama yang terstruktur, aman, dan dapat diakses secara cepat serta mudah
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
- SELECT : syntax untuk menampilkkan sebuah data 
- INSERT : syntax untuk memasukkan/menginput/menambahkan data ke dalam table
- UPDATE : syntax untuk memperbarui data yang sudah ada di dalam database
- DELETE : syntax untuk menghapus data (dapat menghapus berdasarkan kondisi yang ingin dihapus)
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
- WHERE : memfilter baris data berdasarkan kondisi tertentu
- ILIKE : pencarian teks yang tidak case-sensitive
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
- primary key : Identitas unik setiap tabel
- foreign key : Menjaga hubungan antar tabel tetap konsisten
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
- JPA : standar/aturan ORM di Java (Cara kerja: mendefinisikan cara kerja)
- Hibernate : implementasi nyata dari JPA (Cara kerja: menjalankan operasi)
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
- `@Entity` : untuk entitas yang akan disimpan di database
- `@Id` : untuk menandai primary key dalam entity
- `@GeneratedValue` : untuk mengatur bagaimana nilai primary key dibuat otomatis
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
- `@Table` :  untuk menentukan nama tabel di database yang direpresentasikan oleh entity
- `@Column` : untuk menentukan mapping field Java ke kolom database
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
`JpaRepository` : interface bawaan dari Spring yang menyediakan berbagai method siap pakai untuk operasi database
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Derived Query Method : query otomatis dari nama method di repository

Contoh: Customer findByEmail(String email);
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
- `@Query` : untuk membuat query custom di repository

- JPQL -> query berbasis object/entity, bukan tabel langsung
Berbasis entity, Lebih portable dan aman

- Native Query -> query SQL asli (langsung ke database).
SQL langsung, Lebih fleksibel tapi tergantung database
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah tool untuk database migration yang membantu mengelola database

database migration penting karena menjaga konsistensi schema, menghindari error di production, dan mempermudah deployment dan kolaborasi tim
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
Karena standar penamaan di flyway seperti berikut : V<version>__<description>.sql

migration lama sebaiknya tidak diubah setelah dijalankan karena sudah dijalankan, sudah tercatat, dan harus konsisten di semua environment
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
One-to-Many : 1 customer punya banyak order
Many-to-One : Banyak order milik 1 customer
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne : Banyak entity ke satu entity
@OneToMany : Satu entity ke banyak entity
@JoinColumn : Menentukan foreign key di database
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy Loading (LAZY)
- Ambil data saat dibutuhkan
- Lebih hemat & fleksibel


Eager Loading (EAGER)
- Ambil data langsung
- Bisa berat jika tidak dikontrol

Kenapa `FetchType.LAZY` sering lebih aman sebagai default karena mencegah pengambilan data berlebihan, meningkatkan performa, dan memberi kontrol lebih pada query di production. 
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
JOIN : menggabungkan tabel berdasarkan relasi
`INNER JOIN` : hanya ambil data yang cocok di kedua tabel
`LEFT JOIN` : ambil semua data dari tabel kiri, walaupun tidak ada pasangan
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 Query Problem adalah masalah performa di ORM (seperti JPA/Hibernate) di mana sistem menjalankan 1 query utama + N query tambahan untuk mengambil data relasi.

solusi untuk mengurangi yaitu hindari akses LAZY di loop tanpa JOIN dan gunakan JOIN FETCH serta @EntityGraph
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Entity sebaiknya tidak langsung dikembalikan sebagai API response karena risiko security, tight coupling, masalah performance & lazy loading

manfaat DTO:
- Lebih aman
- Lebih fleksibel
- Lebih efisien untuk production
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
fungsi `@Transactional` adalah untuk mengelola transaksi (commit/rollback) dan wajib untuk operasi write

menggunakan `@Transactional(readOnly = true)` saat endpoint GET API, query data (findById, findAll, search), reporting / dashboard 
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Membuat project dari awal
2. Cukup paham memahami flow project
```

Apa 2 hal yang masih membingungkan?

```text
1. syntax yang cukup kompleks
2. ketika saya harus menentukan folder controller dan service seperti apa (kodenya)
```

Apa 1 pertanyaan untuk mentor?

```text
Tidak ada
```
