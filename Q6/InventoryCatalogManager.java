package Q6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {
    private static final int WORKERS = 10;
    private static final int OPERATIONS_PER_WORKER = 50000;
    private static final int PRODUCTS = 1000;
    private static final int INITIAL_STOCK = 100;
    private static final int ROUNDS = 3;

        private static Map<String, Integer> createCatalog(boolean concurrent) {

        Map<String, Integer> catalog =
            Collections.synchronizedMap(new HashMap<>());

        for (int i = 0; i < PRODUCTS; i++) {
            catalog.put("P" + i, INITIAL_STOCK);
        }

        return catalog;
    }
        private static long transaction(
            Map<String, Integer> catalog,
            String key,
            boolean concurrent) {

        synchronized (catalog) {
            int observed = catalog.get(key);
            catalog.put(key, observed + 1);

            return observed;
        }
    }
        private static class Result {
        final long transactions;
        final long elapsedNs;
        final long finalStock;
        final long lookupChecksum;

        Result(
                long transactions,
                long elapsedNs,
                long finalStock,
                long lookupChecksum) {

            this.transactions = transactions;
            this.elapsedNs = elapsedNs;
            this.finalStock = finalStock;
            this.lookupChecksum = lookupChecksum;
        }
    }

    private static Result runBenchmark(
            boolean concurrent,
            int perWorker) throws Exception {

        Map<String, Integer> catalog =
            createCatalog(concurrent);

        String[] keys = new String[PRODUCTS];

        for (int i = 0; i < PRODUCTS; i++) {
            keys[i] = "P" + i;
        }

        ExecutorService pool =
            Executors.newFixedThreadPool(WORKERS);

        CountDownLatch ready =
            new CountDownLatch(WORKERS);

        CountDownLatch startGate =
            new CountDownLatch(1);

        List<Future<Long>> tasks = new ArrayList<>();

        try {
            for (int worker = 0;
                    worker < WORKERS;
                    worker++) {

                final int workerId = worker;

                tasks.add(pool.submit(() -> {
                    ready.countDown();
                    startGate.await();

                    long checksum = 0;

                    for (int op = 0; op < perWorker; op++) {
                        String key = keys[
                            (op + workerId * 31) % PRODUCTS
                        ];

                        checksum += transaction(
                            catalog,
                            key,
                            concurrent
                        );
                    }

                    return checksum;
                }));
            }

            if (!ready.await(1, TimeUnit.MINUTES)) {
                throw new IllegalStateException(
                    "Workers not ready"
                );
            }

            long start = System.nanoTime();
            startGate.countDown();

            long checksum = 0;

            for (Future<Long> task : tasks) {
                checksum += task.get();
            }

            long elapsedNs =
                System.nanoTime() - start;

            long totalStock = 0;

            for (int i = 0; i < PRODUCTS; i++) {
                totalStock += catalog.get(keys[i]);
            }

            long totalTransactions =
                (long) WORKERS * perWorker;

            long expectedStock =
                (long) PRODUCTS * INITIAL_STOCK
                + totalTransactions;

            if (totalStock != expectedStock) {
                throw new IllegalStateException(
                    "Lost stock updates"
                );
            }

            return new Result(
                totalTransactions,
                elapsedNs,
                totalStock,
                checksum
            );
        } finally {
            startGate.countDown();
            pool.shutdownNow();

            if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                throw new IllegalStateException(
                    "Inventory workers timed out"
                );
            }
        }
    }
             public static void main(String[] args) throws Exception {
        Result result = runBenchmark(
            false,
            OPERATIONS_PER_WORKER
        );

        System.out.println(
            "Completed transactions="
            + result.transactions
        );

        System.out.println(
            "Final stock=" + result.finalStock
        );
    }
}