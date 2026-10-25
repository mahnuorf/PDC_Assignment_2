package Q5;

import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) {
        final int count = 50000;
        final int workers = 4;

        double[] data = new double[count];
        Random random = new Random(42);

        for (int i = 0; i < count; i++) {
            data[i] = random.nextDouble() * 100;
        }

        data[12499] = -40;
        data[37500] = 150;

        System.out.println("Sensor count: " + count);

        for (int part = 0; part < workers; part++) {
            int start = part * count / workers;
            int end = (part + 1) * count / workers;

            System.out.printf(
                "Partition %d: [%d, %d)%n",
                part + 1,
                start,
                end
            );
        }
    }
}