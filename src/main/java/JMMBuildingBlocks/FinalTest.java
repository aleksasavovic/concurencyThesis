package JMMBuildingBlocks;

public class FinalTest {
    public static void main(String[] args) {
        class FinalFreezeAction{
            int nonFinalVariable;
            final int finalVariable;

            static FinalFreezeAction instance;

            FinalFreezeAction(){
                finalVariable = 9;
                nonFinalVariable = 10;
            }
            static void writerThreadMethod(){
                instance = new FinalFreezeAction();
            }

             static void readerThreadMethod() {
                if(instance!=null){
                    int a = instance.finalVariable;
                    int b = instance.nonFinalVariable;
                }
            }
        }
        int numTests = 25000000; // Number of test iterations

        for (int i = 0; i < numTests; i++) {
            FinalFreezeAction.instance=null;
            Thread writerThread = new Thread(() -> {
                FinalFreezeAction.writerThreadMethod();
            });

            Thread readerThread = new Thread(() -> {
                FinalFreezeAction.readerThreadMethod();
            });

            // Start the threads
            writerThread.start();
            readerThread.start();

            try {
                // Wait for both threads to complete
                writerThread.join();
                readerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
