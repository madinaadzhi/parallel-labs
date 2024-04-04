package org.madi.lab2.fox;

import org.madi.lab2.Matrix;
import org.madi.lab2.striped.Striped;

public class FoxThread extends Thread {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int curRowShift;
    private final int curColShift;
    private final int blockSize;
    private final Matrix resultMatrix;

    public FoxThread(Matrix Matrix1, Matrix Matrix2, int curRowShift,
                     int curColShift, int blockSize, Matrix resultMatrix) {
        this.resultMatrix = resultMatrix;
        this.matrix1 = Matrix1;
        this.matrix2 = Matrix2;
        this.curRowShift = curRowShift;
        this.curColShift = curColShift;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        int m1RowSize = blockSize;
        int m2ColSize = blockSize;

        if (curRowShift + blockSize > matrix1.getRowSize())
            m1RowSize = matrix1.getRowSize() - curRowShift;

        if (curColShift + blockSize > matrix2.getColumnSize())
            m2ColSize = matrix2.getColumnSize() - curColShift;

        for (int k = 0; k < matrix1.getRowSize(); k += blockSize) {
            int m1ColSize = blockSize;
            int m2RowSize = blockSize;

            if (k + blockSize > matrix2.getRowSize()) {
                m2RowSize = matrix2.getRowSize() - k;
            }

            if (k + blockSize > matrix1.getColumnSize()) {
                m1ColSize = matrix1.getColumnSize() - k;
            }

            Matrix blockFirst = copyBlock(matrix1, curRowShift, curRowShift + m1RowSize,
                    k, k + m1ColSize);
            Matrix blockSecond = copyBlock(matrix2, k, k + m2RowSize,
                    curColShift, curColShift + m2ColSize);

            Matrix resBlock = new Striped().multiply(blockFirst, blockSecond);
            for (int i = 0; i < resBlock.getRowSize(); i++) {
                for (int j = 0; j < resBlock.getColumnSize(); j++) {
                    resultMatrix.set(i + curRowShift, j + curColShift, resBlock.get(i, j)
                            + resultMatrix.get(i + curRowShift, j + curColShift));
                }
            }
        }
    }

    private Matrix copyBlock(Matrix src, int rowStart, int rowFinish, int colStart, int colFinish) {
        Matrix copyMatrix = new Matrix(rowFinish - rowStart, colFinish - colStart);
        for (int i = 0; i < rowFinish - rowStart; i++) {
            for (int j = 0; j < colFinish - colStart; j++) {
                copyMatrix.set(i, j, src.get(i + rowStart, j + colStart));
            }
        }

        return copyMatrix;
    }
}
