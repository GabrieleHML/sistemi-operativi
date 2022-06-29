package appelli.boccaccio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoccaccioLC extends Boccaccio{
    Lock l = new ReentrantLock();
    Condition voglioPrendere = l.newCondition();
    Condition piangi = l.newCondition();
    Condition voglioRiempire = l.newCondition();
    Condition aspetta = l.newCondition();
    boolean puoiPiangere = false;
    boolean possoRiempire = false;

    BoccaccioLC(int numColori) {
        super(numColori);
    }

    @Override
    public boolean prendi(int c) throws InterruptedException {
        l.lock();
        try {
            if (caramelle[c] == 0){
                puoiPiangere = true;
                possoRiempire = true;
                piangi.signal();
                return false;
            }
            while(caramelle[c] == 0)
                voglioPrendere.await();
            caramelle[c]--;
            System.out.printf("B%d prende la caramella %d\n", Thread.currentThread().getId(), c);
            for(int x : caramelle)
                System.out.printf("%d\t",x);
            System.out.println("\n");
            return true;
        }finally {
            l.unlock();
        }
    }

    @Override
    public void piangi() throws InterruptedException {
        l.lock();
        try {
            while(!puoiPiangere)
                piangi.await();
            System.out.printf("B%d piange\n",Thread.currentThread().getId());
            bambiniChePiangono++;
            System.out.printf("Piangono %d bambini\n", bambiniChePiangono);
            if (bambiniChePiangono >= 3)
                voglioRiempire.signal();
            while(bambiniChePiangono >= 3)
                aspetta.await();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void riempi() throws InterruptedException {
        l.lock();
        try {
            while(!possoRiempire)
                voglioRiempire.await();
            System.out.println("Riempimento in corso...");
            riempimento();
            for(int x : caramelle)
                System.out.printf("%d\t",x);
            System.out.println("\n");
            possoRiempire = false;
            puoiPiangere = false;
            bambiniChePiangono = 0;
            aspetta.signal();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Boccaccio b = new BoccaccioLC(3);
        b.test(20);
    }
}
