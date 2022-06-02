package esercitazioni.ese_5.ProduttoreConsumatore;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Produttore implements Runnable{
    private final static int MAX_RANDOM = 10;
    private final static int MIN_TEMPO_PRODUZIONE = 3;
    private final static int MAX_TEMPO_PRODUZIONE = 7;
    private Random random = new Random();
    private Buffer buffer;

    public Produttore(Buffer b){
        buffer = b;
    }

    public void run(){
        try {
            while (true){
                int i = produci();
                buffer.put(i);
            }
        }catch (InterruptedException e){}
    }// run

    private int produci() throws InterruptedException{
        attendi(MIN_TEMPO_PRODUZIONE, MAX_TEMPO_PRODUZIONE);
        return random.nextInt(MAX_RANDOM);
    }

    private void attendi(int min, int max) throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(max - min +1) + min);
    }
}// Produttore
