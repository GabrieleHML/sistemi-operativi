package esercizi.esercitazione_4.ese_4_7;

import java.util.concurrent.Semaphore;

public class Tifoseria1 {

    private static int N = 6;
    private static Semaphore semA = new Semaphore(1);
    private static Semaphore[] sems = new Semaphore[] {new Semaphore(0), new Semaphore(0)};
    private static final int S1 = 0;
    private static final int S2 = 1;
    private static int turn = S1; // 0 = S1, 1 = S2
    private static int[] conts = new int[] {0, 0};
    private static Semaphore[] mutexs = new Semaphore[] {new Semaphore(1), new Semaphore(1)};
    private static Semaphore mutexTurn = new Semaphore(1);

    static class A extends Thread {
        public void run() {
            try {
                while (true) {
                    semA.acquire();
                    System.out.print("A");
                    mutexTurn.acquire();
                    sems[turn].release(N);
                    turn = (turn + 1) % 2;
                    mutexTurn.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // classe A

    static class S1 extends Thread {
        public void run() {
            try {
                sems[S1].acquire();
                System.out.print("S1");
                mutexs[S1].acquire();
                conts[S1]++;
                if (conts[S1] == N) {
                    conts[S1] = 0;
                    System.out.print(" ");
                    semA.release();
                }
                mutexs[S1].release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // classe S1

    static class S2 extends Thread {
        public void run() {
            try {
                sems[S2].acquire();
                System.out.print("S2");
                mutexs[S2].acquire();
                conts[S2]++;
                if (conts[S2] == N) {
                    conts[S2] = 0;
                    System.out.print(" ");
                    semA.release();
                }
                mutexs[S2].release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // classe S2

    public static void main(String[] args) {
        new A().start();
        while (true) {
            new S1().start();
            new S2().start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // main
}
