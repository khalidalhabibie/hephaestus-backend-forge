# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
In-memory Map adalah struktur data yang menyimpan pasangan key-value langsung di dalam memori (RAM) aplikasi, sehingga akses datanya sangat cepat karena tidak perlu melalui proses I/O ke disk. Namun, data yang disimpan bersifat sementara, sehingga akan hilang ketika aplikasi dihentikan atau server mati. In-memory Map biasanya digunakan untuk kebutuhan yang membutuhkan performa tinggi dan tidak memerlukan penyimpanan jangka panjang, seperti caching, session sementara, atau perhitungan cepat di dalam program.
Database adalah sistem penyimpanan data yang biasanya menyimpan data di disk (atau persistent storage), sehingga data tetap aman meskipun aplikasi dimatikan atau terjadi restart. Database juga menyediakan fitur tambahan seperti query kompleks, indexing, transaksi (ACID), kontrol akses, serta kemampuan untuk menangani data dalam skala besar dan multi-user secara konsisten. Oleh karena itu, database digunakan untuk penyimpanan data yang bersifat permanen dan kritikal, seperti data pengguna, transaksi bisnis, atau histori aplikasi.
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Pertama, database dibutuhkan untuk menyimpan data secara permanen (persistent storage), sehingga data seperti akun pengguna, transaksi, dan histori tidak hilang ketika aplikasi dimatikan atau server restart. Hal ini sangat penting untuk menjaga keandalan sistem dalam jangka panjang.
Kedua, database menyediakan konsistensi dan integritas data, misalnya melalui mekanisme transaksi (ACID). Dengan ini, aplikasi dapat memastikan bahwa operasi penting seperti pembayaran atau update data tidak terjadi setengah jalan atau mengalami inkonsistensi.
Ketiga, backend production biasanya melayani banyak pengguna sekaligus, sehingga database berperan dalam mengelola concurrent access (akses bersamaan) secara aman. Tanpa database, akan sulit memastikan bahwa data yang dibaca dan ditulis oleh banyak user tetap akurat.
Keempat, database menyediakan kemampuan query yang fleksibel dan efisien, sehingga aplikasi dapat dengan mudah mencari, memfilter, dan mengolah data dalam jumlah besar. Ini sangat penting untuk fitur seperti pencarian, laporan, dan analitik.
Selain itu, database juga mendukung keamanan dan kontrol akses, seperti autentikasi, authorization, dan audit log, yang penting untuk melindungi data sensitif di lingkungan production.
Terakhir, database memungkinkan skalabilitas, baik dengan cara replikasi, sharding, maupun optimasi performa lainnya, sehingga aplikasi dapat terus berkembang seiring pertumbuhan jumlah pengguna dan data.
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
1. SELECT
Perintah SELECT digunakan untuk mengambil atau membaca data dari tabel di database.
Dengan SELECT, kita bisa melihat data tertentu, memfilter, mengurutkan, atau menggabungkan data dari beberapa tabel.
2. INSERT
Perintah INSERT digunakan untuk menambahkan data baru ke dalam tabel.
3. UPDATE
Perintah UPDATE digunakan untuk mengubah data yang sudah ada di dalam tabel.
4. DELETE
Perintah DELETE digunakan untuk menghapus data dari tabel.
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
1. WHERE
Clause WHERE digunakan untuk menyaring (filter) baris data berdasarkan kondisi tertentu dalam query SQL. Dengan WHERE, kita bisa menentukan data mana yang ingin diambil, diubah, atau dihapus.
2. ILIKE 
Operator ILIKE digunakan untuk pencarian pola teks yang tidak sensitif terhadap huruf besar/kecil (case-insensitive). Ini adalah versi "tidak peduli kapitalisasi" dari LIKE.
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
Primary key adalah kolom (atau kombinasi kolom) yang digunakan untuk mengidentifikasi setiap baris data secara unik dalam sebuah tabel. Artinya, tidak boleh ada dua baris yang memiliki nilai primary key yang sama, dan nilainya juga tidak boleh NULL.
Foreign key adalah kolom dalam suatu tabel yang merujuk ke primary key di tabel lain, sehingga digunakan untuk membuat hubungan antar tabel.
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA hanya berisi aturan dan interface tentang bagaimana mapping objek ke tabel database dilakukan, tetapi tidak menyediakan implementasi langsung.
Hibernate adalah library yang benar-benar menjalankan aturan JPA, sehingga bisa digunakan untuk mapping object ke database dan menjalankan query secara otomatis.
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
Entity adalah class Java yang merepresentasikan tabel di database.
Anotasi @Entity digunakan untuk menandai bahwa sebuah class adalah Entity (tabel di database).
Anotasi @Id digunakan untuk menandai field sebagai primary key.
Anotasi @GeneratedValue digunakan untuk mengatur bagaimana nilai primary key dibuat secara otomatis.
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
Anotasi @Table digunakan untuk menentukan nama tabel di database yang dihubungkan dengan suatu Entity.
Anotasi @Column digunakan untuk menentukan mapping field ke kolom di tabel, termasuk nama kolom dan properti lainnya.
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:
Repository adalah komponen/layer dalam aplikasi yang bertanggung jawab untuk berinteraksi dengan database.
JpaRepository adalah interface bawaan dari Spring Data JPA yang menyediakan implementasi siap pakai untuk operasi database.
```text
Tulis jawaban di sini.
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
Derived query method adalah fitur di Spring Data JPA yang memungkinkan kita membuat query hanya dari nama method, tanpa menulis SQL atau JPQL secara manual.
Contoh: mencari customer berdasarkan email
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
Anotasi @Query di Spring Data JPA digunakan untuk menuliskan query secara manual, baik menggunakan JPQL maupun SQL asli, ketika derived query method tidak cukup fleksibel.
JPQL adalah query berbasis objek (entity), bukan tabel database.
Native query adalah query SQL asli yang langsung dijalankan ke database.
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
Flyway adalah sebuah tool untuk mengelola dan mengotomatisasi database migration, yaitu proses perubahan struktur database (schema) secara terkontrol seiring perkembangan aplikasi.
Kenapa database migration penting
1. Menjaga konsistensi schema
migration memastikan struktur database di semua environment (development, testing, production) tetap sama.
2. Mendukung perubahan aplikasi
ketika aplikasi berkembang (misalnya menambah kolom atau tabel), perubahan tersebut bisa dikelola dengan rapi dan terstruktur.
3. Versioning database
setiap perubahan database memiliki versi, sehingga kita tahu perubahan apa saja yang sudah dilakukan dan bisa melacak history-nya.
4. Memudahkan deployment
saat deploy aplikasi ke production, migration bisa otomatis dijalankan tanpa perlu update database secara manual.
5. Mengurangi error manual
tanpa migration tool, perubahan database sering dilakukan manual dan berisiko menimbulkan kesalahan atau inkonsistensi.
6. Mendukung kolaborasi tim
dalam tim, setiap developer bisa menambahkan migration sendiri tanpa merusak struktur database yang sudah ada.
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
Penamaan file migration seperti V1__create_customers_table.sql memiliki format khusus yang digunakan oleh Flyway untuk mengatur urutan dan deskripsi perubahan database.
Format umumnya adalah:
V<versi>__<deskripsi>.sql
Migration yang sudah dijalankan tidak boleh diubah karena sudah menjadi bagian dari riwayat perubahan database yang harus konsisten.
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
1. One-to-Many (Customer → Orders)
One-to-many berarti satu Customer dapat memiliki banyak Order.
Contoh:

Satu customer bisa melakukan banyak transaksi (order)
Setiap order hanya dimiliki oleh satu customer

2. Many-to-One (Order → Customer)
Many-to-one berarti banyak Order dimiliki oleh satu Customer.
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
Anotasi @ManyToOne digunakan untuk menyatakan bahwa banyak data pada Entity ini berhubungan dengan satu data di Entity lain.
Anotasi @OneToMany digunakan untuk menyatakan bahwa satu data pada Entity ini memiliki banyak data di Entity lain.
Anotasi @JoinColumn digunakan untuk menentukan kolom foreign key di tabel database yang menghubungkan kedua Entity.
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
lazy loading berarti data relasi tidak langsung diambil saat entity utama di-load, tetapi baru diambil ketika benar-benar dibutuhkan.
eager loading berarti data relasi langsung diambil bersamaan dengan entity utama, tanpa menunggu dipanggil.
FetchType.LAZY lebih aman karena membantu menghindari masalah performa dan penggunaan resource yang berlebihan.
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
SQL join adalah operasi dalam SQL yang digunakan untuk menggabungkan data dari dua atau lebih tabel berdasarkan hubungan tertentu, biasanya melalui kolom yang saling berhubungan (misalnya primary key dan foreign key).
INNER JOIN hanya mengembalikan data yang memiliki pasangan (match) di kedua tabel.
LEFT JOIN mengembalikan semua data dari tabel kiri, dan data dari tabel kanan jika ada yang cocok; jika tidak ada, hasil dari tabel kanan akan bernilai NULL.
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 query problem adalah masalah performa yang terjadi ketika aplikasi menjalankan 1 query utama + N query tambahan untuk mengambil data relasi, sehingga jumlah query menjadi sangat banyak dan tidak efisien.
Cara sederhana menguranginya
1. Menggunakan JOIN FETCH (JPQL)
2. Menggunakan @EntityGraph
3. Menggunakan fetch strategy dengan bijak
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
Entity sebaiknya tidak langsung dikembalikan sebagai API response karena dapat menimbulkan masalah pada keamanan, performa, dan desain aplikasi.
DTO adalah objek khusus yang digunakan untuk mengirim data antara backend dan client, dengan struktur yang disesuaikan kebutuhan API.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
Anotasi @Transactional di Spring digunakan untuk mengatur transaksi database, sehingga operasi yang dilakukan di dalamnya berjalan secara atomik (all-or-nothing).
@Transactional(readOnly = true) digunakan untuk operasi yang hanya membaca data tanpa melakukan perubahan.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Saya memahami dasar operasi database seperti SELECT, INSERT, UPDATE, dan DELETE untuk mengelola data.
2. Saya memahami bahwa satu data bisa berhubungan dengan banyak data lain, seperti Customer dan Order (relasi one-to-many).
3. Saya memahami bahwa kita perlu cara yang rapi dan aman untuk mengatur database, misalnya dengan migration dan tidak langsung mengirim Entity ke API.

```

Apa 2 hal yang masih membingungkan?

```text
1. Saya masih agak bingung kapan harus menggunakan @Query dibandingkan derived query method.
2. Saya masih belum terlalu paham cara kerja lazy loading dan kenapa bisa menyebabkan N+1 query.
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara menentukan kapan kita perlu menggunakan DTO dan kapan masih boleh langsung pakai Entity di project sederhana?
```
