package appelli.laghetto;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaghettoLC extends Laghetto{
    // int MIN_NUM_PESCI, int MAX_NUM_PESCI, numPesci
    private Lock l = new ReentrantLock();
    private Condition possoPescare = l.newCondition();
    private Condition possoRipopolare = l.newCondition();

    public LaghettoLC(int minPesci, int maxPesci) {
        super(minPesci, maxPesci);
    }

    @Override
    public void inizia(int t) throws InterruptedException {
        l.lock();
        try {
            if (t == 0){ // turno pescatore
                while (numPesci - 1  < MIN_NUM_PESCI)
                    possoPescare.await();
                numPesci--;
                System.out.println("P"+Thread.currentThread().getId()+" ha pescato un pesce");
                System.out.println(numPesci);
            }else{ // turno addetti
                while (numPesci + 10 > MAX_NUM_PESCI)
                    possoRipopolare.await();
                numPesci += 10;
                System.out.println("P"+Thread.currentThread().getId()+" ha ripopolato 10 pesci");
                System.out.println(numPesci);
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void finisci(int t) throws InterruptedException {
        l.lock();
        try {
            if (t == 0){
                possoPescare.signalAll();
            }else{
                possoRipopolare.signalAll();
            }
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Laghetto lag = new LaghettoSem(50,200);
        System.out.println("Numero iniziale di pesci: "+lag.numPesci);
        lag.test(40, 5);
    }
}
