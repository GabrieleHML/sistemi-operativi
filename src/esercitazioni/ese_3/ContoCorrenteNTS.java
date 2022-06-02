package esercitazioni.ese_3;

public class ContoCorrenteNTS extends ContoCorrente{
    public ContoCorrenteNTS(int depositoIniziale){
        super(depositoIniziale);
    }//costruttore

    public void deposita(int importo) {
        this.deposito += importo;
    }

    public void preleva(int importo){
        this.deposito -= importo;
    }
}//ContoCorrenteNTS
