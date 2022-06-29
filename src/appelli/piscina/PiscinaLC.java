package appelli.piscina;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PiscinaLC extends Piscina{
    int postiLiberi;
    Lock l = new ReentrantLock();
    Condition voglioEntrare = l.newCondition();
    LinkedList<Thread> coda = new LinkedList<>();

    PiscinaLC(int numCorsie, int personeXCorsia) {
        super(numCorsie, personeXCorsia);
        postiLiberi = numCorsie * personeXCorsia;
    }

    private boolean mioTurno(Thread t){
        return coda.getFirst().equals(t);
    }

    @Override
    public void entra() throws InterruptedException {
        l.lock();
        try {
            coda.addLast(Thread.currentThread());
            System.out.printf("P%d vuole entrare\n", Thread.currentThread().getId());
            while(postiLiberi<=0 && !mioTurno(Thread.currentThread()))
                voglioEntrare.await();
            if (postiLiberi > 0){
                postiLiberi--;
                coda.removeFirst();
                System.out.printf("P%d è entrato nella piscina\n", Thread.currentThread().getId());
                System.out.println("Posti liberi: "+postiLiberi);
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void esci() throws InterruptedException {
        l.lock();
        try {
            postiLiberi++;
            voglioEntrare.signalAll();
            System.out.printf("P%d è uscito nella piscina\n", Thread.currentThread().getId());
            System.out.println("Posti liberi: "+postiLiberi);
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Piscina p = new PiscinaLC(10, 4);
        p.test();
    }
}
