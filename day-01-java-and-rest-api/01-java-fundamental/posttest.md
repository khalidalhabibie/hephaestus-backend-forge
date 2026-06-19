# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
Variable adalah sebuah wadah untuk mengubah nilai suatu objek
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
String digunakan untuk teks, int digunakan untuk angka tetapi dengan range tertentu, Long juga mirip dengan int hanya saja size lebih besar
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
karena penamaan camelCase adalah sebuah tradisi untuk penamaan di Java
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah sebuah blueprint, sedangkan object ada wujud nyata atau pemanggilan sebuah class
```

### 5. Apa itu field?

Jawaban:

```text
sebuah atribut
```

### 6. Apa itu method?

Jawaban:

```text
sebuah fungsi atau logic yang berisi operasi atau tindakan
```

### 7. Apa itu parameter?

Jawaban:

```text
sebuah input yang wajib ada apabila sebuah method menginisiasi sebuah atribut
```

### 8. Apa itu return value?

Jawaban:

```text
sebuah syntax yang berfungsi untuk mengembalikan nilai pada suatu method
```

### 9. Apa fungsi constructor?

Jawaban:

```text
sebuah wadah yang berisi atribut atribut untuk digunakan di class lain
```

### 10. Apa fungsi `this`?

Jawaban:

```text
me refer kepada dirinya sendiri agar dapat digunakan di class induknya
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
agar tidak sembarang diubah, apabila ingin diubah maka wajib menggunakan method get
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
getter digunakan untuk mengambil atribut private sedangkan setter untuk mengubah nilai sebuah atribut
```

### 13. Apa itu encapsulation?

Jawaban:

```text
pembatasan akses terhadap suatu method atau atribut
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
List digunakan untuk kumpulan value saja, sedangkan Map berprinsip key value
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
karena customerstorage memiliki key berupa id dan value berupa data customer
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
agar fungsi getAllCustomers dapat memunculkan data customer keseluruhan
```

### 17. Apa itu interface?

Jawaban:

```text
sebuah aturan yang wajib diimplementasi sebuah class apabila mengadopsinya
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
abstract hanya digunakan sebagai class induk dan tidak bisa dijadikan object, dan abstract class hanya berisi aturan berupa method kosong yang wajib diimplementasi oleh class ketika diadopsi
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
ketika membuat createCustomer, masukkan parameter yang ingin di create, lalu ambil sequence sebagai id dan ditambahkan setiap iterasi nya, lalu bikin object cust yang berisi parameter method tadi, setelah itu lakukan tambah ke dalam customerStorage berupa id dan cust(nama, email, notelp)
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
saya mengalami kesulitan ketika memanggil method di main, karena seringkali lupa menginsiasi object dan terkadang lupa logic memanggil methodnya
```
