package pbouda.jitwatch.examples;

public class UncommonTrap {

    static volatile boolean marker;
    static volatile boolean trap;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            hotMethod(true);
        }

        hotMethod(false);
//        measuredHotMethod(true);
//        measuredHotMethod(false);
//
//        for (int i = 0; i < 1; i++) {
//            hotMethod(true);
//        }
    }
//
//    private static void measuredHotMethod(boolean bool) {
//        long start = System.nanoTime();
//        hotMethod(bool);
//        System.out.println(bool + " - " + (System.nanoTime() - start) + " ns");
//    }

    private static void hotMethod(boolean bool) {
        if (bool) {
            marker = true;
        } else {
            trap = true;
        }
    }
}