package appelli.computer;

import java.util.concurrent.Semaphore;

public class ComputerSem extends Computer{
    int[] coreLiberi = new int[2];
    Semaphore[] mutex = new Semaphore[2];
    Semaphore[] core = new Semaphore[2];

    ComputerSem(){
        super();
        coreLiberi[0] = 4;
        coreLiberi[1] = 4;
        mutex[0] = new Semaphore(1);
        mutex[1] = new Semaphore(1);
        core[0] = new Semaphore(4, true);
        core[1] = new Semaphore(4, true);
    }

    @Override
    public void richiedi(int nCore, int idP) throws InterruptedException {
        core[idP].acquire(nCore);
        mutex[idP].acquire();
        if (coreLiberi[idP] >= nCore)
            coreLiberi[idP] -= nCore;
        System.out.printf("U%d richiede (%d, %d)%n", Thread.currentThread().getId(), nCore, idP);
        System.out.println("Core liberi: "+coreLiberi[idP]);
        mutex[idP].release();
    }

    @Override
    public void rilascia(int nCore, int idP) throws InterruptedException {
        mutex[idP].acquire();
        coreLiberi[idP] += nCore;
        System.out.printf("U%d rilascia %d core%n", Thread.currentThread().getId(), nCore);
        System.out.println("Core liberi: "+coreLiberi[idP]);
        mutex[idP].release();
        core[idP].release(nCore);
    }

    public static void main(String[] args) {
        Computer c = new ComputerSem();
        c.test();
    }
}
