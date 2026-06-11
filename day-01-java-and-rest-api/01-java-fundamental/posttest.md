# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```
Variable merupakan suatu tempat untuk menyimpan data sementara
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```
String untuk menyimpan text atau kombinasi karakter 
Int untuk menyimpan angka bulat 
Long untuk menyimpan angka bulat yang sangat besar
boolean untuk menyimpan logika yang biasanya true false
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```
karena merupakan konversi standar java yang bertujuan agar memudahkan dalam membaca kode yang lebih mudah dan konsisten
```

### 4. Apa perbedaan class dan object?

Jawaban:

```
Class merupakan cetakan yang mendefinisikan atribut dan metode, kalau object adalah bentuk nyata yang dibuat dari class dan memiliki data sendiri
```

### 5. Apa itu field?

Jawaban:

```
Field merupakan suatu variabel yang berada di dalam class dan digunakan untuk menyimpan data
```

### 6. Apa itu method?

Jawaban:

```
Method adala sekumpulan perintah di dalam class yang digunakan untuk melakukan suatu tugas atau operasi tertentu
```

### 7. Apa itu parameter?

Jawaban:

```
variable dalam method/fungsi untuk menampung nilai yang terinput
```

### 8. Apa itu return value?

Jawaban:

```
buat mengembalikan hasil ke pemanggilnya
```

### 9. Apa fungsi constructor?

Jawaban:

```
fungsi dalam class yang dibuat khusus yang dijalankan otomatis waktu buat pertama kali object dibuat
```

### 10. Apa fungsi `this`?

Jawaban:

```
digunakan untuk membedakan field class dengan parameter yang memiliki nama yang sama
```

### 11. Kenapa field dibuat private?

Jawaban:

```
mengamankan data agar tidak diubah sembarangan
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```
agar data aman tidak diubah oleh yang tidak memiliki hak mengubah atau mengaksesnya
```

### 13. Apa itu encapsulation?

Jawaban:

```
pembungkus method untuk membatasi akses dari luar dengan mengeset variablenya ke private yang nantinya diimplementasikan getter setter

```

### 14. Apa perbedaan List dan Map?

Jawaban:

```
List digunakan untuk menyimpan kumpulan data secara berurutan dan diakses berdasarkan indeks, sedangkan Map digunakan untuk menyimpan data dalam bentuk pasangan key-value dan diakses berdasarkan key
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```
karena setiap Customer memiliki ID unik bertipe Long yang dapat dijadikan key
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```
karena dengan menggunakan list mendapatkan data customer berdasarkan ID lebih cepat
```

### 17. Apa itu interface?

Jawaban:

```
isinya adalah kontrak atau blue print yang isinya method saja dan konstantanya, jadi dalam interface tidak ada isi atau implementasinya untuk method didalamnya 
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```
kalau interface isinya hanya kontrak atay blueprint atau bisa dibilang isinya aturan tanpa ada isi kalau abstract class bisa memiliki method yang ada isi didalamnya dan abstract ini tidak bisa dibuat object
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```
Method createCustomer akan menerima data customer yang kemudian dia akan melakukan validasi untuk memastikan nama tidak kosong. Jika data valid, sistem akan membuat ID baru secara otomatis dengan menambahkan nilai sequence yang ada, kemudian menyimpan objek Customer ke dalam Map menggunakan ID tersebut sebagai key dan objek Customer sebagai value melalui proses map.put(id, customer).
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```
semuanya tapi saya mau belajar lagi pelan-pelan
```
