package org.madi.lab2.fox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.madi.lab2.Matrix;

@Getter
@Setter
@RequiredArgsConstructor
public class Fox {
    private Matrix matrix1;
    private Matrix matrix2;
    private int threadsCnt;
    private Matrix resultMatrix;

    public Fox(Matrix Matrix1, Matrix Matrix2, int threadsCnt) {
        this.matrix1 = Matrix1;
        this.matrix2 = Matrix2;
        this.resultMatrix = new Matrix(Matrix1.getRowSize(), Matrix2.getColumnSize());
        if (threadsCnt > Matrix1.getRowSize() * Matrix2.getColumnSize() / 4) {
            this.threadsCnt = Matrix1.getRowSize() * Matrix2.getColumnSize() / 4;
        } else {
            this.threadsCnt = Math.max(threadsCnt, 1);
        }
    }

    public Matrix multiply() {
        int step = (int) Math.ceil(1.0 * matrix1.getRowSize() / (int) Math.sqrt(threadsCnt));

        FoxThread[] threads = new FoxThread[threadsCnt];
        int cnt = 0;

        for (int i = 0; i < matrix1.getRowSize(); i += step) {
            for (int j = 0; j < matrix2.getColumnSize(); j += step) {
                threads[cnt] = new FoxThread(matrix1, matrix2, i, j, step, resultMatrix);
                cnt++;
            }
        }

        for (int i = 0; i < cnt; i++) {
            threads[i].start();
        }

        for (int i = 0; i < cnt; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultMatrix;
    }
}

