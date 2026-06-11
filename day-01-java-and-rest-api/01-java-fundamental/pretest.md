# Pretest - Java Fundamental

## Objective

Pretest ini bertujuan mengukur pemahaman awal tentang Java Fundamental sebelum membaca materi dan mengerjakan exercise.

## Instructions

- Jawab dengan bahasa sendiri.
- Tidak perlu takut salah.
- Jangan melihat jawaban teman terlebih dahulu.
- Jika belum tahu, tulis apa yang kamu pahami saat ini.

Estimasi waktu: 20-30 menit.

## Section A - Basic Syntax

### 1. Apa itu variable?

Jawaban:

```text
Variable ialah konsep dalam pemrograman untuk menyimpan nilai/data. Variable terbagi menjadi 2, yaitu mutable variable yang artinya variable yang bisa diubah nilainya, dan imutable Variable yang nilainya tidka bisa diubah
```

### 2. Apa itu data type?

Jawaban:

```text
Tipe data adalah jenis nilai yang disimpan di dalam sebuah variable. Tipe data ini contohnya seperti string, integer, boolean, dan segala macam
```

### 3. Sebutkan contoh tipe data untuk text, number, dan true/false.

Jawaban:

```text
Di bahasa pemrograman Java:
1. Untuk teks, menggunakan tipe data String
2. Untuk number, menggunakan tipe data int (integer), double ataupun long (desimal)
3. Untuk true/false, menggunakan tipe data boolean yang nilainya hanya true atau false
```

### 4. Apa perbedaan variable declaration dan assignment?

Jawaban:

```text
Kalau variable declaration, kita mendefinisikan nama dan tipe data variable. Sedangkan untuk variable assignment, kita memberikan nilai untuk variable tersebut
```

### 5. Apa itu camelCase?

Jawaban:

```text
camelCase merupakan gaya penulisan kode yang di mana persyaratannya:
1. Tidak boleh ada spasi
2. Diawali huruf kecil
3. Awalan kata berikutnya menggunakan huruf kapital
Biasanya di Java dipake untuk penamaan variable (non final), nama function, dan nama parameter function
```

## Section B - Class and Object

### 6. Apa itu class?

Jawaban:

```text
Class ialah blueprint dari suatu objek. Yang mana dia merupakan bentuk atau representasi dari suatu konteks table
```

### 7. Apa itu object?

Jawaban:

```text
Object sendiri ialah representasi bentuk nyata dari Class itu sendiri
```

### 8. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah tempat mendefinisikan blueprint sebuah table, sedangkan Object ialah tempat membuat representasi nyata dari Class itu sendiri
```

## Section C - Field, Method, Constructor

### 9. Apa itu field?

Jawaban:

```text
Field ialah atribut dalam suatu tabel/data yang menyimpan satu jenis informasi tertentu
```

### 10. Apa itu method?

Jawaban:

```text
Method atau function ialah prosedur dalam suatu class yang digunakan untuk melakukan suatu aksi ataupun operasi terhadap suatu data. Baik itu return data, kalkulasi, dan sebagainya
```

### 11. Apa itu parameter?

Jawaban:

```text
Parameter ialah variable yang di-assign/dikirim ke dalam sebuah method sebagai input untuk diproses agar hasilnya bisa lebih dinamis
```

### 12. Apa itu return value?

Jawaban:

```text
Return value ialah variable yang dikembalikan ke function dan berisikan nilai yang telah diproses/sesuai permintaan dari function
```

### 13. Apa itu constructor?

Jawaban:

```text
Constructor ialah method yang digunakan untuk meng-instance objec
```

## Section D - Access Modifier

### 14. Apa fungsi private?

Jawaban:

```text
Private merupakan access modifier yang hanya bisa diakses di class dia sendiri. Jika di-instance ke class lain, dia tidak bisa dipanggil dan juga tidak bisa diakses
```

### 15. Apa fungsi public?

Jawaban:

```text
Public merupakan access modifier yang bisa diakses dan dipanggil di class manapun ketika di-instance
```

### 16. Kenapa field biasanya dibuat private?

Jawaban:

```text
Untuk menjaga nilainya agar tidak bisa diubah sembarangan/secara ugal-ugalan karena bisa saja bersifat strict
```

## Section E - Getter and Setter

### 17. Apa fungsi getter?

Jawaban:

```text
Getter mengembalikan nilai sebuah attribute class
```

### 18. Apa fungsi setter?

Jawaban:

```text
Setter mengubah nilai sebuah attribute class. Sebagai tambahan, method setter harus ada parameternya
```

### 19. Kenapa tidak semua field dibuat public?

Jawaban:

```text
Karena tidak semua field itu bersifat boleh dilihat oleh user, ada field yang bersifat rahasia juga. Selain itu, agar sebuah field tidak dapat diubah semau-maunya, karena bisa saja ada field seperti ID yang nilainya tidak boleh sembarangan diubah
```

## Section F - Collection

### 20. Apa itu List?

Jawaban:

```text
List merupakan tipe data non-primitive atau reference type yang digunakan untuk menyimpan lebih dari 1 data berdasarkan tipe data yang diizinkan
```

### 21. Apa itu Map?

Jawaban:

```text
List merupakan tipe data non-primitive atau reference type untuk menyimpan lebih dari 1 data. Bedanya dengan List ialah, Map itu isinya berbentuk "key-value". Sehingga untuk mengakses value suatu data, kita bisa memanggil key-nya
```

### 22. Kapan menggunakan List dan kapan menggunakan Map?

Jawaban:

```text
Gunakan List saat butuh kumpulan data yang banyak untuk menampungnya. Gunakan Map saat butuh pasangan key-value untuk akses yang lebih cepat berdasarkan key-nya. Biasanya List untuk menyimpan "daftar transaksi", Map untuk menyimpan "nomor rekening - saldo"
```

## Section G - OOP Basic

### 23. Apa itu encapsulation?

Jawaban:

```text
Encapsulation ialah konsep atau paradigma dalam OOP yang digunakan untuk membungkus data atau method dalam satu class. Berguna untuk membatasi akses langsung ke data atau attribute tersebut menggunakan access modifier private
```

### 24. Apa itu interface?

Jawaban:

```text
Interface ialah paradigma OOP yang berperan sebagai kontrak untuk mendefinisikan method tanpa perlu mendefinisikan isi method-nya. Interface digunakan untuk diimplementasikan ke sebuah Class dan Class yang mengimplementasikan harus membuat semua method yang ada di dalam Interface tersebut
```

### 25. Apa itu abstract class?

Jawaban:

```text
Abstract Class ialah class yang tidak bisa di-instance langsung. Abstract Class dapat memiliki method abtract yang kedudukannya hampir sama dengan ketika kita membuat method di dalam Interface. Selain itu, Abstract Class juga tetap bisa memiliki method biasa pada umumnya. Method yang bersifat abstract, maka Class yang meng-extend-nya harus meng-override atau menulis ulang method tersbeut
```

### 26. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Perbedaannya ialah:
1. Interface hanya berisi deklarasi method (tanpa isi). Di Interface, semua method by default akan public abstract
2. Sedangkan Abstract Class bisa memiliki method abstract dan juga method dengan implementasi berisi kode seperti pada umumnya
```

## Self Assessment

Beri tanda sesuai pemahaman kamu saat ini.

| Topik                    | Belum paham | Mulai paham | Cukup paham |
| ------------------------ | ----------- | ----------- | ----------- |
| Basic syntax             | [ ]         | [ ]         | [ ]         |
| Class and object         | [ ]         | [ ]         | [ ]         |
| Field/method/constructor | [ ]         | [ ]         | [ ]         |
| Access modifier          | [ ]         | [ ]         | [ ]         |
| Getter/setter            | [ ]         | [ ]         | [ ]         |
| List/Map                 | [ ]         | [ ]         | [ ]         |
| Interface/abstract class | [ ]         | [ ]         | [ ]         |
