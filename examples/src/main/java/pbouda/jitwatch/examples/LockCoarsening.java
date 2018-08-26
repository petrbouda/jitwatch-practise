package pbouda.jitwatch.examples;

public class LockCoarsening {

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            sync();
        }
    }

    private static void sync() {
        synchronized (LockCoarsening.class) {
            System.out.println();
        }
        synchronized (LockCoarsening.class) {
            System.out.println();
        }
        synchronized (LockCoarsening.class) {
            System.out.println();
        }
    }
}