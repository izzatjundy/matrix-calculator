package ADTMatrix;
//import ADTMatrix.Matrix;

public class Determinan {
    // mencari determinan dengan metode kofaktor
    public static double detKofaktor(Matrix m){
        Matrix tempMatrix;
        int i, j, k;
        double x;
        double [][] temp; //matrix kosong
        //inisialisasi
        double det = 0;

        int row = m.getRowLength();
        int col = m.getColLength();

        //cek apakah matrix persegi
        if (m.isSquare()){
            if (row == 1 || col == 1){ //satu kali satu
                return m.matrix[0][0];
            }
            else{

                //kofaktor baris pertama
                for (i=0; i<col; i++){
                    //membuat matriks kofaktor
                    tempMatrix = new Matrix();
                    temp = new double [row-1][col-1];
                    for (j=1; j<col; j++){
                        for (k=0; k < row; k++){

                            x = m.matrix[j][k];

                            //menentukan lokasinya di matrix kofaktor
                            if (k>i){
                                temp[j-1][k-1] = x;
                            }
                            else if (k<i){
                                temp[j-1][k] = x;
                            }
                        }
                    }
                    tempMatrix.toMatrix(temp, row-1, col-1);
                    det += Math.pow(-1,i) * m.matrix[0][i] * detKofaktor(tempMatrix);  
                }
                return det;
            }
        }
        else {
            return m.MARK;
        }
    }

    //mencari determinan dengan OBE
    public static double detOBE(Matrix m){
        //matriks di OBE kan dulu menggunakan eliminasiGauss
        m.gaussElimination();

        //menghitung determinan berdasarkan perkalian diagonal
        double determinan = 1;
        for (int i=0; i<m.getRowLength(); i++){
            determinan *= m.matrix[i][i]; //mengalikan elemen diagonal
        }

        return determinan;
    }

}

