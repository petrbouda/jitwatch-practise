package pbouda.jitwatch.examples;

public class UncommonTrap {

    public static void main(String[] args) throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < 20_000; i++) {
            sum += hotMethod(true);
        }

        Thread.sleep(2000);
        System.out.println(sum);
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
