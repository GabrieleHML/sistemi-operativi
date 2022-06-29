package appelli.boccaccio;

import java.util.Arrays;
import java.util.Random;

import static java.lang.System.exit;

public abstract class Boccaccio {
    int[] caramelle; // la somma degli elementi deve essere 100
    int numColori;
    Random r = new Random();
    int bambiniChePiangono = 0;

    Boccaccio(int numColori){
        this.numColori = numColori;
        caramelle = new int[numColori];
        Arrays.fill(caramelle,0);
        riempimento();
    }

    public void riempimento(){
        if (sommaCaramelle(caramelle) == 100)
            exit(0);

        for (int i = 0; i < caramelle.length; i++) {
            int x = caramelle[i];
            if (x < 3 && sommaCaramelle(caramelle) + (3-x) <=100)
                caramelle[i] = 3;
        }
        for (int i = 0; i < caramelle.length; i++) {
            int val = r.nextInt(0, (100-sommaCaramelle(caramelle))/(numColori-i));
            caramelle[i] += val;
        }
        while(sommaCaramelle(caramelle) < 100){
            for (int i = 0; i < caramelle.length; i++) {
                if (sommaCaramelle(caramelle) == 100)
                    break;
                caramelle[i]++;
            }
        }
    }

    public int sommaCaramelle(int[] vet){
        int somma = 0;
        for (int x : vet) {
            somma += x;
        }
        return somma;
    }

    public abstract boolean prendi(int c) throws InterruptedException;

    public abstract void piangi() throws InterruptedException;

    public abstract void riempi() throws InterruptedException;

    public void test(int numBambini){
        Addetto a = new Addetto(this);
        a.setDaemon(true);
        a.start();
        for (int i = 0; i < numBambini; i++) {
            new Bambino(this).start();
        }
    }
}
