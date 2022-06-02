package esercitazioni.ese_5.ProduttoreConsumatore;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer{

    private Semaphore ciSonoElementi = new Semaphore(0);
    private Semaphore ciSonoPostiVuoti;
    private Semaphore mutex = new Semaphore(1);

    public BufferSem(int dim){
        super(dim);
        ciSonoPostiVuoti = new Semaphore(dim);
    }
    public void put(int i) throws InterruptedException{
        ciSonoPostiVuoti.acquire();
        mutex.acquire();
        buffer[in] = i;
        in = (in + 1) % buffer.length;
        mutex.release();
        ciSonoElementi.release();
    }

    public int get() throws InterruptedException{
        ciSonoElementi.acquire();
        mutex.acquire();
        int i = buffer[out];
        out = (out + 1) % buffer.length;
        mutex.release();
        ciSonoPostiVuoti.release();
        return i;
    }

    public static void main(String[] args) {
        int dimensione = 10;
        Buffer buffer = new BufferSem(dimensione);
        int numProduttori = 10;
        int numConsumatori = 10;
        buffer.test(numProduttori, numConsumatori);
    }
}// BufferSem
