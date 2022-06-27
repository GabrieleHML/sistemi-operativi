package appelli.ristorante;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLC extends Buffer{
    Lock l = new ReentrantLock();
    Condition[] voglioInserire = new Condition[2];
    Condition[] voglioPrendere = new Condition[2];

    BufferLC(int capacità) {
        super(capacità);
        for (int i = 0; i < 2; i++) {
            voglioInserire[i] = l.newCondition();
            voglioPrendere[i] = l.newCondition();
        }
    }

    @Override
    public void get(int n, int id) throws InterruptedException {
        l.lock();
        try {
            while (capacità < n)
                voglioPrendere[id].await();
            capacità -= n;
            System.out.printf("%s%d ha prelevato %d piatto%n",lettera() ,Thread.currentThread().getId(), n);
            System.out.println(parola(id)+": "+capacità);
            voglioInserire[id].signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void put(int n, int id) throws InterruptedException {
        l.lock();
        try {
            while(capacità + n > maxCap)
                voglioInserire[id].await();
            capacità += n;
            System.out.printf("%s%d ha inserito %d piatti%n",lettera() ,Thread.currentThread().getId(), n);
            System.out.println(parola(id)+": "+capacità);
            voglioPrendere[id].signalAll();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Buffer contenitore = new BufferLC(50);
        Buffer scolapiatti = new BufferLC(30);
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
