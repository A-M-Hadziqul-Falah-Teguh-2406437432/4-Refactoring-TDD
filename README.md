## TDD Reflection

### 1) Refleksi terhadap alur TDD (berdasarkan pertanyaan reflektif Percival, 2017)

Menurut saya, alur TDD pada tutorial ini **cukup berguna** karena memberi arah kerja yang jelas: mulai dari menulis test, melihat test gagal, lalu menulis implementasi minimum sampai test lulus. Pola ini membantu saya fokus pada behavior yang memang dibutuhkan, bukan asal menebak-nebak desain dari awal.

Namun, alur ini belum sepenuhnya cukup jika hanya berhenti pada “test lulus”. Untuk iterasi berikutnya, saya perlu:

- Menambah variasi kasus edge-case (data null, input kosong, duplikasi data, dan ID tidak ditemukan) secara lebih sistematis.
- Melakukan refactor test setelah hijau agar test tetap mudah dibaca dan tidak duplikatif.
- Meninjau kualitas assertion agar tidak hanya memeriksa output akhir, tetapi juga interaksi penting (mis. verifikasi pemanggilan repository).
- Menjalankan test secara rutin setelah tiap perubahan kecil supaya sumber error lebih cepat terdeteksi.

### 2) Refleksi penerapan prinsip F.I.R.S.T pada unit test

Secara umum, test yang dibuat sudah mengarah ke prinsip **F.I.R.S.T**, tetapi masih ada ruang perbaikan:

- **Fast**: Unit test berbasis mock berjalan cepat, tetapi tetap perlu disiplin memisahkan unit test dari functional test agar feedback loop tetap singkat.
- **Independent**: Sebagian besar test sudah independen karena data disiapkan ulang di `@BeforeEach`, tetapi perlu dijaga agar tidak ada ketergantungan urutan eksekusi.
- **Repeatable**: Test repeatable karena tidak tergantung jaringan/DB eksternal, namun konsistensi environment (versi JDK/Gradle) tetap perlu dijaga.
- **Self-validating**: Test sudah self-validating karena memakai assertion dan verifikasi yang jelas (pass/fail otomatis).
- **Timely**: Test sudah ditulis sebelum/bersamaan implementasi pada banyak langkah, tetapi ke depan perlu lebih konsisten menulis test lebih dulu untuk semua perubahan behavior.

Perbaikan berikutnya saat membuat test tambahan adalah memastikan setiap test tetap kecil, satu tujuan per test, nama method lebih deskriptif terhadap skenario, serta memperluas cakupan kasus negatif tanpa membuat test menjadi rapuh.
