package appelli.funiviaV2;

import java.util.concurrent.Semaphore;

public class FuniviaSem extends Funivia{
    private Semaphore[] permettiSalita = new Semaphore[2];
    private Semaphore[] permettiUscita = new Semaphore[2];
    private Semaphore permettiStampa = new Semaphore(0);
    private Semaphore permettiFine = new Semaphore(0);
    private Semaphore mutexTurno = new Semaphore(1);
    private Semaphore mutexPosti = new Semaphore(1);
    private Semaphore funiviaSali = new Semaphore(0);
    private Semaphore funiviaScendi = new Semaphore(0);

    public FuniviaSem(){
        permettiSalita[0] = new Semaphore(0);
        permettiSalita[1] = new Semaphore(0);
        permettiUscita[0] = new Semaphore(0);
        permettiUscita[1] = new Semaphore(0);
    }

    @Override
    public void pilotaStart() throws InterruptedException {

        if (turno == 0) permettiSalita[turno].release(6); else permettiSalita[turno].release(3);
        funiviaSali.acquire();
        System.out.println("La funivia sta salendo");
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

    @Override
    public void pilotaEnd() throws InterruptedException {
        permettiStampa.acquire();
        stampa(turno);
        System.out.println("La funivia sta scendendo");
        if (turno == 0) permettiUscita[0].release(6); else permettiUscita[1].release(3);
        permettiFine.acquire();
        listaID.clear();
    }

    @Override
    public void turistaSali(int t) throws InterruptedException {
        permettiSalita[t].acquire();
        mutexPosti.acquire();
        postiLiberi[t]--;
        listaID.add(Thread.currentThread().getId());
        if (postiLiberi[t] == 0)
            funiviaSali.release();
        mutexPosti.release();
        System.out.println("T"+Thread.currentThread().getId()+" è salito sulla funivia");
    }

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        permettiUscita[t].acquire();
        mutexPosti.acquire();
        postiLiberi[t]++;
        System.out.println("T"+Thread.currentThread().getId()+" è salito sulla funivia");
        if ((postiLiberi[t] == 6 && turno == 0) || (postiLiberi[t] == 3 && turno == 1))
            permettiFine.release();
        mutexPosti.release();
    }

    public static void main(String[] args) {
        Funivia fun = new FuniviaSem();
        fun.test(18,9);
    }
}
