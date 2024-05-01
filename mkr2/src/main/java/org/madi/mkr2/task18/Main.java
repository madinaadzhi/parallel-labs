//package org.madi.mkr2.task18;
//
//import mpi.*;
//
//public class Main {
//    public static void main(String[] args) {
//        MPI.Init(args);
//        int size = MPI.COMM_WORLD.Size();
//        int rank = MPI.COMM_WORLD.Rank();
//
//        double[] arr = new double[100];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = rank * arr.length + i;
//        }
//
//        double localSum = 0;
//        for (double element : arr) {
//            localSum += element;
//        }
//
//        double[] totalSum = new double[1];
//        MPI.COMM_WORLD.Reduce(new double[]{localSum}, 0, totalSum, 0, 1, MPI.DOUBLE, MPI.SUM, 0);
//        if (rank == 0) {
//            System.out.println("Total sum: " + totalSum[0]);
//        }
//        MPI.Finalize();
//    }
//}