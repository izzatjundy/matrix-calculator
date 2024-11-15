package Function;

import ADTMatrix.Matrix;
import IO.Output;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class RegresiKuadratik {


    public static String[] regresi_kuadrat_berganda(double[][] data){
        int n, row, col, i, j, k, banyakData, p;
        double y;
        String[] solusi, hasil;

        // membuat sigma
        n = data[0].length - 1;
        banyakData = data.length - 1;

        double[] sigma = new double[n+1], parameter = new double[n];

        i = 0;
        while(i<n+1){

            sigma[i] = data[0][i];

            j = 1;
            while(j<banyakData){

                sigma[i] += data[j][i];

                j+=1;
            }

            i+=1;
        }

        // membuat matrix
        row = 1 + 2 * n + (n * (n-1)) / 2;
        col = row + 1;
        Matrix m = new Matrix();
        m.createMatrix(row, col);
        i = 0;
        while (i<row) {
            j = 0;
            while(j<col){
                m.matrix[i][j] = 0;
                j+=1;
            }
            i+=1;
        }

        /*
        // naro anu
        i = 1;
        j = 0;
        k = 0;
        while(i<col-1){

            if(i<=n){
                m.matrix[0][i] = sigma[i-1];
            }else{

                m.matrix[0][i] = sigma[j] * sigma[k];
                
                if(k >= n){
                    k = j;
                    j+=1;
                }

                k+=1;
            }

            
            i+=1;
        }*/
        Matrix asoy = m.copy();

        p = 0;
        while(p<data.length-1){

            i = 1;
            j = 0;
            k = 1;
            while(i<col-1){
            
                if(i<=n){
                
                    m.matrix[0][i] = data[p][i-1];
                
                }else if(i<=2*n){
                
                    m.matrix[0][i] = data[p][(i-1) % n] * data[p][(i-1) % n];
                
                }else{
                
                    m.matrix[0][i] = data[p][j] * data[p][k];
                
                    k+=1;

                    if(k >= n){
                        j+=1;
                        k = j+1;
                    }
                }
            
                i+=1;
            }
        
            m.matrix[0][i] = data[p][data[p].length - 1];
        
            i = 1;
            while(i<row){
            
                m.matrix[i][0] = m.matrix[0][i];
            
                i+=1;
            }
        
            m.matrix[0][0] = row-1;

            Output.printMatrix(m);
        
            i = 1;
            while(i<row){
            
                j = 1;
                while(j<col){
                
                    m.matrix[i][j] = m.matrix[0][j] * m.matrix[i][0];
                
                    j+=1;
                }
            
                i+=1;
            }

            asoy = Matrix.sumMatrix(asoy.copy(), m);

            p+=1;
        }

        asoy.matrix[0][0] = n-1;

        i = 0;
        while(i<row){
        
            asoy.matrix[i][0] = 1;
        
            i+=1;
        }

        Output.printMatrix(asoy.copy());

        solusi = SPL.solve(asoy.copy());

        // Nyiapin parameter buat dicari y-nya
        i = 0;
        while(i<n){
            parameter[i] = data[data.length - 1][i];
            i+=1;
        }

        y = 0;
        hasil = new String[1+n+1];

        hasil[0] = "f(";
        i = 0;
        while(i < n){

            hasil[0] += "x_{" + String.valueOf(i+1) + "}";

            i+=1;
        }
        hasil[0] += ") = ";

        if(solusi.length == 0) return hasil;

        hasil[0] += solusi[0];
        y += Double.valueOf(solusi[0]);

        i = 1;
        j = 0;
        k = 1;
        while(i<col-1){

            if(i<=n){

                hasil[0] += " + " + solusi[i] + "x_{" + String.valueOf(i) + "}";
                y+= (Double.valueOf(solusi[i]) + parameter[i-1]);

            }else if(i<=2*n){

                hasil[0] += " + " + solusi[i] + "x_{" + String.valueOf(i-n) + "}^2";
                y+= (Double.valueOf(solusi[i]) + parameter[i-n-1] * parameter[i-n-1]);

            }else{

                hasil[0] += " + " + solusi[i] + "x_{" + String.valueOf(j+1) + "}" + "x_{" + String.valueOf(k+1) + "}";
                y+= (Double.valueOf(solusi[i]) + parameter[j] * parameter[k]);

                k+=1;

                if(k >= n){
                    j+=1;
                    k = j+1;
                }
            }

            i+=1;
        }

        i = 1;
        while(i<=n){

            hasil[i] = String.format("%.4f", data[row-1][i-1]);

            i+=1;
        }

        hasil[i] = String.format("%.4f", y);
        //System.out.println("Ini hasilnya cuy "+y);

        return hasil;
    }

    public static void RegresiKuadratikKeyboard(Matrix m) {
        Scanner scanner = new Scanner(System.in);
        int i;
        double result = 0;
        double sum = 0;
        Matrix mTemp;
        double[] x;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));

        // Memperluas array untuk menampung nilai kuadrat
        x = new double[m.getColLength() - 1]; // Ambil panjang kolom dikurangi 1 untuk menampung variabel x

        // Input nilai-nilai x yang ingin ditaksir
        System.out.println("Masukkan nilai x yang ingin ditaksir: ");
        for (i = 0; i < x.length; i++) {
            x[i] = scanner.nextDouble();
        }

        // Membuat matriks SPL untuk persamaan kuadratik
        mTemp = createSPLKuadratik(m);

        // // Melakukan Eliminasi Gauss
        // mTemp = mTemp.gaussElimination();

        // mengubah matrix mnejadi segitiga atas
        int j,k;
        for (i=0; i < mTemp.row; i++){
            //jika elemen diagonal 0, cari baris bawahnya untuk ditukar
            if(mTemp.matrix[i][i] == 0){
                j = i + 1;
                while (j<mTemp.row && mTemp.matrix[j][i] == 0){
                    j++;
                }

                if (j<mTemp.row){
                    //tukar baris
                    for (k=0; k<mTemp.col; k++){
                        double temp = mTemp.matrix[i][k];
                        mTemp.matrix[i][k] = mTemp.matrix[j][k];
                        mTemp.matrix[j][k] = temp;
                    }
                }
                // else{
                //     return 0; //jika seluruh kolom di bawah diagonal 0, determinan = 0
                // }
            }

            //eliminasi untuk elemen di bawah diagonal
            for (j=i+1; j<mTemp.row; j++){
                double factor = mTemp.matrix[j][i] / mTemp.matrix[i][i];
                for (k=i; k<mTemp.col; k++){
                    mTemp.matrix[j][k] -= factor * mTemp.matrix[i][k];
                }
            }
        }

        double[] m1 = new double[mTemp.getRowLength()];
        Matrix.backSubstitution(mTemp, m1);

        // Menampilkan persamaan kuadratik
        System.out.print("f(x1, x2) = ");
        for (i = 0; i < m1.length; i++) {
            if (i == 0) {
                // a0 (konstanta)
                result = m1[i];
                if (m1[i] > 0) {
                    System.out.printf("%.4f ", m1[i]);
                } else {
                    System.out.printf("- %.4f ", Math.abs(m1[i]));
                }
            } else if (i == 1) {
                // a1 * x1 (linear untuk x1)
                result = m1[i] * x[0];  // x[0] adalah x1
                if (m1[i] > 0) {
                    System.out.printf("+ %.4fx1 ", m1[i]);
                } else {
                    System.out.printf("- %.4fx1 ", Math.abs(m1[i]));
                }
            } else if (i == 2) {
                // a2 * x2 (linear untuk x2)
                result = m1[i] * x[1];  // x[1] adalah x2
                if (m1[i] > 0) {
                    System.out.printf("+ %.4fx2 ", m1[i]);
                } else {
                    System.out.printf("- %.4fx2 ", Math.abs(m1[i]));
                }
            } else if (i == 3) {
                // a3 * x1^2 (kuadratik untuk x1)
                result = m1[i] * x[0] * x[0];
                if (m1[i] > 0) {
                    System.out.printf("+ %.4fx1^2 ", m1[i]);
                } else {
                    System.out.printf("- %.4fx1^2 ", Math.abs(m1[i]));
                }
            } else if (i == 4) {
                // a4 * x2^2 (kuadratik untuk x2)
                result = m1[i] * x[1] * x[1];
                if (m1[i] > 0) {
                    System.out.printf("+ %.4fx2^2 ", m1[i]);
                } else {
                    System.out.printf("- %.4fx2^2 ", Math.abs(m1[i]));
                }
            } else if (i == 5) {
                // a5 * x1 * x2 (interaksi antara x1 dan x2)
                result = m1[i] * x[0] * x[1];
                if (m1[i] > 0) {
                    System.out.printf("+ %.4fx1x2 ", m1[i]);
                } else {
                    System.out.printf("- %.4fx1x2 ", Math.abs(m1[i]));
                }
            }
            sum += result;  // Menambahkan hasil tiap iterasi ke sum
        }
        System.out.printf(" f(xk) = %.4f\n", sum);


        // Output
        int opsi = Output.opsiOutput();
        if (opsi == 1) {
            // Mencetak output ke dalam bentuk file
            String nameFile = "";
            System.out.println("Masukkan nama file: ");
            try {
                nameFile = inputFile.readLine();
                String path = "test/Output/" + nameFile;

                // Cek apakah file sudah ada
                File file = new File(path);
                if (file.exists()) {
                    System.out.println("File sudah ada. Apakah Anda ingin menimpanya? (y/n)");
                    char choice = scanner.next().charAt(0);
                    if (choice != 'y' && choice != 'Y') {
                        System.out.println("Output dibatalkan.");
                        return; // Jika tidak memilih 'y', batalkan
                    }
                }

                try (FileWriter fileWriter = new FileWriter(path)) {
                    fileWriter.write("f(x) = ");
                    for (i = 0; i < m1.length; i++) {
                        if (i == 0) {
                            fileWriter.write(String.format("%.4f ", m1[i]));
                        } else if (i == 1) {
                            fileWriter.write(String.format("+ %.4fx ", m1[i]));
                        } else {
                            fileWriter.write(String.format("+ %.4fx^2 ", m1[i]));
                        }
                    }
                    fileWriter.write(String.format("\nf(xk) = %.4f\n", sum));
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }

    public static void RegresiKuadratikFile() {
        Scanner scanner = new Scanner(System.in);
        int i, j;
        double result = 0;
        double sum = 0;
        Matrix mTemp;
        Matrix m1;
        double[] x;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));

        // Membaca file 
        String fileName = "";
        System.out.println("Masukkan nama file untuk membaca data: ");
        try {
            fileName = inputFile.readLine();
            // Membaca data dari file
            FileReader fr = new FileReader("test/Input/" + fileName);
            BufferedReader br = new BufferedReader(fr);

            // Membaca jumlah baris dan kolom dari file
            int rowCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                rowCount++;
            }

            // Mengatur ulang reader untuk membaca dari awal lagi
            br.close();
            fr = new FileReader("test/Input/" + fileName);
            br = new BufferedReader(fr);

            // Membuat matriks dengan ukuran yang sesuai
            m1 = new Matrix();
            String firstLine = br.readLine();
            String[] firstLineValues = firstLine.split("\\s+"); // Misalnya, nilai dipisahkan oleh spasi
            int colCount = firstLineValues.length;
            m1.createMatrix(rowCount, colCount);

            // Membaca isi file ke dalam matriks m1
            int currentRow = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+"); // Misalnya, nilai dipisahkan oleh spasi
                for (j = 0; j < values.length; j++) {
                    m1.setElement(currentRow, j, Double.parseDouble(values[j]));
                }
                currentRow++;
            }
            br.close();
        } catch (IOException err) {
            err.printStackTrace();
            return; // Keluar jika ada kesalahan
        }

        // Membuat array x yang akan digunakan dalam regresi
        x = new double[m1.getColLength() - 1]; // Asumsi kolom terakhir adalah y
        for (i = 0; i < x.length; i++) {
            x[i] = m1.getElement(m1.getRowLength() - 1, i);
        }

        // Membuat matriks SPL untuk persamaan kuadratik
        mTemp = createSPLKuadratik(m1);

        // Melakukan eliminasi Gauss untuk mendapatkan solusi SPL
        mTemp = mTemp.gaussElimination();

        double[] m3 = new double[mTemp.getRowLength()];
        Matrix.backSubstitution(mTemp, m3);

        // Menampilkan persamaan kuadratik
        System.out.print("f(x1, x2) = ");
        for (i = 0; i < m3.length; i++) {
            if (i == 0) {
                // a0 (konstanta)
                result = m3[i];
                if (m3[i] > 0) {
                    System.out.printf("%.4f ", m3[i]);
                } else {
                    System.out.printf("- %.4f ", Math.abs(m3[i]));
                }
            } else if (i == 1) {
                // a1 * x1 (linear untuk x1)
                result = m3[i] * x[0];  // x[0] adalah x1
                if (m3[i] > 0) {
                    System.out.printf("+ %.4fx1 ", m3[i]);
                } else {
                    System.out.printf("- %.4fx1 ", Math.abs(m3[i]));
                }
            } else if (i == 2) {
                // a2 * x2 (linear untuk x2)
                result = m3[i] * x[1];  // x[1] adalah x2
                if (m3[i] > 0) {
                    System.out.printf("+ %.4fx2 ", m3[i]);
                } else {
                    System.out.printf("- %.4fx2 ", Math.abs(m3[i]));
                }
            } else if (i == 3) {
                // a3 * x1^2 (kuadratik untuk x1)
                result = m3[i] * x[0] * x[0];
                if (m3[i] > 0) {
                    System.out.printf("+ %.4fx1^2 ", m3[i]);
                } else {
                    System.out.printf("- %.4fx1^2 ", Math.abs(m3[i]));
                }
            } else if (i == 4) {
                // a4 * x2^2 (kuadratik untuk x2)
                result = m3[i] * x[1] * x[1];
                if (m3[i] > 0) {
                    System.out.printf("+ %.4fx2^2 ", m3[i]);
                } else {
                    System.out.printf("- %.4fx2^2 ", Math.abs(m3[i]));
                }
            } else if (i == 5) {
                // a5 * x1 * x2 (interaksi antara x1 dan x2)
                result = m3[i] * x[0] * x[1];
                if (m3[i] > 0) {
                    System.out.printf("+ %.4fx1x2 ", m3[i]);
                } else {
                    System.out.printf("- %.4fx1x2 ", Math.abs(m3[i]));
                }
            }
            sum += result;  // Menambahkan hasil tiap iterasi ke sum
        }
        System.out.printf(" f(xk) = %.4f\n", sum);

        // Output
        int opsi = Output.opsiOutput();
        if (opsi == 1) {
            // Mencetak output ke dalam bentuk file
            String nameFile = "";
            System.out.println("Masukkan nama file: ");
            try {
                nameFile = inputFile.readLine();
                String path = "test/Output/" + nameFile;

                // Cek apakah file sudah ada
                File file = new File(path);
                if (file.exists()) {
                    System.out.println("File sudah ada. Apakah Anda ingin menimpanya? (y/n)");
                    char choice = scanner.next().charAt(0);
                    if (choice != 'y' && choice != 'Y') {
                        System.out.println("Output dibatalkan.");
                        return; // Jika tidak memilih 'y', batalkan
                    }
                }

                try (FileWriter fileWriter = new FileWriter(path)) {
                    fileWriter.write("f(x) = ");
                    for (i = 0; i < m3.length; i++) {
                        if (i == 0) {
                            fileWriter.write(String.format("%.4f ", m3[i]));
                        } else if (i == 1) {
                            fileWriter.write(String.format("+ %.4fx ", m3[i]));
                        } else {
                            fileWriter.write(String.format("+ %.4fx^2 ", m3[i]));
                        }
                    }
                    fileWriter.write(String.format("\nf(xk) = %.4f\n", sum));
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }


    // public static void RegresiKuadratikFile(Matrix m) {
    //     Scanner scanner = new Scanner(System.in);
    //     int i, j;
    //     double result = 0;
    //     double sum = 0;
    //     Matrix mTemp;
    //     Matrix m1;
    //     double[] x;
    //     BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));

    //     //membaca file 
    //     String fileName = "";
    //     System.out.println("Masukkan nama file untuk membaca data: ");
    //     try {
    //         fileName = inputFile.readLine();
    //         // Membaca data dari file
    //         FileReader fr = new FileReader("test/Input/" + fileName);
    //         BufferedReader br = new BufferedReader(fr);
            
    //         // Membaca jumlah baris dan kolom dari file
    //         int rowCount = 0;
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             rowCount++;
    //         }

    //         // Mengatur ulang reader untuk membaca dari awal lagi
    //         br.close();
    //         fr = new FileReader("test/Input/" + fileName);
    //         br = new BufferedReader(fr);

    //         // membuat matriks dengan ukuran yang sesuai
    //         m1 = new Matrix();
    //         m1.createMatrix(rowCount, m.col); // Menggunakan jumlah baris yang dibaca dari file

    //         // membaca isi file ke dalam matriks m1
    //         int currentRow = 0;
    //         while ((line = br.readLine()) != null) {
    //             String[] values = line.split("\\s+"); // Misalnya, nilai dipisahkan oleh spasi
    //             for (j = 0; j < values.length; j++) {
    //                 m1.setElement(currentRow, j, Double.parseDouble(values[j]));
    //             }
    //             currentRow++;
    //         }
    //         br.close();
    //     } catch (IOException err) {
    //         err.printStackTrace();
    //         return; // Keluar jika ada kesalahan
    //     }
        

    //     // membuat matriks dari file yang diinput
    //     m1 = new Matrix();
    //     m1.createMatrix(m.row - 1, m.col);
    //     for(i = 0; i < m.row - 1; i++){
    //         for(j = 0; j < m.col; j++){
    //             m1.setElement(i, j, m.matrix[i][j]);
    //         }
    //     }

    //     x = new double[m.col - 1];
    //     for(i = 0; i < x.length; i++){ 
    //         x[i] = m.getElement(m.row - 1, i);
    //     }

    //     // Membuat matriks SPL untuk persamaan kuadratik
    //     mTemp = createSPLKuadratik(m);

    //     // // Melakukan Eliminasi Gauss
    //     // mTemp = mTemp.gaussElimination();

    //     // mengubah matrix mnejadi segitiga atas
    //     int jj ,k;
    //     for (i=0; i < mTemp.row; i++){
    //         //jika elemen diagonal 0, cari baris bawahnya untuk ditukar
    //         if(mTemp.matrix[i][i] == 0){
    //             jj = i + 1;
    //             while (jj<mTemp.row && mTemp.matrix[jj][i] == 0){
    //                 jj++;
    //             }

    //             if (jj<mTemp.row){
    //                 //tukar baris
    //                 for (k=0; k<mTemp.col; k++){
    //                     double temp = mTemp.matrix[i][k];
    //                     mTemp.matrix[i][k] = mTemp.matrix[jj][k];
    //                     mTemp.matrix[jj][k] = temp;
    //                 }
    //             }
    //             // else{
    //             //     return 0; //jika seluruh kolom di bawah diagonal 0, determinan = 0
    //             // }
    //         }

    //         //eliminasi untuk elemen di bawah diagonal
    //         for (jj=i+1; jj<mTemp.row; jj++){
    //             double factor = mTemp.matrix[jj][i] / mTemp.matrix[i][i];
    //             for (k=i; k<mTemp.col; k++){
    //                 mTemp.matrix[jj][k] -= factor * mTemp.matrix[i][k];
    //             }
    //         }
    //     }

    //     double[] m3 = new double[mTemp.getRowLength()];
    //     Matrix.backSubstitution(mTemp, m3);

    //     // Menampilkan persamaan kuadratik
    //     System.out.print("f(x1, x2) = ");
    //     for (i = 0; i < m3.length; i++) {
    //         if (i == 0) {
    //             // a0 (konstanta)
    //             result = m3[i];
    //             if (m3[i] > 0) {
    //                 System.out.printf("%.4f ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4f ", Math.abs(m3[i]));
    //             }
    //         } else if (i == 1) {
    //             // a1 * x1 (linear untuk x1)
    //             result = m3[i] * x[0];  // x[0] adalah x1
    //             if (m3[i] > 0) {
    //                 System.out.printf("+ %.4fx1 ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4fx1 ", Math.abs(m3[i]));
    //             }
    //         } else if (i == 2) {
    //             // a2 * x2 (linear untuk x2)
    //             result = m3[i] * x[1];  // x[1] adalah x2
    //             if (m3[i] > 0) {
    //                 System.out.printf("+ %.4fx2 ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4fx2 ", Math.abs(m3[i]));
    //             }
    //         } else if (i == 3) {
    //             // a3 * x1^2 (kuadratik untuk x1)
    //             result = m3[i] * x[0] * x[0];
    //             if (m3[i] > 0) {
    //                 System.out.printf("+ %.4fx1^2 ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4fx1^2 ", Math.abs(m3[i]));
    //             }
    //         } else if (i == 4) {
    //             // a4 * x2^2 (kuadratik untuk x2)
    //             result = m3[i] * x[1] * x[1];
    //             if (m3[i] > 0) {
    //                 System.out.printf("+ %.4fx2^2 ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4fx2^2 ", Math.abs(m3[i]));
    //             }
    //         } else if (i == 5) {
    //             // a5 * x1 * x2 (interaksi antara x1 dan x2)
    //             result = m3[i] * x[0] * x[1];
    //             if (m3[i] > 0) {
    //                 System.out.printf("+ %.4fx1x2 ", m3[i]);
    //             } else {
    //                 System.out.printf("- %.4fx1x2 ", Math.abs(m3[i]));
    //             }
    //         }
    //         sum += result;  // Menambahkan hasil tiap iterasi ke sum
    //     }
    //     System.out.printf(" f(xk) = %.4f\n", sum);


    //     // Output
    //     int opsi = Output.opsiOutput();
    //     if (opsi == 1) {
    //         // Mencetak output ke dalam bentuk file
    //         String nameFile = "";
    //         System.out.println("Masukkan nama file: ");
    //         try {
    //             nameFile = inputFile.readLine();
    //             String path = "test/Output/" + nameFile;

    //             // Cek apakah file sudah ada
    //             File file = new File(path);
    //             if (file.exists()) {
    //                 System.out.println("File sudah ada. Apakah Anda ingin menimpanya? (y/n)");
    //                 char choice = scanner.next().charAt(0);
    //                 if (choice != 'y' && choice != 'Y') {
    //                     System.out.println("Output dibatalkan.");
    //                     return; // Jika tidak memilih 'y', batalkan
    //                 }
    //             }

    //             try (FileWriter fileWriter = new FileWriter(path)) {
    //                 fileWriter.write("f(x) = ");
    //                 for (i = 0; i < m3.length; i++) {
    //                     if (i == 0) {
    //                         fileWriter.write(String.format("%.4f ", m3[i]));
    //                     } else if (i == 1) {
    //                         fileWriter.write(String.format("+ %.4fx ", m3[i]));
    //                     } else {
    //                         fileWriter.write(String.format("+ %.4fx^2 ", m3[i]));
    //                     }
    //                 }
    //                 fileWriter.write(String.format("\nf(xk) = %.4f\n", sum));
    //             }
    //         } catch (IOException err) {
    //             err.printStackTrace();
    //         }
    //     }
    // }

    // Membuat matriks SPL untuk persamaan kuadratik
    public static Matrix createSPLKuadratik(Matrix m) {
        int n = 3; // Jumlah koefisien (a0, a1, ..., an)
        Matrix mTemp = new Matrix();
        mTemp.createMatrix(n, n + 1); // Membuat matriks SPL

        double[] sumX = new double[2 * 2 + 1];
        double[] sumY = new double[n];

        // Menghitung sumX untuk berbagai pangkat x (sumX[i] = sum x^i)
        for (int i = 0; i <= 2 * 2; i++) {
            sumX[i] = sumPowerCol(m, 0, i); // sum x^i
        }

        // Menghitung sumY untuk berbagai pangkat x dikalikan y (sumY[i] = sum x^i * y)
        for (int i = 0; i < n; i++) {
            sumY[i] = sumXPowerMultiplyY(m, 0, 1, i); // sum x^i * y
        }

        // Mengisi matriks SPL berdasarkan sumX dan sumY
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mTemp.setElement(i, j, sumX[i + j]);
            }
            mTemp.setElement(i, n, sumY[i]);
        }

        return mTemp;
    }

    public static double sumPowerCol(Matrix m, int col, int power) {
        double sum = 0.0;
        for (int i = 0; i < m.getRowLength(); i++) {
            double value = m.getElement(i, col);
            sum += Math.pow(value, power);
        }
        return sum;
    }

    public static double sumXPowerMultiplyY(Matrix m, int xCol, int yCol, int power) {
        double sum = 0.0;
        for (int i = 0; i < m.getRowLength(); i++) {
            double xValue = m.getElement(i, xCol);
            double yValue = m.getElement(i, yCol);
            sum += Math.pow(xValue, power) * yValue;
        }
        return sum;
    }
}
