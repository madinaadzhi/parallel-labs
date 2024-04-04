package org.madi.lab2;

import lombok.*;

@Getter
@Setter
public class MatrixGenerator {
    Matrix matrix;

    public Matrix generateRandomMatrix(int rows, int columns) {
        matrix = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.set(i, j, (int) (Math.random() * 10));
            }
        }
        return matrix;
    }
}
