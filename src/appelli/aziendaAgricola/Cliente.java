package appelli.aziendaAgricola;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread{
    private AziendaAgricola aa;
    private Random r = new Random();
    private LinkedList<Long> clientID;

    public Cliente(AziendaAgricola aa, LinkedList<Long> clientID){
        this.aa = aa;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            int n = r.nextInt(1,10);
            aa.pagaAllaCassa(n);
            for (int i = 0; i < n; i++) {
                aa.ritiraSacchetto();
                TimeUnit.SECONDS.sleep(1);
            }
            clientID.removeFirst();
            System.out.println(clientID);
            System.out.println("C"+Thread.currentThread().getId()+" ha ritirato "+n+" sacchetti");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
