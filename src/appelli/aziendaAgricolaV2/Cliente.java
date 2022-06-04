package appelli.aziendaAgricolaV2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread{
    private AziendaAgricola aa;
    Random r = new Random();

    public Cliente(AziendaAgricola aa){
        this.aa = aa;
    }

    @Override
    public void run() {
        try {
            int n = r.nextInt(1,10);
            aa.acquista(n);
            for (int i = 0; i < n; i++) {
                aa.ritira();
                TimeUnit.SECONDS.sleep(1); // attende 1 minuto
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
