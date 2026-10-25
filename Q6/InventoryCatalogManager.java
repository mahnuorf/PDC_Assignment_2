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

    public static void main(String[] args) {
        System.out.println(
            "Workers=" + WORKERS
            + ", transactions per worker="
            + OPERATIONS_PER_WORKER
        );
    }
}