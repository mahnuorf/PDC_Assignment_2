package Q4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    private static double serial(double[] values, double cap) {
        double total = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > cap) {
                values[i] *= 0.95;
            }

            total += values[i];
        }

        return total;
    }

    private static double parallel(
            ForkJoinPool pool,
            double[] values,
            double cap) {

        pool.invoke(
            new PortfolioThresholdTask(
                values, 0, values.length, cap
            )
        );

        return pool.invoke(
            new ParallelRiskCalculator(
                values, 0, values.length
            )
        );
    }

    public static void main(String[] args) {
        final double cap = 1000;

        double[] original = new double[10000];
        Random random = new Random(42);

        double initialRisk = 0;
        int adjusted = 0;

        for (int i = 0; i < original.length; i++) {
            original[i] = 250 + random.nextDouble() * 1500;
            initialRisk += original[i];

            if (original[i] > cap) {
                adjusted++;
            }
        }

        ForkJoinPool pool = new ForkJoinPool();

        try {
            double[] sequentialValues = original.clone();
            double[] parallelValues = original.clone();

            long serialStart = System.nanoTime();

            double serialRisk =
                serial(sequentialValues, cap);

            long serialNs =
                System.nanoTime() - serialStart;

            long parallelStart = System.nanoTime();

            double parallelRisk =
                parallel(pool, parallelValues, cap);

            long parallelNs =
                System.nanoTime() - parallelStart;

            boolean match =
                Math.abs(serialRisk - parallelRisk)
                <= 1e-9 * Math.max(1.0, Math.abs(serialRisk));

            for (int i = 0; i < original.length; i++) {
                if (Math.abs(
                        sequentialValues[i] - parallelValues[i]
                    ) > 1e-9) {

                    match = false;
                }
            }

            if (!match) {
                throw new IllegalStateException(
                    "Portfolio results differ"
                );
            }

            System.out.printf(
                "Assets: %d | threshold: 1000"
                + " | pool parallelism: %d%n",
                original.length,
                pool.getParallelism()
            );

            System.out.printf(
                "Risk cap: %.2f | adjusted assets: %d"
                + " | penalty: 5%%%n",
                cap,
                adjusted
            );

            System.out.printf(
                "Initial risk: %.6f%n"
                + "Serial adjusted risk: %.6f%n"
                + "Parallel adjusted risk: %.6f%n",
                initialRisk,
                serialRisk,
                parallelRisk
            );

            System.out.println("Results match: " + match);
        } finally {
            pool.shutdown();
        }
    }
}