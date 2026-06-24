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
Tulis jawaban di sini.
Tempat menyimpan data
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Tulis jawaban di sini.
data di map disimpan di memori dan akan hilang. di database disimpan dan persistent
```

3. Apa itu table?

Jawaban:

```text
Tulis jawaban di sini.
Struktur utama dalam database relasional yang terdiri dari baris dan kolom untuk menyimpan objek data yang sejenis.
```

4. Apa itu row?

Jawaban:

```text
Tulis jawaban di sini.
Satu baris data (record) di dalam tabel yang mewakili satu instans data atau entitas tunggal secara utuh.
```

5. Apa itu column?

Jawaban:

```text
Tulis jawaban di sini.
Bagian vertikal dari tabel yang mendefinisikan atribut atau tipe data dari informasi yang disimpan (misal: id, nama, email).
```

6. Apa itu primary key?

Jawaban:

```text
Tulis jawaban di sini.
Kolom atau kombinasi kolom yang nilainya unik untuk setiap baris dalam tabel, digunakan sebagai identitas utama pembeda antar record.
```

7. Apa itu foreign key?

Jawaban:

```text
Tulis jawaban di sini.
Kolom dalam suatu tabel yang merujuk ke Primary Key di tabel lain, berfungsi untuk membangun dan menjaga hubungan (relationship) antar tabel.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Tulis jawaban di sini.
Sebagai jembatan atau penerjemah bahasa pemograman aplikasi agar dapat berkomunikasi, mengirim perintah, dan memahami respons dari engine database spesifik (seperti PostgreSQL, MySQL).
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
Tulis jawaban di sini.
Untuk mengambil atau menampilkan data dari satu atau beberapa tabel dalam database.
```

10. Apa fungsi INSERT?

Jawaban:

```text
Tulis jawaban di sini.
Untuk memasukkan atau menambahkan baris data baru ke dalam tabel.
```

11. Apa fungsi UPDATE?

Jawaban:

```text
Tulis jawaban di sini.
Untuk mengubah atau memperbarui data yang sudah ada di dalam tabel berdasarkan kondisi tertentu.
```

12. Apa fungsi DELETE?

Jawaban:

```text
Tulis jawaban di sini.
Untuk menghapus satu atau lebih baris data dari dalam tabel.
```

13. Apa fungsi WHERE?

Jawaban:

```text
Tulis jawaban di sini.
Untuk menyaring (filter) baris data yang akan diambil, diubah, atau dihapus berdasarkan kondisi yang ditentukan.
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
Tulis jawaban di sini.
LIKE bersifat case-sensitive (membedakan huruf besar/kecil), sedangkan ILIKE bersifat case-insensitive (tidak membedakan huruf besar/kecil).
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
Tulis jawaban di sini.
Untuk mengurutkan hasil query berdasarkan satu atau lebih kolom, baik secara menaik (ASC) maupun menurun (DESC).
```

16. Apa fungsi LIMIT?

Jawaban:

```text
Tulis jawaban di sini.
Untuk membatasi jumlah baris maksimal yang dikembalikan oleh hasil query.
```

17. Apa itu JOIN?

Jawaban:

```text
Tulis jawaban di sini.
Operasi SQL untuk menggabungkan baris dari dua atau lebih tabel berdasarkan kolom yang saling berhubungan (biasanya PK dan FK).
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
Tulis jawaban di sini.
INNER JOIN hanya mengembalikan baris yang memiliki kecocokan di kedua tabel. LEFT JOIN mengembalikan semua baris dari tabel kiri, plus data yang cocok dari tabel kanan (jika tidak ada yang cocok, kolom tabel kanan diisi NULL).
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Tulis jawaban di sini.
Jakarta Persistence (dulu Java Persistence API), yaitu sebuah spesifikasi atau standar resmi di Java untuk mengelola data relasional menggunakan Object-Relational Mapping (ORM).
```

20. Apa itu Hibernate?

Jawaban:

```text
Tulis jawaban di sini.
Salah satu framework ORM populer di Java yang bertindak sebagai implementasi konkret (provider) dari spesifikasi JPA.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
Tulis jawaban di sini.
JPA adalah blueprint/antarmuka (interface & aturan), sedangkan Hibernate adalah mesin/realisasi fisik (class & code) yang menjalankan aturan dari JPA tersebut.
```

22. Apa itu Entity?

Jawaban:

```text
Tulis jawaban di sini.
Sebuah class Java biasa (POJO) yang dipetakan secara langsung ke sebuah tabel di dalam database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
Tulis jawaban di sini.
Annotation untuk memberi tahu JPA/Hibernate bahwa class tersebut adalah sebuah entitas yang harus dipetakan ke tabel database.
```

24. Apa fungsi @Table?

Jawaban:

```text
Tulis jawaban di sini.
Annotation opsional untuk menentukan konfigurasi spesifik tabel di database, seperti mengubah nama tabel jika berbeda dengan nama class Java-nya.
```

25. Apa fungsi @Id?

Jawaban:

```text
Tulis jawaban di sini.
Annotation untuk menandai sebuah field di dalam class entitas sebagai Primary Key dari tabel tersebut.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Tulis jawaban di sini.
Annotation untuk menentukan strategi pembuatan nilai Primary Key secara otomatis (misal: AUTO_INCREMENT atau UUID).
```

27. Apa itu Repository?

Jawaban:

```text
Tulis jawaban di sini.
Lapisan (layer) dalam arsitektur aplikasi yang bertugas menangani operasi akses data (CRUD) ke database, memisahkan logika bisnis dari logika database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Tulis jawaban di sini.
Interface dari Spring Data JPA yang menyediakan method-method standar penanganan database (save, findAll, delete, dll.) siap pakai tanpa perlu menulis query manual.
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Tulis jawaban di sini.
Karena adanya transisi kepemilikan Java EE dari Oracle ke Eclipse Foundation, yang mengharuskan perubahan nama paket (namespace) dari 'javax' menjadi 'jakarta'.
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Tulis jawaban di sini.
Fitur di mana Spring Data JPA akan otomatis membuat query SQL secara dinamis hanya berdasarkan pola penamaan (naming convention) method di interface Repository.
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
Tulis jawaban di sini.
Optional<Customer> findByEmail(String email);
```

32. Apa fungsi @Query?

Jawaban:

```text
Tulis jawaban di sini.
Annotation untuk mendefinisikan query kustom (baik JPQL maupun Native SQL) secara eksplisit di atas sebuah method repository jika penamaan derived query sudah terlalu kompleks.
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
Tulis jawaban di sini.
JPQL berorientasi pada objek (merujuk ke nama Class Entity dan Field Java), bersifat database-agnostic. Native Query menggunakan sintaks SQL murni yang merujuk langsung ke nama Tabel dan Kolom database fisik, terikat pada engine database tertentu.
```

34. Kapan menggunakan native query?

Jawaban:

```text
Tulis jawaban di sini.
Saat membutuhkan fungsi spesifik bawaan database tertentu (vendor-specific) yang tidak didukung oleh JPQL, atau untuk optimasi query kompleks yang sangat mementingkan performa.
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Tulis jawaban di sini.
Proses mengelola, melacak, dan menerapkan perubahan schema database (tambah tabel, ubah kolom) secara terarah dan tercatat sepanjang siklus pengembangan aplikasi.
```

36. Apa itu Flyway?

Jawaban:

```text
Tulis jawaban di sini.
Tool open-source untuk otomatisasi database migration yang berbasis versi, memastikan schema database di semua environment (dev, staging, prod) selalu sinkron.
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Tulis jawaban di sini.
Agar perubahan terekam secara historis, mempermudah pelacakan (tracking), menghindari konflik antar developer, dan menjamin replikasi schema yang konsisten di server berbeda.
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
Tulis jawaban di sini.
File script migrasi Flyway versi ke-1 (V1) dengan deskripsi aksi membuat tabel bernama 'customers'.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Tulis jawaban di sini.
Terjadi ketidakcocokan schema (schema drift) antar environment, potensi error di production karena ada kolom tertinggal, susah di-rollback, dan hilangnya riwayat perubahan schema.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Tulis jawaban di sini.
Hubungan logis yang dibentuk antara dua tabel database berdasarkan keterkaitan data di dalamnya, dihubungkan melalui kecocokan PK dan FK.
```

41. Apa itu one-to-many?

Jawaban:

```text
Tulis jawaban di sini.
Hubungan di mana satu record di Tabel A dapat terhubung dengan banyak record di Tabel B (contoh: 1 Customer punya Banyak Loan Applications).
```

42. Apa itu many-to-one?

Jawaban:

```text
Tulis jawaban di sini.
Sudut pandang kebalikan dari one-to-many, di mana banyak record di Tabel B merujuk pada satu record yang sama di Tabel A (contoh: Banyak Loan Applications milik 1 Customer).
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Tulis jawaban di sini.
Annotation di JPA pada sisi entitas "Many" untuk mendefinisikan relasi objek bersangkutan ke entitas tunggal ("One"), bertindak sebagai pemilik foreign key pemilik hubungan.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Tulis jawaban di sini.
Annotation di JPA untuk mendefinisikan relasi ke sebuah Collection objek (List/Set) dari entitas lain.
```

45. Apa itu lazy loading?

Jawaban:

```text
Tulis jawaban di sini.
Strategi fetch data di mana objek relasi tidak akan diambil dari database sampai objek tersebut benar-benar diakses secara eksplisit melalui kode (via getter).
```

46. Apa itu eager loading?

Jawaban:

```text
Tulis jawaban di sini.
Strategi fetch data di mana objek yang berelasi akan langsung ikut diambil dari database secara bersamaan pada saat objek utama di-load.
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Tulis jawaban di sini.
Bisa memicu `LazyInitializationException` jika data relasi diakses di luar scope transaksi (session sudah closed), atau memicu masalah performa N+1 Query.
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Tulis jawaban di sini.
Masalah performa di mana aplikasi mengeksekusi 1 query awal untuk mengambil list data utama, lalu menjalankan N buah query tambahan secara terpisah hanya untuk mengambil data relasi dari masing-masing baris utama tersebut.
```

49. Apa itu join fetch?

Jawaban:

```text
Tulis jawaban di sini.
Solusi di JPQL untuk mengatasi N+1 query dengan memaksa JPA melakukan operasi INNER/LEFT JOIN secara eksplisit dan mengambil entitas utama beserta relasinya dalam 1 kali query database tunggal.
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Tulis jawaban di sini.
Karena relasinya adalah One-to-Many. Satu customer bisa mengajukan permohonan pinjaman (loan application) lebih dari satu kali di waktu yang berbeda. Pemisahan menjamin normalisasi data agar profil customer tidak terduplikasi.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Tulis jawaban di sini.
Satu pinjaman (loan) akan dipecah menjadi banyak tenor atau termin pembayaran (misal: 12 bulan). Data jadwal tiap bulan (jatuh tempo, pokok, bunga, status bayar) dinamis dan berulang, sehingga membutuhkan tabel tersendiri bertipe One-to-Many dari tabel Loan.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
Tulis jawaban di sini.
SELECT * FROM loans WHERE status = 'APPROVED';
(Atau di JPA Repository: List<Loan> findByStatus(String status);)
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
Tulis jawaban di sini.
SELECT customer_id, SUM(amount_paid) FROM repayments GROUP BY customer_id;
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic | 5|
| SQL basic | 5|
| JPA | 5|
| Hibernate | 5|
| Repository | 5|
| Flyway | 5|
| Relationship | 5|
| Join query |5 |
| Lazy loading | 5|
| Finance data modeling | 5|

## Notes

```text
Tulis bagian yang masih membingungkan.
Bagian mendalam tentang optimasi Lazy vs Eager loading, penanganan N+1 query menggunakan JOIN FETCH, serta best practice penulisan JPQL yang kompleks masih membutuhkan penguatan konsep.
```

