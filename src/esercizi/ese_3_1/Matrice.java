package esercizi.ese_3_1;

public abstract class Matrice {
    protected int[][] mat;

    public Matrice(int n, int m){
        mat = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                mat[i][j] = 0;
    }//costruttore

    protected Matrice() {
    }

    public abstract void decrementaRiga(int riga, int x);

    public abstract void incrementaColonna(int colonna, int x);

    public int[][] getMat(){
        return mat;
    }// getMat

    public abstract String toString();
}// Matrice
