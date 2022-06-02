package appelli.aziendaAgricola;

import java.util.concurrent.TimeUnit;

public class Magazziniere extends Thread{
    private AziendaAgricola aa;

    public Magazziniere(AziendaAgricola aa){
        this.aa = aa;
    }

    @Override
    public void run() {
        while (true){
            try {
                aa.ricaricaSacchetti();
                TimeUnit.SECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }// run
}
