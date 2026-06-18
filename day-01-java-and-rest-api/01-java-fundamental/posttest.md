# Posttest - Java Fundamental

Jawab pertanyaan berikut setelah membaca materi dan mengerjakan exercise Java Fundamental.

### 1. Apa itu variable?

Jawaban:
variabel seperti wadah yang dapat diisi dengan nilai/value yang dapat digunakan dan diubah

### 2. Apa perbedaan String, int, Long, dan boolean?

Jawaban:
- String untuk menyimpan teks bisa berupa angka, huruf, simbol
- int untuk menyimpan bilangan bulat
- Long untuk menyimpan bilangan bulat yang lebih panjang atau kapasitas yang besar
- boolean hany abisa untuk value true atau false saja

### 3. Kenapa Java menggunakan camelCase untuk variable?

Jawaban:
untuk dapat mudah dibaca atau readability

### 4. Apa perbedaan class dan object?

Jawaban:
- class itu seperti desain yang akan kita gunakan untuk objek atau blueprint
- objek itu hasil dari class yang dapat digunakan (asli/nyata dan bisa digunakan)

### 5. Apa itu field?

Jawaban:
variabel yang dideklarasikan di dalam class tapi diluar method/function ataupun constructur (biasanya ada dibagian atas)

### 6. Apa itu method?

Jawaban:
function yang dibuat untuk menjalankan sesuatu sesuai dengan perintah yang dibuat/diinginkan

### 7. Apa itu parameter?

Jawaban:
variabel yang dideklarasikan di method yang dimana fungsinya untuk menerima value saat method/function dipanggil

### 8. Apa itu return value?

Jawaban:
value yang dikembalikan ke dalam method setelah method tersebut dijalankan/dilakukan

### 9. Apa fungsi constructor?

Jawaban:
untuk menginisialisasi field field objek yang sudah dideklarasikan sebelumnya yaitu dengan memberikan nilai pada field

### 10. Apa fungsi `this`?

Jawaban:
untuk merujuk ke objek yang sedang berjalan
- menghindari ambigu antara property objek dan parameter
- mengakses data & method milik objek

### 11. Kenapa field dibuat private?

Jawaban:
karena enkapsulasi yang dimana untuk melindungi data objek agar tidak dapat diakses sembarangan dari luar class, jika ingin menggunakan field tersebut bisa menggunakan setter getter

### 12. Apa fungsi getter dan setter?

Jawaban:
untuk mengambil (get) dan mengubah (set) nilai dari field yang dibuat private.

### 13. Apa itu encapsulation?

Jawaban:
konsep OOP yang menyembunyikan data (field) dan hanya memberikan akses melalui method tertentu dengan menggunakan getter dan setter

### 14. Apa perbedaan List dan Map?

Jawaban:
- list adalah kumpulan data yang tersusun berurutan (berdasarkan index)
- map adalah kumpulan data berbentuk key dan value (berpasangan)

### 15. Kenapa CustomerService menggunakan Map<Long, Customer>?

Jawaban:
karena untuk menyimpan data customer berdasarkan ID unik (key) agar akses data lebih cepat dan terstruktur

### 16. Kenapa getAllCustomers mengembalikan List<Customer>?

Jawaban:
karena untuk mengambil seluruh data customer dalam bentuk urutan, bukan berdasarkan key

### 17. Apa itu interface?

Jawaban:
bisa dibilang kontrak, yaitu berisi kumpulan method tanpa isi (abstrak) yang harus diimplementasikan oleh class lain

### 18. Apa perbedaan interface dan abstract class?

Jawaban:
- interface class adalah kontrak (apa yang harus dilakukan)
- abstract class adalah class yang bisa dibilang sudah cukup lengkap yang memiliki logic dan kontrak atau interface (kerangka)

### 19. Dari exercise, jelaskan flow createCustomer.

Jawaban:
1. method createCustomer() dipanggil dengan input nama, email, dan nomor telepon
2. program masuk ke blok try untuk menjalankan proses dengan aman
3. program mengecek apakah fullName kosong atau tidak
4. jika nama kosong atau hanya spasi, program akan:
    - menampilkan pesan "Nama harus ada isi"
    - menghentikan proses
    - mengembalikan nilai null
5. jika nama valid (tidak kosong), program lanjut ke langkah berikutnya
6. program membuat object Customer baru dengan:
    - ID dari sequence
    - nama, email, dan nomor telepon dari input
7. object Customer tersebut kemudian disimpan ke dalam customerStorage (HashMap) dengan key berupa ID
8. setelah disimpan, nilai sequence ditambah 1 agar ID berikutnya berbeda
9. method mengembalikan object Customer yang sudah berhasil dibuat

### 20. Bagian mana yang paling sulit?

Jawaban:
Saat membuat file Main karena ada beberapa logic yang saya tidak tahu dan ada beberapa syntx yang saya tidak pernah menggunakannya