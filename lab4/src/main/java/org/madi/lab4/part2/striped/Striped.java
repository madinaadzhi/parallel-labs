package org.madi.lab4.part2.striped;

import lombok.Getter;
import lombok.Setter;
import org.madi.lab4.part2.matrix.Matrix;

import java.util.ArrayList;

@Getter
@Setter
public class Striped {
    private Matrix matrix1;
    private Matrix matrix2;
    private int threadsCnt;

    public Striped(Matrix matrix1, Matrix matrix2, int threadsCnt) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.threadsCnt = threadsCnt;
    }

    public Matrix multiply() {
        Matrix resultMatrix = new Matrix(matrix1.getRowSize(), matrix2.getColumnSize());
        resultMatrix = multiplyMatrices(resultMatrix);
        return resultMatrix;
    }

    public Matrix multiplyMatrices(Matrix matrix) {
        ArrayList<StripedThread> threads = new ArrayList<>();

        for (int i = 0; i < matrix1.getRowSize(); i++) {
            StripedThread t = new StripedThread(matrix1.getRow(i), i, matrix2);
            threads.add(t);
            threads.get(i).start();
        }

        for (StripedThread t : threads) {
            try {
                t.join();
                matrix.getMatrix()[t.getRowIndex()] = t.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matrix;
    }
}
