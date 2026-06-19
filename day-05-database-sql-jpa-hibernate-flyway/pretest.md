# Pretest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang database, SQL, JPA, Hibernate, Flyway, dan relationship antar table.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Database Basic

1. Apa itu database?

Jawaban:

```text
Database adalah kumpulan data yang disusun secara sistematis dan terstruktur sehingga mudah untuk disimpan, dikelola, serta diakses kembali oleh pengguna atau sistem komputer sesuai kebutuhan.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Menyimpan data di Map biasanya dilakukan di dalam program (in-memory), sehingga datanya bersifat sementara dan hanya tersedia selama aplikasi berjalan. Map cocok untuk menyimpan pasangan key-value sederhana dengan akses cepat, tetapi tidak aman untuk penyimpanan jangka panjang karena data bisa hilang saat program berhenti.
Sementara itu, menyimpan data di database digunakan untuk penyimpanan yang lebih permanen dan terstruktur. Database mampu menangani data dalam jumlah besar, mendukung banyak pengguna, serta memiliki fitur seperti keamanan, backup, dan query kompleks untuk mengelola serta mengambil data dengan lebih fleksibel.
```

3. Apa itu table?

Jawaban:

```text
Table adalah sebuah struktur dalam database yang digunakan untuk menyimpan data dalam bentuk baris (row) dan kolom (column).
```

4. Apa itu row?

Jawaban:

```text
Row adalah baris dalam sebuah table pada database yang digunakan untuk menyimpan satu data atau satu record lengkap.
```

5. Apa itu column?

Jawaban:

```text
Column adalah kolom dalam sebuah table pada database yang digunakan untuk menyimpan jenis atau atribut data tertentu.
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah sebuah kolom (atau kombinasi beberapa kolom) dalam sebuah table yang digunakan untuk mengidentifikasi setiap baris data secara unik.
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah sebuah kolom (atau kumpulan kolom) dalam sebuah table yang digunakan untuk menghubungkan data dengan table lain.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Aplikasi backend membutuhkan database driver karena driver berfungsi sebagai penghubung antara aplikasi dan database.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
Fungsi SELECT adalah untuk mengambil atau menampilkan data dari sebuah tabel dalam database.
```

10. Apa fungsi INSERT?

Jawaban:

```text
Fungsi INSERT adalah untuk menambahkan atau memasukkan data baru ke dalam sebuah tabel di database.
```

11. Apa fungsi UPDATE?

Jawaban:

```text
Fungsi UPDATE adalah untuk mengubah atau memperbarui data yang sudah ada di dalam tabel database.
```

12. Apa fungsi DELETE?

Jawaban:

```text
Fungsi DELETE adalah untuk menghapus data dari sebuah tabel di database.
```

13. Apa fungsi WHERE?

Jawaban:

```text
Fungsi WHERE adalah untuk menentukan kondisi atau kriteria dalam suatu perintah SQL.
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
Perbedaan LIKE dan ILIKE di PostgreSQL terletak pada sensitivitas huruf (case sensitivity) saat melakukan pencarian data.
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
Fungsi ORDER BY adalah untuk mengurutkan hasil data yang diambil dari database berdasarkan kolom tertentu.
```

16. Apa fungsi LIMIT?

Jawaban:

```text
Fungsi LIMIT adalah untuk membatasi jumlah data yang ditampilkan dalam hasil query.
```

17. Apa itu JOIN?

Jawaban:

```text
JOIN adalah perintah dalam SQL yang digunakan untuk menggabungkan data dari dua atau lebih tabel berdasarkan hubungan tertentu.
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN digunakan untuk menampilkan hanya data yang memiliki pasangan di kedua tabel. Artinya, jika tidak ada data yang cocok di salah satu tabel, maka data tersebut tidak akan ditampilkan.
LEFT JOIN digunakan untuk menampilkan semua data dari tabel kiri (tabel utama), meskipun tidak memiliki pasangan di tabel kanan. Jika tidak ada data yang cocok, maka bagian dari tabel kanan akan bernilai NULL.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
JPA (Java Persistence API) adalah sebuah standar atau spesifikasi di Java yang digunakan untuk mengelola data pada database secara objek (Object-Relational Mapping / ORM).
```

20. Apa itu Hibernate?

Jawaban:

```text
Hibernate adalah sebuah framework (tool) ORM (Object-Relational Mapping) di Java yang digunakan untuk mengelola dan mempermudah interaksi antara aplikasi Java dengan database.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA (Java Persistence API) adalah sebuah standar atau spesifikasi yang mendefinisikan bagaimana cara mengelola data antara aplikasi Java dan database menggunakan konsep ORM. JPA hanya berisi aturan dan interface, sehingga tidak bisa digunakan secara langsung tanpa implementasi.
Hibernate adalah sebuah framework atau implementasi nyata dari JPA yang menyediakan fitur lengkap untuk menjalankan standar tersebut. Hibernate benar-benar bisa digunakan untuk menyimpan, mengambil, memperbarui, dan menghapus data dari database.
```

22. Apa itu Entity?

Jawaban:

```text
Entity adalah sebuah class di Java yang merepresentasikan sebuah tabel dalam database pada konsep JPA atau Hibernate.
```

23. Apa fungsi @Entity?

Jawaban:

```text
Fungsi @Entity adalah untuk menandai sebuah class di Java sebagai entity yang merepresentasikan tabel dalam database.
```

24. Apa fungsi @Table?

Jawaban:

```text
Fungsi @Table adalah untuk menentukan nama tabel di database yang akan dipetakan dengan sebuah entity.
```

25. Apa fungsi @Id?

Jawaban:

```text
Fungsi @Id adalah untuk menandai sebuah field (atribut) dalam entity sebagai primary key.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Fungsi @GeneratedValue adalah untuk mengatur bagaimana nilai primary key dibuat secara otomatis oleh sistem.
```

27. Apa itu Repository?

Jawaban:

```text
Repository adalah sebuah komponen dalam JPA atau Spring Data JPA yang digunakan untuk mengelola akses data ke database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Fungsi JpaRepository adalah untuk menyediakan berbagai method siap pakai dalam mengelola data di database tanpa perlu menulis query secara manual.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Pada Spring Boot 3, import JPA menggunakan jakarta.persistence karena terjadi perubahan namespace dari Java EE ke Jakarta EE.
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Derived query method di Spring Data JPA adalah metode pada repository yang dibuat otomatis berdasarkan nama method tanpa perlu menulis query SQL atau JPQL secara manual.
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
}
```

32. Apa fungsi @Query?

Jawaban:

```text
Fungsi @Query adalah untuk menuliskan query secara manual (custom query) pada repository di Spring Data JPA.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL (Java Persistence Query Language) adalah query yang ditulis menggunakan nama entity dan field di Java, bukan langsung nama tabel di database. JPQL bersifat lebih fleksibel karena mengikuti konsep ORM.
Native query adalah query yang ditulis menggunakan SQL asli sesuai dengan database, sehingga menggunakan nama tabel dan kolom secara langsung.
```

34. Kapan menggunakan native query?

Jawaban:

```text
Native query digunakan ketika query yang dibutuhkan tidak bisa atau sulit dibuat dengan JPQL.
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Database migration adalah proses mengelola perubahan struktur database secara terkontrol dan bertahap.
```

36. Apa itu Flyway?

Jawaban:

```text
Flyway adalah sebuah tool database migration yang digunakan untuk mengelola dan menjalankan perubahan schema database secara terstruktur dan terkontrol.
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Perubahan schema database perlu versioning karena untuk melacak dan mengelola setiap perubahan yang terjadi pada struktur database secara teratur dan konsisten.
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
File V1__create_customers_table.sql adalah file migration pada Flyway yang digunakan untuk menjalankan perubahan pada database.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Tidak terdokumentasi dengan baik, sehingga developer lain sulit mengetahui apa saja yang sudah diubah.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Relationship antar table adalah hubungan atau keterkaitan antara dua atau lebih tabel dalam database.
```

41. Apa itu one-to-many?

Jawaban:

```text
One-to-many (1:N) adalah jenis relationship dalam database di mana satu data di sebuah tabel dapat berhubungan dengan banyak data di tabel lain.
```

42. Apa itu many-to-one?

Jawaban:

```text
Many-to-one (N:1) adalah jenis relationship dalam database di mana banyak data di satu tabel berhubungan dengan satu data di tabel lain.
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Fungsi @ManyToOne adalah untuk menentukan hubungan many-to-one antara dua entity di JPA/Hibernate.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Fungsi @OneToMany adalah untuk menentukan hubungan one-to-many antara dua entity di JPA/Hibernate.
```

45. Apa itu lazy loading?

Jawaban:

```text
Lazy loading adalah teknik dalam JPA atau Hibernate di mana data relasi tidak langsung diambil dari database saat entity utama diambil, tetapi baru diambil ketika data tersebut benar-benar diakses.
```

46. Apa itu eager loading?

Jawaban:

```text
Eager loading adalah teknik dalam JPA atau Hibernate di mana data relasi langsung diambil bersamaan dengan entity utama dalam satu waktu (satu query atau langsung saat load).
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Risiko lazy loading jika tidak dipahami adalah dapat menyebabkan masalah performa dan error pada aplikasi.
```

48. Apa itu N+1 query problem?

Jawaban:

```text
N+1 query problem adalah kondisi di mana aplikasi menjalankan terlalu banyak query ke database secara tidak efisien.
```

49. Apa itu join fetch?

Jawaban:

```text
Join fetch adalah teknik dalam JPA atau Hibernate yang digunakan untuk mengambil data dari entity beserta relasinya sekaligus dalam satu query.
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Customer dan loan application dipisah ke tabel berbeda agar tidak terjadi duplikasi data, karena satu customer bisa memiliki banyak pengajuan pinjaman, sehingga data lebih rapi, efisien, dan mudah dikelola.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Repayment schedule perlu tabel sendiri agar setiap cicilan bisa disimpan sebagai data terpisah dan terstruktur, karena satu loan bisa memiliki banyak jadwal pembayaran.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text

SELECT * FROM loan
WHERE status = 'APPROVED';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT customer_id, SUM(amount) AS total_pembayaran
FROM repayment
GROUP BY customer_id;
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        |     3     |
| SQL basic             |     2     |
| JPA                   |     2     |
| Hibernate             |     2     |
| Repository            |     2     |
| Flyway                |     2     |
| Relationship          |     2     |
| Join query            |     1     |
| Lazy loading          |     2     |
| Finance data modeling |     2     |

## Notes

```text
keseluruhan proses integrasi database dan backend.
```
