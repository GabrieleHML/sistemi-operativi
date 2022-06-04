package appelli.aziendaAgricolaV2;

public abstract class AziendaAgricola {
    protected int numSacchi = 200;
    protected int incasso = 0;

    public AziendaAgricola(){
    }

    public abstract void acquista(int n) throws InterruptedException;

    public abstract void ritira() throws InterruptedException;

    public abstract void iniziaRifornimento() throws InterruptedException;

    public abstract void finisciRifornimento() throws InterruptedException;

    public void test(int numClienti) throws InterruptedException{
        Magazziniere m = new Magazziniere(this);
        m.setDaemon(true);
        m.start();

        Cliente[] lista = new Cliente[numClienti];

        for (int i = 0; i < numClienti; i++) {
            lista[i] = new Cliente(this);
            lista[i].start();
        }
        for (int i = 0; i < numClienti; i++) {
            lista[i].join();
        }
        System.out.println("Incasso finale: "+incasso);
    }
}
