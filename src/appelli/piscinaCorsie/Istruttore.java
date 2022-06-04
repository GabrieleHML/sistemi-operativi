package appelli.piscinaCorsie;

import java.util.concurrent.TimeUnit;

public class Istruttore extends Thread{
    private Piscina p;

    public Istruttore(Piscina p){
        this.p = p;
    }

    @Override
    public void run() {
        try {
            p.iniziaTurno();
            TimeUnit.SECONDS.sleep(20); // sleep di 4 ore
            //p.interrompiTutti();
            p.finisciTurno();

            TimeUnit.SECONDS.sleep(5); // sleep di 1 ora

            p.iniziaTurno();
            TimeUnit.SECONDS.sleep(25); // sleep di 5 ore
            //p.interrompiTutti();
            p.finisciTurno();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
