package esercizi.ese_5_2;

import java.util.concurrent.TimeUnit;

public abstract class Tavolo {

    public boolean bacchette[]; // false -> è occupata ; true -> è libera

    public Tavolo(int num){
        bacchette = new boolean[num];
    }

    public abstract void prendiBacchette(int i) throws InterruptedException;

    public abstract void lasciaBacchette(int i) throws InterruptedException;

    public void test(int N) throws InterruptedException{
        for (int i = 0; i < N; i++) {
            new Filosofo(i, this).start();
        }
    }
}
