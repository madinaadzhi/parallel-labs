package org.madi.lab2;

import java.util.ArrayList;

public class MultithreadMultiplication {
    public Matrix multiply(Matrix matrix1, Matrix matrix2, int threadsCnt) {
        if (matrix1.getColumnSize() != matrix2.getRowSize()) {
            throw new IllegalArgumentException("Matrices cannot be multiplied because the " +
                    "number of columns of matrix A is not equal to the number of rows of matrix B.");
        }

        int height = matrix1.getRowSize();
        int width = matrix2.getColumnSize();
        Matrix resultMatrix = new Matrix(height, width);

        int rowsPerThread = height / threadsCnt;
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCnt; i++) {
            int from = i * rowsPerThread;
            int to;
            if (i == threadsCnt - 1) {
                to = height;
            } else {
                to = (i + 1) * rowsPerThread;
            }
            threads.add(new Thread(() -> {
                for (int row = from; row < to; row++) {
                    for (int col = 0; col < width; col++) {
                        for (int k = 0; k < matrix2.getRowSize(); k++) {
                            resultMatrix.set(row, col, resultMatrix.get(row, col)
                                    + matrix1.get(row, k) * matrix2.get(k, col));
                        }
                    }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMatrix;
    }
}
