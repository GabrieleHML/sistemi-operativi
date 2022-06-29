package appelli.boccaccio;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bambino extends Thread{
    Boccaccio b;
    Random r = new Random();

    Bambino(Boccaccio b){
        this.b = b;
    }

    @Override
    public void run() {
        try {
            int c = r.nextInt(0, b.numColori);
            while(true){
                if (b.prendi(c))
                    TimeUnit.SECONDS.sleep(r.nextInt(4,8));
                else
                    b.piangi();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
