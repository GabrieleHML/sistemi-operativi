package appelli.cementificio;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread{
    Cementificio c;
    Random r = new Random();

    Cliente(Cementificio c){
        this.c = c;
    }

    @Override
    public void run() {
        try {
            int n = r.nextInt(10,30);
            c.entra();
            c.preleva(n);
            TimeUnit.SECONDS.sleep(1);
            c.esci();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
