package esercizi.esercitazione_4.ese_4_6;

import java.util.concurrent.Semaphore;

public class sequenza_AABB {
    private static Semaphore semA = new Semaphore(2);
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
                    if (cont == 2){
                        cont = 0;
                        semB.release(2);
                    }
                    mutex.release();
            }catch (InterruptedException e){
                System.out.println("problema thread A");
            }
        }
    }// threadA

    static class B extends Thread{
        @Override
        public void run() {
            try {
                semB.acquire();
                System.out.print("B");
                mutex.acquire();
                cont++;
                if(cont == 2){
                    System.out.print(" ");
                    cont = 0;
                    semA.release(2);
                }
                mutex.release();
            }catch (InterruptedException e){
                System.out.println("problema thread B");
            }
        }
    }// threadB

    public static void main(String[] args) {
        while(true){
            new sequenza_AABB.A().start();
            new sequenza_AABB.B().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("problema sleep");
            }
        }
    }// main
}
