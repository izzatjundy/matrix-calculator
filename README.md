# Tubes 1 Algeo — Kopi Jawa

Tugas Besar 1 mata kuliah **IF2132 Aljabar Linear dan Geometri** — Institut Teknologi Bandung.

Program ini adalah **kalkulator matriks berbasis CLI (Command-Line Interface)** yang menerapkan konsep matriks dan Sistem Persamaan Linear (SPL) untuk menyelesaikan berbagai persoalan numerik.

---

## Anggota Kelompok

| Nama | NIM |
|------|-----|
| Mayla Yaffa Ludmilla | 13523050 |
| Diyah Susan Nugrahani | 13523080 |
| Muhammad Izzat Jundy | 13523092 |

---

## Fitur

Program ini memiliki **6 menu utama**:

### 1. Sistem Persamaan Linear (SPL)
Menyelesaikan SPL dengan empat metode:
- **Eliminasi Gauss** — reduksi ke bentuk eselon baris, lalu substitusi balik
- **Eliminasi Gauss-Jordan** — reduksi ke bentuk eselon baris tereduksi (RREF)
- **Metode Invers** — solusi menggunakan $A^{-1}b$
- **Kaidah Cramer** — solusi menggunakan rasio determinan

Program mendeteksi secara otomatis apakah SPL memiliki tidak ada solusi, solusi unik, atau tak hingga banyak solusi (dengan variabel bebas dalam bentuk parameter $t_i$).

### 2. Determinan
Menghitung determinan matriks persegi dengan dua metode:
- **Reduksi baris (OBE)** — triangularisasi atas, lalu kalikan diagonal
- **Ekspansi kofaktor** — rekursi sepanjang baris pertama

### 3. Matriks Balikan (Invers)
Menghitung invers matriks dengan dua metode:
- **Metode Gauss-Jordan** — augmentasi $[A \mid I]$, reduksi penuh
- **Metode Adjoin** — $A^{-1} = \frac{1}{\det(A)} \cdot \text{adj}(A)$

### 4. Interpolasi Polinomial
Mencari polinomial derajat $n{-}1$ yang melalui $n$ titik data yang diberikan menggunakan **sistem Vandermonde** yang diselesaikan dengan SPL, lalu mengevaluasi hasilnya di titik $x$ yang ditentukan.

### 5. Interpolasi Bicubic Spline
Melakukan interpolasi bicubic pada persegi satuan. Diberikan nilai $f$, $f_x$, $f_y$, dan $f_{xy}$ di empat sudut, program membangun dan menginvers sistem $16 \times 16$ untuk mendapatkan koefisien $a_{ij}$, lalu mengevaluasi $f(x,y) = \sum a_{ij} x^i y^j$.

### 6. Regresi Linear dan Kuadratik Berganda
- **Regresi Linear Berganda** — model $f = b_0 + b_1 x_1 + \cdots + b_k x_k$ menggunakan persamaan normal $X^T X \mathbf{b} = X^T \mathbf{y}$
- **Regresi Kuadratik Berganda** — model orde-2 dengan suku kuadrat dan silang, seperti $b_{ij} x_i x_j$

---

## Struktur Proyek

```
Algeo01-23050/
├── src/
│   ├── Main.java                  # Entry point & menu utama
│   ├── ADTMatrix/
│   │   └── Matrix.java            # Struktur data matriks & operasi dasar
│   ├── Function/
│   │   ├── SPL.java               # Penyelesaian Sistem Persamaan Linear
│   │   ├── Determinan.java        # Perhitungan determinan
│   │   ├── Invers.java            # Pencarian matriks invers
│   │   ├── Interpolasi.java       # Interpolasi polinomial
│   │   ├── Bicubic.java           # Interpolasi bicubic spline
│   │   ├── RegresiLinear.java     # Regresi linear berganda
│   │   └── RegresiKuadratik.java  # Regresi kuadratik berganda
│   └── IO/
│       ├── Input.java             # Parsing input (keyboard & file)
│       └── Output.java            # Pemformatan output (layar & file)
├── bin/                           # File .class hasil kompilasi
├── test/
│   ├── Input/                     # File input pengujian (.txt)
│   └── Output/                    # File output hasil pengujian
└── doc/                           # Dokumentasi
```

---

## Cara Menjalankan

### Prasyarat
- **Java JDK 11** atau lebih baru

### Clone Repository
```bash
git clone https://github.com/izzatjundy/Algeo01-23050.git
cd Algeo01-23050
```

### Kompilasi
```bash
javac -d bin src/ADTMatrix/Matrix.java src/IO/Input.java src/IO/Output.java src/Function/*.java src/Main.java
```

### Jalankan
```bash
cd bin
java Main
```

---

## Format Input File

File input disimpan di `test/Input/` dengan format teks biasa (`.txt`).

- **SPL / Matriks** — Setiap baris adalah satu baris matriks dengan elemen dipisahkan spasi. Untuk SPL, kolom terakhir adalah vektor konstanta $b$.
  ```
  2 1 -1 8
  -3 -1 2 -11
  -2 1 2 -3
  ```

- **Interpolasi Polinomial** — Baris pertama adalah jumlah titik $n$, diikuti $n$ baris berisi pasangan $x\ y$, dan baris terakhir adalah nilai $x$ yang dicari.
  ```
  3
  1 1
  2 4
  3 9
  2.5
  ```

- **Bicubic Spline** — Tabel $4 \times 4$ nilai batas (baris untuk $f$, $f_x$, $f_y$, $f_{xy}$ masing-masing di empat sudut), diikuti target $x$ dan $y$.

- **Regresi** — Baris pertama berisi jumlah variabel dan jumlah sampel, diikuti baris-baris data. Kolom terakhir adalah nilai $y$ (variabel dependen).

---

## Bahasa Pemrograman
Java
