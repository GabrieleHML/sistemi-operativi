package esercizi.seminario;

import java.io.File;
import java.util.Scanner;

public class Main {
    static long start;

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        System.out.println("cripta o decripta?");
        String mode = k.nextLine();
        File in = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\document2022.encrypted");
        if (mode.equalsIgnoreCase("cripta")){
            // TODO cripta
            System.out.print("Digitare la chiave con cui si vuole criptare >  ");
            int intKey = k.nextInt();
            File out = new File(in.getAbsolutePath().substring(0,in.getAbsolutePath().indexOf("."))+".encrypted");
            String key = String.format("%016d", intKey);
            KeyFinder.encrypt(key, in, out);
        }else if (mode.equalsIgnoreCase("decripta")){
            // TODO decripta
            System.out.print("Con quanti Thread vuoi decriptare il file? >  ");
            int n = k.nextInt();
            String parola = "SISOP-corsoB";
            start = System.currentTimeMillis();
            KeyFinder.setData(in,parola,n);
            KeyFinder[] threads = new KeyFinder[n];
            for (int i = 0; i<n; i++){
                threads[i] = new KeyFinder(i*(Integer.MAX_VALUE/n));
                threads[i].start();
            }
        }else
            System.out.println("Digitare 'cripta' o 'decripta'");
    }// main
}// Main
