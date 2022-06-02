package appelli.laghetto;

import java.util.concurrent.Semaphore;

public class LaghettoSem extends Laghetto{
    private Semaphore mutex = new Semaphore(1);

    public LaghettoSem(int minPesci, int maxPesci) {
        super(minPesci, maxPesci);
    }

    @Override
    public void inizia(int t) throws InterruptedException{
        if (t == 0){
            mutex.acquire();
            pesca();
            System.out.println("P"+Thread.currentThread().getId()+" ha pescato un pesce");
            System.out.println(numPesci);
        }else{
            mutex.acquire();
            ripopola();
            System.out.println("A"+Thread.currentThread().getId()+" ha ripopolato 10 pesci");
            System.out.println(numPesci);
        }
    }

    private void pesca(){
        if (numPesci-1 > MIN_NUM_PESCI){
            numPesci--;
        }
    }

    private void ripopola(){
        if (numPesci+10 < MAX_NUM_PESCI){
            numPesci += 10;
        }
    }

    @Override
    public void finisci(int t) throws InterruptedException{
        if (t == 0){
            mutex.release();
        }else{
            mutex.release();
        }
    }

    public static void main(String[] args) {
        Laghetto lag = new LaghettoSem(50,200);
        System.out.println("numero iniziale di pesci: "+lag.numPesci);
        lag.test(40, 5);
    }
}
