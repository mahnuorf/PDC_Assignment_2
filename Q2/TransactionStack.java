package Q2;

public class TransactionStack {
    private final char[] data;
    private int top;

    public TransactionStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(
                "Capacity must be positive"
            );
        }

        data = new char[capacity];
        top = -1;
    }
}