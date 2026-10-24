package Q4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        double[] values = new double[10000];
        Random random = new Random(42);

        for (int i = 0; i < values.length; i++) {
            values[i] = 250 + random.nextDouble() * 1500;
        }

        ForkJoinPool pool = new ForkJoinPool();

        try {
            double before = pool.invoke(
                new ParallelRiskCalculator(
                    values, 0, values.length
                )
            );

            pool.invoke(
                new PortfolioThresholdTask(
                    values, 0, values.length, 1000
                )
            );

            double after = pool.invoke(
                new ParallelRiskCalculator(
                    values, 0, values.length
                )
            );

            System.out.printf(
                "Assets: %d | risk before: %.6f"
                + " | risk after: %.6f%n",
                values.length,
                before,
                after
            );
        } finally {
            pool.shutdown();
        }
    }
}