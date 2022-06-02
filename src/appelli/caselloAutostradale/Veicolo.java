package appelli.caselloAutostradale;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread{

    private Casello casello;
    private Random r = new Random();

    public Veicolo(Casello casello){
        this.casello = casello;
    }

    @Override
    public void run() {
        try {
            int n = r.nextInt(casello.numPorte);
            //int x = r.nextInt(5,10);
            int x = 5;
            TimeUnit.SECONDS.sleep((long) x * 4);
            casello.scegliPorta(n);
            casello.effettuaPagamento(x,n);
            TimeUnit.SECONDS.sleep(r.nextLong(3,6));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
