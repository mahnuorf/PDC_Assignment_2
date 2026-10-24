package Q2;

public class PacketSymmetryAuditor {
    private static boolean audit(String payload) {
        int capacity = Math.max(1, payload.length());

        TransactionStack stack = new TransactionStack(capacity);
        RequestQueue queue = new RequestQueue(capacity);

        for (int i = 0; i < payload.length(); i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean symmetric = true;
        int comparisons = 0;

        while (!stack.isEmpty() && !queue.isEmpty()) {
            char reverse = stack.pop();
            char forward = queue.dequeue();

            comparisons++;

            System.out.printf(
                "Pair %d: stack='%c', queue='%c', match=%s%n",
                comparisons, reverse, forward, reverse == forward
            );

            if (reverse != forward) {
                symmetric = false;
            }
        }

        if (!stack.isEmpty() || !queue.isEmpty()) {
            symmetric = false;
        }

        System.out.println(
            "Compared characters: " + comparisons
        );

        return symmetric;
    }

    public static void main(String[] args) {
        String payload = args.length == 0 ? "RADAR" : args[0];

        System.out.println("Payload: [" + payload + "]");
        System.out.println("Symmetric: " + audit(payload));
    }
}