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
        public static void main(String[] args) {
        CacheLocalityBenchmark benchmark =
            new CacheLocalityBenchmark();

        System.out.println(
            "First=" + benchmark.matrix[0][0]
            + ", last="
            + benchmark.matrix[N - 1][N - 1]
        );
    }
}