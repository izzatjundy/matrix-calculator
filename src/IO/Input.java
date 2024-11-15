package IO;
import java.util.*;
import java.io.*;
import java.nio.file.Paths;

import ADTMatrix.Matrix;
import IO.Output;

public class Input {
    public static Scanner scanner = new Scanner(System.in);
    public static Matrix readMatrix() {
        int i,j, row = 0, col= 0;
        double[][] m;
        Matrix hasil = new Matrix();

        while (row <= 0 || col <= 0){
            System.out.print("Masukkan jumlah baris: ");
            row = scanner.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            col = scanner.nextInt();
            if (row <= 0 || col <= 0){
                System.out.println("Baris dan kolom harus berjumlah lebih dari 0!");
            }
        }
        

        m = new double[row][col];
        System.out.println("Masukkan elemen matriks: ");
        for(i = 0; i < row; i++){
            for(j = 0; j < col; j++){
                m[i][j] = scanner.nextDouble();
            }
        }

        hasil.toMatrix(m, row, col);
        return hasil;
    }

    public static double[][] readInterpolasi(){
        int i, n;
        double [][] m;

        System.out.print("Masukkan banyak titik yang diketahui> ");
        n = scanner.nextInt();
        System.out.println();

        m = new double[n+1][2];

        i = 0;
        while(i<n){

            System.out.print("Titik ke-" + (i+1) + ": ");
            m[i][0] = scanner.nextDouble();
            m[i][1] = scanner.nextDouble();

            i+=1;
        }
        System.out.println();

        System.out.print("Masukkan absis (x) dari ordinat (y) yang ingin Anda cari> ");
        m[n][0] = scanner.nextDouble();
        System.out.println();

        return m;
    }

    public static double[][] readBicubic(){
        int i,j;
        double x, y;
        double [][] m;

        m = new double[5][4];
        System.out.println("Masukkan nilai fungsi yang diketahui: ");
        System.out.println("**Ekspektasi format**");
        System.out.println("[f(0,0),   f(1,0),   f(0,1),   f(1,1)  ]");
        System.out.println("[fx(0,0),  fx(1,0),  fx(0,1),  fx(1,1) ]");
        System.out.println("[fy(0,0),  fy(1,0),  fy(0,1),  fy(1,1) ]");
        System.out.println("[fxy(0,0), fxy(1,0), fxy(0,1), fxy(1,1)]");
        for(i = 0; i < 4 ; i++){
            for(j = 0; j < 4; j++){
                m[i][j] = scanner.nextDouble();
            }
        }

        System.out.print("Masukkan nilai x dan y: ");
        x = scanner.nextDouble();
        y = scanner.nextDouble();

        m[4][0] = x;
        m[4][1] = y;
       

        return m;
    }

    public static double[][] readRegresi(){
        int i, j;
        int x, m;
        double [][] matrix;

        System.out.print("Masukkan jumlah peubah (x): ");
        x = scanner.nextInt();
        System.out.print("Masukkan jumlah sampel (m): ");
        m = scanner.nextInt();

        matrix = new double[m][x + 1];
        System.out.println("Masukkan titik x dan y: ");
        for(i = 0; i < m; i++){
            for(j = 0; j < x + 1; j++){
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }
    public static Matrix readMatrixFile() {
        scanner.nextLine();
        System.out.print("Masukkan nama file: ");
        String file = scanner.nextLine();
        String path = String.format("Test%sInput%s%s", 
                              File.separator, 
                              File.separator, 
                              file);
                              
        System.out.println("Reading from: " + path);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IOException("File is empty");
            }
            
            double[] firstRow = parseRow(firstLine);
            Matrix matrix = new Matrix();
            matrix.createMatrix(1, firstRow.length);
            matrix.matrix = new double[][]{ firstRow };
            
            String line;
            while ((line = br.readLine()) != null) {
                double[] row = parseRow(line);
                double[] paddedRow = padRow(row, matrix.col);
                matrix = Matrix.addRow(matrix, paddedRow);
            }
            
            return matrix;
            
        } catch (IOException ex) {
            System.out.println("File tidak ditemukan");
            System.out.println("Mengembalikan matriks kosong");
            Matrix emptyMatrix = new Matrix();
            emptyMatrix.createMatrix(1, 1);
            return emptyMatrix;
        }
    }

    public static double[][] input_interpolasi_file(){

        int i, j;

        scanner.nextLine();
        System.out.print("Masukkan nama file: ");
        String file = scanner.nextLine();
        String path = String.format("Test%sInput%s%s", 
                              File.separator, 
                              File.separator, 
                              file);
                              
        System.out.println("Reading from: " + path);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String firstLine = br.readLine();
            if (firstLine == null) {
                throw new IOException("File is empty");
            }
            
            double[] firstRow = parseRow(firstLine);
            double[][] matrix = new double[1][1], temp = new double[1][2];
            matrix[0] = firstRow;
            
            String line;
            while ((line = br.readLine()) != null) {
                double[] row = parseRow(line);
                
                temp = new double[matrix.length+1][2];

                for (i=0; i<matrix.length; i++){
                    for (j=0; j<2; j++){
                        temp[i][j] = matrix[i][j];
                    }
                }
            
                for (j=0; j<row.length; j++){
                    temp[matrix.length][j] = row[j];
                }

                matrix = temp;
            }
            
            return matrix;
            
        } catch (IOException ex) { 
            System.out.println("File tidak ditemukan");
            System.out.println("Mengembalikan matriks kosong");
            double[][] anu = new double[0][0];
            return anu;
        }
    }

    private static double[] parseRow(String line) {
        return Arrays.stream(line.split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
    }

    private static double[] padRow(double[] row, int targetLength) {
        double[] result = new double[targetLength];
        System.arraycopy(row, 0, result, 0, Math.min(row.length, targetLength));
        return result;
    }

    public static int caraInput(boolean salahInput){
        int hasil;
        while(true){

            Output.menu_input();

            salahInput = Output.pesan_salah_input(salahInput);

            System.out.print("> ");
            hasil = scanner.nextInt();

            if(hasil >= 0 && hasil <= 2){
                break;
            }else{
                salahInput = true;
            }
        }
        return hasil;
    }

    public static double[][] input_regresi_kuadratik(){
        
        double[][] anu = new double[21][4];
        int i, j;
        
        i = 0;
        while(i<20){

            j = 0;
            while(j<4){

                anu[i][j] = scanner.nextDouble();

                j+=1;
            }

            i+=1;
        }

        i = 0;
        while(i<3){

            anu[20][i] = scanner.nextDouble();

            i+=1;
        }

        return anu;
    }
}
