package appelli.funiviaV2;

import java.util.concurrent.TimeUnit;

public class Pilota extends Thread{
    private Funivia f;

    public Pilota(Funivia f){
        this.f = f;
    }

    @Override
    public void run() {
        try {
            while (true){
                f.pilotaStart();
                TimeUnit.SECONDS.sleep(5);
                f.pilotaEnd();
                TimeUnit.SECONDS.sleep(2);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
