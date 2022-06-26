package appelli.computer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utente extends Thread{
    Computer c;
    Random r = new Random();

    Utente(Computer c){
        this.c = c;
    }

    @Override
    public void run() {
        try {
            while (true){
                int nCore = r.nextInt(3)+1;
                int idP = r.nextInt(1);
                c.richiedi(nCore, idP);
                TimeUnit.SECONDS.sleep(1); // utilizza i core
                c.rilascia(nCore, idP);
                TimeUnit.SECONDS.sleep(5);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
