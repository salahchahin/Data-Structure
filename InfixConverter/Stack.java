
public class Stack<T> {

    protected T[] list;
    protected int maxSize;
    protected int stackTop;

    public Stack() {
        maxSize = 100;
        stackTop = 0;
        list = (T[]) new Object[maxSize];
    }

    public Stack(int size) {
        if (size <= 0) {
            System.out.println("Stack overflow error: Stack size can not be negative");
            maxSize = 100;
        } else {
            maxSize = size;
        }
        stackTop = 0;
        list = (T[]) new Object[maxSize];
    }

    public boolean isEmpty() {
        return stackTop == 0;
    }

    public boolean isFull() {
        return stackTop == maxSize;

    }

    public int size() {
        return stackTop;
    }

    public void push(T item) {
        if (isFull()) {
            System.out.println("Stack overflow error: Stack is full");
            return;
        }
        list[stackTop] = item;
        stackTop++;
    }

    public T top() {
        if (isEmpty()) {
            System.out.println("Stack underflow error: Stack is Empty");
            return null;
        }
        return list[stackTop - 1];
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow error: Stack is Empty");
            return null;
        }
        return list[stackTop-- - 1];
    }

}
