package appelli.funiviaV2;

public class Turista extends Thread{
    private Funivia f;
    private int tipo;

    public Turista(Funivia f, int tipo){
        this.f = f;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        try {
            f.turistaSali(tipo);
            f.turistaScendi(tipo);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
