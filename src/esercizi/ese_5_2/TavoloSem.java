package esercizi.ese_5_2;

import java.util.concurrent.Semaphore;

public class TavoloSem extends Tavolo{

    private final int N;
    private Semaphore bacchetta[];

    public TavoloSem(int numBacchette){
        super(numBacchette);
        N = numBacchette;
        bacchetta = new Semaphore[numBacchette];
        for (int i = 0; i < N; i++)
            bacchetta[i] = new Semaphore(1, true);
    }

    @Override
    public void prendiBacchette(int i) throws InterruptedException {
        if (i % 2 == 0){
            bacchetta[i].acquire();
            bacchetta[(i+1)%N].acquire();
        }else{
            bacchetta[(i+1)%N].acquire();
            bacchetta[i].acquire();
        }
    }

    @Override
    public void lasciaBacchette(int i) throws InterruptedException {
        bacchetta[i].release();
        bacchetta[(i+1)%N].release();
    }

    public static void main(String[] args) {
        try {
            Tavolo t = new TavoloSem(5);
            t.test(5);
        }catch (InterruptedException e){}
    }
}
