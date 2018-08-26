package pbouda.jitwatch.examples;

public class UncommonTrap {

    /**
     * 1. Only Compilation
     * 2. With FALSE to be caught in uncommon trap
     * 3. Is there any way how to see that the code is interpreted again?
     */
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 20_000; i++) {
            sum += hotMethod(true);
        }

        measuredHotMethod(true);
        measuredHotMethod(false);
    }

    private static void measuredHotMethod(boolean bool) {
        long start = System.nanoTime();
        hotMethod(bool);
        System.out.println(System.nanoTime() - start);
    }

    private static int hotMethod(boolean bool) {
        int counter;

        if (bool) {
            counter = 1;
        } else {
            counter = 2;
        }

        return counter;
    }
}
