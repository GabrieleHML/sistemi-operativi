package appelli.computer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ComputerLC extends Computer{

    Lock l = new ReentrantLock();
    Condition voglioUtilizzare = l.newCondition();
    int[] coreLiberi = new int[2];
    LinkedList<Thread> coda = new LinkedList<>();

    ComputerLC(){
        super();
        coreLiberi[0] = 4;
        coreLiberi[1] = 4;
    }

    private boolean mioTurno(Thread t){
        return coda.getFirst().equals(t);
    }

    @Override
    public void richiedi(int nCore, int idP) throws InterruptedException {
        l.lock();
        try {
            coda.addLast(Thread.currentThread());
            while(coreLiberi[idP] < nCore && mioTurno(Thread.currentThread()))
                voglioUtilizzare.await();
            coreLiberi[idP] -= nCore;
            coda.removeFirst();
            System.out.printf("U%d richiede (%d, %d)%n", Thread.currentThread().getId(), nCore, idP);
            System.out.println("Core liberi: "+coreLiberi[idP]);
        }finally {
            l.unlock();
        }
    }

    @Override
    public void rilascia(int nCore, int idP) throws InterruptedException {
        l.lock();
        try {
            coreLiberi[idP] += nCore;
            System.out.printf("U%d rilascia %d core%n", Thread.currentThread().getId(), nCore);
            System.out.println("Core liberi: "+coreLiberi[idP]);
            voglioUtilizzare.signalAll();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Computer c = new ComputerSem();
        c.test();
    }
}
