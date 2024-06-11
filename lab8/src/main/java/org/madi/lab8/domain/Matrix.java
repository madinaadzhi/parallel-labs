package org.madi.lab8.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class Matrix {
    private int[][] matrix;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    public int getRowSize() {
        return matrix.length;
    }

    public int getColumnSize() {
        return matrix[0].length;
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public int[] getRow(int index) {
        return matrix[index];
    }

    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public void print() {
        Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);
    }
}
