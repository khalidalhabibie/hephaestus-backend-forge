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
Variabel adalah salah satu attribute yang ada di dunia programming, variabel digunakan untuk menyimpan sebuah value. Variabel bisa dinamis dan bisa statis.

Contoh -> String nama = "Risjad"
```

### 2. Apa itu data type?

Jawaban:

```text
Data type atau tipe data adalah tipe dari sebuah data, atau variable. Ada beberapa Tipe data, contohnya yang umum digunakan adalah Integer, String, Boolean, Float, dan lain lain.
Biasanya tipe data ini disebutkan saat kita mendeklarasi sebuah variabel dan disebutkan juga tipe datanya.
```

### 3. Sebutkan contoh tipe data untuk text, number, dan true/false.

Jawaban:

```text
Text -> String, Number -> Integer/float, True/False -> Boolean.
```

### 4. Apa perbedaan variable declaration dan assignment?

Jawaban:

```text
Variabel deklarasi adalah pembuatan variabel diawal, dan disebutkan tipe datanya.
Contoh -> String Nama;

Assignment adalah menentukan value dari sebuah variabel yang sudah dibuat.
Contoh -> Nama = "Risjad"
```

### 5. Apa itu camelCase?

Jawaban:

```text
camel case adalah cara penulisan dua kata atau lebih tanpa spasi, tapi menggunakan huruf kapital di setiap kata barunya.

Contoh -> String phoneNumber.
```

## Section B - Class and Object

### 6. Apa itu class?

Jawaban:

```text
Class adalah sebuah struktur yang didalamnya bisa berisi banyak atribut variabel dan fungsi. Class bisa disebut juga sebagai blueprint atau template.

Contoh -> Class Hewan
```

### 7. Apa itu object?

Jawaban:

```text
Object adalah hasil implementasi dari sebuah class.
```

### 8. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah template/struktur/blueprint, sedangkan object adalah hasil implementasi dari class tersebut.
```

## Section C - Field, Method, Constructor

### 9. Apa itu field?

Jawaban:

```text
Field bisa disebut juga variabel yang ada disebuah class.
```

### 10. Apa itu method?

Jawaban:

```text
Method adalah kumpulan instruksi yang disatukan didalam sebuah fungsi atau procedure.
Method yang mereturn sebuah value disebut fungsi, dan method yang tidak mereturn apa apa disebut procedure.
```

### 11. Apa itu parameter?

Jawaban:

```text
Parameter adalah data yang digunakan atau dibutuhkan didalam sebuah method mau itu fungsi atau procedure.
```

### 12. Apa itu return value?

Jawaban:

```text
Value yang akan di return dari sebuah fungsi setelah melalui proses pengolahan data. Sebuah method yang mereturn sebuah value disebut juga fungsi/function.
```

### 13. Apa itu constructor?

Jawaban:

```text
 Constructor adalah method yang pertamakali dijalankan ketika pembuatan object dari sebuah class.
```

## Section D - Access Modifier

### 14. Apa fungsi private?

Jawaban:

```text
private adalah hak akses yang hanya bisa diakses oleh kelas itu sendiri.
```

### 15. Apa fungsi public?

Jawaban:

```text
public adalah hak akses yang bisa diakses oleh siapa saja
```

### 16. Kenapa field biasanya dibuat private?

Jawaban:

```text
Agar sebuah data field atau attribut lebih secure dan tidak bisa diakses diluar dari class tersebut.
```

## Section E - Getter and Setter

### 17. Apa fungsi getter?

Jawaban:

```text
getter dalah sebuah fungsi untuk mereturn dan mengakses sebuah atttribut / field.
```

### 18. Apa fungsi setter?

Jawaban:

```text
Setter adalah sebuah method untuk mengassign sebuah attribut / field dari sebuah class, dengan menerima parameter input dari user.
```

### 19. Kenapa tidak semua field dibuat public?

Jawaban:

```text
Agar field yang bersifat rahasia tidak bisa diaskes secara langsung dari luar classnya.
Biasanya field tersebut masih bisa diakses melalui getter atau setter yang telah dibuat.
```

## Section F - Collection

### 20. Apa itu List?

Jawaban:

```text
List adalah sebuah stuktur data untuk menyimpan banyak data yang biasanya memiliki tipe data yang sama.
```

### 21. Apa itu Map?

Jawaban:

```text
Map adalah sebuah struktur data untuk menyimpan banyak data yang terdiri dari key dan value.
```

### 22. Kapan menggunakan List dan kapan menggunakan Map?

Jawaban:

```text
Kita menggunakan list ketika data tunggal dan map ketika data yang ingin kita simpan terdiri dari key dan value.
```

## Section G - OOP Basic

### 23. Apa itu encapsulation?

Jawaban:

```text
Enkapsulasi adalah sebuah konsep dalam OOP, yaitu sebagai pembungkus. Jadi attribut-attribut private di dalam sebuah class tidak bisa diakses langsung, tetapi harus menggunakan setter dan getter
```

### 24. Apa itu interface?

Jawaban:

```text
Interface digunakan untuk mendeklarasikan rule rule apa saja yang harus dimiliki oleh sebuah class. Misal disebuah class harus memiliki fungsi atau method apa saja.
```

### 25. Apa itu abstract class?

Jawaban:

```text
Abstract class adalah sebuah template atau blueprint sebuah class yang class itu sendiri tidak bisa dijadikan sebuah object.
Melainkan class tersebut akan mewarisi sifat sifat ke anak class yang mengExtend class tersebut.
```

### 26. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Interface bisa dibilang juga kontrak yang mendeklarasikan fungsi atau method apa saja yang harus ada di class yang implement interface tersebut. Abstract class menyediakan template dari sebuah class.

Sebuah class bisa mengimplement BANYAK interface, dan sebuah class hanya bisa memiliki SATU induk class.
```

## Self Assessment

Beri tanda sesuai pemahaman kamu saat ini.

| Topik                    | Belum paham | Mulai paham | Cukup paham |
| ------------------------ | ----------- | ----------- | ----------- |
| Basic syntax             | [ ]         | [ ]         | [X]         |
| Class and object         | [ ]         | [ ]         | [X]         |
| Field/method/constructor | [ ]         | [ ]         | [X]         |
| Access modifier          | [ ]         | [X]         | [ ]         |
| Getter/setter            | [ ]         | [ ]         | [X]         |
| List/Map                 | [ ]         | [X]         | [ ]         |
| Interface/abstract class | [ ]         | [ ]         | [X]         |
