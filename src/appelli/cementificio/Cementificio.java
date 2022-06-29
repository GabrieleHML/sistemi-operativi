package appelli.cementificio;

public abstract class Cementificio {
    final int maxSacchi;
    final int maxPersone;
    int numSacchi;
    int personeDentro;

    Cementificio(int N, int P){
        maxSacchi = P;
        numSacchi = P;
        maxPersone = N;
        personeDentro = 0;
    }

    public abstract void entra() throws InterruptedException;

    public abstract void esci() throws InterruptedException;

    public abstract void preleva(int n) throws InterruptedException;

    public abstract void iniziaRifornimento() throws InterruptedException;

    public abstract void terminaRifornimento() throws InterruptedException;
    
    public void test(){
        Addetto a = new Addetto(this);
        a.setDaemon(true);
        a.start();
        for (int i = 0; i < 50; i++) {
            new Cliente(this).start();
        }
    }
}
