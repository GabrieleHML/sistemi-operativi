package esercizi.esercitazione_2.ese_2_3;

import java.util.LinkedList;

public class ThreadColonna extends Thread{
    private static int[][] mat;
    private final int colonna;
    private LinkedList<Punto> listaMinimi = new LinkedList<>();

    public ThreadColonna(int colonna) {
        this.colonna = colonna;
    }//ThreadRiga

    public static void setData(int[][] matrice){
        mat = matrice;
    }//setData

    @Override
    public void run() {
        int minimo = mat[0][colonna];
        for (int i=1; i<mat.length; i++)
            if (minimo > mat[i][colonna]){
                minimo = mat[i][colonna];
            }
        for (int i = 0; i < mat.length; i++) {
            if (mat[i][colonna] == minimo)
                listaMinimi.add(new Punto(i,colonna));
        }
    }//run

    public LinkedList<Punto> getMin() throws InterruptedException {
        this.join();
        return listaMinimi;
    }//getMinimo
}//ThreadColonna
