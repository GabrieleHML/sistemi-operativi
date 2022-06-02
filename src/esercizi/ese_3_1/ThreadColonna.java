package esercizi.ese_3_1;

import java.util.Random;

public class ThreadColonna extends Thread{
    private Random random = new Random();
    private Matrice mat;
    private int colonna;

    public ThreadColonna(Matrice mat, int colonna){
        this.mat = mat;
        this.colonna = colonna;
    }// costruttore

    @Override
    public void run(){
        //int x = random.nextInt(1,9);
        mat.incrementaColonna(colonna,4);
    }// run
}// ThreadColonna
