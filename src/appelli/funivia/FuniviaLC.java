package appelli.funivia;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuniviaLC extends Funivia{

    private Lock l = new ReentrantLock();

    private Condition possoSalire = l.newCondition();
    private Condition possoScendere = l.newCondition();

    private Condition entrataTurPiedi = l.newCondition();
    private Condition entrataTurBici = l.newCondition();

    private Condition uscitaTurPiedi = l.newCondition();
    private Condition uscitaTurBici = l.newCondition();

    private boolean permessoEntrata = false;
    private boolean permessoUscita = false;

    private int postiFuniviaOccupati = 0;
    private int turno = 0;
    private int numViaggio = 0;
    private ArrayList<Long> turistiId = new ArrayList<>();

    public FuniviaLC(){
        super();
    }

    @Override
    public void pilotaStart() throws InterruptedException{
        l.lock();
        try {
            permessoEntrata = true;
            if (numViaggio % 2 == 0)
                entrataTurPiedi.signalAll();
            else
                entrataTurBici.signalAll();
            while(postiFuniviaOccupati != 6)
                possoSalire.await();
        }finally {
            l.unlock();
        }
    }

    @Override
    public void pilotaEnd() throws InterruptedException{
        l.lock();
        try {
            System.out.println("turno: "+turno);
            for (int i = 0; i < turistiId.size(); i++) {
                System.out.print(" "+turistiId.get(i));
            }
            System.out.println("\n");
            permessoUscita = true;
            if (numViaggio % 2 == 0)
                uscitaTurPiedi.signalAll();
            else
                uscitaTurBici.signalAll();
            while (postiFuniviaOccupati != 0)
                possoScendere.await();
            turistiId.clear();
            numViaggio++;
        }finally {
            l.unlock();
        }
    }

    @Override
    public void turistaSali(int t) throws InterruptedException{
        l.lock();
        try {
            if (turno == 0){
                while (!(permessoEntrata && postiFuniviaOccupati < 6 && numViaggio % 2 == 0))
                    entrataTurPiedi.await();
                postiFuniviaOccupati++;
            }else{
                while (!(permessoEntrata && postiFuniviaOccupati < 6 && numViaggio % 2 == 1))
                    entrataTurBici.await();
                postiFuniviaOccupati += 2;
            }
            turistiId.add(Thread.currentThread().getId());
            if (postiFuniviaOccupati == 6) {
                permessoEntrata = false;
                possoSalire.signal();
            }
        }finally {
            l.unlock();
        }
    }

    @Override
    public void turistaScendi(int t) throws InterruptedException{
        l.lock();
        try {
            if (turno == 0){
                while (!(permessoUscita && postiFuniviaOccupati > 0 && numViaggio % 2 == 0))
                    uscitaTurPiedi.await();
                postiFuniviaOccupati--;
            }
            else{
                while (!(permessoUscita && postiFuniviaOccupati > 0 && numViaggio % 2 == 1))
                    uscitaTurBici.await();
                postiFuniviaOccupati -= 2;
            }
            if (postiFuniviaOccupati == 0){
                permessoUscita = false;
                possoScendere.signal();
            }
        }finally {
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Funivia fun = new FuniviaLC();
        fun.test(18,9);
    }
}
