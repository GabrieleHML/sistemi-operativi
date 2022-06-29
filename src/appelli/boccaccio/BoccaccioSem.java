package appelli.boccaccio;

import java.util.concurrent.Semaphore;

public class BoccaccioSem extends Boccaccio{
    Semaphore mutex = new Semaphore(1);
    Semaphore necessarioRiempimento = new Semaphore(0);
    Semaphore aspetta = new Semaphore(0);


    BoccaccioSem(int numColori) {
        super(numColori);
    }

    @Override
    public boolean prendi(int c) throws InterruptedException {
        mutex.acquire();
        if (caramelle[c] == 0){
            mutex.release();
            return false;
        }
        caramelle[c]--;
        System.out.printf("B%d prende la caramella %d\n", Thread.currentThread().getId(), c);
        for(int x : caramelle)
            System.out.printf("%d\t",x);
        System.out.println("\n");
        mutex.release();
        return true;
    }

    @Override
    public void piangi() throws InterruptedException {

        System.out.printf("B%d piange\n",Thread.currentThread().getId());
        mutex.acquire();
        bambiniChePiangono++;
        System.out.printf("Piangono %d bambini\n", bambiniChePiangono);
        if (bambiniChePiangono >= 3)
            necessarioRiempimento.release();
        mutex.release();
        aspetta.acquire();
    }



    @Override
    public void riempi() throws InterruptedException {
        necessarioRiempimento.acquire();
        System.out.println("Riempimento in corso...");
        mutex.acquire();
        riempimento();
        bambiniChePiangono = 0;
        aspetta.release(bambiniChePiangono);
        for(int x : caramelle)
            System.out.printf("%d\t",x);
        System.out.println("\n");
        mutex.release();
    }

    public static void main(String[] args) {
        Boccaccio b = new BoccaccioSem(3);
        b.test(20);
    }
}
