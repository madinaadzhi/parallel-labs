package org.madi.lab4.part2.fox;

import org.madi.lab4.part2.matrix.Matrix;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class FoxForkJoin extends RecursiveTask<HashMap<String, Object>> {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int stepI;
    private final int stepJ;

    public FoxForkJoin(Matrix matrix1, Matrix matrix2, int stepI, int stepJ) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.stepI = stepI;
        this.stepJ = stepJ;
    }

    private Matrix multiplyBlock() {
        Matrix blockRes = new Matrix(matrix1.getColumnSize(), matrix2.getRowSize());
        for (int i = 0; i < matrix1.getRowSize(); i++) {
            for (int j = 0; j < matrix2.getColumnSize(); j++) {
                for (int k = 0; k < matrix1.getColumnSize(); k++) {
                    blockRes.getMatrix()[i][j] += matrix1.getMatrix()[i][k] * matrix2.getMatrix()[k][j];
                }
            }
        }
        return blockRes;
    }

    @Override
    protected HashMap<String, Object> compute() {
        Matrix blockRes = multiplyBlock();
        HashMap<String, Object> result = new HashMap<>();
        result.put("blockRes", blockRes);
        result.put("stepI", stepI);
        result.put("stepJ", stepJ);
        return result;
    }
}
