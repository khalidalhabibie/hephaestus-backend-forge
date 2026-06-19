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
Database adalah tempat penyimpanan data / source of truth / persistance layer -> yang bertugas untuk menyimpan data baik itu terstruktur (sql) maupun yang tidak terstruktur.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Jika menyimpan di map data akan hilang ketika aplikasi di matikan karena disimpan di ram, sedangkan jika database tidak akan hilang dan akan terus tersimpan ke dalam server.
```

3. Apa itu table?

Jawaban:

```text
Sebuah tempat untuk menyimpan suatu entitas data tertentu -> misal:
- data customer
- data product dll
```

4. Apa itu row?

Jawaban:

```text
Baris atau jumlah data yangh ada dalam table
```

5. Apa itu column?

Jawaban:

```text
Jumlah atribut/key yang ada di dalam tabel
```

6. Apa itu primary key?

Jawaban:

```text
key unik untuk membedakan data yang ada di dalam tabel
```

7. Apa itu foreign key?

Jawaban:

```text
key unik untuk membedakan data tamu (dari tabel lain) yang ada di dalam tabel.

```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Karena aplikasi memerlukan tempat penyimpanan yang tersimpan terus dan menjadi source of truth
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
Mencari data berdasarkan kolom tertentu? -> "mau kolom yg mana?"
```

10. Apa fungsi INSERT?

Jawaban:

```text
Menambahkan data baru ke dalam satu tabel
```

11. Apa fungsi UPDATE?

Jawaban:

```text
Mengubah/edit data dalam tabel? -> "aku mau ngubah nael jadi naelpardede"
```

12. Apa fungsi DELETE?

Jawaban:

```text
Menghapus data dari database
```

13. Apa fungsi WHERE?

Jawaban:

```text
untuk filtering data tertentu berdasarkan parameter yang kita set -> "aku mau cari custmer yang namanya "nael" "
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
sama sama mencari data yang mirip dari parameter yang diberikan, bedanya like case sesitif i like enggak.
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
mengurutkan data berdasarkan paramater yang di set - misal order by name -> maka akan mengurutkan dari huruf a - z yg menjadi hasil querynya
```

16. Apa fungsi LIMIT?

Jawaban:

```text
Melimit atau membatasi data yang menjadi hasil query - misal : limit 10 -> maka akan menghasilkan 10 data pertama saja
```

17. Apa itu JOIN?

Jawaban:

```text
Menggabungkan data dari 2 atau lebih tabel - misal ada tabel customer dan product dan aku mau ambil kolom dari kedua tabel ini jadi aku bisa pake join
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
Inner join menyamakan kondisi dari 2tabel selain itu maka ga akan ke return


Left Join akan return semau record yang ada di tabel kiri yang match di tabel kanan -> nah misal ga ada di tabel kanan dia tinggal return null
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Jakarta Persistance API -> aturan yang dibuat untuk transksi database menggunakan code yang akan di eksekusi oleh hibernate
```

20. Apa itu Hibernate?

Jawaban:

```text
sebuah framework ORM (Object-Relational Mapping) open-source yang bertindak sebagai penyedia (provider) atau implementasi konkrit dari spesifikasi JPA.
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
JPA sintaksnya hibarnate yang mengeksekusi sintaks yan dibuat jpa
```

22. Apa itu Entity?

Jawaban:

```text
Sama halnya dengan tabel -> entitas data
```

23. Apa fungsi @Entity?

Jawaban:

```text
Anotasi untuk menandakan bahwa kode yang dibawahnya terdapat struktur data / tabel
```

24. Apa fungsi @Table?

Jawaban:

```text
Menandai bahwa code yang dibawahnya adalah representasi langsung tabel di database
```

25. Apa fungsi @Id?

Jawaban:

```text
ID unik yang membedakan row data, sama halnya dengan primary key di database namun di code springboot
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
anotasi untuk memberitahu java nilai kunci @ID akan di buat secara otomatis oleh sistem, jadi user gausah bikin sendiri
```

27. Apa itu Repository?

Jawaban:

```text
Repository adalah sebuah pattern atau komponen dalam Spring Data yang bertindak sebagai jembatan antara lapisan bisnis (Service Layer) dan lapisan data (Database). Repository mengabstraksi semua operasi akses data, sehingga pengembang bisa mengelola data objek Java tanpa harus menulis query SQL secara manual.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Metode siap pakai / seperti query tapi sudah disediakan oleh jpa yang bisa di akses dengan method. misal : findall() dll
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Update versi aja sih dengan service yang lebih lengkap
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
fitur dari spring boot unruk melakukan query data hanya menggunakan function
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
findCustomerbyemail()
```

32. Apa fungsi @Query?

Jawaban:

```text
menggunakan raw qery langsung tapi di code
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
JPQL querynya menggunakan class java nya kalo native pake nama tabelnya langsung
```

34. Kapan menggunakan native query?

Jawaban:

```text
jika memerlukan query yang kompleks seperti join agregasi dan yang tidak didukung oleh jpql
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
konfigurasi untuk mengatur dan mengoneksikan proyek java ke database secara langsung
```

36. Apa itu Flyway?

Jawaban:

```text
tools untuk database migration untuk project java spring
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
supaya kita bisa melihat riwayat dan jika diperlukan roleback ya tinggal kembali
```

38. Apa maksud file V1\_\_create_customers_table.sql?

Jawaban:

```text
Berkas file untuk membuat tabel baru dengan versi 1 bernama customer saat aplikasi dijalankan pertama kalinya.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Code bisa ga sinkron dengan database dan crash
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Relasi untuk menandakan bahwa tabel satu dan lainnya memiliki relasi sehingga bisa di joinkan jika diperlukan penggabungan data.
```

41. Apa itu one-to-many?

Jawaban:

```text
relasi antar tabel yang menandakan 1 entity bisa memiliki banyak entity lain

misal : Customer bisa memiliki banyak pengajuan loans
```

42. Apa itu many-to-one?

Jawaban:

```text
relasi antar tabel yang menandakan 1 entity bisa memiliki banyak entity lain

misal : Customer bisa memiliki banyak pengajuan loans

jujur ini sebenrnya sama aja
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Anotasi untuk menandakan tabel/entitty memiliki relasi many to one dengan tabel / entitiy lain di class java
```

44. Apa fungsi @OneToMany?

Jawaban:

```text

Anotasi untuk menandakan tabel/entitty memiliki relasi  one to many dengan tabel / entitiy lain di class java
```

45. Apa itu lazy loading?

Jawaban:

```text
data yang diambil hanya data saat daya diperlukan saja
```

46. Apa itu eager loading?

Jawaban:

```text
Eager loading adalah cara ambil data di mana data utama dan semua data relasinya langsung diangkut sekaligus dari database sejak awal, meskipun data relasi tersebut belum tentu dipakai.

```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Risiko utamanya adalah membuat aplikasi menjadi sangat lambat (karena bolak-balik manggil database), atau memicu error 'LazyInitializationException' saat data relasi diakses ketika koneksi ke database sudah terputus.

```

48. Apa itu N+1 query problem?

Jawaban:

```text
N+1 query problem adalah pemborosan query ke database, di mana aplikasi menjalankan 1 query untuk mengambil daftar data utama, lalu terpaksa menjalankan 'N' query tambahan secara terpisah hanya untuk mengambil detail relasi dari setiap baris data tersebut.

```

49. Apa itu join fetch?

Jawaban:

```text
Tulis jawaban di sini.
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
karena mereka memili fungsi data yang berbeda, sehingga perlu dipisahkan (normalisasi)
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Agar cicilan bisa di breakdown menjadi banyak repayment schedlu jatohnya noramlisasi juga
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```sql
SELECT * (INI BISA DI CUSTOM PER COLUMNT TERTENTU)
FROM LOAN
WHERE STATUS ? (INI BISA DI CUSTOM)
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```sql
SELECT C.ID, C.NAMA , SUM(L.PAY) AS TOTAL_PAY
FROM CUSTOMER C JOIN LOANS L ON C.ID = L.ID
WHERE C.ID ?
GROUP BY C.NAME
```

## Self Assessment

| Area                  | Score 1-5 |
| --------------------- | --------- |
| Database basic        | 4         |
| SQL basic             | 4         |
| JPA                   | 3         |
| Hibernate             | 3         |
| Repository            | 2         |
| Flyway                | 2         |
| Relationship          | 4         |
| Join query            | 3         |
| Lazy loading          | 2         |
| Finance data modeling | 2         |

## Notes

```text
Tulis bagian yang masih membingungkan.
```
