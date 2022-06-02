package esercitazioni.ese_4;

import java.util.ArrayList;

public class Semaforo {
    private int contatore;
    private ArrayList<Thread> threadSospesi;

    public Semaforo(int valoreIniziale) {
        contatore = valoreIniziale;
        threadSospesi = new ArrayList<>();
    }

    public void P() { //acquire metodo atomico
        Thread T = Thread.currentThread();
        contatore--;
        if (contatore < 0) {
            threadSospesi.add(T);
            //sospensione di T
        }
        //al termine della sospensione, T riprende da qui
    }
    public void V() { //release metodo atomico
        contatore++;
        if (contatore <= 0) {
            Thread T = threadSospesi.remove(contatore);
        }
    }
}
