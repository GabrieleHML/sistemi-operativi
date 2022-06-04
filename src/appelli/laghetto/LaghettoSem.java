package appelli.laghetto;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class LaghettoSem extends Laghetto{
    // int MIN_NUM_PESCI, int MAX_NUM_PESCI, int numPesci
    private Semaphore mutex = new Semaphore(1);
    private Semaphore mutexPesci = new Semaphore(1);
    private Semaphore mutexLaghetto = new Semaphore(1);
    private int[] numPersone = new int[2];
    private boolean[] primo = new boolean[2];

    public LaghettoSem(int minPesci, int maxPesci) {
        super(minPesci, maxPesci);
        Arrays.fill(numPersone, 0);
        Arrays.fill(primo, false);
    }

    @Override
    public void inizia(int t) throws InterruptedException{
        mutex.acquire();
        if (primo[t] && numPersone[t] == 1)
            mutexLaghetto.acquire();
        numPersone[t]++;
        mutex.release();
    }

    @Override
    public void finisci(int t) throws InterruptedException{
        mutex.acquire();
        mutexPesci.acquire();
        if (t == 0)
            if (numPesci - 1 > MIN_NUM_PESCI){
                numPesci--;
                System.out.println("P"+Thread.currentThread().getId()+" ha pescato un pesce");
                System.out.println(numPesci);
            }

        else
            if (numPesci + 10 < MAX_NUM_PESCI){
                numPesci += 10;
                System.out.println("A"+Thread.currentThread().getId()+" ha ripopolato 10 pesci");
                System.out.println(numPesci);
            }

        mutexPesci.release();

        if (numPersone[t] == 1)
            primo[t] = !primo[t];
        if (!primo[t] && numPersone[t] == 1)
            mutexLaghetto.release();

        numPersone[t]--;
        mutex.release();
    }



    public static void main(String[] args) {
        Laghetto lag = new LaghettoSem(50,200);
        System.out.println("numero iniziale di pesci: "+lag.numPesci);
        lag.test(40, 5);
    }
}
