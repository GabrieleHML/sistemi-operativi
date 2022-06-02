package esercizi.ese_3_1;

import java.util.Random;

public class ThreadRiga extends Thread{
    private Random random = new Random();
    private Matrice mat;
    private int riga;

    public ThreadRiga(Matrice mat, int riga){
        this.mat = mat;
        this.riga = riga;
    }// costruttore

    @Override
    public void run(){
        //int x = random.nextInt(1,9);
        mat.decrementaRiga(riga,4);
    }// run
}// ThreadRiga
