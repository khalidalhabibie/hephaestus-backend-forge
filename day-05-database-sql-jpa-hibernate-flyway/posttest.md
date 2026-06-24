# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In-memory -> data disimpan di RAM (sementara), biasanya dipakai di cache. Real case-nya adalah data akan hilang ketika kita matikan aplikasinya.

Database -> tempat penyimpanan data yang permanen, jadi data tetap aman walaupun aplikasi mati. Real case-nya adalah products yang kita simpan di e-comemrce tetap tersimpan di archive atau keranjang.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Karena aplikasi production butuh data yang aman, permanen, dan bisa diakses banyak user.
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
SELECT -> mengambil data, (e.g. SELECT * FROM customer WHERE email = 'grace@mail.com').
INSERT -> menambahkan data baru (e.g. INSERT INTO customer (name, email) VALUES ('grace', 'grace@mail.com')).
UPDATE -> mengubah data yang sudah ada (e.g. UPDATE customer SET name = 'grace' WHERE id = 1).
DELETE -> menghapus data (e.g. DELETE FROM customer WHERE id = 1).
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
WHERE -> filter data (e.g. WHERE id = 1).
ILIKE -> seperti LIKE tapi tidak peduli huruf besar ataupun kecil (e.g. WHERE name ILIKE '%dith%').
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
Primary key -> identitas unik tiap baris data (e.g. id_customer di table Customer sebagai unique code).
Foreign key -> kunci yang menghubungkan tabel lain (e.g. id_customer di table Order sebagai penghubungan table Customer dengan table Order)
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA -> interface ORM di Java, sehingga harus nulis SQL manual.
Hibernate -> implementasi JPA, sehingga tinggal pakai Java object.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
@Entity -> representasi tabel database dalam Java class.
@Id -> primary key.
@GeneratedValue -> generated ID.
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table -> kasih nama tabel (e.g. @Table(name = "customers")).
@Column -> setting nama kolom (e.g. @Column(name = "full_name")).
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
Repository adalah jembatan Java ke database dan JpaRepository sudah punya fitur otomatis untuk menghubungkannya.
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Query otomatis dari nama method, misalnya findByNameContaining(String name) untuk mencari berdasarkan nama.
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query digunakan untuk membuat query manual.

JPQL -> menggunakan nama entity Java (e.g. @Query("SELECT c FROM Customer c WHERE c.email = :email")).
Native Query -> menggunakan SQL asli database (e.g. @Query(value = "SELECT * FROM customer WHERE email = :email", nativeQuery = true))
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah tool untuk mengatur versioning database yang kita punya. Databas migration ini dapat menjaga struktur database tetap konsisten dan memudahkan perubahan database antar developer.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1 berarti versi pertama migration dan create_customers_table adalah nama perubahan yang dilakukan. Migration lama tidak boleh diubah karena sudah dipakai di database lain. Jika ingin perubahan, buat versi baru (e.g. V2, V3, dst).
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
One-to-many misalnya satu customer bisa memiliki banyak order, sedangkan many-to-one misalnya banyak order dimiliki oleh satu customer.
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne -> banyak data ke satu data.
@OneToMany -> satu data ke banyak data.
@JoinColumn -> menentukan foreign key di table.
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
Lazy loading -> data diambil saat dibutuhkan saja, misalnya sistem mengambil customer dulu kemudian order baru diambil saat dipanggil.
Eager loading -> data langsung diambil semua, misalnya sistem langsung ambil customer dan semua order.
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
JOIN digunakan untuk menggabungkan tabel.

INNER JOIN -> hanya data yang cocok di kedua tabel (e.g. hanya customer yang punya order).
LEFT JOIN -> semua data dari tabel kiri tetap ditampilkan (e.g. semua customer meskipun belum punya order).
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 problem adalah masalah ketika query terlalu banyak, cara pencegahannya mungkin dapat dilakukan dengan mengurangi penggunaan EAGER agar performance meningkat.
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Karena entity bisa membawa data sensitif dan struktur database, di sini DTO lebih aman karena sistem hanya mengirim data yang dibutuhkan dan lebih fleksibel untuk API.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@Transactional memastikan semua proses database berhasil atau gagal semuanya (e.g. kredit gagal maka semua dibatalkan).
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. JPA dan Hibernate mempermudah akses database di Java;
2. Relasi dan query sangat penting untuk aplikasi di production; dan;
3. Proses keseluruhan dari awal sampai bisa membuat koneksi DB.
```

Apa 2 hal yang masih membingungkan?

```text
1. Optimasi query di kasus besar dan lebih kompleks; dan;
2. Kapan menggunakan JPQL atau native query.
```

Apa 1 pertanyaan untuk mentor?

```text
Best practice penggunaan keseluruhannya seperti apa ya apabila terdapat N+1 yang lebih complex?
```
