package Q3;

public class TelemetryThread extends Thread {
    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();

        for (int i = 1; i <= 5; i++) {
            double usedMiB =
                (runtime.totalMemory() - runtime.freeMemory())
                / (1024.0 * 1024);

            System.out.printf(
                "[%s] sample=%d | available CPU cores=%d"
                + " | used JVM heap=%.2f MiB%n",
                getName(),
                i,
                runtime.availableProcessors(),
                usedMiB
            );

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                interrupt();

                System.out.println("Telemetry worker interrupted");
                return;
            }
        }
    }
}