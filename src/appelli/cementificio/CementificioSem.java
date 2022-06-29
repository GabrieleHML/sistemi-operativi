package appelli.cementificio;

import java.util.concurrent.Semaphore;

public class CementificioSem extends Cementificio{
    Semaphore mutex = new Semaphore(1);
    Semaphore cePosto;
    Semaphore ciSonoSacchi;
    Semaphore necessarioRifornimento = new Semaphore(0);


    CementificioSem(int N, int P) {
        super(N, P);
        cePosto = new Semaphore(N, true);
        ciSonoSacchi = new Semaphore(P);
    }

    @Override
    public void entra() throws InterruptedException {
        System.out.printf("C%d vuole entrare\n", Thread.currentThread().getId());
        cePosto.acquire();
        mutex.acquire();
        personeDentro++;
        System.out.printf("C%d è entrato\n", Thread.currentThread().getId());
        mutex.release();
    }

    @Override
    public void esci() throws InterruptedException {
        mutex.acquire();
        personeDentro--;
        System.out.printf("C%d è uscito\n", Thread.currentThread().getId());
        mutex.release();

        cePosto.release();
    }

    @Override
    public void preleva(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            ciSonoSacchi.acquire();
            mutex.acquire();
            numSacchi--;
            //System.out.printf("C%d ha prelevato, sacchi rimanenti: %d\n",Thread.currentThread().getId(), numSacchi);
            if (numSacchi == 0)
                necessarioRifornimento.release();
            mutex.release();
        }
        mutex.acquire();
        System.out.printf("C%d ha prelevato %d sacchi, sacchi rimanenti: %d\n", Thread.currentThread().getId(), n, numSacchi);
        mutex.release();
    }

    @Override
    public void iniziaRifornimento() throws InterruptedException {
        necessarioRifornimento.acquire();
        System.out.println("L'addetto ha iniziato il rifornimento");
    }

    @Override
    public void terminaRifornimento() throws InterruptedException {
        mutex.acquire();
        numSacchi = maxSacchi;
        mutex.release();
        System.out.println("L'addetto ha terminato il rifornimento");
        ciSonoSacchi.release(maxSacchi);
    }

    public static void main(String[] args) {
        Cementificio cem = new CementificioSem(10, 300);
        cem.test();
    }
}
