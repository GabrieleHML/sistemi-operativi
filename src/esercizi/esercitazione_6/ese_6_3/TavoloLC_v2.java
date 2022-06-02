package esercizi.esercitazione_6.ese_6_3;

import esercizi.ese_5_2.Tavolo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TavoloLC_v2 extends Tavolo { // senza deadlock e starvation

    private final int numFilosofi;
    private Lock l = new ReentrantLock();
    private Condition[] possoMangiare;

    public TavoloLC_v2(int num){
        super(num);
        numFilosofi = num;
        possoMangiare = new Condition[num];
        for (int i = 0; i < num; i++) {
            possoMangiare[i] = l.newCondition();
        }
    }

    private boolean possoPrendere(int i){
        return bacchette[i] || bacchette[(i + 1) % numFilosofi];
    }

    @Override
    public void prendiBacchette(int i) throws InterruptedException {
        l.lock();
        try {
            while (possoPrendere(i)){
                if (bacchette[i])
                    possoMangiare[i].await();
                else
                    possoMangiare[(i + 1) % numFilosofi].await();
            }
            bacchette[i] = false;
            bacchette[(i + 1) % numFilosofi] = false;
        }finally {
            l.unlock();
        }
    }

    @Override
    public void lasciaBacchette(int i) throws InterruptedException {
        l.lock();
        try {
            bacchette[i] = true;
            bacchette[(i + 1) % numFilosofi] = false;
            possoMangiare[i].signal();
            possoMangiare[(i + 1) % numFilosofi].signal();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        int N = 5;
        TavoloLC_v2 tavolo = new TavoloLC_v2(5);
        try {
            tavolo.test(N);
        }catch (InterruptedException e){}
    }
}
