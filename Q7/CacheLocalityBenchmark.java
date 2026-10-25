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
     public static void main(String[] args) {
        CacheLocalityBenchmark benchmark =
            new CacheLocalityBenchmark();

        System.out.println(
            "Row-major sum="
            + benchmark.traverseRowMajor()
        );
                System.out.println(
            "Column-major sum="
            + benchmark.traverseColumnMajor()
        );
    }
}