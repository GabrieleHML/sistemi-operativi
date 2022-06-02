package esercitazioni.ese_3;

import java.util.Scanner;

public class ContoCorrenteTest {
    public static void main(String[] args) throws InterruptedException {
        Scanner k = new Scanner(System.in);
        System.out.println("Inserire deposito iniziale > ");
        int depositoIniziale = k.nextInt();
        ContoCorrente cc = new ContoCorrenteAI(depositoIniziale);

        System.out.println("Inserire numero correntisti > ");
        int numCorrentisti = k.nextInt();
        System.out.println("Inserire l'importo > ");
        int importo = k.nextInt();
        System.out.println("Inserire numero operazioni (n pari) > ");
        int numOperazioni = k.nextInt();
        testContoCorrente(cc, numCorrentisti, importo, numOperazioni);

        if (cc.getDeposito() == depositoIniziale)
            System.out.format("Corretto! Il deposito finale è %s%n",cc.getDeposito());
        else
            System.out.format("Errore! Il deposito iniziale era di %s, il deposito finale è di %s%n", depositoIniziale, cc.getDeposito());
    }// main

    private static void testContoCorrente(ContoCorrente cc, int numCorrentisti, int importo, int numOperazioni) throws InterruptedException {
        Correntista correntisti[] = new Correntista[numCorrentisti];
        for (int i = 0; i < numCorrentisti; i++)
            correntisti[i] = new Correntista(cc, importo, numOperazioni);

        Thread threadCorrentisti[] = new Thread[numCorrentisti];
        for (int i = 0; i < numCorrentisti; i++) {
            threadCorrentisti[i] = new Thread(correntisti[i]);
            threadCorrentisti[i].start();
        }
        for (int i = 0; i < numCorrentisti; i++)
            threadCorrentisti[i].join();
    }// testContoCorrente
}
