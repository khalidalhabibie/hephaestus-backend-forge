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
tempat untuk menyimpan data dan bisa digunakan untuk menjalankan prorgram tertentu, misal saya punya variabel :
String nama; -> artinya `nama` adalah sebuah variabel yang bertipe string dan bisa digunakan untuk kebutuhan membuat suatu program nantinya


```

### 2. Apa itu data type?

Jawaban:

```text
tipe data adalah tipe dari suatu variabel, 
tipe data membatasi apa apa saja yang bisa dilakukan.
jika kita asign ke 1 variabel : misal `int usia` artinya usia hanya bisa di isi dengan int/integer/angka bilangan bulat saja.
```

### 3. Sebutkan contoh tipe data untuk text, number, dan true/false.

Jawaban:

```text
text : String, varchar, Text (kalo di database), char dll
number : int, double, float, numeric dll
true false : boolean
```

### 4. Apa perbedaan variable declaration dan assignment?

Jawaban:

```text
Variabel Declaration : variabel yang dideklarasikan dan bisa digunakan nantinya untuk membuat program tertentu, dan variabel ini tanpa value default.

Variable Assignment :  variabel yang dideklarasikan dan bisa digunakan nantinya untuk membuat program tertentu, namun variabel ini memiliki value default. contoh : String nama = steven;
```

### 5. Apa itu camelCase?

Jawaban:

```text
Standar yang ditetapakan untuk menamai variabel, fungsi class dll - biasanya ditandai dengan awlan huruf pertama kecil dan huruf kedua besar : getCustomer, tambahData dll.
```

## Section B - Class and Object

### 6. Apa itu class?

Jawaban:

```text
Class adalah blue print untuk membuat sebuah objek, di dalam kelas bisa di isi dengan attribut kelas : field/variabelnya, method di dalamnya, konstruktor, setter getter dll. 
```

### 7. Apa itu object?

Jawaban:

```text
objek adalah hasil isian dari kelas yang sudah dibuat/blueprint. dari blue print/kelas yang kita buat kita bisa membuat banyak objek : 
- Class Manusia -> atributnya ; nama dan usia
objeknya -> kita bisa menggunakan kelas sebagai blue print dan mengisi nama dan usia ini berkali kali
```

### 8. Apa perbedaan class dan object?

Jawaban:

```text
Kelas adalah blueprintnya dan object adalah hasil dari blueprint
```

## Section C - Field, Method, Constructor

### 9. Apa itu field?

Jawaban:

```text
variabel yang ada di dalam kelas yang dibuat. contoh :
kelas mahasisa punya atribut : nama , nim, tanggal lahir dll
```

### 10. Apa itu method?

Jawaban:

```text
method/function adalah hal yang bisa dilakukan - biasanya berisikan beberapa baris code untuk melakukan sesuatu hal. 

kalo dalam kalimat pendek itu "dia bisa ngapain aja?" contoh kalo class mahasiswa punyam method : belajar(), mencontek() dll
```

### 11. Apa itu parameter?

Jawaban:

```text
parameter adalah masukan yang diterima oleh suatu method yang dibutuhkan untuk mengeksekusi tindakan atau kode tertentu
 -> misal kita punya method Tambah(int a, int b) 
 -> artinya fungsi tambah ini harus mendapatkan masukan a dan b yg berupa angka untuk melakukan eksekusi kode di dalam method/fungsi tsb.
```

### 12. Apa itu return value?

Jawaban:

```text
keluaran yang dihasilkan oleh suatu method contoh dalam method tambah tadi kita return hasil penambahan antara a dan b 
```

### 13. Apa itu constructor?

Jawaban:

```text
method yang pertamakali disi saat kita ingin membuat suatu objek, karena pada saat kita membuat objek kita akan mengisi kontruktor terlebih dahylu baru di teruskan ke field classnya.
```

## Section D - Access Modifier

### 14. Apa fungsi private?

Jawaban:

```text
private hanya bisa di gunakan di 1 file yang sama - contoh kita punya nama file :
mahasiswa.java - di dalam file ini kita punya  `private String name` artinya, nama hanya bisa di akses id mahasiswa.java saja.
```

### 15. Apa fungsi public?

Jawaban:

```text
berbeda dengan private, public bisa di akses disemua file : jadi jika aku punya nama file dosen.java kita bisa memanggil nama yang ada di mahasiswa.java dst
```

### 16. Kenapa field biasanya dibuat private?

Jawaban:

```text
agar tidak sembarangan bisa di akses oleh kelas lain dan meminimilisir kesalahan dalam penulisan code dan penggunaannya.
```

## Section E - Getter and Setter

### 17. Apa fungsi getter?

Jawaban:

```text
getter digunakan dalam mengambil suatu field yang adaa di dalam kelas tertentu - getter diciptakan agar kita ga langsung akses ke kelasnya namun lewat method getter
```

### 18. Apa fungsi setter?

Jawaban:

```text
Stter digunakan untuk mengeset value tertentu didalam suatu objek, kaya update gtu.
```

### 19. Kenapa tidak semua field dibuat public?

Jawaban:

```text
agar lebih aman dan tidak digunakan secara sembarangan, untuk mengaksesnya hanya bisa lewat setter atau getternya.
```

## Section F - Collection

### 20. Apa itu List?

Jawaban:

```text
suatu data struktur yang berisikan value tertentu dan banyak -> biasanya digambarkan dengan [] 
```

### 21. Apa itu Map?

Jawaban:

```text
map adalah suatu data struktur yang berisikan gabungan key dan values -> kalo di python dictionary{key : value}
```

### 22. Kapan menggunakan List dan kapan menggunakan Map?

Jawaban:

```text
list digunakan jika kita tidak memerluakan value tertentu sebaliknya map harus digunakan jika terdapat value didalamnya , misal kita perlu tau namanya siapa : 
{nama : steven} - kalo di list hasilnya akan [nama, steven ].
```

## Section G - OOP Basic

### 23. Apa itu encapsulation?

Jawaban:

```text
peraturan yang dibuat untuk akses modifier ; public private protect.
```

### 24. Apa itu interface?

Jawaban:

```text
interface adalah sebuah kontrak yang harus di dilakukan/implementasikan jika kita mengadopsinya/implement.
```

### 25. Apa itu abstract class?

Jawaban:

```text
abstract adalah sebuah kontrak namun kita bisa mengimplemntasikannya atau tidak itu terserah kita -> lebih bebas
selain itu abstract class tidak bisa digunakan dalam membuat objek beda dengan class biasa
```

### 26. Apa perbedaan interface dan abstract class?

Jawaban:

```text
kalo interface hukumnya harus dilakukan/diimplemntasikan jika kita mengadopisnya kalo abstract tidak harus. 

selain itu abstract class tidak bisa digunakan dalam membuat objek
```

## Self Assessment

Beri tanda sesuai pemahaman kamu saat ini.

| Topik | Belum paham | Mulai paham | Cukup paham |
| --- | --- | --- | --- |
| Basic syntax | [ ] | [ ] | [ok] |
| Class and object | [ ] | [ ] | [ok] |
| Field/method/constructor | [ ] | [ ] | [ok] |
| Access modifier | [ ] | [ ] | [ok] |
| Getter/setter | [ ] | [ok] | [ ] |
| List/Map | [ ] | [ ] | [ok] |
| Interface/abstract class | [ ] | [ok] | [ ] |
