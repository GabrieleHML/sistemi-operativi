package appelli.funivia;

public abstract class Funivia {

    public Funivia(){
    }

    public abstract void pilotaStart() throws InterruptedException;

    public abstract void pilotaEnd() throws InterruptedException;

    public abstract void turistaSali(int t) throws InterruptedException;

    public abstract void turistaScendi(int t) throws InterruptedException;

    public void test(int numTuristiPiedi, int numTuristiBici){
        for (int i = 0; i < numTuristiPiedi; i++) {
            new Thread(new Turista(0, this)).start();
        }
        for (int i = 0; i < numTuristiBici; i++) {
            new Thread(new Turista(1, this)).start();
        }

        Thread p = new Thread(new Pilota(this));
        p.setDaemon(true);
        p.start();
    }
}
