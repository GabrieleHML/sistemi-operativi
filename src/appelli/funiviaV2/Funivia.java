package appelli.funiviaV2;

import java.util.ArrayList;

public abstract class Funivia {
    protected final int postiFunivia = 6;
    protected int[] postiLiberi = new int[2];
    protected int turno = 0;
    protected ArrayList<Long> listaID = new ArrayList<>();

    public Funivia(){
        postiLiberi[0] = 6;
        postiLiberi[1] = 3;
    }

    public abstract void pilotaStart() throws InterruptedException;

    public abstract void pilotaEnd() throws InterruptedException;

    public abstract void turistaSali(int t) throws InterruptedException;

    public abstract void turistaScendi(int t) throws InterruptedException;

    public void test(int numTuristiAPiedi, int numTuristiInBici){
        Pilota p = new Pilota(this);
        p.setDaemon(true);
        p.start();

        for (int i = 0; i < numTuristiAPiedi; i++) {
            new Turista(this, 0).start();
        }
        for (int i = 0; i < numTuristiInBici; i++) {
            new Turista(this, 1).start();
        }
    }
}
