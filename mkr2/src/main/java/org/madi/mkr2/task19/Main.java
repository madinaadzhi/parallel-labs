package org.madi.mkr2.task19;

import mpi.*;

public class Main {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int size = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();

        if (rank == 2) {
            double[] temperatureData = generateTemperatureData();
            MPI.COMM_WORLD.Send(temperatureData, 0, temperatureData.length, MPI.DOUBLE, 0, 0);
        }

        if (rank == 0) {
            double[] receivedData = new double[30];
            MPI.COMM_WORLD.Recv(receivedData, 0, receivedData.length, MPI.DOUBLE, 2, 0);
            System.out.println("Received temperature data from rank 2:");
            for (int i = 0; i < receivedData.length; i++) {
                System.out.println("Day " + (i + 1) + ": " + receivedData[i]);
            }
        }
        MPI.Finalize();
    }

    private static double[] generateTemperatureData() {
        double[] temperatureData = new double[30];
        return temperatureData;
    }
}
