# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
variable adalah sebuah nilai yang bisa kita set dan berubah ubah sesuai dengan kebutuhan kita
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
String = untuk menyimpang kalimat
int = untuk menyimpan angka
boolean = untuk menyimpan true/false
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
karena camelCase mempermudah pembacaan variable yang dimana hal ini sudah menjadi standar penulisan di java
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
class adalah template untuk membuat objek
object adalah instance dari class yang dibuat
```

### 5. Apa itu field?

Jawaban:

```text
field adalah attribute dari objek itu sendiri
```

### 6. Apa itu method?

Jawaban:

```text
method adalah fungsi di dalam class yang berisi kumpulan perintah yang akan dijalankan
```

### 7. Apa itu parameter?

Jawaban:

```text
parameter adalah nilai input yang akan kita gunakan ke dalam method/functions
```

### 8. Apa itu return value?

Jawaban:

```text
return value adalah nilai yang dikembalikan oleh method/function
```

### 9. Apa fungsi constructor?

Jawaban:

```text
constructor digunakan untuk menginisialisasi object saat pertama kali dibuat
```

### 10. Apa fungsi `this`?

Jawaban:

```text
this merujuk kepada object yang dibuat saat ini, biasanya untuk membedakan antara field dan parameter method
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
agar lebih aman dan terhindar dari diakses langsung dari luar class
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
getter digunakan untuk mengambil nilai dari field
setter digunakan untuk mengsett nilai dari field
```

### 13. Apa itu encapsulation?

Jawaban:

```text
konsep untuk mengatur data dan hanya memberikan akses melalui method tertentu
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
List menyimpan data secara berurutan
Map menyimpan data dalam bentuk dengan key dan value
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
karena map memudahkan pencarian customer berdasarkan key yang unik
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
karena list memudahkan untuk menampilkan semua data customer secara berurutan
```

### 17. Apa itu interface?

Jawaban:

```text
interface adalah kumpulan method tanpa implementasi yang harus diimplementasikan oleh class yang menggunakannya (semuanya harus dipakai tanpa terkecuali)
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
interface hanya berisi deklarasi method tanpa implementasi
abstract class bisa punya method dengan implementasi maupun tanpa implementasi
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
user memanggil method createCustomer
data customer dibuat menjadi object Customer
object tersebut disimpan ke dalam Map di CustomerService
id (sequence) digunakan sebagai key untuk menyimpan data
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
bagian saat membuat create customer karena saya lupa untuk menambahkan nilai sequencenya, dan saya baru mengerti ternyata sequence tinggal di set menjadi sequence++
```
