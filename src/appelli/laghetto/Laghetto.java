package appelli.laghetto;

import java.util.Random;

public abstract class Laghetto {
    final int MIN_NUM_PESCI;
    final int MAX_NUM_PESCI;
    private Random r = new Random();
    int numPesci;

    public Laghetto(int minPesci, int maxPesci){
        MIN_NUM_PESCI = minPesci;
        MAX_NUM_PESCI = maxPesci;
        numPesci = 60;//r.nextInt(minPesci, maxPesci);
    }

    public abstract void inizia(int t) throws InterruptedException;

    public abstract void finisci(int t) throws InterruptedException;

    public void test(int numPescatori, int numAddetti){
        for (int i = 0; i < numPescatori; i++) {
            new Pescatore(this).start();
        }
        for (int i = 0; i < numAddetti; i++) {
            new Addetto(this).start();
        }
    }
}
