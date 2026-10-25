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
          public static void main(String[] args) {
        Map<String, Integer> catalog =
            createCatalog(false);

        transaction(catalog, "P0", false);

        System.out.println(
            "Stock after one increment="
            + catalog.get("P0")
        );
    }
}