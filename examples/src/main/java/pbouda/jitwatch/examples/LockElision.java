package pbouda.jitwatch.examples;

public class LockElision {

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            sync();
        }
    }

    private static void sync() {
        synchronized (LockElision.class) {
            System.out.println();
        }
        synchronized (LockElision.class) {
            System.out.println();
        }
        synchronized (LockElision.class) {
            System.out.println();
        }
    }
}