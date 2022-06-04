package appelli.piscinaCorsie;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Piscina {
    protected final int numCorsie = 5;
    protected final int numPersone;

    protected ArrayList<Persona> listaPersone = new ArrayList<>();

    public Piscina(int numPersone){
        this.numPersone = numPersone;
    }

    public abstract int scegliCorsia() throws InterruptedException;

    public abstract void iniziaANuotare(int c) throws InterruptedException;

    public abstract void finisciDiNuotare(int c) throws InterruptedException;

    public abstract void faiLaDoccia() throws InterruptedException;

    public abstract void iniziaTurno() throws InterruptedException;

    public abstract void finisciTurno() throws InterruptedException;

    public void test(){
        Istruttore t = new Istruttore(this);
        t.setDaemon(true);
        t.start();

        for (int i = 0; i < numPersone; i++) {
            Persona p = new Persona(this);
            listaPersone.add(p);
            p.start();
        }
    }
}
