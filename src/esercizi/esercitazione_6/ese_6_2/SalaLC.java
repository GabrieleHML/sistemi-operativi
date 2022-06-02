package esercizi.esercitazione_6.ese_6_2;

import esercitazioni.ese_5.barbiere_addormentato.Cliente;
import esercitazioni.ese_5.barbiere_addormentato.Sala;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaLC extends Sala {

    private Lock l = new ReentrantLock();
    Condition clienteDisponibile = l.newCondition();
    Condition poltrona = l.newCondition();
    LinkedList<Cliente> clienti = new LinkedList<Cliente>();
    protected boolean poltronaLibera = false;

    public SalaLC(int sedie){
        super(sedie);
    }

    @Override
    public void tagliaCapelli() throws InterruptedException {
        l.lock();
        try {
            while(numSedie == sedieLibere){
                clienteDisponibile.await();
            }
            poltronaLibera = true;
            poltrona.signalAll();
        }finally {
            l.unlock();
        }
    }

    @Override
    public boolean attendiTaglio() throws InterruptedException {
        l.lock();
        Cliente c = (Cliente) Thread.currentThread();
        try {
            if (sedieLibere == 0)
                return false;
            clienti.addLast(c);
            sedieLibere--;
            clienteDisponibile.signal();
            while (!(clienti.getFirst().getID() == c.getID() && poltronaLibera))
                poltrona.await();
            clienti.removeFirst();
            poltronaLibera = false;
            sedieLibere++;
            return true;
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        try {
            Sala s = new SalaLC(5);
            s.test(30);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
