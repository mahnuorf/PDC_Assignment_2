package Q2;

public class RequestQueue {
    private final char[] data;
    private int front;
    private int rear;
    private int size;

    public RequestQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(
                "Capacity must be positive"
            );
        }

        data = new char[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }
}