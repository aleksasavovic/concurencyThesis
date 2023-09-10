package intrinsic;

public class SynchronizedMethodsAndBlocks {
    public synchronized void m1(){
        //kritična sekcija, zaključavanje na tekućem objektu
    }
    public void m2(){
        synchronized (this){
            //kritična sekcija, zaključavanje na tekućem objektu
        }
    }
    public static synchronized void m3(){
        //kritična sekcija, zaključavanje na klasnom objektu
    }
    public static void m4(){
        synchronized (SynchronizedMethodsAndBlocks.class){
            //kritična sekcija, zaključavanje na klasnom objektu
        }
    }
}
