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
        double total = 0.0;

        for (int i = start; i < end; i++) {
            total += values[i];
        }

        return total;
    }
}