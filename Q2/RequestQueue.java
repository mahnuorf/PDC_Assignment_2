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
        public void enqueue(char c) {
        if (size == data.length) {
            throw new IllegalStateException("Queue overflow");
        }

        data[rear] = c;
        rear = (rear + 1) % data.length;
        size++;
    }
        public char dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue underflow");
        }

        char value = data[front];

        front = (front + 1) % data.length;
        size--;

        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}