package appelli.cementificio;

import java.util.concurrent.TimeUnit;

public class Addetto extends Thread{
    Cementificio c;

    Addetto(Cementificio c){
        this.c = c;
    }

    @Override
    public void run() {
        try {
            while(true){
                c.iniziaRifornimento();
                TimeUnit.SECONDS.sleep(5);
                c.terminaRifornimento();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
