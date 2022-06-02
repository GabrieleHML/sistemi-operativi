package appelli.laghetto;

import java.util.Random;

public class Pescatore extends Thread{
    private final int TEMPO_MIN_PESCA = 200;
    private final int TEMPO_MAX_PESCA = 800;
    private final int TEMPO_ALLONTANAMENTO = 1000;
    private Random r = new Random();
    private Laghetto lag;

    public Pescatore(Laghetto lag){
        this.lag = lag;
    }

    @Override
    public void run() {
        try {
            while(true){
                lag.inizia(0);
                attendi(TEMPO_MIN_PESCA, TEMPO_MAX_PESCA);
                lag.finisci(0);
                sleep(TEMPO_ALLONTANAMENTO);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    private void attendi(int min, int max) throws InterruptedException{
        sleep(r.nextLong(min, max));
    }
}
