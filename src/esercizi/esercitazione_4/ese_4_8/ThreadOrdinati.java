package esercizi.esercitazione_4.ese_4_8;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ThreadOrdinati {

    private static int N = 10;
    private static Semaphore[] listaOrdine;

    static class Atleta extends Thread{
        private int numMaglia;

        public Atleta(int nMaglia) {
            this.numMaglia = nMaglia;
        }//costruttore

        public void run(){
            try {
                listaOrdine[numMaglia].acquire();
                System.out.print(numMaglia+" ");
                TimeUnit.SECONDS.sleep(2);
                if (numMaglia+1 < N)
                    listaOrdine[numMaglia+1].release();
            }catch (InterruptedException e){}

        }//run
    }//Atleta

    public static void main(String[] args) {

        listaOrdine = new Semaphore[N];
        for (int i = 0; i < N; i++) {
            if (i == 0)
                listaOrdine[i] = new Semaphore(1);
            else
                listaOrdine[i] = new Semaphore(0);
        }

        Atleta[] atleti = new Atleta[N];
        for (int i = 0; i < N; i++) {
            atleti[i] = new Atleta(i);
            atleti[i].start();
        }
    }// main
}
