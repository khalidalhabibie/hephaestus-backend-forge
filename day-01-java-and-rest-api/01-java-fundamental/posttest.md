# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:

```text
Variable adalah deklarasi dalam Java, contohnya adalah String name. name itu variablenya
```

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:

```text
String itu untuk tipe text (cth. String name), Long itu untuk tipe number, boolean adalah true/false.
```

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:

```text
camelCase seperti namaNama.java atau String namaNama karena memang udah hatamnya. Kalau python dia snakecase
```

### 4. Apa perbedaan class dan object?

Jawaban:

```text
Class itu kayak nama file nya di Java. cth. public class namaClass {}.
```

### 5. Apa itu field?

Jawaban:

```text
Field itu propertri di Java, kayak String nama
```

### 6. Apa itu method?

Jawaban:

```text
Function di Java, kayak  public void bacaRead(){}
```

### 7. Apa itu parameter?

Jawaban:

```text
Isian () di method.
```

### 8. Apa itu return value?

Jawaban:

```text
Nilai kembalian method
```

### 9. Apa fungsi constructor?

Jawaban:

```text
Membuat object baru
```

### 10. Apa fungsi `this`?

Jawaban:

```text
Untuk mengenali field milik kelas itu sendiri.
```

### 11. Kenapa field dibuat private?

Jawaban:

```text
Untuk menjaga keamanan field tersebut sebagai access modifier
```

### 12. Apa fungsi getter dan setter?

Jawaban:

```text
Metode untuk membaca dan mengubah nilai field yang di-private
```

### 13. Apa itu encapsulation?

Jawaban:

```text
Access modifier yang ada seperti private, public, protected.
```

### 14. Apa perbedaan List dan Map?

Jawaban:

```text
List itu sebuah collection, map itu dictionary yang melakukan store data dengan key dan value.
```

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:

```text
Karena kita membutuhkan set key terhadap Long dan set Customer untuk menjadikan value dan kita bisa mengakses value customer berdasarkan index key
```

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:

```text
karena getAllCustomers menggunakan return customer.
```

### 17. Apa itu interface?

Jawaban:

```text
Kelas yang isinya method template tanpa isian/operasi methodnya.
```

### 18. Apa perbedaan interface dan abstract class?

Jawaban:

```text
interface itu isinya method tanpa isiannya, kecuali pake default. abstract class itu kelas yang benmtuk nya tidak menentu dan bisa menerapkan kontrak terhadap kelas yang dieksten
```

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:

```text
public Customer createCustomer(String fullName, String email, String phoneNumber) {
        Customer customer = new Customer(sequence, fullName, email, phoneNumber);
        Long id = sequence;
        sequence++;
        customerStorage.put(id, customer);
        return customer;

membuat object customer dengan parimeter seq, fullName, email, phoneNumber. declare Long id = sequence, sequence +1, return customer.
```

### 20. Bagian mana yang paling sulit?

Jawaban:

```text
Abstract, interface
```
