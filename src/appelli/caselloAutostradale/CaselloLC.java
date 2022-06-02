package appelli.caselloAutostradale;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaselloLC extends Casello{
    private Lock l = new ReentrantLock();
    private Condition possoAccedere = l.newCondition();
    private boolean[] portaOccupata = new boolean[numPorte]; // false -> NON è occupata ; true -> occupata

    public CaselloLC(int numPorte, int tariffaKm) {
        super(numPorte, tariffaKm);
        for (int i = 0; i < numPorte; i++) {
            codeVeicoli[i] = new LinkedList<>();
            portaOccupata[i] = false;
        }
    }

    private boolean mioTurno(int n){
        return Thread.currentThread().getId() == codeVeicoli[n].getFirst();
    }

    @Override
    public void scegliPorta(int n) throws InterruptedException {
        l.lock();
        try {
            while (portaOccupata[n])
                possoAccedere.await();
            if (mioTurno(n)){
                portaOccupata[n] = true;
                codeVeicoli[n].addLast(Thread.currentThread().getId());
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void effettuaPagamento(int x, int n) throws InterruptedException {
        l.lock();
        try {
            incasso += tariffaKm * x;
            System.out.println("incasso "+incasso);
            codeVeicoli[n].removeFirst();
            portaOccupata[n] = false;
            possoAccedere.signalAll();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Casello c = new CaselloLC(2, 5);
        c.test(10);
    }
}
