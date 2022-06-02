package appelli.aziendaAgricola;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaLC extends AziendaAgricola{
    // TODO da AziendaAgricola abbiamo: incasso, numSacchettiInMagazzino, clientID

    private Lock lockCassa = new ReentrantLock();
    private Lock lockMagazzino = new ReentrantLock();
    private Condition possoPagare = lockCassa.newCondition();
    private Condition possoRitirare = lockMagazzino.newCondition();
    private Condition magazzinoVuoto = lockMagazzino.newCondition();

    public AziendaAgricolaLC(){
        super();
    }

    @Override
    public void pagaAllaCassa(int numSacchetti) throws InterruptedException {
        lockCassa.lock();
        try {
            incasso += (3 * numSacchetti);
            System.out.println("C"+Thread.currentThread().getId()+" ha acquistato "+numSacchetti+" sacchetti");
            clientID.addLast(Thread.currentThread().getId());
        }finally {
            lockCassa.unlock();
        }
    }

    @Override
    public void ritiraSacchetto() throws InterruptedException {
        lockMagazzino.lock();
        try {
            while (clientID.getFirst() != Thread.currentThread().getId())
                possoRitirare.await();
            numSacchettiInMagazzino--;
            if (numSacchettiInMagazzino == 0)
                magazzinoVuoto.signal();
            else
                possoRitirare.signalAll();
        }finally {
            lockMagazzino.unlock();
        }
    }

    @Override
    public void ricaricaSacchetti() throws InterruptedException {
        lockMagazzino.lock();
        try {
            while(numSacchettiInMagazzino != 0)
                magazzinoVuoto.await();
            numSacchettiInMagazzino = 200;
            TimeUnit.SECONDS.sleep(10);
            possoRitirare.signalAll();
        }finally {
            lockMagazzino.unlock();
        }
    }

    public static void main(String[] args) {
        try {
            AziendaAgricola aa = new AziendaAgricolaLC();
            aa.test(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
