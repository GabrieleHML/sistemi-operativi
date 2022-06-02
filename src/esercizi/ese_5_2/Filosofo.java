package esercizi.ese_5_2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Filosofo extends Thread{
    private int ID;
    private Tavolo tavolo;

    private static final int MAX_TEMPO_MAGIA = 3;
    private static final int MIN_TEMPO_MANGIA = 1;
    private static final int MAX_TEMPO_PENSA = 3;
    private static final int MIN_TEMPO_PENSA = 1;
    private static Random r = new Random();

    public Filosofo(int i, Tavolo tav){
        ID = i;
        tavolo = tav;
    }

    @Override
    public void run() {
        try {
            while (true){
                tavolo.prendiBacchette(ID);
                mangia();
                tavolo.lasciaBacchette(ID);
                pensa();
            }
        }catch (InterruptedException e){}
    }

    public void pensa() throws InterruptedException{
        System.out.println("F"+ID+" mangia");
        attendi(MAX_TEMPO_MAGIA, MIN_TEMPO_MANGIA);
    }

    public void mangia() throws InterruptedException{
        System.out.println("F"+ID+" pensa");
        attendi(MAX_TEMPO_PENSA, MIN_TEMPO_PENSA);
    }

    private void attendi(int max, int min){
        try {
            TimeUnit.SECONDS.sleep(r.nextInt(max - min + 1) + min);
        }catch (InterruptedException e){}
    }
}
