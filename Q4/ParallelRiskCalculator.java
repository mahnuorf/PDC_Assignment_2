package Q4;

import java.util.concurrent.RecursiveTask;

public class ParallelRiskCalculator extends RecursiveTask<Double> {
    private static final int THRESHOLD = 1000;

    private final double[] values;
    private final int start;
    private final int end;

    public ParallelRiskCalculator(
            double[] values,
            int start,
            int end) {

        this.values = values;
        this.start = start;
        this.end = end;
    }

          @Override
    protected Double compute() {
        if (end - start <= THRESHOLD) {
            double total = 0.0;

            for (int i = start; i < end; i++) {
                total += values[i];
            }

            return total;
        }

        int mid = start + (end - start) / 2;

        ParallelRiskCalculator left =
            new ParallelRiskCalculator(values, start, mid);

        ParallelRiskCalculator right =
            new ParallelRiskCalculator(values, mid, end);

        left.fork();
        right.fork();

        return left.join() + right.join();
    }
}