package org.madi.lab6;

import mpi.MPI;

import java.util.Arrays;

public class Block {
    static final int N = 1000;
    static final int MASTER = 0;
    static final int FROM_MASTER = 1;
    static final int FROM_WORKER = 5;

    public static void main(String[] args) {
        MPI.Init(args);
        int currProc = MPI.COMM_WORLD.Rank();
        int procCnt = MPI.COMM_WORLD.Size();
        int workersCnt = procCnt - 1;
        if (N % workersCnt != 0) {
            if (currProc == MASTER) {
                System.out.println("It is impossible to allocate the specified number of calls to " + procCnt + " threads!");
            }
            MPI.Finalize();
            return;
        }

        int rowsPerProc = N / workersCnt;
        int[][] matrix2 = new int[N][N];
        if (currProc == MASTER) {
            int[][] matrix1 = new int[N][N];
            int[][] resultMatrix = new int[N][N];
            long startTime = System.currentTimeMillis();
            Matrix.fill(matrix1, 8);
            Matrix.fill(matrix2, 8);
            int[] arr2 = Matrix.convertMatrixToArr(matrix2);
            for (int dest = 1; dest <= workersCnt; dest++) {
                int offSet = (dest - 1);
                int startRow = offSet * rowsPerProc;
                int endRow = startRow + rowsPerProc;
                int[] arr1 = Matrix.convertMatrixToArr(Arrays.copyOfRange(matrix1, startRow, endRow));
                MPI.COMM_WORLD.Send(new int[]{offSet}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(arr1, 0, rowsPerProc * N, MPI.INT, dest, FROM_MASTER + 1);
                MPI.COMM_WORLD.Send(arr2, 0, N * N, MPI.INT, dest, FROM_MASTER + 2);
            }
            for (int source = 1; source <= workersCnt; source++) {
                int[] offset = new int[1];
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, FROM_WORKER);
                int[] resultMatrix1 = new int[rowsPerProc * N];
                MPI.COMM_WORLD.Recv(resultMatrix1, 0, rowsPerProc * N, MPI.INT, source, FROM_WORKER + 1);
                Matrix.fillWithOffset(resultMatrix, resultMatrix1, offset[0] * rowsPerProc);
            }
            System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + " ms" + " for " + workersCnt + " workers");
//            Matrix.print(resultMatrix);
        } else {
            int[] offset = new int[1];
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            int[] subArr1 = new int[rowsPerProc * N];
            MPI.COMM_WORLD.Recv(subArr1, 0, rowsPerProc * N, MPI.INT, MASTER, FROM_MASTER + 1);
            int[][] matrix1 = Matrix.convertArrToMatrix(subArr1, rowsPerProc, N);
            int[] arr1 = new int[N * N];
            MPI.COMM_WORLD.Recv(arr1, 0, N * N, MPI.INT, MASTER, FROM_MASTER + 2);
            matrix2 = Matrix.convertArrToMatrix(arr1, N, N);
            int[][] resultMatrix = new int[rowsPerProc][N];
            for (int i = 0; i < rowsPerProc; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                    }
                }
            }
            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(Matrix.convertMatrixToArr(resultMatrix), 0, rowsPerProc * N, MPI.INT, MASTER, FROM_WORKER + 1);
        }
        MPI.Finalize();
    }
}