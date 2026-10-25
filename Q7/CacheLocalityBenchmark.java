package Q7;

public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static final int REPETITIONS = 10;

    private static volatile double sink;

    private final double[][] matrix =
        new double[N][N];
    public CacheLocalityBenchmark() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = (i + j) % 100;
            }
        }
    }
        public double traverseRowMajor() {
        double sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }
        public double traverseColumnMajor() {
        double sum = 0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }
        private long measure(boolean rowMajor) {
        long start = System.nanoTime();

        double sum = rowMajor
            ? traverseRowMajor()
            : traverseColumnMajor();

        long elapsed = System.nanoTime() - start;

        sink = sum;

        return elapsed;
    }
        public static void main(String[] args) {
        CacheLocalityBenchmark benchmark =
            new CacheLocalityBenchmark();

        double expected = 198000000.0;

        double rowSum =
            benchmark.traverseRowMajor();

        double columnSum =
            benchmark.traverseColumnMajor();

        boolean match =
            rowSum == expected
            && columnSum == expected;

        if (!match) {
            throw new IllegalStateException(
                "Matrix sums differ"
            );
        }

        System.out.printf(
            "Matrix: %d x %d | row sum=%.0f"
            + " | column sum=%.0f | sums match=%s%n",
            N,
            N,
            rowSum,
            columnSum,
            match
        );
            for (int warmup = 0; warmup < 3; warmup++) {
            sink = benchmark.traverseRowMajor();
            sink = benchmark.traverseColumnMajor();
        }
        

        long rowTotal = 0;
        long columnTotal = 0;

        for (int iteration = 1;
                iteration <= REPETITIONS;
                iteration++) {

            long rowNs = benchmark.measure(true);
            long columnNs = benchmark.measure(false);

            rowTotal += rowNs;
            columnTotal += columnNs;

            System.out.printf(
                "Iteration %2d | row=%.3f ms"
                + " | column=%.3f ms%n",
                iteration,
                rowNs / 1_000_000.0,
                columnNs / 1_000_000.0
            );
        }
                System.out.printf(
            "Measured repetitions per traversal: %d%n",
            REPETITIONS
        );

        System.out.printf(
            "Average row-major time: %.3f ms%n",
            rowTotal / (REPETITIONS * 1_000_000.0)
        );

        System.out.printf(
            "Average column-major time: %.3f ms%n",
            columnTotal / (REPETITIONS * 1_000_000.0)
        );

        System.out.printf(
            "Row-major speedup (column / row): %.3f x%n",
            (double) columnTotal / rowTotal
        );

        System.out.printf(
            "Consumed checksum: %.0f%n",
            sink
        );

    }
}