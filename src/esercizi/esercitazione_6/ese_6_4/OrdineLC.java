package esercizi.esercitazione_6.ese_6_4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrdineLC {

    private static final int N = 10;
    private Lock l = new ReentrantLock();
    Condition[] c = new Condition[N];
    private int cont = 0;

    public OrdineLC(){
        for (int i = 0; i < N; i++) {
            c[i] = l.newCondition();
        }
    }

    public void ordina() throws InterruptedException{
        l.lock();
        Atleta a = (Atleta) Thread.currentThread();
        int num = a.getNumMaglia();
        try {
            while(!(num == cont))
                c[num].await();
            System.out.println(num);
            cont++;
            c[num].signal();
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        OrdineLC o = new OrdineLC();
        for (int i = 0; i < N; i++) {
            new Atleta(i, o).start();
        }
    }// main
}// OrdineLC
