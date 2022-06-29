package appelli.boccaccio;

public class Addetto extends Thread{
    Boccaccio b;

    Addetto(Boccaccio b){
        this.b = b;
    }

    @Override
    public void run() {
        try {
            while(true){
                b.riempi();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
