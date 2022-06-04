package appelli.piscinaCorsie;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PiscinaLC extends Piscina{
    // int numCorsie, LinkedList<Long>[] codaCorsie, ArrayList<Persona> listaPersone
    private Lock l = new ReentrantLock();
    private Condition possoNuotare = l.newCondition();
    private Condition inizioTurno = l.newCondition();
    private Condition fineTurno = l.newCondition();
    private boolean piscinaAperta;
    private int[] postiCorsia = new int[numCorsie];
    private LinkedList<Long>[] codaCorsie = new LinkedList[numCorsie];

    public PiscinaLC(int numPersone){
        super(numPersone);
        for (int i = 0; i < numCorsie; i++) {
            postiCorsia[i] = 4;
            codaCorsie[i] = new LinkedList<>();
        }
    }

    private int codaMinore(){
        int coda = 0;
        int min = codaCorsie[0].size();
        for (int i = 1; i < codaCorsie.length; i++) {
            if (codaCorsie[i].size() < min)
                min = codaCorsie[i].size();
        }
        return coda;
    }

    @Override
    public int scegliCorsia() throws InterruptedException {
        int c = codaMinore();
        l.lock();
        try {
            if (piscinaAperta){
                codaCorsie[c].addLast(Thread.currentThread().getId());
            }
        }finally {
            l.unlock();
        }
        return c;
    }

    @Override
    public void iniziaANuotare(int c) throws InterruptedException {
        l.lock();
        try {
            while (postiCorsia[c] == 0){
                possoNuotare.await();
            }
            if (piscinaAperta){
                postiCorsia[c]--;
                System.out.println("P"+Thread.currentThread().getId()+" sta nuotando");
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void finisciDiNuotare(int c) throws InterruptedException {
        l.lock();
        try {
            if (piscinaAperta){
                postiCorsia[c]++;
                System.out.println("P"+Thread.currentThread().getId()+" ha finito di nuotare");
                possoNuotare.signal();
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void faiLaDoccia() throws InterruptedException {

    }

    @Override
    public void iniziaTurno() throws InterruptedException {

    }

    @Override
    public void finisciTurno() throws InterruptedException {

    }



    public static void main(String[] args) {

    }
}
