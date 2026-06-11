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
Variable adalah penamaan atas tipe data yang di declare. Contoh String name, name itu variable nya.
```

### 2. Apa itu data type?

Jawaban:

```text
Tipe data adalah yang ada di Java seperti integer, float, String, double, varchar, char, dan boolean.
```

### 3. Sebutkan contoh tipe data untuk text, number, dan true/false.

Jawaban:

```text
a. String nama;
b. int angka;
c. boolean true;
```

### 4. Apa perbedaan variable declaration dan assignment?

Jawaban:

```text
Variable declaration itu untuk mendeklarasikan tipe data si variable tsb. Supaya gak salah tampil data juga. Misal kalo di python kan kita ga declare, takutnya gada batasan variable tsb. Karena tipe data ini juga ada fungsi untuk mengelola jumlah karakternya. Contoh kayak int dan float. Kalo int itu kan hanya untuk angka bulat saja, sementara float itu engga.
```

### 5. Apa itu camelCase?

Jawaban:

```text
camelCase adalah cara formatting pengetikan di Java, cth. kelas main activity, di Java di-rename nya "mainActivity.java"
```

## Section B - Class and Object

### 6. Apa itu class?

Jawaban:

```text
Class biasanya ada di kayak "public class xxxx", class ini harus sesuai dengan nama file yang dinamain.
```

### 7. Apa itu object?

Jawaban:

```text
Object itu bisa dikonotasikan sebagai anakan dari Class. Jadi 1 class itu bisa punya banyak Object. cth.
class nya Mahasiswa, objectnya itu mhs1, jadi ntar penulisannya :
Mahasiswa mhs1 = new Mahasiswa();
Mahasiswa ini adalah classnya, mhs1 ini object barunya, nah new Mahasiswa() ini untuk menyatakan bahwa ada object baru nih di kelas Mahasiswa, yaitu si mhs1 ini.
```

### 8. Apa perbedaan class dan object?

Jawaban:

```text
Class bisa terdiri dari banyak object.
```

## Section C - Field, Method, Constructor

### 9. Apa itu field?

Jawaban:

```text
Menurut saya, field itu kayak si parameternya.
```

### 10. Apa itu method?

Jawaban:

```text
Method adalah fungsi yang dioperasikan di Java (kalau di Python dia kayak function). Nah, method ini nantinya dapat dipanggil di kelas di package tsb. Fungsinya adalah untuk menyederhanakan proses program di Java, supaya gak redundan.
```

### 11. Apa itu parameter?

Jawaban:

```text
Parameter itu variable yang digunakan di method tertentu. Misal method nya public void bacaBuku (String buku, String jenisBuku){
    xxxx
}

nah yang didalam () itu adalah parameter. Parameter ini harus sudah di declare sebelum dipanggil.
```

### 12. Apa itu return value?

Jawaban:

```text
Return value adalah nilai yang dikembalikan suatu fungsi. Umumnya untuk method yang ada pengolahannya
```

### 13. Apa itu constructor?

Jawaban:

```text
Constructor itu mirip method tapi dia memberikan nilai.
```

## Section D - Access Modifier

### 14. Apa fungsi private?

Jawaban:

```text
Private adalah tipe akses di Java, ketika dia private maka tidak bisa diakses oleh kelas lain semudah itu. Butuh Getter Setter. cth. Private int angka; , Private class XXX;
```

### 15. Apa fungsi public?

Jawaban:

```text
Public adalah tipe akses di Java, ketika dia public, maka kelas itu akan dapat diakses oleh kelas lainnya. contoh Public Class xxx.
```

### 16. Kenapa field biasanya dibuat private?

Jawaban:

```text
Agar kelas lain tidak bisa sembarangan mengakses field tersebut.
```

## Section E - Getter and Setter

### 17. Apa fungsi getter?

Jawaban:

```text
Getter adalah cara untuk membaca data pada kelas lain yang dibungkus oleh akses private.
```

### 18. Apa fungsi setter?

Jawaban:

```text
Setter adalah cara untuk merubah data pada kelas lain yang dibungkus oleh akses private di kelas tersebut.
```

### 19. Kenapa tidak semua field dibuat public?

Jawaban:

```text
Untuk menjaga keamanan data dari data leak dan unconfirmed execution.
```

## Section F - Collection

### 20. Apa itu List?

Jawaban:

```text
List itu misal kayak nama1, nama2, nama3. Hanya kumpulan data tanpa nilai didalamnya.

```

### 21. Apa itu Map?

Jawaban:

```text
Map itu kayak dictionary dia misal ada angka = 1.
```

### 22. Kapan menggunakan List dan kapan menggunakan Map?

Jawaban:

```text
Kalo data cuma 1 dan gada di-assign kemana2 dan gaada nilai nya, pake List cukup.
```

## Section G - OOP Basic

### 23. Apa itu encapsulation?

Jawaban:

```text
Encapsulation adalah salah satu metode untuk memberikan akses kepada sebuah field.
```

### 24. Apa itu interface?

Jawaban:

```text
Interface adalah sebuah blueprint yang memanggil semua method/property yang ada
```

### 25. Apa itu abstract class?

Jawaban:

```text
Abstract class itu gaperlu manggil semua method/property
```

### 26. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Abstract class itu gaperlu manggil semua method/property
```

## Self Assessment

Beri tanda sesuai pemahaman kamu saat ini.

| Topik | Belum paham | Mulai paham | Cukup paham |
| --- | --- | --- | --- |
| Basic syntax | [ ] | [ ] | [v] |
| Class and object | [ ] | [ ] | [v] |
| Field/method/constructor | [ ] | [v] | [ ] |
| Access modifier | [ ] | [ ] | [v] |
| Getter/setter | [ ] | [v] | [ ] |
| List/Map | [v] | [ ] | [ ] |
| Interface/abstract class | [v] | [ ] | [ ] |
