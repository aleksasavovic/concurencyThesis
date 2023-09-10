package JITOptimizations.reordering;

public class TestReordering {
    private static final int numberOfIterations = 2500000;
    public static void main(String[]args) {
         class Reordering {
            int a;
            int b;
        }
        for (int i = 0; i < numberOfIterations; i++) {
            Reordering reordering = new Reordering();
            Thread writerThread  = new Thread(() -> {
                reordering.a += 1;
                reordering.b += 1;
                reordering.a += 1;

            });
            Thread readerThread  = new Thread(() -> {
                if(reordering.a == 2 && reordering.b==0)
                    System.out.println("unexpected, a=2 and b=0");
                else if(reordering.a==0 && reordering.b==1)
                    System.out.println("unexpected, a=0 and b=1");

            });
            writerThread.start();
            readerThread .start();
            try {
                writerThread.join();
                readerThread .join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
