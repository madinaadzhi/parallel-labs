package org.madi.lab2.striped;

import org.madi.lab2.Matrix;

public class Striped {
    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnSize() != matrix2.getRowSize()) {
            throw new IllegalArgumentException("matrices cannot be multiplied because the " +
                    "number of columns of matrix A is not equal to the number of rows of matrix B.");
        }

        Matrix resultMatrix = new Matrix(matrix1.getRowSize(), matrix2.getColumnSize());
        for (int i = 0; i < matrix1.getRowSize(); i++) {
            for (int j = 0; j < matrix2.getColumnSize(); j++) {
                for (int k = 0; k < matrix1.getColumnSize(); k++) {
                    resultMatrix.set(i, j, resultMatrix.get(i, j) + matrix1.get(i, k) * matrix2.get(k, j));
                }
            }
        }

        return resultMatrix;
    }
}
