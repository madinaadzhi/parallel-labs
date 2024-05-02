package org.madi.lab4.part2.fox;

import org.madi.lab4.part2.matrix.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class Fox extends RecursiveTask<Matrix> {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final Matrix resultMatrix;
    private int blocksNum;
    private final int step;
    private final int[][] sizeIMatrix;
    private final int[][] sizeJMatrix;

    public Fox(Matrix matrix1, Matrix matrix2, int blocksNum) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = new Matrix(matrix1.getRowSize(), matrix2.getColumnSize());
        this.blocksNum = blocksNum;

        if (!(matrix1.getRowSize() == matrix1.getColumnSize()
                & matrix2.getRowSize() == matrix2.getColumnSize()
                & matrix1.getRowSize() == matrix2.getColumnSize())) {
            try {
                throw new Exception("Matrix A and B have different dimensions!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        this.blocksNum = Math.min(this.blocksNum, matrix1.getRowSize());
        this.blocksNum = findDivider(this.blocksNum, matrix1.getRowSize());
        this.step = matrix1.getRowSize() / this.blocksNum;

        this.sizeIMatrix = new int[this.blocksNum][this.blocksNum];
        this.sizeJMatrix = new int[this.blocksNum][this.blocksNum];

        int stepI = 0;
        for (int i = 0; i < this.blocksNum; i++) {
            int stepJ = 0;
            for (int j = 0; j < this.blocksNum; j++) {
                sizeIMatrix[i][j] = stepI;
                sizeJMatrix[i][j] = stepJ;
                stepJ += this.step;
            }
            stepI += this.step;
        }
    }

    private int findDivider(int s, int p) {
        int i = s;
        while (i > 1) {
            if (p % i == 0) break;
            if (i >= s) {
                i++;
            } else {
                i--;
            }
            if (i > Math.sqrt(p)) i = Math.min(s, p / s) - 1;
        }
        return i >= s ? i : i != 0 ? p / i : p;
    }

    private Matrix copyBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size, size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.getMatrix()[k + i], j, block.getMatrix()[k], 0, size);
        }
        return block;
    }

    @Override
    protected Matrix compute() {
        List<RecursiveTask<HashMap<String, Object>>> tasks = new ArrayList<>();

        for (int l = 0; l < blocksNum; l++) {
            for (int i = 0; i < blocksNum; i++) {
                for (int j = 0; j < blocksNum; j++) {
                    int stepI0 = sizeIMatrix[i][j];
                    int stepJ0 = sizeJMatrix[i][j];

                    int stepI1 = sizeIMatrix[i][(i + l) % blocksNum];
                    int stepJ1 = sizeJMatrix[i][(i + l) % blocksNum];

                    int stepI2 = sizeIMatrix[(i + l) % blocksNum][j];
                    int stepJ2 = sizeJMatrix[(i + l) % blocksNum][j];

                    FoxForkJoin task = new FoxForkJoin(copyBlock(matrix1, stepI1, stepJ1, step),
                            copyBlock(matrix2, stepI2, stepJ2, step), stepI0, stepJ0);

                    tasks.add(task);
                    task.fork();
                }
            }
        }

        for (RecursiveTask<HashMap<String, Object>> task : tasks) {
            HashMap<String, Object> r = task.join();

            Matrix blockRes = (Matrix) r.get("blockRes");
            int stepI = (int) r.get("stepI");
            int stepJ = (int) r.get("stepJ");

            for (int i = 0; i < blockRes.getRowSize(); i++) {
                for (int j = 0; j < blockRes.getColumnSize(); j++) {
                    resultMatrix.getMatrix()[i + stepI][j + stepJ] += blockRes.getMatrix()[i][j];
                }
            }
        }

        return this.resultMatrix;
    }
}
