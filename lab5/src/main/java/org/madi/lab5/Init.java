package org.madi.lab5;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class Init implements Callable<double[]> {
    private boolean isAvailable;

    public double[] call() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Service service = new Service();
        Statistic statistic = new Statistic(service);

        executor.execute(new Consumer(service));
        if (isAvailable) {
            executor.execute(new Spectator(service));
        }
        executor.execute(new Producer(service));
        executor.execute(statistic);
        executor.shutdown();
        System.out.println("System is started...");

        try {
            boolean isAwaited = executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new double[]{service.calcRejectedPercentage(), statistic.getAvgQueueLength()};
    }
}
