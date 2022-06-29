package appelli.piscina;

import java.util.concurrent.Semaphore;

public class PiscinaSem extends Piscina{
    Semaphore mutex = new Semaphore(1);
    Semaphore cePosto = new Semaphore(1, true);
    int postiLiberi;

    PiscinaSem(int numCorsie, int personeXCorsia){
        super(numCorsie, personeXCorsia);
        postiLiberi = numCorsie * personeXCorsia;
    }

    @Override
    public void entra() throws InterruptedException{
        System.out.printf("P%d vuole entrare\n", Thread.currentThread().getId());
        cePosto.acquire();
        mutex.acquire();
        postiLiberi--;
        if (postiLiberi != 0)
            cePosto.release();
        System.out.printf("P%d è entrato nella piscina\n", Thread.currentThread().getId());
        System.out.println("Posti liberi: "+postiLiberi);
        mutex.release();
    }

    @Override
    public void esci() throws InterruptedException{
        mutex.acquire();
        postiLiberi++;
        System.out.printf("P%d è uscito nella piscina\n", Thread.currentThread().getId());
        System.out.println("Posti liberi: "+postiLiberi);
        mutex.release();
    }

    public static void main(String[] args) {
        Piscina p = new PiscinaSem(10, 4);
        p.test();
    }
}
