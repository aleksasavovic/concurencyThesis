package JITOptimizations.loopOptimization;

public class LoopOptimizationTest {
    public static void main(String[] args) {
        class LoopOptimization {
            boolean flag = true;
            int counter = 0;
        }
        for (int i = 0; i < 50; i++) {
            LoopOptimization loopOptimization = new LoopOptimization();
            Thread thread1 = new Thread(() -> {
                while (loopOptimization.flag) {
                    loopOptimization.counter++;
                    if (loopOptimization.counter == Integer.MAX_VALUE)
                        break;
                }
            });
            Thread thread2 = new Thread(() -> {
                loopOptimization.flag = false;
            });
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Iteration number " + i + "Counter value : " + loopOptimization.counter);
        }
    }
}

