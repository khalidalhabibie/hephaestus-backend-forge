# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
Variable adalah suatu tempat untuk menyimpan suatu nilai tertentu. Misalnya, kita ingin menyimpan data tinggi badan orang, maka kita membuat variable tinggiBadan.
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
String -> text
int -> bilangan bulat max 4 byte (32 bit) 
Long -> bilangan bulat max 8 byte (64 bit) 
boolean -> untuk menampung value true atau false
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
Agar codenya lebih readable, konsisten, dan standar di seluruh proyek.
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah blueprint nya, sedangkan Object adalah penggunaan dari blueprint tersebut. Misalnya: saat kita ingin membeli rumah, biasanya ada rumah contoh yang menggambarkan isian dari rumah tersebut. Maka rumah contoh tsb adalah Class dan rumah yang kita beli adalah Object.
```

### 5. Apa itu field?

Jawaban:

```text
Field adalah variabel yang ada di dalam suatu class atau object.
```

### 6. Apa itu method?

Jawaban:

```text
Method adalah semacam behavior atau aktivitas yang bisa dilakukan.
Biasanya logic untuk melakukan suatu aktivitas disimpan dalam suatu method sehingga ketika
kita ingin menjalankan logic tsb, kita hanya perlu memanggil methodnya.
```

### 7. Apa itu parameter?

Jawaban:

```text
Parameter adalah data yang dibutuhkan untuk menjalankan suatu method.
Contohnya ketika ada method untuk melakukan kalkulasi Debt Burden Ratio (DBR),
maka kita membutuhkan data penghasilan dan data utang dari orang tsb sehingga
method calculateDbr membutuhkan parameter penghasilanBulanan dan utangBerjalan.
```

### 8. Apa itu return value?

Jawaban:

```text
Return value adalah nilai yang dikembalikan dari suatu method. Jadi ketika method tsb dijalankan, maka akan ada hasil yang dikembalikan. Contohnya untuk method calculateDbr maka return nya adalah hasil kalkulasinya (Dbr).
```

### 9. Apa fungsi constructor?

Jawaban:

```text
Constructor adalah hal-hal yang mengkonstruk atau membangun suatu class.
Jadi isinya adalah variabel-variabel dari suatu class beserta tipe datanya.
Contoh: public class Customer {
    String name,
    int age,
    char gender
}
Maka untuk membentuk suatu object dari class Customer, kita membutuhkan variable name,
age, dan gender.
```

### 10. Apa fungsi `this`?

Jawaban:

```text
Untuk mendeklarasikan variabel atau method milik objek itu sendiri meskipun ada nama variabel yang sama dengan parameter.
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
Agar field tsb hanya bisa diakses oleh class tsb.
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
Getter digunakan untuk mengakses variabel2 yang private.
Jadi apabila kita ingin mengakses variabel yang private, kita bisa memanggil fungsi .getVariabel nya saja.
```

### 13. Apa itu encapsulation?

Jawaban:

```text
Encapsulation adalah pembungkusan sekumpulan variabel dan method dalam suatu class sebagai suatu kesatuan yang bisa diatur aksesnya.
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
List digunakan apabila ingin menampung sekumpulan data tunggal saja,
sedangkan Map digunakan untuk menampung sekumpulan data yeng memiliki pasangan key-value. 
Misalnya apabila kita ingin menyimpan data nama saja maka bisa membuat List Nama,
sedangkan apabila ingin menyimpan data customer maka kita membuat Map Customer yang berisi
cust1: {
    'name': 'Edith'
    'height': 158
}

cust2: {
    'name': 'Kayla'
    'height': 157
}

```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
Untuk mendapatkan data Customer (value) berdasarkan id nya (key).
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
Karena getAllCustomers akan me-return semua data object customer yang ada di customerStorage pada class CustomerService.
```

### 17. Apa itu interface?

Jawaban:

```text
Interface adalah suatu template behavior yang bisa di-implement oleh class lain.
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Interface hanya berisi kontrak (method tanpa logic di dalamnya) dan semua interface dalam class harus di-implement ketika melakukan inheritance dari class tsb, sedangkan abstract class bisa punya method dengan dan tanpa implementasi.
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
kita membuat object dari class Customer untuk memanggil constructor Customer, dan melakukan deklarasi variabel sequence yang di-increment. kemudian data-data object tsb disimpan ke dalam map customerStorage dan return nya berupa object customer juga.
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
Melakukan coding untuk logic-logicnya.
```
