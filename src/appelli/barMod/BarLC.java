package appelli.barMod;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarLC extends Bar{

    private Lock lockCassa = new ReentrantLock();
    private Lock lockBancone = new ReentrantLock();
    private Lock scelta = new ReentrantLock();
    private Condition possoPagare = lockCassa.newCondition();
    private Condition possoBere = lockBancone.newCondition();

    public BarLC(){
        super();
    }

    @Override
    public int scegli() {
        int op = -1;
        scelta.lock();
        try {
            if (cassaLibera)
                op = 1;
            else if(postiLiberiBancone > 0)
                op = 0;
            else if(filaCassa <= filaBancone)
                op = 1;
            else
                op = 0;
        }finally {
            scelta.unlock();
        }
        return op;
    }

    @Override
    public void inizia(int i) throws InterruptedException{
        if (i == 1) {
            lockCassa.lock();
            try {
                filaCassa++;
                while(!cassaLibera)
                    possoPagare.await();
                cassaLibera = false;
                filaCassa--;
                TimeUnit.SECONDS.sleep(5);
                System.out.println("T"+Thread.currentThread().getId()+" paga il caffe");
            }finally {
                lockCassa.unlock();
            }
        }else{
            lockBancone.lock();
            try {
                filaBancone++;
                while(!(postiLiberiBancone > 0))
                    possoBere.await();
                postiLiberiBancone--;
                filaBancone--;
                TimeUnit.SECONDS.sleep(10);
                System.out.println("T"+Thread.currentThread().getId()+" beve il caffe");
            }finally {
                lockBancone.unlock();
            }
        }
    }

    @Override
    public void finisci(int i) {
        if (i == 1){
            lockCassa.lock();
            try {
                System.out.println("T"+Thread.currentThread().getId()+" ha finito di pagare");
                cassaLibera = true;
                possoPagare.signalAll();
            }finally {
                lockCassa.unlock();
            }
        }else{
            lockBancone.lock();
            try {
                System.out.println("T"+Thread.currentThread().getId()+" ha finito di bere");
                postiLiberiBancone++;
                possoBere.signalAll();
            }finally {
                lockBancone.unlock();
            }
        }
    }

    public static void main(String[] args) {

        Bar bar = new BarLC();
        bar.test(20);

    }
}
