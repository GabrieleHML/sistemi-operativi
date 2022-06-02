package appelli.funivia;

public class Turista extends Thread{
    private int tipo;
    private Funivia f;

    public Turista(int tipo, Funivia f){
        this.tipo = tipo;
        this.f = f;
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

    public int getTipo() {
        return tipo;
    }
}
