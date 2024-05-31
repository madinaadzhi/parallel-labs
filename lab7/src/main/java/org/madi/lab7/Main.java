package org.madi.lab7;

import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        execution(rank, size);
        MPI.Finalize();
    }

    private static void syncMultiply(int rank, int size, int arrSize, int[] arr1, int[] arr2, int[] res) {
        int rowsPerProc = arrSize / size;

        if (rank == 0) {
            for (int dest = 1; dest < size; dest++) {
                MPI.COMM_WORLD.Send(arr1, dest * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, dest, 0);
            }
        } else {
            MPI.COMM_WORLD.Recv(arr1, rank * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, 0, 0);
        }

        for (int source = 0; source < size; source++) {
            if (rank == source) {
                MPI.COMM_WORLD.Send(arr2, 0, arrSize * arrSize, MPI.INT, (rank + 1) % size, 0);
            } else if (rank == (source + 1) % size) {
                MPI.COMM_WORLD.Recv(arr2, 0, arrSize * arrSize, MPI.INT, source, 0);
            }
            MPI.COMM_WORLD.Barrier();
        }

        for (int i = rank * rowsPerProc; i < (rank + 1) * rowsPerProc; i++) {
            for (int j = 0; j < arrSize; j++) {
                for (int k = 0; k < arrSize; k++) {
                    res[i * arrSize + j] += arr1[i * arrSize + k] * arr2[k * arrSize + j];
                }
            }
        }

        if (rank == 0) {
            for (int source = 1; source < size; source++) {
                MPI.COMM_WORLD.Recv(res, source * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, source, 0);
            }
        } else {
            MPI.COMM_WORLD.Send(res, rank * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, 0, 0);
        }
    }

    private static void asyncMultiply(int rank, int size, int arrSize, int[] arr1, int[] arr2, int[] res) {
        int rowsPerProc = arrSize / size;
        Request[] requests = new Request[5];

        if (rank == 0) {
            for (int dest = 1; dest < size; dest++) {
                requests[0] = MPI.COMM_WORLD.Isend(arr1, dest * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, dest, 0);
            }
        } else {
            MPI.COMM_WORLD.Irecv(arr1, rank * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, 0, 0).Wait();
        }

        for (int source = 0; source < size; source++) {
            if (rank == source) {
                MPI.COMM_WORLD.Isend(arr2, 0, arrSize * arrSize, MPI.INT, (rank + 1) % size, 0);
            } else if (rank == (source + 1) % size) {
                MPI.COMM_WORLD.Irecv(arr2, 0, arrSize * arrSize, MPI.INT, source, 0).Wait();
            }
        }

        for (int i = rank * rowsPerProc; i < (rank + 1) * rowsPerProc; i++) {
            for (int j = 0; j < arrSize; j++) {
                for (int k = 0; k < arrSize; k++) {
                    res[i * arrSize + j] += arr1[i * arrSize + k] * arr2[k * arrSize + j];
                }
            }
        }

        if (rank == 0) {
            for (int source = 1; source < size; source++) {
                MPI.COMM_WORLD.Recv(res, source * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, source, 0);
            }
        } else {
            MPI.COMM_WORLD.Isend(res, rank * rowsPerProc * arrSize, rowsPerProc * arrSize, MPI.INT, 0, 0);
        }
    }

    public static void collectMultiply(int size, int n, int[] arr1, int[] arr2, int[] res) {
        int rowsPerProc = n / size;
        int[] blockMultiplied = new int[rowsPerProc * n];
        int[] result = new int[rowsPerProc * n];

        MPI.COMM_WORLD.Scatter(arr1, 0, rowsPerProc * n, MPI.INT, blockMultiplied, 0, rowsPerProc * n, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(arr2, 0, n * n, MPI.INT, 0);

        for (int i = 0; i < rowsPerProc; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i * n + j] += blockMultiplied[i * n + k] * arr2[k * n + j];
                }
            }
        }

        MPI.COMM_WORLD.Gather(result, 0, rowsPerProc * n, MPI.INT, res, 0, rowsPerProc * n, MPI.INT, 0);
    }

    public static void execution(int rank, int size) {
        int expCnt = 2;
        List<Integer> arrSizes = new ArrayList<>();
        arrSizes.add(500);
        arrSizes.add(1000);
        arrSizes.add(1500);

        if (rank == 0) {
            System.out.println("ProcCnt: " + size + ";");
        }

        for (int arrSize : arrSizes) {
            int[] arr1 = generateArr(arrSize, arrSize);
            int[] arr2 = generateArr(arrSize, arrSize);
            int[] result = new int[arrSize * arrSize];
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < expCnt; i++) {
                syncMultiply(rank, size, arrSize, arr1, arr2, result);
//                asyncMultiply(rank, size, arrSize, arr1, arr2, result);
//                collectMultiply(size, arrSize, arr1, arr2, result);
            }
            MPI.COMM_WORLD.Barrier();
            if (rank == 0) {
                System.out.println((System.currentTimeMillis() - startTime) / expCnt + " ms for " + arrSize + " matrix size");
//                print(convertArrToMatrix(arr1, arrSize, arrSize));
//                System.out.println(" ");
//                print(convertArrToMatrix(arr2, arrSize, arrSize));
//                System.out.println(" ");
//                print(convertArrToMatrix(result, arrSize, arrSize));
            }

        }
    }

    public static int[] generateArr(int row, int col) {
        Random random = new Random();
        int[] arr = new int[row * col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i * col + j] = random.nextInt(9 - 1 + 1) + 1;
            }
        }
        return arr;
    }

    public static void print(int[][] matrix) {
        for (int[] row : matrix) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public static int[][] convertArrToMatrix(int[] arr, int row, int col) {
        int[][] matrix = new int[row][col];

        if (arr.length != row * col) {
            throw new IllegalArgumentException("Invalid array size");
        }

        for (int i = 0; i < row; i++) {
            System.arraycopy(arr, i * col, matrix[i], 0, col);
        }

        return matrix;
    }
}