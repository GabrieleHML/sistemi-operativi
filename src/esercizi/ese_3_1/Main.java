package esercizi.ese_3_1;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    /*
    public static void main(String[] args) throws InterruptedException {
        int n = 3, m = 4;
        Matrice mat = new MatriceAI(n, m);

        ThreadRiga[] tr = new ThreadRiga[n];
        ThreadColonna[] tc = new ThreadColonna[m];

        for (int i = 0; i < n; i++) {
            tr[i] = new ThreadRiga(mat, i);
            tr[i].start();
        }
        for (int i = 0; i < m; i++) {
            tc[i] = new ThreadColonna(mat, i);
            tc[i].start();
        }

        for (int i = 0; i < n; i++) {
            tr[i].join();
        }
        for (int i = 0; i < m; i++) {
            tc[i].join();
        }
        System.out.println(mat);
    }//main
    */
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 30; j++) {
            int n = 3, m = 4;
            Matrice mat = new MatriceAI(n, m);

            ThreadRiga[] tr = new ThreadRiga[n];
            ThreadColonna[] tc = new ThreadColonna[m];

            for (int i = 0; i < n; i++) {
                tr[i] = new ThreadRiga(mat, i);
                tr[i].start();
            }
            for (int i = 0; i < m; i++) {
                tc[i] = new ThreadColonna(mat, i);
                tc[i].start();
            }

            for (int i = 0; i < n; i++) {
                tr[i].join();
            }
            for (int i = 0; i < m; i++) {
                tc[i].join();
            }
            //System.out.println(mat);
            if (matriceDiZeri(null)){
                //TODO stress test
                System.out.println("matrice scorretta!");
            }

        }
    }//main StressTest

    public static boolean matriceDiZeri(AtomicInteger[][] matrice){
        boolean ret = true;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                if (matrice[i][j].get() != 0)
                    return false;
            }
        }
        return ret;
    }
}//Main
