package appelli.barMod;

import java.util.concurrent.Semaphore;

public class BarSem extends Bar{
    private Semaphore mutex = new Semaphore(1);
    private Semaphore cassa = new Semaphore(1);
    private Semaphore postiBancone = new Semaphore(4);


    public BarSem(){
        super();
    }

    @Override
    public int scegli() {
        int op = -1;
        try {
            mutex.acquire();
            if (cassaLibera)
                op = 1;
            else if (postiLiberiBancone > 0)
                op = 0;
            else if (filaCassa <= filaBancone)
                op = 1;
            else
                op = 0;
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return op;
    }

    @Override
    public void inizia(int i){
        try {
            if (i == 1){ // prima paga, poi beve
                mutex.acquire();
                filaCassa++;
                mutex.release();

                cassa.acquire();
                mutex.acquire();
                filaCassa--;
                cassaLibera = false;
                System.out.println("T"+Thread.currentThread().getId()+" paga il caffe");
                mutex.release();
            }else{
                mutex.acquire();
                filaBancone++;
                mutex.release();

                postiBancone.acquire();
                mutex.acquire();
                filaBancone--;
                postiLiberiBancone--;
                System.out.println("T"+Thread.currentThread().getId()+" beve il caffe");
                mutex.release();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void finisci(int i){
        try {
            if (i == 1){
                mutex.acquire();
                cassaLibera = true;
                cassa.release();
                System.out.println("T"+Thread.currentThread().getId()+" ha finito di pagare");
                mutex.release();
            }else{
                mutex.acquire();
                filaBancone--;
                postiBancone.release();
                System.out.println("T"+Thread.currentThread().getId()+" ha finito di bere");
                mutex.release();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bar bar = new BarSem();
        bar.test(10);
    }
}
