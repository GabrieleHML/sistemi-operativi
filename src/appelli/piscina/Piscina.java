package appelli.piscina;

public abstract class Piscina {
    final int N;
    final int X;

    Piscina(int numCorsie, int personeXCorsia){
        N = numCorsie;
        X = personeXCorsia;
    }

    public abstract void entra() throws InterruptedException;

    public abstract void esci() throws InterruptedException;

    public void test(){
        for (int i = 0; i < 80; i++) {
            new Persona(this).start();
        }
    }// test
}
