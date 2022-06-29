package appelli.boccaccio;

import java.util.Arrays;
import java.util.Random;

public class Testing {
    static Random r = new Random();

    private static int sommaCaramelle(int[] vet){
        int somma = 0;
        for (int x : vet) {
            somma += x;
        }
        return somma;
    }

    public static void main(String[] args) {
        int numColori = 3;
        int[] caramelle = new int[numColori];
        //Arrays.fill(caramelle,0);
        caramelle[0] = 0;
        caramelle[1] = 17;
        caramelle[2] = 45;

        for(int x : caramelle)
            System.out.printf("%d\t",x);
        System.out.printf("\n");

        for (int i = 0; i < caramelle.length; i++) {
            int x = caramelle[i];
            if (x < 3 && sommaCaramelle(caramelle) + (3-x) <=100)
                caramelle[i] = 3;
        }
        for (int i = 0; i < caramelle.length; i++) {
            int val = r.nextInt(0, (100-sommaCaramelle(caramelle))/(numColori-i));
            caramelle[i] += val;
        }
        while(sommaCaramelle(caramelle) < 100){
            for (int i = 0; i < caramelle.length; i++) {
                if (sommaCaramelle(caramelle) == 100)
                    break;
                caramelle[i]++;
            }
        }

        for(int x : caramelle)
            System.out.printf("%d\t",x);
        System.out.printf("\n");

        System.out.println("Somma: "+sommaCaramelle(caramelle));

    }

}
