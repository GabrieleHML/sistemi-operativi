package appelli.aziendaAgricolaV2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaLC extends AziendaAgricola{
    private Lock l = new ReentrantLock();
    private Condition possoPagare = l.newCondition();
    private Condition possoRitirare = l.newCondition();
    private Condition rifornimento = l.newCondition();
    private LinkedList<Long> codaCassa = new LinkedList<>();
    private LinkedList<Long> codaMagazzino = new LinkedList<>();

    private boolean mioTurno(int turno){
        if (turno == 0){ // 0 -> cassa
            return codaCassa.getFirst().equals(Thread.currentThread().getId());
        }else{ // 1 -> magazzino
            return codaMagazzino.getFirst().equals(Thread.currentThread().getId()) && numSacchi != 0;
        }
    }

    @Override
    public void acquista(int n) throws InterruptedException {
        l.lock();
        try {
            codaCassa.addLast(Thread.currentThread().getId());
            while (!mioTurno(0))
                possoPagare.await();
            incasso += (n * 3);
            System.out.println("C"+Thread.currentThread().getId()+" ha acquistato "+n+" sacchi");
            System.out.println("Incasso attuale: "+incasso);
            codaCassa.removeFirst();
            possoPagare.signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void ritira() throws InterruptedException {
        l.lock();
        try {
            codaMagazzino.add(Thread.currentThread().getId());
            while (!mioTurno(1))
                possoRitirare.await();
            numSacchi--;
            System.out.println("C"+Thread.currentThread().getId()+" ha ritirato un sacco");
            System.out.println("Num sacchi: "+numSacchi);
            codaMagazzino.removeFirst();
            if (numSacchi == 0)
                rifornimento.signal();
            possoRitirare.signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void iniziaRifornimento() throws InterruptedException {
        l.lock();
        try {
            while (numSacchi != 0)
                rifornimento.await();
            System.out.println("Inizio rifornimento!");
        }finally {
            l.unlock();
        }
    }

    @Override
    public void finisciRifornimento() throws InterruptedException {
        l.lock();
        try {
            numSacchi = 200;
            System.out.println("Fine rifornimento!");
            possoRitirare.signalAll();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        AziendaAgricola aa = new AziendaAgricolaLC();
        try {
            aa.test(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
