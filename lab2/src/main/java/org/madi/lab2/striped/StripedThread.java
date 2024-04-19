package org.madi.lab2.striped;

import lombok.*;
import org.madi.lab2.Matrix;
@Getter
public class StripedThread extends Thread{
    private final int rowIndex;
    private final int[] row;
    private final int[] result;
    private final Matrix matrix2;

    public StripedThread(int[] row, int rowIndex, Matrix matrix2) {
        this.row = row;
        this.rowIndex = rowIndex;
        this.result = new int[matrix2.getColumnSize()];
        this.matrix2 = matrix2;
    }

    @Override
    public void run() {
        for (int j = 0; j < matrix2.getColumnSize(); j++) {
            for (int i = 0; i < row.length; i++) {
                result[j] += row[i] * matrix2.getMatrix()[i][j];
            }
        }
    }
}
