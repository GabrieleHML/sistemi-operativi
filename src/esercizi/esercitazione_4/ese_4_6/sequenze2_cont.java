package esercizi.esercitazione_4.ese_4_6;

import java.util.concurrent.Semaphore;

public class sequenze2_cont { //AAB AAB AAB
    private static int N = 2;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semB = new Semaphore(0);
    private static int contA = 0;
    private static Semaphore mutexA = new Semaphore(1);

    static class A extends Thread{
        @Override
        public void run() {
            try {
                semA.acquire();
                System.out.println("A");
                mutexA.acquire();
                contA++;
                if (contA == N){
                    contA = 0;
                    semB.release();
                }
            }catch (InterruptedException e) { e.printStackTrace(); }
        }
    }// Thread A

    static class B extends Thread{
        @Override
        public void run() {
            try {
                semB.acquire();
                System.out.println("B ");
                semA.release(N);
            }catch (InterruptedException e) { e.printStackTrace(); }
        }
    }// Thread B

    public static void main(String[] args) {
        while (true){
            new A().start();
            new B().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){ e.printStackTrace(); }
        }
    }// main
}// sequenze2_cont
