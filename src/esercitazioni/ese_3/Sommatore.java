package esercitazioni.ese_3;

public class Sommatore extends Thread{
    private int da;
    private int a;
    private int somma;

    //private boolean continua = true;

    public Sommatore(int da, int a){
        this.da = da;
        this.a = a;
    }

    public void run(){
        //while(continua){ ...instruzioni... }
        somma = 0;
        for (int i=da; i<a; i++)
            somma+=i;
    }

    public int getSomma() throws InterruptedException{
        this.join();
        return somma;
    }
}
