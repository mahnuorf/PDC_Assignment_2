package Q5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws Exception {
        final int count = 50000;
        final int workers = 4;

        double[] data = new double[count];
        Random random = new Random(42);

        for (int i = 0; i < count; i++) {
            data[i] = random.nextDouble() * 100;
        }

        data[12499] = -40;
        data[37500] = 150;

        long startTime = System.nanoTime();

        ExecutorService pool =
            Executors.newFixedThreadPool(workers);

        List<Future<Double>> mins = new ArrayList<>();
        List<Future<Double>> maxes = new ArrayList<>();

        try {
            for (int part = 0; part < workers; part++) {
                int start = part * count / workers;
                int end = (part + 1) * count / workers;

                SensorSearchEngine minTask =
                    new ParallelMinSearch(data, start, end);

                SensorSearchEngine maxTask =
                    new ParallelMaxSearch(data, start, end);

                mins.add(
                    pool.submit(() -> minTask.processGrid())
                );

                maxes.add(
                    pool.submit(() -> maxTask.processGrid())
                );
            }

                        double globalMin = Double.POSITIVE_INFINITY;
            double globalMax = Double.NEGATIVE_INFINITY;

            for (int part = 0; part < workers; part++) {
                globalMin = Math.min(
                    globalMin,
                    mins.get(part).get()
                );

                globalMax = Math.max(
                    globalMax,
                    maxes.get(part).get()
                );
            }

            double elapsedMs =
                (System.nanoTime() - startTime)
                / 1_000_000.0;

            System.out.println(
                "Sensor count: " + count
                + " | worker threads: " + workers
            );

            System.out.println(
                "Global minimum: " + globalMin
            );

            System.out.println(
                "Global maximum: " + globalMax
            );
                        double serialMin = Double.POSITIVE_INFINITY;
            double serialMax = Double.NEGATIVE_INFINITY;

            for (double value : data) {
                serialMin = Math.min(serialMin, value);
                serialMax = Math.max(serialMax, value);
            }

            boolean correct =
                globalMin == serialMin
                && globalMax == serialMax;

            if (!correct) {
                throw new IllegalStateException(
                    "Sensor extrema mismatch"
                );
            }

            System.out.println(
                "Sequential verification: " + correct
            );

            System.out.printf(
                "Parallel search time: %.3f ms%n",
                elapsedMs
            );

            System.out.println(
                "Eight min/max tasks share four workers; "
                + "all are submitted before waiting."
            );
        } finally {
            pool.shutdown();

            if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                pool.shutdownNow();

                throw new IllegalStateException(
                    "Sensor workers timed out"
                );
            }
        }
    }
}