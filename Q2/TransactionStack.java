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
        public void push(char c) {
        if (top == data.length - 1) {
            throw new IllegalStateException("Stack overflow");
        }

        data[++top] = c;
    }
        public char pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }

        return data[top--];
    }

    public char peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }

        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}