package JMMBuildingBlocks;

public class OptimizationTest {
    private static final int numberOfIterations = 2500000;

    public static void main(String[] args) {
        class Optimizations {
            boolean flag;
            int value;
        }
        for (int i = 0; i < numberOfIterations; i++) {
            Optimizations optimizations = new Optimizations();
            Thread thread1 = new Thread(() -> {
                while (!optimizations.flag);
                if (optimizations.value == 9)
                    System.out.println("correct");
            });
            Thread thread2 = new Thread(() -> {
                optimizations.value = 9;
                optimizations.flag = true;
            });
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
