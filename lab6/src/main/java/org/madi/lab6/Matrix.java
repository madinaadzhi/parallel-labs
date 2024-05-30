package org.madi.lab6;

public class Matrix {
    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void fill(int[][] matrix, int value) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = matrix[i][j] = value;
            }
        }
    }

    public static int[] convertMatrixToArr(int[][] matrix) {
        int[] arr = new int[matrix.length * matrix[0].length];
        int arrI = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                arr[arrI++] = matrix[i][j];
            }
        }
        return arr;
    }

    public static int[][] convertArrToMatrix(int[] arr, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        int arrI = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = arr[arrI++];
            }
        }
        return matrix;
    }

    public static void fillWithOffset(int[][] matrix, int[] arr, int offsetByRows) {
        int arrI = 0;
        for (int i = offsetByRows; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(arrI == arr.length)
                    return;
                matrix[i][j] = arr[arrI];
                arrI++;
            }
        }
    }
}