package Q4;

import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;

    private final double[] values;
    private final int start;
    private final int end;
    private final double riskCap;

    public PortfolioThresholdTask(
            double[] values,
            int start,
            int end,
            double riskCap) {

        this.values = values;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }

    @Override
    protected void compute() {
    }
}