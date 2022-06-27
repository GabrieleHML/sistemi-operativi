package appelli.ristorante;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer{
    Semaphore mutex[] = new Semaphore[2];
    Semaphore ciSonoElementi[] = new Semaphore[2];
    Semaphore ciSonoPostiVuoti[] = new Semaphore[2];

    BufferSem(int capacità) {
        super(capacità);
        for (int i = 0; i < 2; i++) {
            mutex[i] = new Semaphore(1);
            ciSonoElementi[i] = new Semaphore(0);
            ciSonoPostiVuoti[i] = new Semaphore(capacità);
        }
    }



    @Override
    public void get(int n, int id) throws InterruptedException {
        ciSonoElementi[id].acquire();
        mutex[id].acquire();
        if (capacità >= n)
            capacità -= n;
        System.out.printf("%s%d ha prelevato %d piatto%n",lettera() ,Thread.currentThread().getId(), n);
        System.out.println(parola(id)+": "+capacità);
        mutex[id].release();
        ciSonoPostiVuoti[id].release();
    }

    @Override
    public void put(int n,  int id) throws InterruptedException {
        ciSonoPostiVuoti[id].acquire();
        mutex[id].acquire();
        if (capacità + n <= maxCap)
            capacità += n;
        System.out.printf("%s%d ha inserito %d piatti%n",lettera() ,Thread.currentThread().getId(), n);
        System.out.println(parola(id)+": "+capacità);
        mutex[id].release();
        ciSonoElementi[id].release();
    }

    public static void main(String[] args) {
        Buffer contenitore = new BufferSem(50);
        Buffer scolapiatti = new BufferSem(30);
        for (int i = 0; i < 10; i++) {
            new Cameriere(contenitore).start();
        }
        for (int i = 0; i < 6; i++) {
            new Lavapiatti(contenitore, scolapiatti).start();
        }
        for (int i = 0; i < 3; i++) {
            new Asciugapiatti(scolapiatti).start();
        }
    }
}
