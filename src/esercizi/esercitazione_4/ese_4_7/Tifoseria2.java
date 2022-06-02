package esercizi.esercitazione_4.ese_4_7;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Tifoseria2 {

    private static int N = 6;
    private static Semaphore semA = new Semaphore(1);
    private static Semaphore[] sems = new Semaphore[] {new Semaphore(0), new Semaphore(0)};
    private static final int S1 = 0;
    private static final int S2 = 1;
    private static int turn = -1; // 0 = S1, 1 = S2
    private static int[] conts = new int[] {0, 0};
    private static Semaphore[] mutexs = new Semaphore[] {new Semaphore(1), new Semaphore(1)};
    private static Semaphore mutexTurn = new Semaphore(1);
    private static int[] contWait = new int[] {0, 0};
    private static Semaphore[] mutexWait = new Semaphore[] {new Semaphore(1), new Semaphore(1)};
    private static Random r = new Random();

    static class A extends Thread{
        public void run() {
            try {
                while (true){
                    semA.acquire();
                    System.out.print("A");
                    mutexTurn.acquire();
                    mutexWait[S1].acquire();
                    mutexWait[S2].acquire();
                    if (contWait[S1] == contWait[S2]){
                        if (r.nextDouble() > 0.5)
                            turn = S1;
                        else
                            turn = S2;
                    } else if (contWait[S1] > contWait[S2]) {
                        turn = S1;
                    }else
                        turn = S2;
                    sems[turn].release(N);
                    mutexWait[S2].release();
                    mutexWait[S1].release();
                    mutexTurn.release();
                }
            }catch (InterruptedException e){}
        }
    }

    static class S1 extends Thread{
        public void run() {
            try {
                mutexWait[S1].acquire();
                contWait[S1]++;
                mutexWait[S1].release();

                sems[S1].acquire();

                mutexWait[S1].acquire();
                contWait[S1]--;
                mutexWait[S1].release();

                System.out.print("S1");
                mutexs[S1].acquire();
                conts[S1]++;
                if (conts[S1] == N){
                    conts[S1] = 0;
                    System.out.print(" ");
                    semA.release();
                }
                mutexs[S1].release();
            }catch (InterruptedException e){}
        }
    }// s1

    static class S2 extends Thread{
        public void run() {
            try {
                mutexWait[S2].acquire();
                contWait[S2]++;
                mutexWait[S2].release();

                sems[S2].acquire();

                mutexWait[S2].acquire();
                contWait[S2]--;
                mutexWait[S2].release();

                System.out.print("S2");
                mutexs[S2].acquire();
                conts[S2]++;
                if (conts[S2] == N){
                    conts[S2] = 0;
                    System.out.print(" ");
                    semA.release();
                }
                mutexs[S2].release();
            }catch (InterruptedException e){}
        }
    }// s2

    public static void main(String[] args) {
        new A().start();
        while (true){
            new S1().start();
            new S2().start();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){}
        }
    }// main
}
