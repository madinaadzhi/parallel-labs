package org.madi.lab2;

import lombok.*;

@Getter
@Setter
public class Basic {
    private Matrix matrix1;
    private Matrix matrix2;
    public Basic(Matrix matrix1, Matrix matrix2) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    public Matrix multiply() {
        Matrix resultMatrix = new Matrix(matrix1.getRowSize(), matrix2.getColumnSize());
        for (int i = 0; i < matrix1.getRowSize(); i++) {
            for (int j = 0; j < matrix2.getColumnSize(); j++) {
                resultMatrix.getMatrix()[i][j] = 0;
                for (int k = 0; k < matrix1.getColumnSize(); k++) {
                    resultMatrix.getMatrix()[i][j] += matrix1.getMatrix()[i][k] * matrix2.getMatrix()[k][j];
                }
            }
        }
        return resultMatrix;
    }
}
