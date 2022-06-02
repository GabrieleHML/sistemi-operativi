package appelli.barMod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Persona extends Thread{
    private static final int[] MIN_ATTESA = {10, 2};
    private static final int[] MAX_ATTESA = {20, 5};

    Bar bar;
    Random r = new Random();

    public Persona(Bar b){
        this.bar = b;
    }

    @Override
    public void run() {
        try {
            attendi(0,20);
            int op = bar.scegli();

            bar.inizia(op); // op = 1 -> paga ; op = 0 -> beve
            attendi(MIN_ATTESA[op], MAX_ATTESA[op]);
            bar.finisci(op); // op = 1 -> beve ; op = 0 -> paga

            op = 1 - op;

            bar.inizia(op);
            attendi(MIN_ATTESA[op], MAX_ATTESA[op]);
            bar.finisci(op);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void attendi(int min, int max) throws InterruptedException{
        TimeUnit.SECONDS.sleep(r.nextInt(max - min + 1) + min);
    }
}
