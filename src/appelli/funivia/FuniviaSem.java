package appelli.funivia;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FuniviaSem extends Funivia{

    private Semaphore possoScendere = new Semaphore(0);
    private Semaphore possoSalire = new Semaphore(0);
    private Semaphore postiFunivia = new Semaphore(6);
    private Semaphore inCima = new Semaphore(0);
    private int turno = 0; // 0 -> a piedi ; 1 -> in bici
    private ArrayList<Long> turistiId = new ArrayList<>();
    private Semaphore mutex = new Semaphore(1);
    private int postiOccupati = 0;
    private Semaphore possoFinire = new Semaphore(0);

    public FuniviaSem(){
    } // costruttore

    @Override
    public void pilotaStart() {
        try {
            possoSalire.acquire();
            inCima.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void pilotaEnd() {
        try {
            inCima.acquire();
            System.out.println("turno: " + turno);
            if (turno == 0) {
                for (int i = 0; i < 6; i++) {
                    System.out.print(" "+turistiId.get(i));
                }
                System.out.println("\n");
            }else{
                for (int i = 0; i < 3; i++) {
                    System.out.println(" "+turistiId.get(i));
                }
                System.out.println("\n");
            }
            turno = 1 - turno;
            turistiId.clear();
            possoFinire.acquire();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void turistaSali(int t) {
        try {
            if (t == 0){ // sale turista a piedi
                mutex.acquire();
                postiFunivia.acquire(1);
                postiOccupati++;
                turistiId.add(Thread.currentThread().getId());
                if (postiOccupati == 6)
                    possoSalire.release();
                mutex.release();
            }else{ // sale turista in bici
                mutex.acquire();
                postiFunivia.acquire(2);
                postiOccupati++;
                turistiId.add(Thread.currentThread().getId());
                if (postiOccupati == 3)
                    possoSalire.release();
                mutex.release();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void turistaScendi(int t) {
        try {
            if (t == 0){ // scende turista a piedi
                mutex.acquire();
                postiFunivia.release(1);
                postiOccupati--;
                if (postiOccupati == 0)
                    possoFinire.release();
                mutex.release();
            }else{ // scende turista in bici
                mutex.acquire();
                postiFunivia.release(2);
                postiOccupati--;
                if (postiOccupati == 0)
                    possoFinire.release();
                mutex.release();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Funivia fun = new FuniviaSem();
        fun.test(18,9);
    }
}
