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
tempat penyimpanan data yang terstruktur agar bisa diakses, dikelola, dan diperbarui dengan mudah oleh aplikasi
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
data di map disimpan di memori ram jadi akan hilang saat aplikasi mati atau restart

database menyimpan data di harddisk , jadi data tetap aman meskipun aplikasi mati atau restart
```

3. Apa itu table?

Jawaban:

```text
bagian dari database yang berfungsi sebagai tempat untuk menyimpan kumpulan data sejenis
```

4. Apa itu row?

Jawaban:

```text
satu baris horizontal di dalam tabel yang mewakili satu baris data atau satu objek secara utuh sepertidata milik satu user
```

5. Apa itu column?

Jawaban:

```text
bagian vertikal di dalam tabel yang menentukan kategori atau atribut dari data yang disimpan misalnya kolom nama
```

6. Apa itu primary key?

Jawaban:

```text
kolom unik yang menjadi identitas utama dari setiap baris data di dalam tabel
```

7. Apa itu foreign key?

Jawaban:

```text
kolom di sebuah tabel yang menghubungkan ke primary key di tabel lain, fungsinya untuk buat relasi antar tabel
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
sebagai jembatan penerjemah dari backend ke sistem database seperti postgresql dan mysql
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
untuk mengambil data dari satu atau beberapa tabel di database
```

10. Apa fungsi INSERT?

Jawaban:

```text
untuk memasukkan atau menambahkan data baru ke dalam tabel
```

11. Apa fungsi UPDATE?

Jawaban:

```text
untuk mengubah atau memperbarui data yang sudah ada di dalam tabel
```

12. Apa fungsi DELETE?

Jawaban:

```text
untuk menghapus data dari dalam tabel
```

13. Apa fungsi WHERE?

Jawaban:

```text
untuk mem filter data berdasarkan kondisi tertentu
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
belum mengerti
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
untuk mengurutkan baris data hasil query, bisa dari yang terkecil ke terbesar atau terbesar ke terkecil
```

16. Apa fungsi LIMIT?

Jawaban:

```text
untuk membatasi jumlah data maksimal yang ingin ditampilkan dari hasil query
```

17. Apa itu JOIN?

Jawaban:

```text
perintah sql untuk menggabungkan kolom dari dua tabel atau lebih berdasarkan kolom relasi yang terhubung biasanya primary key dan foreign key
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
inner join hanya mengambil data yang cocok di kedua tabel

left join mengambil semua data dari tabel kiri ditambah data yang cocok dari tabel kanan (jika tidak cocok, tabel kanan akan bernilai null).

```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
belum tau
```

20. Apa itu Hibernate?

Jawaban:

```text
belum tau
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
belum tau
```

22. Apa itu Entity?

Jawaban:

```text
sebuah class java yang dipetakan sebagai representasi dari sebuah tabel di dalam database
```

23. Apa fungsi @Entity?

Jawaban:

```text
annotation untuk memberi tahu bahwa class java tersebut harus dianggap sebagai tabel database
```

24. Apa fungsi @Table?

Jawaban:

```text
annotation untuk menentukan detail tabel database secara spesifik, seperti mengubah nama tabel jika tidak ingin sama dengan nama class java nya
```

25. Apa fungsi @Id?

Jawaban:

```text
annotation untuk menandai bahwa suatu attribute di dalam class java merupakan primary key dari tabel tersebut
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
belum tau
```

27. Apa itu Repository?

Jawaban:

```text
sebuah layer di aplikasi yang bertugas menangani operasi database (crud)
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
belum tau
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
belum tau
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
belum tau
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
optional<customer> findbyemail(string email);
```

32. Apa fungsi @Query?

Jawaban:

```text
annotation untuk menulis query khusus secara manual di dalam repository jika query yang kita butuhkan terlalu kompleks untuk dibuat otomatis oleh spring
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
belum tau
```

34. Kapan menggunakan native query?

Jawaban:

```text
belum tau
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
proses mengelola, melacak, dan menerapkan perubahan struktur database secara konsisten dan bertahap seiring berjalannya pengembangan aplikasi
```

36. Apa itu Flyway?

Jawaban:

```text
belum tau
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
agar kita punya riwayat perubahan yang jelas, mencegah konflik antar tim developer, dan memudahkan proses deployment atau rollback jika ada eror pada database
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
belum tau
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
struktur database antar stage (lokal, staging, production) akan berbeda, aplikasi bisa langsung eror saat dideploy karena tabel atau kolom yang dibutuhkan tidak ada, dan perubahan susah ditrace kembali
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
hubungan yang dibuat antara dua tabel database untuk menghubungkan data yang saling berkaitan
```

41. Apa itu one-to-many?

Jawaban:

```text
relasi di mana satu baris data di tabel a bisa terhubung dengan banyak baris data di tabel b
```

42. Apa itu many-to-one?

Jawaban:

```text
banyak baris data di tabel b merujuk pada satu baris data yang sama di tabel a
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
annotation di java untuk menandai hubungan objek yang mengarah ke satu entitas utama (tabel induk)
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
annotation di java untuk menandai hubungan objek yang menampung banyak data (biasanya dalam bentuk list atau set) dari entitas anak
```

45. Apa itu lazy loading?

Jawaban:

```text
belum tau
```

46. Apa itu eager loading?

Jawaban:

```text
belum tau
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
belum tau
```

48. Apa itu N+1 query problem?

Jawaban:

```text
belum tau
```

49. Apa itu join fetch?

Jawaban:

```text
belum tau
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
karena hubungan keduanya adalah one-to-many. satu customer bisa mengajukan pinjaman berkali kali sepanjang waktu. kalau digabung, data pribadi customer akan ditulis berulang kali dan bikin database tidak rapi
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
karena satu pinjaman akan dipecah menjadi beberapa kali tenor pembayaran (misal cicilan bulan ke 1 sampai ke 12). tabel ini dibutuhkan untuk melacak detail tanggal jatuh tempo, nominal cicilan pokok, bunga, dan status bayar per bulan secara terpisah
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
select * from loan_applications where status = 'approved';
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
belum tau secara spesifik
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        | 3         |
| SQL basic             | 3         |
| JPA                   | 2         |
| Hibernate             | 2         |
| Repository            | 3         |
| Flyway                | 2         |
| Relationship          | 3         |
| Join query            | 2         |
| Lazy loading          | 2         |
| Finance data modeling | 2         |

## Notes

```text
saya masih belum paham konsep jpa. flyaway, dan lazy loading
```
