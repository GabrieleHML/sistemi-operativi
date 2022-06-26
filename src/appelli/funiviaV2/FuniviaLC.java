package appelli.funiviaV2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuniviaLC extends Funivia{
    // int turno, int[2] postiLiberi, ArList<Long> listaID
    private Lock l = new ReentrantLock();
    private Condition possoSalire = l.newCondition(); // quando i postiLiberi == 0 e inCima == false
    private Condition possoScendere = l.newCondition(); // turno==0 -> postiLiberi == 6 ; turno==1 -> postiLiberi == 3
    private Condition possoEntrare = l.newCondition(); // quando postiLiberi > 0
    private Condition possoUscire = l.newCondition(); // quando sono arrivati in cima
    private boolean inCima;

    @Override
    public void pilotaStart() throws InterruptedException {
        l.lock();
        try {
            inCima = false;
            possoEntrare.signalAll();
            while (postiLiberi[turno] != 0 || !inCima)
                possoSalire.await();
            System.out.println("La funivia sta salendo!");
        }finally {
            l.unlock();
        }
    }

    @Override
    public void pilotaEnd() throws InterruptedException {
        l.lock();
        try {
            stampa(turno);
            inCima = true;
            possoUscire.signalAll();
            if (turno == 0){
                while(postiLiberi[0] != 6)
                    possoScendere.await();
            }else{
                while (postiLiberi[1] != 3)
                    possoScendere.await();
            }
            listaID.clear();
            turno = 1 - turno;
        }finally {
            l.unlock();
        }
    }

    @Override
    public void turistaSali(int t) throws InterruptedException {
        l.lock();
        try {
            while (postiLiberi[t] <= 0 && turno != t)
                possoEntrare.await();
            postiLiberi[t]--;
            listaID.add(Thread.currentThread().getId());
            if (postiLiberi[t] == 0)
                possoSalire.signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        l.lock();
        try {
            while (!inCima && turno != t)
                possoUscire.await();
            postiLiberi[t]++;
            if ((turno == 0 && postiLiberi[0] == 6) || (turno == 1 && postiLiberi[1] == 3))
                possoScendere.signalAll();
        }finally {
            l.unlock();
        }
    }

    private void stampa(int t){
        if (t == 0){
            System.out.println("Turno dei turisti a piedi!");
        }else{
            System.out.println("Turno dei turisti in bici!");
        }
        for (Long l : listaID) {
            System.out.print(l+" ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        Funivia fun = new FuniviaLC();
        fun.test(18,9);
    }
}
