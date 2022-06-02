package esercizi.ese_3_1;

public class MatriceNTS extends Matrice{

    public MatriceNTS(int n, int m) {
        super(n, m); 
    }

    @Override
    public void decrementaRiga(int riga, int x) {
        for (int i = 0; i < x; i++)
            for (int j = 0; j < mat[0].length; j++)
                mat[riga][j] -= 1;
    }// decrementaRiga

    @Override
    public void incrementaColonna(int colonna, int x) {
        for (int i = 0; i < x; i++)
            for (int j = 0; j < mat.length; j++) {
                mat[j][colonna] += 1;
            }
    }// incrementaColonna

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
