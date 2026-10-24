package Q3;

public class LogProcessorTask implements Runnable {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf(
                "[%s] log batch=%d%n",
                Thread.currentThread().getName(),
                i
            );
        }
    }
}