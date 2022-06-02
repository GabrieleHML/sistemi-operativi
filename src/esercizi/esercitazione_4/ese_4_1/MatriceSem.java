package esercizi.esercitazione_4.ese_4_1;

import java.util.concurrent.Semaphore;
import esercizi.ese_3_1.Matrice;

public class MatriceSem extends Matrice {
    private Semaphore[][] matriceMutex;

    public MatriceSem(int[][] matrice){
        super(matrice.length, matrice[0].length);
        matriceMutex = new Semaphore[matrice.length][matrice[0].length];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matriceMutex[i][j] = new Semaphore(1);
            }
        }
    }

    @Override
    public void decrementaRiga(int riga, int x) {
        try{
            for (int j = 0; j < mat[riga].length; j++) {
                matriceMutex[riga][j].acquire();
                mat[riga][j] -= 1;
                matriceMutex[riga][j].release();
            }
        }catch (InterruptedException e){}
    }

    @Override
    public void incrementaColonna(int colonna, int x) {
        try {
            for (int i = 0; i < mat.length; i++) {
                matriceMutex[i][colonna].acquire();
                mat[i][colonna] += 1;
                matriceMutex[i][colonna].release();
            }
        }catch (InterruptedException e){}
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                sb.append(mat[i][j]+"\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
