package ADTMatrix;

import Function.Invers;
import Function.SPL;
import IO.Output;

public class TestMatrix {
    public static void main(String[] args) {
        double[][] data1 = {
            {1, 2, 3, 0},
            {-4, 5, 6, 0},
            {7, -8, 9, 0}
        };

        Matrix m1 = new Matrix();
        m1.toMatrix(data1, 3, 4);

        String[] anu = SPL.metode_gauss(m1.copy());

        Output.solusi_spl(anu);
    }

    public static void testGaussElimination() {
        System.out.println("Testing Gauss Elimination\n");

        // Example 1: Unique solution
        double[][] data1 = {
            {2, 3, 1, 1},
            {4, 5, 2, 2},
            {6, 7, 3, 3}
        };
        Matrix m1 = new Matrix();
        m1.toMatrix(data1, 3, 4);
        System.out.println("Input Matrix (Example 1 - Unique Solution):");
        printMatrix(m1);
        Matrix result1 = m1.gaussElimination();
        System.out.println("Output Matrix after Gauss Elimination:");
        printMatrix(result1);

        // Example 2: No solution
        double[][] data2 = {
            {1, 2, 1, 1},
            {2, 4, 2, 3},
            {3, 6, 3, 4}
        };
        Matrix m2 = new Matrix();
        m2.toMatrix(data2, 3, 4);
        System.out.println("Input Matrix (Example 2 - No Solution):");
        printMatrix(m2);
        Matrix result2 = m2.gaussElimination();
        System.out.println("Output Matrix after Gauss Elimination:");
        printMatrix(result2);
        
        // Add more test cases as needed
    }

    public static void testGaussJordanElimination() {
        System.out.println("\nTesting Gauss-Jordan Elimination\n");

        // Example 1: Unique solution
        double[][] data1 = {
            {1, 2, -1, 8},
            {2, 3, 3, 22},
            {1, -1, 2, 5}
        };
        Matrix m1 = new Matrix();
        m1.toMatrix(data1, 3, 4);
        System.out.println("Input Matrix (Example 1 - Unique Solution):");
        printMatrix(m1);
        Matrix result1 = m1.gaussJordanElimination();
        System.out.println("Output Matrix after Gauss-Jordan Elimination:");
        printMatrix(result1);

        // Example 2: No solution
        double[][] data2 = {
            {1, 2, -1, 8},
            {2, 4, 2, 16},
            {3, 6, -3, 24}
        };
        Matrix m2 = new Matrix();
        m2.toMatrix(data2, 3, 4);
        System.out.println("Input Matrix (Example 2 - No Solution):");
        printMatrix(m2);
        Matrix result2 = m2.gaussJordanElimination();
        System.out.println("Output Matrix after Gauss-Jordan Elimination:");
        printMatrix(result2);
        
        // Add more test cases as needed
    }

    private static void printMatrix(Matrix m) {
        for (int i = 0; i < m.getRowLength(); i++) {
            for (int j = 0; j < m.getColLength(); j++) {
                System.out.printf("%8.3f ", m.getElement(i, j)); // Format to 3 decimal places
            }
            System.out.println();
        }
        System.out.println();
    }
}
