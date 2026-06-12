# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
Variable adalah nilai yang disimpan  di dalam program yang nantinya dapat berubah sesuai dengan yang kita setting.
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
String -> menyimpan teks (e.g. Kayla)
int -> menyimpan bilangan bulat (e.g. 1, 2, 3, dan etc)
Long -> menyimpan bilangan bulat (e.g. 1L)
boolean -> true/false

int dan Long memiliki perbedaan pada nilai kapasitas nilainya, di mana Long dapat menyimpan nilai yang lebih besar.
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
camelCase merupakan format penulisan yang mempermudah kita dalam membaca kode karena bentuknya konsisten.
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
Class -> merupakan blueprint atau template yang nantinya akan diwariskan kepada setiap Object.

Object -> merupakan hasil dari blueprint yang terwariskan nilai-nilai.

E.g. Class Hewan dan Object Kucing.
```

### 5. Apa itu field?

Jawaban:

```text
Field adalah attribute dari setiap Object yang kita buat (e.g. Kucing memiliki nama dan warna).
```

### 6. Apa itu method?

Jawaban:

```text
Method ada kode yang mirip seperti Function tetapi dimiliki suatu Class (e.g.addPayment).
```

### 7. Apa itu parameter?

Jawaban:

```text
Parameter adalah patokan untuk memanggil suatu variabel (e.g. ingin memanggil nama atau warna pada kucing)
```

### 8. Apa itu return value?

Jawaban:

```text
Return value adalah nilai yang dikembalikan setelah nilainya disimpan.
```

### 9. Apa fungsi constructor?

Jawaban:

```text
Constructor adalah sebuah kumpulan yang menyimpan Object.
```

### 10. Apa fungsi `this`?

Jawaban:

```text
this berfungsi untuk mereferensikan Object yang ingin kita gunakan (e.g. this.warna = warna).
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
Field dibuat provate agar aksesnya dapat dikelola sesuai dengan yang kita inginkan dan tidak dapat diubah sembarangan dari luar Class-nya.
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
Getter -> berfungsi untuk mengambil atau membaca nilai field, di mana sering digunakan ketika field bersifat private.
Setter -> berfungsi untuk mengubah nilai yang tadinya sudah dipanggil.
```

### 13. Apa itu encapsulation?

Jawaban:

```text
Encapsulation adalah sesuatu yang dapat menyembunyikan nilai agar tidak dapat diakses sembarang.
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
List -> berfungsi untuk mengumpulkan data yang disimpan.
Map -> berfungsi untuk mengumpulkan data yang disimpan, namun dengan pasangan key serta value-nya.
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
CustomerService menggunakan Map<Long,Customer> karena dapat mempermudah dalam mereferensikan Customer dengan setiap ID-nya yang unik.
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
getAllCustomers mengembalikan List<Customer> agar nilai yang disimpan dapat dikembalikan.
```

### 17. Apa itu interface?

Jawaban:

```text
Interface adalah suatu method yang digunkan pada Class-nya (e.g. method Sound yang dapat digunakan untuk Class-nya dengan memanggil Interface yang sudah kita buat).
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Interface -> hanya untuk mengarahkan apa yang harus dilakukan Class.
Abstract -> memiliki constructor sehingga memiliki konsep yang lebih jelas.
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
Flow pertamanya dimulai dari menentukan Parameter yang ingin dipanggil dalam Method createCustomer, kemudian membuat Object Customer untuk menentukan field nilai yang ingin kita ambil dari Customer. Nilai kemudian disimpan dan dikembalikan menggunakan Return.
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
Menurut saya bagian yang paling sulit adalah penggunaan Sequence untuk ID Customer generated secara otomatis karena saya belum terlalu terbiasa menggunakan Sequence ini.
```
