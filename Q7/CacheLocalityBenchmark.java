package Q7;

public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static final int REPETITIONS = 10;

    private static volatile double sink;

    private final double[][] matrix =
        new double[N][N];

    public static void main(String[] args) {
        CacheLocalityBenchmark benchmark =
            new CacheLocalityBenchmark();

        System.out.println(
            "Matrix allocated: " + N + " x " + N
        );
    }
}