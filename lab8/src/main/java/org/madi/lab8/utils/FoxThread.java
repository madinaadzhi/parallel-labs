package org.madi.lab8.utils;


import org.madi.lab8.domain.Matrix;

public class FoxThread extends Thread {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int curRowShift;
    private final int curColShift;
    private final Matrix resultMatrix;

    public FoxThread(Matrix Matrix1, Matrix Matrix2, int curRowShift, int curColShift, Matrix resultMatrix) {
        this.resultMatrix = resultMatrix;
        this.matrix1 = Matrix1;
        this.matrix2 = Matrix2;
        this.curRowShift = curRowShift;
        this.curColShift = curColShift;
    }

    @Override
    public void run() {
    Matrix blockRes = multiplyBlock();
    for (int i = 0; i < blockRes.getRowSize(); i++) {
        for (int j = 0; j < blockRes.getColumnSize(); j++) {
            resultMatrix.getMatrix()[i + curRowShift][j + curColShift] += blockRes.getMatrix()[i][j];
        }
    }
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
}
