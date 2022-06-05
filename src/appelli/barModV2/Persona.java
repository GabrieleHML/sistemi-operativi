package appelli.barModV2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Persona extends Thread{
    private Bar b;
    private Random r = new Random();

    public Persona(Bar b){
        this.b = b;
    }

    @Override
    public void run() {
        try {
            int i = b.scegli();

            b.inizia(i);
            attendi(i);
            b.finisci(i);

            i = 1 - i;

            b.inizia(i);
            attendi(i);
            b.finisci(i);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void attendi(int i) throws InterruptedException{
        int n;
        if (i == 0){
            n = r.nextInt(1,2);
            TimeUnit.SECONDS.sleep(n);
        }else{
            n = r.nextInt(3,6);
            TimeUnit.SECONDS.sleep(n);
        }
    }
}
