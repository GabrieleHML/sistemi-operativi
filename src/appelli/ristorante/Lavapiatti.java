package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class Lavapiatti extends Thread{
    Buffer contenitore;
    Buffer scolapiatti;

    Lavapiatti(Buffer contenitore, Buffer scolapiatti){
        this.contenitore = contenitore;
        this.scolapiatti = scolapiatti;
    }

    @Override
    public void run() {
        try {
            while(true){
                contenitore.get(1, 0);
                TimeUnit.SECONDS.sleep(3);
                scolapiatti.put(1, 1);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
