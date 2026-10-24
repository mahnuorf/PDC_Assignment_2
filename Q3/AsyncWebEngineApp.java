package Q3;

public class AsyncWebEngineApp {
    public static void main(String[] args)
            throws InterruptedException {

        System.out.println(
            "Driver thread: " + Thread.currentThread().getName()
        );

        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");

        Thread logs = new Thread(
            new LogProcessorTask(),
            "Log-Worker"
        );

        System.out.printf(
            "Before start: telemetry=%s, logs=%s%n",
            telemetry.getState(),
            logs.getState()
        );
                telemetry.start();
        logs.start();

        System.out.printf(
            "After start: telemetry=%s, logs=%s%n",
            telemetry.getState(),
            logs.getState()
        );
                telemetry.join();
        logs.join();

        System.out.printf(
            "After join: telemetry=%s, logs=%s%n",
            telemetry.getState(),
            logs.getState()
        );

        System.out.println(
            "Both workers completed five iterations."
        );
    }
}