package esercitazioni.ese_3;

import java.util.concurrent.atomic.AtomicInteger;

public class ContoCorrenteAI extends ContoCorrente{
    private AtomicInteger deposito;

    public ContoCorrenteAI(int depositoIniziale){
        super(depositoIniziale);
        deposito = new AtomicInteger(depositoIniziale);
    }//costruttore

    public void deposita(int importo){
        deposito.addAndGet(importo);
    }

    public void preleva(int importo){
        deposito.addAndGet(-importo);
    }

    public int getDeposito(){
        return deposito.get();
    }
}//ContoCorrenteAI
