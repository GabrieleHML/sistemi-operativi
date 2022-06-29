package appelli.cementificio;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CementificioLC extends Cementificio{
    Lock l = new ReentrantLock();
    Condition voglioEntrare = l.newCondition();
    Condition voglioPrelevare = l.newCondition();
    Condition necessarioRifornimento = l.newCondition();
    LinkedList<Thread> coda = new LinkedList<>();

    CementificioLC(int N, int P) {
        super(N, P);
    }

    private boolean mioTurno(Thread t){
        return coda.getFirst().equals(t);
    }

    @Override
    public void entra() throws InterruptedException {
        l.lock();
        try {
            System.out.printf("C%d vuole entrare\n", Thread.currentThread().getId());
            coda.addLast(Thread.currentThread());
            while(personeDentro==maxPersone || !mioTurno(Thread.currentThread()))
                voglioEntrare.await();
            personeDentro++;
            System.out.printf("C%d è entrato, persone dentro: %d\n", Thread.currentThread().getId(), personeDentro);
            coda.removeFirst();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void esci() throws InterruptedException {
        l.lock();
        try {
            personeDentro--;
            System.out.printf("C%d è uscito, persone dentro: %d\n", Thread.currentThread().getId(), personeDentro);
            voglioEntrare.signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void preleva(int n) throws InterruptedException {
        l.lock();
        try {
            for (int i = 0; i < n; i++) {
                while(numSacchi <= 0)
                    voglioPrelevare.await();
                numSacchi--;
                if (numSacchi == 0)
                    necessarioRifornimento.signal();
            }
            System.out.printf("C%d ha prelevato %d sacchi, sacchi rimanenti: %d\n", Thread.currentThread().getId(), n, numSacchi);
        }finally {
            l.unlock();
        }
    }

    @Override
    public void iniziaRifornimento() throws InterruptedException {
        l.lock();
        try {
            while(numSacchi > 0)
                necessarioRifornimento.await();
            System.out.println("L'addetto ha iniziato il rifornimento");
        }finally {
            l.unlock();
        }
    }

    @Override
    public void terminaRifornimento() throws InterruptedException {
        l.lock();
        try {
            numSacchi = maxSacchi;
            System.out.println("L'addetto ha terminato il rifornimento");
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Cementificio cem = new CementificioLC(10, 300);
        cem.test();
    }
}
