package appelli.barModV2;

import java.util.concurrent.Semaphore;

public class BarSem extends Bar{
    private Semaphore mutexCassa = new Semaphore(1, true);
    private Semaphore mutexBancone = new Semaphore(numPostiBancone, true);
    private Semaphore mutexCodaCassa = new Semaphore(1);
    private Semaphore mutexCodaBancone = new Semaphore(1);

    public BarSem(){
    }

    @Override
    public int scegli() throws InterruptedException{
        int op = -1;
        mutexCodaCassa.acquire();
        mutexCodaBancone.acquire();
        if (personeInCodaCassa == 0)
            op = 0;
        else if (personeInCodaBancone == 0)
            op = 1;
        else{
            if (personeInCodaCassa <= personeInCodaBancone)
                op = 0;
            else
                op = 1;
        }
        mutexCodaBancone.acquire();
        mutexCodaCassa.acquire();
        return op;
    }

    @Override
    public void inizia(int i) throws InterruptedException{
        if (i == 0){ // comincia a pagare
            mutexCodaCassa.acquire();
            personeInCodaCassa++;
            mutexCodaCassa.release();

            mutexCassa.acquire();
            System.out.println("P"+Thread.currentThread().getId()+" comincia a pagare");

            mutexCodaCassa.acquire();
            personeInCodaCassa--;
            mutexCodaCassa.release();
        }else{ // comincia a bere
            mutexCodaBancone.acquire();
            personeInCodaBancone++;
            mutexCodaBancone.release();

            mutexBancone.acquire();
            System.out.println("P"+Thread.currentThread().getId()+" comincia a bere");

            mutexCodaBancone.acquire();
            personeInCodaBancone--;
            mutexCodaBancone.release();
        }
    }

    @Override
    public void finisci(int i) throws InterruptedException{
        if (i == 0){ // finisce di pagare
            System.out.println("P"+Thread.currentThread().getId()+" ha finito di pagare");
            mutexCassa.release();
        }else{ // finisce di bere
            System.out.println("P"+Thread.currentThread().getId()+" ha finito di bere");
            mutexBancone.release();
        }
    }

    public static void main(String[] args) {
        Bar b = new BarSem();
        b.test(100);
    }
}
