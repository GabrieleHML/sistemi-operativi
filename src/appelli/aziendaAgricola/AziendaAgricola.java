package appelli.aziendaAgricola;

import java.util.LinkedList;

public abstract class AziendaAgricola {
    protected static int incasso = 0;
    protected int numSacchettiInMagazzino = 200;
    protected LinkedList<Long> clientID = new LinkedList<>();

    public AziendaAgricola(){
    }

    public abstract void pagaAllaCassa(int numSacchetti) throws InterruptedException;

    public abstract void ritiraSacchetto() throws InterruptedException;

    public abstract void ricaricaSacchetti() throws InterruptedException;

    public void test(int numClienti) throws InterruptedException{
        Magazziniere m = new Magazziniere(this);
        m.setDaemon(true);
        m.start();
        Thread[] clienti = new Cliente[numClienti];

        for (int i = 0; i < numClienti; i++) {
            clienti[i] = new Cliente(this,clientID);
            clienti[i].start();
        }

        for (int i = 0; i < numClienti; i++) {
            clienti[i].join();
        }
        System.out.println("incasso: "+incasso);
    }
}
