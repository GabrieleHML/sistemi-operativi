package esercizi.esercitazione_4.ese_4_6;

import java.util.concurrent.Semaphore;

public class sequenze6 { // ABB AABB AAABB AAAABB AAAAABB ...
    private static int N = 1;
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
                    N++;
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
                if (cont == 2){
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
            new sequenze6.A().start();
            new sequenze6.B().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// main
}
