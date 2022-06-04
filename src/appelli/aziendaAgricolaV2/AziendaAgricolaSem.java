package appelli.aziendaAgricolaV2;

import java.util.concurrent.Semaphore;

public class AziendaAgricolaSem extends AziendaAgricola{
    private Semaphore mutexSacchi = new Semaphore(1);
    private Semaphore mutexIncasso = new Semaphore(1);
    private Semaphore mutexCassa = new Semaphore(1, true);
    private Semaphore magazzinoSem = new Semaphore(200, true);
    private Semaphore mutexMagazzino = new Semaphore(0);

    public AziendaAgricolaSem(){
        super();
    }

    @Override
    public void acquista(int n) throws InterruptedException {
        mutexCassa.acquire();
        mutexIncasso.acquire();
        incasso += (n * 3);
        System.out.println("C"+Thread.currentThread().getId()+" ha acquistato "+n+" sacchi");
        System.out.println("Incasso attuale: "+incasso);
        mutexIncasso.release();
        mutexCassa.release();
    }

    @Override
    public void ritira() throws InterruptedException {
        magazzinoSem.acquire();
        mutexSacchi.acquire();
        numSacchi--;
        if (numSacchi == 0)
            mutexMagazzino.release();
        System.out.println("C"+Thread.currentThread().getId()+" ha ritirato un sacco");
        System.out.println("Num sacchi: "+numSacchi);
        mutexSacchi.release();
    }

    @Override
    public void iniziaRifornimento() throws InterruptedException {
        mutexMagazzino.acquire();
        System.out.println("Inizio rifornimento!");
    }

    @Override
    public void finisciRifornimento() throws InterruptedException {
        if (numSacchi == 0){
            mutexSacchi.acquire();
            numSacchi = 200;
            mutexSacchi.release();
            magazzinoSem.release(200);
            System.out.println("Fine rifornimento!");
            mutexMagazzino.release();
        }
    }

    public static void main(String[] args) {
        AziendaAgricola aa = new AziendaAgricolaSem();
        try {
            aa.test(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
