package esercizi.ese_3_1;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        AtomicInteger[][] matrice = new AtomicInteger[3][4];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                matrice[i][j] = new AtomicInteger(0);
        stampa(matrice);
        System.out.println("/-----------/");
        for (int i = 0; i < 3; i++) {
            decrementaRiga(matrice, i, 4);
        }
        stampa(matrice);
        System.out.println("/-----------/");
        for (int i = 0; i < 4; i++) {
            incrementaColonna(matrice, i, 4);
        }
        stampa(matrice);
    }// main

    public static void stampa(AtomicInteger[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[i][j]+"\t");
            }
            System.out.println("\n");
        }
    }// stampa

    public static void decrementaRiga(AtomicInteger[][] matrice, int riga, int x) {
        for (int i = 0; i < x; i++)
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[riga][j].set(matrice[riga][j].get()-1);
            }
    }// decrementaRiga

    public static void incrementaColonna(AtomicInteger[][] matrice, int colonna, int x) {
        for (int i = 0; i < x; i++)
            for (int j = 0; j < matrice.length; j++) {
                matrice[j][colonna].set(matrice[j][colonna].get()+1);
            }
    }// incrementaColonna
}
