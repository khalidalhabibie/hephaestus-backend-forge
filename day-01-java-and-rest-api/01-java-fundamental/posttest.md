# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text

Variable adalah tempat untuk menyimpan data sementara di dalam program. Setiap variable memiliki nama, tipe data, dan nilai

```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text

String digunakan untuk menyimpan teks, contohnya fullName, email, dan phoneNumber.

int digunakan untuk menyimpan angka bilangan bulat dengan ukuran standar, misalnya 10 atau 100.

Long digunakan untuk menyimpan angka bilangan bulat dengan kapasitas lebih besar daripada int.
Contohnya pada Customer:
private Long id;

boolean digunakan untuk menyimpan nilai benar atau salah, yaitu true atau false.

```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
Java menggunakan camelCase untuk variable agar nama variable lebih mudah dibaca dan mengikuti konvensi penulisan Java.
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text

Class adalah blueprint atau cetakan untuk membuat object.
Object adalah hasil nyata dari class tersebut.
```

### 5. Apa itu field?

Jawaban:

```text

ield adalah variable yang berada di dalam class dan digunakan untuk menyimpan data atau state dari object.
```

### 6. Apa itu method?

Jawaban:

```text

Method adalah fungsi atau aksi yang dimiliki oleh sebuah class. Method digunakan untuk menjalankan proses tertentu.

```

### 7. Apa itu parameter?

Jawaban:

```text
Parameter adalah nilai input yang dikirim ke dalam method atau constructor agar bisa diproses.
```

### 8. Apa itu return value?

Jawaban:

```text
eturn value adalah nilai yang dikembalikan oleh sebuah method setelah method selesai dijalankan.
```

### 9. Apa fungsi constructor?

Jawaban:

```text
Constructor berfungsi untuk membuat object baru dan mengisi nilai awal pada field object tersebut
```

### 10. Apa fungsi `this`?

Jawaban:

```text
this digunakan untuk menunjuk field atau method milik object saat ini.
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
Field dibuat private agar data di dalam class tidak bisa diakses atau diubah langsung dari luar class.
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text

Getter berfungsi untuk mengambil nilai dari field private.

Setter berfungsi untuk mengubah nilai dari field private.

```

### 13. Apa itu encapsulation?

Jawaban:

```text
Encapsulation adalah konsep membungkus data dan method di dalam sebuah class, serta membatasi akses langsung ke data tersebut.
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text

List adalah struktur data yang menyimpan kumpulan data secara berurutan.
Data di dalam List diakses berdasarkan index.

Map adalah struktur data yang menyimpan data dalam bentuk pasangan key dan value.
Data di dalam Map diakses menggunakan key

```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text

CustomerService menggunakan Map<Long, Customer> karena setiap Customer memiliki id bertipe Long. Dengan Map, customer bisa disimpan menggunakan id sebagai key. Hal ini membuat proses mencari customer berdasarkan id menjadi lebih mudah dan cepat.

```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
getAllCustomers mengembalikan List<Customer> karena method ini bertujuan mengambil semua data customer dalam bentuk kumpulan data.
```

### 17. Apa itu interface?

Jawaban:

```text
Interface adalah kontrak atau aturan yang berisi method yang harus dibuat oleh class yang mengimplementasikannya.
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text

Interface berisi kontrak method yang harus diimplementasikan oleh class. Satu class bisa mengimplementasikan lebih dari satu interface.


Perbedaannya:
Interface lebih fokus pada aturan atau kontrak.
Abstract class lebih cocok digunakan sebagai class dasar yang masih memiliki sebagian implementasi.


```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text

1. Method createCustomer dipanggil dengan parameter fullName, email, dan phoneNumber.

2. Program mengecek apakah fullName bernilai null atau kosong.
   Jika fullName kosong, maka program akan melempar error:
   IllegalArgumentException("Full name tidak boleh kosong")

3. Jika fullName valid, program membuat object Customer baru:
   Customer customer = new Customer(sequence, fullName, email, phoneNumber);

4. Field id Customer diisi menggunakan nilai sequence.

5. Object customer disimpan ke dalam customerStorage:
   customerStorage.put(sequence, customer);

6. Nilai sequence ditambah 1 agar id customer berikutnya berbeda.

7. Method mengembalikan object customer yang baru dibuat.

```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
Bagian yang paling sulit adalah memahami hubungan antara class, object, field, method, constructor, serta penggunaan Map pada CustomerService.
```

