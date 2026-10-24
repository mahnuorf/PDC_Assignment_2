package Q3;

public class LogProcessorTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf(
                "[%s] log batch=%d | timestamp=%d%n",
                Thread.currentThread().getName(),
                i,
                System.currentTimeMillis()
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                System.out.println("Log worker interrupted");
                return;
            }
        }
    }
}