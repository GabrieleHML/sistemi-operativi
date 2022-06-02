package appelli.laghetto;

import java.util.Random;

public class Addetto extends Thread{
    private final int TEMPO_MIN_RIPOPOLA = 300;
    private final int TEMPO_MAX_RIPOPOLA = 600;
    private final int TEMPO_ALLONTANAMENTO = 3000;
    private Random r = new Random();
    private Laghetto lag;

    public Addetto(Laghetto lag){
        this.lag = lag;
    }

    @Override
    public void run() {
        try {
            while (true){
                lag.inizia(1);
                attendi(TEMPO_MIN_RIPOPOLA, TEMPO_MAX_RIPOPOLA);
                lag.finisci(1);
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
