package appelli.piscina;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Persona extends Thread{
    Piscina p;
    final int MIN_TEMPO_CAMBIO = 1;
    final int MAX_TEMPO_CAMBIO = 3;
    final int MIN_TEMPO_DOCCIA = 3;
    final int MAX_TEMPO_DOCCIA = 5;
    Random r = new Random();

    Persona(Piscina p){
        this.p = p;
    }

    @Override
    public void run() {
        try {
            attendi(MIN_TEMPO_CAMBIO, MAX_TEMPO_CAMBIO);
            p.entra();
            TimeUnit.SECONDS.sleep(10);
            p.esci();
            attendi(MIN_TEMPO_DOCCIA, MAX_TEMPO_DOCCIA);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void attendi(int min, int max) throws InterruptedException{
        TimeUnit.SECONDS.sleep(r.nextInt(min,max));
    }
}
