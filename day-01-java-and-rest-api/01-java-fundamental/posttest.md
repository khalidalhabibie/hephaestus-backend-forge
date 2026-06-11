# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
Variable ialah konsep dalam pemrograman untuk menyimpan nilai/data. Variable terbagi menjadi 2, yaitu mutable variable yang artinya variable yang bisa diubah nilainya, dan imutable Variable yang nilainya tidka bisa diubah
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
- String: Tipe data non primitive yang dikhususkan untuk menyimpan teks dan dibungkus dalam kutip 2. Contoh "Adnan"
- int: Tipe data primitive untuk menyimpan angka. Contoh: 12
- Long: Tipe data untuk menyimpan angka desimal. Contoh: 12.5
- boolean: Tipe data non primitive yang hanya memiliki nilai true atau false
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
Karena camelCase merupakan standar gaya penulisan atau biasa dikenal sebagai "naming convension", sehingga akan lebih mudah dibaca dan dipahami oleh developer lain, dan juga agar kode-nya konsisten
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah tempat mendefinisikan blueprint sebuah table, sedangkan Object ialah tempat membuat representasi nyata dari Class itu sendiri
```

### 5. Apa itu field?

Jawaban:

```text
Field ialah atribut dalam suatu tabel/data yang menyimpan satu jenis informasi tertentu
```

### 6. Apa itu method?

Jawaban:

```text
Method atau function ialah prosedur dalam suatu class yang digunakan untuk melakukan suatu aksi ataupun operasi terhadap suatu data. Baik itu return data, kalkulasi, dan sebagainya
```

### 7. Apa itu parameter?

Jawaban:

```text
Parameter ialah variable yang di-assign/dikirim ke dalam sebuah method sebagai input untuk diproses agar hasilnya bisa lebih dinamis
```

### 8. Apa itu return value?

Jawaban:

```text
Return value ialah variable yang dikembalikan ke function dan berisikan nilai yang telah diproses/sesuai permintaan dari function
```

### 9. Apa fungsi constructor?

Jawaban:

```text
Constructor ialah method yang digunakan untuk meng-instance object dan akan terpanggil ketika instance object
```

### 10. Apa fungsi `this`?

Jawaban:

```text
`this` digunakan untuk merujuk ke class itu sendiri. Jika dalam sebuah constructor ada parameter fullName, maka untuk mengubah atribut fullName menggunakan `this` untuk memberitahu ke Java kalau yang dimaksud ialah fullName pada atribut melalui keyword `this`
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
Untuk menjaga nilainya agar tidak bisa diubah sembarangan/secara ugal-ugalan karena bisa saja bersifat strict
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
- Getter mengembalikan nilai sebuah attribute class
- Setter mengubah nilai sebuah attribute class. Sebagai tambahan, method setter harus ada parameternya
```

### 13. Apa itu encapsulation?

Jawaban:

```text
Encapsulation ialah konsep atau paradigma dalam OOP yang digunakan untuk membungkus data atau method dalam satu class. Berguna untuk membatasi akses langsung ke data atau attribute tersebut menggunakan access modifier private
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
- Kalau List merupakan tipe data non-primitive atau reference type yang digunakan untuk menyimpan lebih dari 1 data berdasarkan tipe data yang diizinkan
- Sedangkan List merupakan tipe data non-primitive atau reference type untuk menyimpan lebih dari 1 data. Bedanya dengan List ialah, Map itu isinya berbentuk "key-value". Sehingga untuk mengakses value suatu data, kita bisa memanggil key-nya
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
Karena customerStorage di CustomerService digunakan untuk menyimpan list data customer denggan bentuk Map karena ada key-value di mana id sebagai key dan data Customer sebagai value. Dan data Customer di customerStorage bisa banyak, jadi ditaro di dalam tipe data Map
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
Karena fungsi tersebut digunakan untuk mengembalikan/mendapatkan "semua" data Customer. Disimpan di dalam List karena akan ada banyak data yang akan di-return/dikembalikan
```

### 17. Apa itu interface?

Jawaban:

```text
Interface ialah paradigma OOP yang berperan sebagai kontrak untuk mendefinisikan method tanpa perlu mendefinisikan isi method-nya. Interface digunakan untuk diimplementasikan ke sebuah Class dan Class yang mengimplementasikan harus membuat semua method yang ada di dalam Interface tersebut
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Perbedaannya Interface dan Abstract Class ialah:
1. Interface hanya berisi deklarasi method (tanpa isi). Di Interface, semua method by default akan public abstract
2. Sedangkan Abstract Class bisa memiliki method abstract dan juga method dengan implementasi berisi kode seperti pada umumnya
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
- createCustomer berisikan parameter String nama dan detail data yang lain
- Lalu di-instance menjadi object Customer menggunakan constructor dengan id-nya diisi menggunakan atribut sequence di CustomerService
- Tambahkan object customer baru tersebut ke customerStorage
- Tambah nilai sequence (dengan menggunakan `sequence++`) agar sequence tetap bersifat unique (tidak sama dengan nilai object Customer sebelumnya)
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
Tidak terlalu sulit, tapi lumayan challenging. Yakni di bagian ketika looping untuk print semua data customer
```
