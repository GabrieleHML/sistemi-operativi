package esercizi.esercitazione_6.ese_6_1;

import esercitazioni.ese_6.Buffer;
import esercitazioni.ese_6.BufferLC;
import esercitazioni.ese_6.Elemento;

import java.util.LinkedList;

public class BufferLCFifo extends BufferLC {
    private static LinkedList<Thread> codaProduttori = new LinkedList<>();
    private static LinkedList<Thread> codaConsumatori = new LinkedList<>();

    public BufferLCFifo(int dimensione){
        super(dimensione);
    }

    private boolean possoInserire(){
        return numElementi < buffer.length && Thread.currentThread() == codaProduttori.getFirst();
    }

    private void addWithPriority(Thread t, int prodORcons){ // 0 -> Produttori ; 1 -> Consumatori
        l.lock();
        try {
            switch (prodORcons){
                case 0:
                    if (codaProduttori.size() == 0)
                        codaProduttori.add(t);
                    else{
                        for (int i = 0; i < codaProduttori.size()-1; i++) {
                            if (codaProduttori.get(i).getId() > t.getId()){
                                codaProduttori.add(i,t);
                                break;
                            }
                        }
                    }
                    break;
                case 1:
                    if (codaConsumatori.size() == 0)
                        codaConsumatori.add(t);
                    else{
                        for (int i = 0; i < codaConsumatori.size(); i++) {
                            if (codaConsumatori.get(i).getId() > t.getId())
                                codaConsumatori.add(i,t);
                        }
                    }
                    break;
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void put(Elemento e) throws InterruptedException {
        l.lock();
        try {
            //codaProduttori.add(Thread.currentThread());
            addWithPriority(Thread.currentThread(),0);
            while (!possoInserire()){
                bufferPieno.await(); // bufferPieno è in attesa finchè possoInserire restituisce false
            }
            codaProduttori.remove();
            buffer[in] = e;
            in = (in + 1) % buffer.length;
            numElementi++;
            System.out.println(this);
            bufferVuoto.signalAll();
        }finally {
            l.unlock();
        }
    }

    private boolean possoPrelevare(){
        return numElementi > 0 && Thread.currentThread() == codaConsumatori.getFirst();
    }

    @Override
    public Elemento get() throws InterruptedException {
        Elemento e = null;
        l.lock();
        try {
            //codaConsumatori.add(Thread.currentThread());
            addWithPriority(Thread.currentThread(), 1);
            while (!possoPrelevare()){
                bufferVuoto.await();
            }
            codaConsumatori.remove();
            e = buffer[out];
            buffer[out] = null;
            out = (out + 1) % buffer.length;
            numElementi--;
            System.out.println(this);
            bufferPieno.signalAll();
        }finally {
            l.unlock();
        }
        return e;
    }

    public static void main(String[] args) {
        int dimensione = 10;
        Buffer buffer = new BufferLCFifo(dimensione);
        int numProduttori = 10;
        int numConsumatori = 10;
        buffer.test(numProduttori, numConsumatori);
    }
}
