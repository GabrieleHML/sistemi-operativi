package esercitazioni.ese_4;

import esercitazioni.ese_3.ContoCorrenteAI;
import java.util.concurrent.Semaphore;

public class ContoCorrenteSem extends ContoCorrenteAI {
    private Semaphore mutex = new Semaphore(1);
    private int deposito;

    public ContoCorrenteSem(int depositoIniziale) {
        super(depositoIniziale);
    }

    public void deposita(int importo){
        try{
            mutex.acquire();
            deposito += importo;
            mutex.release();
        }catch (InterruptedException e){}
    }

    public void preleva(int importo) {
        try{
            mutex.acquire();
            deposito -= importo;
            mutex.release();
        }catch (InterruptedException e) {}
    }
}
