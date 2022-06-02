package appelli.caselloAutostradale;

import java.util.LinkedList;

public abstract class Casello {
    protected int numPorte = 0;
    protected int tariffaKm = 0;
    protected int incasso = 0;
    protected LinkedList<Long>[] codeVeicoli = new LinkedList[numPorte];

    public Casello(int numPorte, int tariffaKm){
        this.numPorte = numPorte;
        this.tariffaKm = tariffaKm;
    }

    public abstract void scegliPorta(int n) throws InterruptedException;

    public abstract void effettuaPagamento(int x, int n) throws InterruptedException;

    public void test(int numVeicoli){
        for (int i = 0; i < numVeicoli; i++) {
            new Veicolo(this).start();
        }
    }
}
