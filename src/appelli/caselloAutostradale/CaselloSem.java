package appelli.caselloAutostradale;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class CaselloSem extends Casello{
    private Semaphore mutex = new Semaphore(1);
    private Semaphore[] porte = new Semaphore[numPorte];

    public CaselloSem(int numPorte, int tariffaKm) {
        super(numPorte, tariffaKm);
        for (int i = 0; i < numPorte; i++) {
            codeVeicoli[i] = new LinkedList<>();
            porte[i] = new Semaphore(1);
        }
    }

    private boolean mioTurno(int n){
        return Thread.currentThread().getId() == codeVeicoli[n].getFirst();
    }

    @Override
    public void scegliPorta(int n) throws InterruptedException{
        try {
            mutex.acquire();
            codeVeicoli[n].addLast(Thread.currentThread().getId());
            System.out.println("T"+Thread.currentThread().getId()+" ha scelto la porta "+n);
            if (mioTurno(n)){
                porte[n].acquire();
            }
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void effettuaPagamento(int x, int n) throws InterruptedException{
        try {
            mutex.acquire();
            incasso += tariffaKm * x;
            System.out.println(incasso);
            codeVeicoli[n].removeFirst();
            porte[n].release();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Casello c = new CaselloSem(5,3);
        c.test(5);
    }
}
