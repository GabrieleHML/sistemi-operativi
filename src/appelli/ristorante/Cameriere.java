package appelli.ristorante;

import java.util.concurrent.TimeUnit;

public class Cameriere extends Thread{
    Buffer contenitore;

    Cameriere(Buffer contenitore){
        this.contenitore = contenitore;
    }

    @Override
    public void run() {
        try {
            while (true){
                contenitore.put(4, 0);
                TimeUnit.SECONDS.sleep(4);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
