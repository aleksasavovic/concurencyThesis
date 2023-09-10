package lockCost.lockCoarsening;

public class CoarseningExample {
    Object object;
    public synchronized void m1() {
        //uradi nešto u m1
    }

    public synchronized void m2(int a) {
        synchronized(object){
            //kritična sekcija 1
            //funkcionalnost bloka 1
        }

        synchronized (object){
            //kritična sekcija 2
            //funkcionalnost bloka 2
        }
    }

}
