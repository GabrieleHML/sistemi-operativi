package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class Asciugapiatti extends Thread{
    Buffer scolapiatti;

    Asciugapiatti(Buffer scolapiatti){
        this.scolapiatti = scolapiatti;
    }

    @Override
    public void run() {
        try {
            while(true){
                scolapiatti.get(1, 1);
                TimeUnit.SECONDS.sleep(2);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
