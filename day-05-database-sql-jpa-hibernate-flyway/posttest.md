# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
in memory map menyimpan data di memori aplikasi sehingga data akan hilang ketika aplikasi dimatikan atau restart

database menyimpan data secara permanen di media penyimpanan sehingga data tetap tersedia meskipun aplikasi dimatikan atau di restart
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
karena database bisa menghandle penyimpanan data yang besar serta juga bisa menghandle query query yang rumit dalam skala profesional serta menjaga konsistensi data
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
select digunakan untuk mengambil data dari tabel

insert digunakan untuk menambahkan data baru ke tabel

update digunakan untuk mengubah data yang sudah ada

delete digunakan untuk menghapus data dari tabel
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
where digunakan untuk memfilter data berdasarkan kondisi tertentu

ilike digunakan untuk melakukan pencarian teks tanpa membedakan huruf besar dan kecil (seperti konsep ignorecase)
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
primary key adalah kolom yang digunakan untuk identifikasi setiap baris dari data yang di set secara unik dalam sebuah tabel

foreign key adalah kolom yang digunakan untuk menghubungkan suatu tabel dengan tabel lain melalui primary key yang dimiliki tabel tersebut
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
jpa adalah aturan atau kontraknya, sedangkan hibernate adalah tools yang menjalankan aturan tersebut
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
entity adalah class java yang merepresentasikan tabel pada database

@Entity digunakan untuk menandai bahwa class tersebut adalah entity

@Id digunakan untuk menandai field sebagai primary key

@GeneratedValue digunakan untuk menghasilkan nilai id secara otomatis sesuai strategi yang digunakan
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@Table digunakan untuk menentukan nama tabel yang dipetakan oleh entity

@Column digunakan untuk mengatur detail kolom seperti nama kolom, panjang data, nullable, dll
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
repository adalah komponen yang bertugas mengakses dan mengelola data pada database.

JpaRepository menyediakan berbagai operasi CRUD secara otomatis tanpa perlu menulis query dasar secara manual, sehingga proses development menjadi lebih cepat dan simple
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
derived query method adalah fitur spring data jpa yang memungkinkan pembuatan query berdasarkan nama method

contoh:
Optional<Customer> findByEmail(String email);

spring akan secara otomatis membuat query mencari data berdasarkan nama method tersebut yaitu email
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@Query digunakan untuk menuliskan query secara manual ketika derived query method tidak cukup

jpql menggunakan nama entity dan atribut java

contoh:
select c from Customer c where c.email = :email

native query menggunakan sintaks sql asli sesuai database yang digunakan

contoh:
select * from customers where email = :email

jpql lebih portable, sedangkan native query lebih fleksibel untuk memanfaatkan fitur spesifik database.
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
flyway adalah tools untuk mengelola perubahan struktur database secara terkontrol dan terversioning

database migration penting agar perubahan schema database dapat dilakukan secara konsisten di seluruh environment, mengurangi risiko perbedaan struktur database antar tim atau server
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
V1 menunjukkan versi migration

create_customers_table merupakan deskripsi perubahan yang dilakukan

migration lama tidak sebaiknya diubah karena flyway menyimpan histori dan checksum migration yang sudah dijalankan. jika file diubah, flyway dapat mendeteksi perbedaan dan menyebabkan error atau inkonsistensi antar environment
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
one-to-many berarti satu customer dapat memiliki banyak loanApplication

many-to-one berarti banyak loanApplication dapat dimiliki oleh satu customer

contohnya, customer dengan id 1 dapat memiliki beberapa loanApplicationr, sedangkan setiap loanApplication hanya terkait dengan satu customer
```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text
@ManyToOne digunakan untuk mendefinisikan relasi banyak ke satu

@OneToMany digunakan untuk mendefinisikan relasi satu ke banyak

@JoinColumn digunakan untuk menentukan kolom foreign key yang digunakan dalam relasi tersebut
```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
lazy loading akan mengambil data relasi hanya ketika data tersebut dibutuhkan

eager loading akan langsung mengambil data relasi saat entity utama diambil

FetchType.LAZY lebih aman sebagai default karena membantu mengurangi query dan penggunaan memori yang tidak diperlukan sehingga performa aplikasi lebih baik
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
sql join digunakan untuk menggabungkan data dari dua atau lebih tabel yang saling berhubungan

INNER JOIN hanya menampilkan data yang memiliki pasangan di kedua tabel

LEFT JOIN menampilkan seluruh data dari tabel kiri meskipun tidak memiliki pasangan di tabel kanan
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
N+1 query problem terjadi ketika aplikasi menjalankan satu query untuk mengambil data utama dan kemudian menjalankan banyak query tambahan untuk setiap data relasi

cara sederhana menguranginya adalah menggunakan fetch join, entity graph, atau mengoptimalkan fetch strategy agar data relasi dapat diambil dalam jumlah query yang lebih sedikit
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
karena dapat mengekspos data yang tidak diperlukan, menyebabkan masalah relasi, dan membuat struktur response sulit dikontrol

dto membantu menentukan data apa saja yang akan dikirim ke client sehingga response menjadi lebih aman, lebih jelas, dan lebih fleksibel
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@Transactional digunakan untuk memastikan serangkaian operasi database berjalan dalam satu transaksi yang konsisten

@Transactional(readOnly = true) digunakan untuk operasi yang hanya membaca data tanpa melakukan perubahan. penggunaan readOnly dapat membantu optimasi performa karena database dan jpa mengetahui bahwa tidak ada proses update yang akan dilakukan
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. cara kerja jpa dan hibernate dalam memetakan object java ke tabel database
2. pentingnya database migration menggunakan flyway untuk menjaga konsistensi schema
3. bagaimana relationship antar entity serta dampaknya terhadap query dan performa aplikasi (lazy, onetomany,manytoone)
```

Apa 2 hal yang masih membingungkan?

```text
1. perbedaan penggunaan fetch join, entity graph, dan strategi fetch lainnya dalam kasus production
2. cara menentukan strategi relationship dan fetch type yang paling optimal pada aplikasi berskala besar dan logicnya
```

Apa 1 pertanyaan untuk mentor?

```text
kapan sebaiknya menggunakan native query dibandingkan jpql atau derived query method dalam pengembangan aplikasi production?
```
