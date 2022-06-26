package appelli.computer;

public abstract class Computer {
    Computer(){
    }

    public abstract void richiedi(int nCore, int idP) throws InterruptedException;

    public abstract void rilascia(int nCore, int idP) throws InterruptedException;

    public void test(){
        for (int i = 0; i < 20; i++) {
            new Utente(this).start();
        }
    }
}
