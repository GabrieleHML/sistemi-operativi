package appelli.barModV2;

public abstract class Bar{
    protected final int numPostiBancone = 4;
    protected int personeInCodaCassa = 0;
    protected int personeInCodaBancone = 0;

    public Bar(){
    }

    public abstract int scegli() throws InterruptedException;

    public abstract void inizia(int i) throws InterruptedException;

    public abstract void finisci(int i) throws InterruptedException;

    public void test(int numPersone){
        /*for (int i = 0; i < numPersone; i++) {
            new Persona(this).start();
        }*/
        Persona p=new Persona(this);
        System.out.println(p.getState());
    }
}
