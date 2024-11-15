package Function;

import ADTMatrix.Matrix;
import IO.Output;

public class Bicubic{

    public static void f (Matrix m){
        int row, col, x, y;

        for (row = 0; row < 4; row++) {
            if (row == 0) {
                x = 0;
                y = 0;
            }
            else if (row == 2) {
                x = 1;
                y = 0;
            }
            else if (row == 1) {
                x = 0;
                y = 1;
            }
            else {
                x = 1;
                y = 1;
            }
            int i = 0, j = 0;
    
            for (col = 0; col < 16; col++) {
                m.setElement(row, col, Math.pow(x, i) * Math.pow(y, j));
                
                j++;
                if (j == 4) {
                    j = 0;
                    i++;
                }
            }
        }
        
    }

    public static void fx (Matrix m){
        int row, col, x, y;

        for (row = 4; row < 8; row++) {
            if (row == 4) {
                x = 0;
                y = 0;
            }
            else if (row == 5) {
                x = 1;
                y = 0;
            }
            else if (row == 6) {
                x = 0;
                y = 1;
            }
            else {
                x = 1;
                y = 1;
            }
            int i = 0, j = 0;
    
            for (col = 0; col < 16; col++){
                if (i == 0){
                    m.setElement(row, col, 0.0000);
                }
                else{
                    m.setElement(row, col, Math.pow(x, i-1) * Math.pow(y, j) * i);
                }
                i++;
                if (i > 3){
                    i = 0;
                    j++;
                }
            }
        }

    }

    public static void fy (Matrix m){
        int row, col, x, y;

        for (row = 8; row < 12; row++) {
            if (row == 8) {
                x = 0;
                y = 0;
            }
            else if (row == 9) {
                x = 1;
                y = 0;
            }
            else if (row == 10) {
                x = 0;
                y = 1;
            }
            else {
                x = 1;
                y = 1;
            }
            int i = 0, j = 0;
    
            for (col = 0; col < 16; col++){
                if (j == 0){
                    m.setElement(row, col, 0.0000);
                }
                else{
                    m.setElement(row, col, Math.pow(x, i) * Math.pow(y, j-1) * j);
                }
                i++;
                if (i > 3){
                    i = 0;
                    j++;
                }
            }
        }
   
    }

    public static void fxy (Matrix m){
        int row, col, x, y;

        for (row = 12; row < 16; row++) {
            if (row == 12) {
                x = 0;
                y = 0;
            }
            else if (row == 13) {
                x = 1;
                y = 0;
            }
            else if (row == 14) {
                x = 0;
                y = 1;
            }
            else {
                x = 1;
                y = 1;
            }
            int i = 0, j = 0;
    
            for (col = 0; col < 16; col++){
                if (i == 0 || j == 0){
                    m.setElement(row, col, 0.0000);
                }
                else{
                    m.setElement(row, col, Math.pow(x, i-1) * Math.pow(y, j-1) * i * j);
                }
                i++;
                if (i > 3){
                    i = 0;
                    j++;
                }
            }
        }
        
    }

    public static double BicubicSplineInterpolation(double[][] fInput) {
        Matrix m = new Matrix();
        m.createMatrix(16,16);

        double x = fInput[4][0];
        double y = fInput[4][1];

        f(m);
        fx(m);
        fy(m);
        fxy(m);

        Matrix mInvers = new Matrix();
        mInvers.createMatrix(16,16);

        Matrix mHasilKali = new Matrix();
        mHasilKali.createMatrix(16,1);

        Matrix nilaiFungsi = new Matrix();
        nilaiFungsi.createMatrix(16, 1);
        
        //mengubah input menjadi matriks 16x1
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nilaiFungsi.matrix[idx][0] = fInput[i][j];
                idx++;
            }
        }

        //OutputFile.printMatrix(m);

        double hasil = 0.0;

        mInvers = Invers.inversMatriksIdentitas(m);

        mHasilKali = Matrix.multiplyMatrix(mInvers, nilaiFungsi);

        int i,j, index=0;

        for (i=0; i<4; i++){
            for (j=0; j<4; j++){
                hasil +=  Math.pow(x, i) * Math.pow(y, j) * mHasilKali.getElement(index, 0);
                index++;
            }
        }

        return hasil;
        
    }
}
