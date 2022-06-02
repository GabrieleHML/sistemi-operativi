package appelli.aziendaAgricola;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AziendaAgricolaSem extends AziendaAgricola{
    private Semaphore cassa = new Semaphore(1);
    private Semaphore magazzino = new Semaphore(1);
    private Semaphore finiti = new Semaphore(0);

    public AziendaAgricolaSem(){
        super();
    }

    @Override
    public void pagaAllaCassa(int numSacchetti) throws InterruptedException{
        try {
            cassa.acquire();
            System.out.println("T"+Thread.currentThread().getId()+" ha comprato "+numSacchetti+" sacchetti");
            incasso += (3*numSacchetti);
            clientID.addLast(Thread.currentThread().getId());
            cassa.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void ritiraSacchetto() throws InterruptedException{
        try {
            if (clientID.getFirst() != null && Thread.currentThread().getId() == clientID.getFirst()){
                magazzino.acquire();
                numSacchettiInMagazzino--;
                if (numSacchettiInMagazzino == 0)
                    finiti.release();
                else
                    magazzino.release();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void ricaricaSacchetti() throws InterruptedException{
        try {
            finiti.acquire();
            numSacchettiInMagazzino = 200;
            System.out.println("Magazziniere ha ricaricato");
            TimeUnit.SECONDS.sleep(10);
            magazzino.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            AziendaAgricola aa = new AziendaAgricolaSem();
            aa.test(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
