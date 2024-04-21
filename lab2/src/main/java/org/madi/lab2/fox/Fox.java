package org.madi.lab2.fox;

import lombok.*;
import org.madi.lab2.Matrix;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
@Setter
public class Fox {
    private Matrix matrix1;
    private Matrix matrix2;
    private int threadsCnt;

    public Fox(Matrix Matrix1, Matrix Matrix2, int threadsCnt) {
        this.matrix1 = Matrix1;
        this.matrix2 = Matrix2;
        this.threadsCnt = threadsCnt;
    }

    private int findDivider(int s, int p) {
        int i = s;
        while (i > 1) {
            if (p % i == 0) {
                break;
            }
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }
        return i >= s ? i : i != 0 ? p / i : p;
    }

    public Matrix multiply() {
        Matrix resultMatrix = new Matrix(matrix1.getRowSize(), matrix2.getColumnSize());

        if (!(matrix1.getRowSize() == matrix1.getColumnSize()
                & matrix2.getRowSize() == matrix2.getColumnSize()
                & matrix1.getRowSize() == matrix2.getRowSize())) {
            try {
                throw new Exception("Matrix 1 and 2 have different dimensions!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        threadsCnt = Math.min(threadsCnt, matrix1.getRowSize());
        threadsCnt = findDivider(threadsCnt, matrix1.getRowSize());
        int step = matrix1.getRowSize() / threadsCnt;

        ExecutorService exec = Executors.newFixedThreadPool(threadsCnt);
        ArrayList<Future> threads = new ArrayList<>();

        int[][] matrixOfSizesI = new int[threadsCnt][threadsCnt];
        int[][] matrixOfSizesJ = new int[threadsCnt][threadsCnt];

        int stepI = 0;
        for (int i = 0; i < threadsCnt; i++) {
            int stepJ = 0;
            for (int j = 0; j < threadsCnt; j++) {
                matrixOfSizesI[i][j] = stepI;
                matrixOfSizesJ[i][j] = stepJ;
                stepJ += step;
            }
            stepI += step;
        }

        for (int l = 0; l < threadsCnt; l++) {
            for (int i = 0; i < threadsCnt; i++) {
                for (int j = 0; j < threadsCnt; j++) {
                    int stepI0 = matrixOfSizesI[i][j];
                    int stepJ0 = matrixOfSizesJ[i][j];

                    int stepI1 = matrixOfSizesI[i][(i + l) % threadsCnt];
                    int stepJ1 = matrixOfSizesJ[i][(i + l) % threadsCnt];

                    int stepI2 = matrixOfSizesI[(i + l) % threadsCnt][j];
                    int stepJ2 = matrixOfSizesJ[(i + l) % threadsCnt][j];

                    FoxThread t = new FoxThread(copyBlock(matrix1, stepI1, stepJ1, step),
                            copyBlock(matrix2, stepI2, stepJ2, step), stepI0, stepJ0, resultMatrix);
                    threads.add(exec.submit(t));
                }
            }
        }
        for (Future mapFuture : threads) {
            try {
                mapFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
        return resultMatrix;
    }

    private Matrix copyBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size, size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getMatrix()[k + i], j, block.getMatrix()[k], 0, size);
        }
        return block;
    }
}


