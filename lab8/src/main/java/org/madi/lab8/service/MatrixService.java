package org.madi.lab8.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.madi.lab8.domain.Matrix;
import org.madi.lab8.utils.FoxThread;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
@Setter
@AllArgsConstructor
@Service
public class MatrixService {
    public Matrix generateRandomMatrix(int rows, int columns) {
        Matrix matrix = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.set(i, j, (int) (Math.random() * 10));
            }
        }
        return matrix;
    }

    public void getJsonMatrix(int[][] matrix, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMatrix = objectMapper.writeValueAsString(matrix);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(jsonMatrix);
        writer.close();
    }

    public int[][] convertIntArrToMatrix(String jsonMatrix) throws JsonProcessingException {
        JSONArray jsonArray = new JSONArray(jsonMatrix);
        int rows = jsonArray.length();
        int cols = jsonArray.getJSONArray(0).length();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            JSONArray rowArray = jsonArray.getJSONArray(i);
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rowArray.getInt(j);
            }
        }
        return matrix;
    }

    public void convertIntArrToMatrix(int[][] arr, Matrix matrix) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                matrix.getMatrix()[i][j] = arr[i][j];
            }
        }
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

    public Matrix multiply(Matrix matrix1, Matrix matrix2, int threadsCnt) {
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


