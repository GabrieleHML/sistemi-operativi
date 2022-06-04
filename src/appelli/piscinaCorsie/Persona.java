package appelli.piscinaCorsie;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Persona extends Thread{
    private Piscina p;
    private Random r = new Random();
    private boolean staFacendoLaDoccia = false;

    public Persona(Piscina p){
        this.p = p;
    }

    @Override
    public void run() {
        try {
            int c = p.scegliCorsia();
            p.iniziaANuotare(c);
            TimeUnit.SECONDS.sleep(r.nextLong(2,5));// sleep di 30-60 minuti
            p.finisciDiNuotare(c);
            p.faiLaDoccia();
            TimeUnit.SECONDS.sleep(1); // sleep di 20 minuti
        }catch (InterruptedException e){
            if (!staFacendoLaDoccia) {
                try {
                    p.faiLaDoccia();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void setStaFacendoLaDoccia(boolean staFacendoLaDoccia) {
        this.staFacendoLaDoccia = staFacendoLaDoccia;
    }
}
