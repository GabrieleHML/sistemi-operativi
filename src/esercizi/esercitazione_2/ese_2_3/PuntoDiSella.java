package esercizi.esercitazione_2.ese_2_3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PuntoDiSella{
    public static void main(String[] args) throws InterruptedException {
        int[][] mat =  {{2,7,2,5,2},
                        {2,5,9,7,2},
                        {1,4,1,4,1},
                        {2,5,2,9,2}};
        int n = mat.length;
        int m = mat[0].length;
        LinkedList<Punto> listaPuntiDiSella = new LinkedList<>();
        HashMap<Punto, Integer> punti = new HashMap<>();

        ThreadRiga.setData(mat);
        ThreadColonna.setData(mat);

        ThreadRiga[] tr = new ThreadRiga[n];
        ThreadColonna[] tc = new ThreadColonna[m];

        for (int i=0; i<n; i++) {
            tr[i] = new ThreadRiga(i);
            tr[i].start();
        }
        for (int i=0; i<m; i++) {
            tc[i] = new ThreadColonna(i);
            tc[i].start();
        }

        for (ThreadRiga tRiga : tr)
            for (Punto pr : tRiga.getMax())
                if (!punti.containsKey(pr))
                    punti.put(pr,1);
                else
                    punti.put(pr,2);

        for (ThreadColonna tCol : tc)
            for (Punto pc : tCol.getMin())
                if (!punti.containsKey(pc))
                    punti.put(pc,1);
                else
                    punti.put(pc,2);

        for (Map.Entry<Punto, Integer> corrente : punti.entrySet()) {
            if (corrente.getValue() == 2)
                listaPuntiDiSella.add(corrente.getKey());
        }

        if (listaPuntiDiSella.isEmpty())
            System.out.println("Non sono presenti punti di sella");
        for (Punto p : listaPuntiDiSella)
            System.out.println(p.toString());
    }//main
}//PuntoDiSella
