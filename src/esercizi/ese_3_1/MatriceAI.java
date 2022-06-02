package esercizi.ese_3_1;

import java.util.concurrent.atomic.AtomicInteger;

public class MatriceAI extends Matrice{
    protected AtomicInteger[][] matrice;

    public MatriceAI(int n, int m) {
        matrice = new AtomicInteger[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrice[i][j] = new AtomicInteger(0);
    }// costruttore

    @Override
    public void decrementaRiga(int riga, int x){
        for (int i = 0; i < x; i++)
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[riga][j].set(matrice[riga][j].get()-1);
            }
    }// decrementaRiga

    @Override
    public void incrementaColonna(int colonna, int x){
        for (int i = 0; i < x; i++)
            for (int j = 0; j < matrice.length; j++) {
                matrice[j][colonna].set(matrice[j][colonna].get()+1);
            }
    }// incrementaColonna

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                sb.append(matrice[i][j].get()+"\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
