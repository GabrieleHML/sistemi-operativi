package esercizi.esercitazione_4.ese_4_6;

import java.util.concurrent.Semaphore;

public class sequenze5 { // AAAAAB AAAAB AAAB AAB AB
    private static int N = 5;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semB = new Semaphore(0);
    private static int cont = 0;
    private static Semaphore mutex = new Semaphore(1);

    static class A extends Thread{
        @Override
        public void run() {
            try {
                semA.acquire();
                System.out.print("A");
                mutex.acquire();
                cont++;
                if (cont == N){
                    cont = 0;
                    N--;
                    semB.release();
                }
                mutex.release();
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// thread A

    static class B extends Thread{
        @Override
        public void run() {
            try {
                semB.acquire();
                System.out.print("B");
                mutex.acquire();
                cont++;
                if (cont == 1){
                    System.out.print(" ");
                    cont = 0;
                    semA.release(N);
                }
                mutex.release();
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// thread B

    public static void main(String[] args) {
        while (true){
            new sequenze5.A().start();
            new sequenze5.B().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// main
}
