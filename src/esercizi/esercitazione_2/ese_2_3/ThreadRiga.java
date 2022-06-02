package esercizi.esercitazione_2.ese_2_3;

import java.util.LinkedList;

public class ThreadRiga extends Thread{
    private static int[][] mat;
    private final int riga;
    private LinkedList<Punto> listaMassimi = new LinkedList<>();

    public ThreadRiga(int riga) {
        this.riga = riga;
    }//costruttore

    public static void setData(int[][] matrice){
        mat = matrice;
    }//setData

    @Override
    public void run() {
        int massimo = mat[riga][0];
        for (int i=1; i<mat[0].length; i++)
            if (massimo <= mat[riga][i]){
                massimo = mat[riga][i];
            }
        for (int i = 0; i < mat[0].length; i++) {
            if (mat[riga][i] == massimo)
                listaMassimi.add(new Punto(riga,i));
        }
    }//run

    public LinkedList<Punto> getMax() throws InterruptedException {
        this.join();
        return listaMassimi;
    }//getMassimo
}//ThreadRiga
