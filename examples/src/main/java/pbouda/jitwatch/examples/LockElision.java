package pbouda.jitwatch.examples;

public class LockElision {

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            sync();
        }
    }

    private static String sync() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("C1");
        buffer.append("C2");
        return buffer.toString();
    }
}