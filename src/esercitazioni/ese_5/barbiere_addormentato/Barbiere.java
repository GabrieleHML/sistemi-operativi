package esercitazioni.ese_5.barbiere_addormentato;

import java.util.concurrent.TimeUnit;

public class Barbiere extends Thread{
    private Sala sala;

    public Barbiere(Sala s){
        sala = s;
    }

    @Override
    public void run() {
        while(true) {
            try {
                sala.tagliaCapelli();
                System.out.println("Taglio in corso...");
                taglio();
            }catch (InterruptedException e){}
        }
    }

    private void taglio() throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
    }
}
