package esercizi.esercitazione_4.ese_4_6;

import java.util.concurrent.Semaphore;

public class sequenze3 { // AA BB AA BB...
    private static int N = 2;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semB = new Semaphore(0);
    private static int cont = 0;
    private static Semaphore mutex = new Semaphore(1);

    static class A extends Thread {
        @Override
        public void run() {
            try {
                semA.acquire();
                System.out.print("A");
                mutex.acquire();
                cont++;
                if (cont == N){
                    System.out.print(" ");
                    cont = 0;
                    semB.release(N);
                }
                mutex.release();
            }catch (InterruptedException e) {e.printStackTrace();}
        }
    }// Thread A

    static class B extends Thread{
        @Override
        public void run() {
            try {
                semB.acquire();
                System.out.print("B");
                mutex.acquire();
                cont++;
                if (cont == N){
                    System.out.print(" ");
                    cont = 0;
                    semA.release(N);
                }
                mutex.release();
            }catch (InterruptedException e){ e.printStackTrace();}
        }
    }// Thread B

    public static void main(String[] args) {
        while (true){
            new sequenze3.A().start();
            new sequenze3.B().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// main
}