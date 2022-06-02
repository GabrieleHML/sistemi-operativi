package esercitazioni.ese_5.barbiere_addormentato;

import java.util.concurrent.Semaphore;

public class SalaSem extends Sala{
    Semaphore mutex = new Semaphore(1);
    Semaphore clienteDisponibile = new Semaphore(0, true);
    Semaphore poltrona = new Semaphore(0, true);

    public SalaSem(int sedie){
        super(sedie);
    }

    @Override
    public void tagliaCapelli() throws InterruptedException {
        clienteDisponibile.acquire();
        poltrona.release();
    }

    @Override
    public boolean attendiTaglio() throws InterruptedException {
        mutex.acquire();
        if (sedieLibere == 0){
            mutex.release();
            return false;
        }
        sedieLibere--;
        mutex.release();
        clienteDisponibile.release();
        poltrona.acquire();
        mutex.acquire();
        sedieLibere++;
        mutex.release();
        return true;
    }

    public static void main(String[] args) {
        try {
            Sala s = new SalaSem(5);
            s.test(30);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
