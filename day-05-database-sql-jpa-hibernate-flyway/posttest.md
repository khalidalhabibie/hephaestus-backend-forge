# Posttest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari Database, SQL, JPA, Hibernate, Flyway, dan Query Relationship.

1. Apa perbedaan in-memory Map dan database?

Jawaban:

```text
Kalo di map dia akan disimpan di ram, jadi kalo program di close data akan terhapus, kalo di database akan tersimpan walaupun program di close
```

2. Kenapa database diperlukan pada aplikasi backend production?

Jawaban:

```text
Agar data tersimpan di persistance layer dan tidak terhapus saat aplikasi di close
```

3. Apa fungsi SELECT, INSERT, UPDATE, dan DELETE pada SQL?

Jawaban:

```text
- SELECT : MENGAMBIL DATA
- INSERT : INPUT DATA KE DATABASE
- UPDATE : MENGUBAH NILAI DATA DI DATABASE
- DELETE : MENGHAPUS DATA
```

4. Apa fungsi WHERE dan ILIKE di PostgreSQL?

Jawaban:

```text
- WHERE : MELAKUKAN FILTERING SEBELUM MENGAMBIL DATA
- ILIKE : MENCARI DATA YANG VALUENYA MIRIP DAN MENGABAIKAN UPPER LOWER
```

5. Apa itu primary key dan foreign key?

Jawaban:

```text
- PRIMARY : NILAI KUNCI UNIK YANG MEMBEDAKAN VALUE YANG ADA DI DALAM DATABASE

- FOREIGN KEY : NILAI KUNCI UNIK YANG MEMBEDAKAN VALUE TAMU DARI TABEL LAIN
```

6. Apa itu JPA dan Hibernate, serta apa perbedaannya?

Jawaban:

```text
JPA UNTUK SINTAKSIS/PENGELOLA DALAM MENGAKSES DATABASE SEDANGKAN HIBERNATE UNTUK IMPLEMENTASI SINTAKSIS DARI JPA
```

7. Apa itu Entity dan apa fungsi anotasi `@Entity`, `@Id`, serta `@GeneratedValue`?

Jawaban:

```text
- @ENTITY : MENDANDAKAN BAHWA CLASS MERUPAKAN CLASS YANG MENGGAMBARKAN TABEL DI DATABASE

- @ID : MENANDAKAN KOLOM ADALAH PRIMARY KEY DARI CLASS YG MEREPRESENTASIKAN TABEL

- @GENERATEDVALUE : UNTUK MELAKUKAN GENERATE ID OTOMATIS
```

8. Apa fungsi `@Table` dan `@Column`?

Jawaban:

```text
@TABLE : MENAMAI CLASS ENTITY MENJADI TABEL DATABASE
@COLUMN : REPRESENTASI KOLOM DI TABEL DATABASE
```

9. Apa itu Repository dan apa manfaat `JpaRepository`?

Jawaban:

```text
UNTUK MENGAKSES DATABASE MENGGUNAKAN SINTAKSIS YANG SUDAH DISEDIAKAN JPA
```

10. Apa itu derived query method? Berikan contoh method untuk mencari customer berdasarkan email.

Jawaban:

```text
QUERY METHODS DIMANA MEMUNGKINKAN KITA MELAKUKAN QUERY KE DATABASE HANYA MENGGUNAKAN FUNCTION/METHOD

.FINDBYID()
.SAVE()
.DELETE()
.FINDALL()
```

11. Apa fungsi `@Query`? Jelaskan perbedaan JPQL dan native query.

Jawaban:

```text
@QUERY : UNTUK MENULISKAN QUERY
KALO JPQL QUERY KE CLASS ENTITYNYA
KALO NATIVE : RAW QUERY KE DATABASE/TABELNYA
```

12. Apa itu Flyway dan kenapa database migration penting?

Jawaban:

```text
AGAR KETIKA ADA PERUBAHAN DI DATABASE KITA HANYA PERLU MENAMBAHAKN FILE MIGRATION SAJA, TIDAK PERLU ALTERTABEL KE DATABASE LANGSUNG DAN AGAR APLIKASI BISA TERUS BERJALAN DAN GA CRASH
```

13. Apa maksud penamaan file migration seperti `V1__create_customers_table.sql`? Kenapa migration lama sebaiknya tidak diubah setelah dijalankan?

Jawaban:

```text
VERSI 1 - MEBUAT CUSTOMER TABEL
KARENA KALO DIUBAH FLYAWAY AKAN CRASH, JADI HARUS BUAT FILE MIGRATION LAGI WALAUPUN PERUBAHAN SEKECIL APAPUN
```

14. Jelaskan relationship one-to-many dan many-to-one dengan contoh Customer dan Order.

Jawaban:

```text
- ONE TO MANY : 1 ENTITY BISA MEMILIKI / BERELASI KE BANYAK ENTITY LAIN

- MANY TO ONE : BANYAK ENTITY HANYA DIMILIKI OLEH 1 CUSTOMER

CUSTOMER -> MEMILIKI BANYAK -> ORDER

```

15. Apa fungsi `@ManyToOne`, `@OneToMany`, dan `@JoinColumn`?

Jawaban:

```text

@ManyToOne  : relasi banyak ke satu
@OneToMany  : relasi satu ke banyak
@JoinColumn : menentukan kolom foreign key pada tabel

```

16. Apa perbedaan lazy loading dan eager loading? Kenapa `FetchType.LAZY` sering lebih aman sebagai default?

Jawaban:

```text
KARENA LAZY AKAN DIEKSEKUSI KETIKA DIPERLUKAN SAJA, BUKAN LANGSUNG DI AWAL SEPERTI EAGER. NAG JIKA EAGER LIST OF CUSTOMER YANG BANYAK MAKA QUERY AKAN BERAT DAN DATABASE AKAN CRASH
```

17. Apa itu SQL join? Jelaskan perbedaan `INNER JOIN` dan `LEFT JOIN`.

Jawaban:

```text
KALO INNER DIA AKAN RETURN VALUE YANG SAMA DI KEDUA TABEL INTERCEP, SEDANGKAN KALO LEFT JOIN AKAN RETURN BERDASARKAN TABEL YANG KIRI
```

18. Apa itu N+1 query problem dan bagaimana cara sederhana menguranginya?

Jawaban:

```text
1 QUERY DI IKUTI BANYAK QUERY YANG ADA DIBELAKANNGNYA
CONTOH : CUSTOMER + 100 LOAN APPLICATION

CARA MENGURANGI :
- JOIN FETCH
- PAKE LAZY DAN EGAER DENGAN TAPAT
```

19. Kenapa Entity sebaiknya tidak langsung dikembalikan sebagai API response? Apa manfaat DTO?

Jawaban:

```text
AGAR DATA YANG SENSITIF TIDAK IKUT KE RETURN KE CLIENT DAN TIDAK MEMBOCORKAN DATA.
```

20. Apa fungsi `@Transactional` dan kapan menggunakan `@Transactional(readOnly = true)`?

Jawaban:

```text
@TRANSACTIONAL DIGUNAKAN KETIKA SERVICE MELAKUKAN TRANSAKSI DI DATABASE DAN ANTAR DATABASE, READ ONLY DIPAKE JIKA TRANSAKSI HANYA MENGAMBIL DATA SAJA KE DATABASE.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. PENGGUNAAN READ ONLY TRUE PADA DATABASE
2. MENANGANI PERMASALAHAN QUERY N+1
3. FILE MIGRATION HARUS DIBUAT KETIKA ADA PERUBAHAN FIELD DI DATABASE
```

Apa 2 hal yang masih membingungkan?

```text
1. PENGGUNAAN JPQL
2. MEMBUAT SERVICE TRANSACTIONAL YANG LOGIKA BISNISNYA TIDAK HANYA CRUD
```

Apa 1 pertanyaan untuk mentor?

```text
1. KAPAN KITA BISA MENYIMPULKAN BAHWA CODE KITA SUDAH BAIK. DAN JIKA KITA BEKERJA SENDIRI BAGAIMANA MENGURANGI TITIK BUTA
```
