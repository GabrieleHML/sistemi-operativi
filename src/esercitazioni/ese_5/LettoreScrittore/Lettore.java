package esercitazioni.ese_5.LettoreScrittore;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Lettore implements Runnable{
    private MemoriaCondivisa memoria;
    public Lettore(MemoriaCondivisa mem){
        memoria = mem;
    }

    private final static int MIN_TEMPO_LETTURA = 1;
    private final static int MAX_TEMPO_LETTURA = 4;
    private final static int MIN_TEMPO_ALTRO = 6;
    private final static int MAX_TEMPO_ALTRO = 10;

    private Random random = new Random();

    public void run(){
        try {
            while (true){
                memoria.inizioLettura();
                leggi();
                memoria.fineLettura();
                faiAltro();
            }
        }catch (InterruptedException e){}
    }// run

    private void leggi() throws InterruptedException{
        attendi(MIN_TEMPO_LETTURA, MAX_TEMPO_LETTURA);
    }

    private void faiAltro() throws InterruptedException{
        attendi(MIN_TEMPO_ALTRO, MAX_TEMPO_ALTRO);
    }

    private void attendi(int min, int max) throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
    }
}// Lettore
