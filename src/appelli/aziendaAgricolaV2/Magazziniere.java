package appelli.aziendaAgricolaV2;

import java.util.concurrent.TimeUnit;

public class Magazziniere extends Thread{
    private AziendaAgricola aa;

    public Magazziniere(AziendaAgricola aa){
        this.aa = aa;
    }

    @Override
    public void run() {
        try {
            while (true){
                aa.iniziaRifornimento();
                TimeUnit.SECONDS.sleep(10); // attendi 10 minuti
                aa.finisciRifornimento();
            }
        }catch (InterruptedException e){
            System.out.println("incasso finale: "+aa.incasso);
            e.printStackTrace();
        }
    }
}
