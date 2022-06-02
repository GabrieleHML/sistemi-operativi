package esercizi.esercitazione_4.ese_4_1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        Thread[] listaT = new Thread[10];
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread();
            listaT[i] = t;

        }
    }
    class Tr extends Thread{

    }
}
