package appelli.barMod;

public abstract class Bar {
    protected int postiLiberiBancone;
    protected int filaBancone;
    protected int filaCassa;
    protected boolean cassaLibera;

    public Bar(){
        postiLiberiBancone = 4;
        cassaLibera = true;
        filaBancone = 0;
        filaCassa = 0;
    }

    public abstract int scegli();
    // 1 -> paga e dopo beve il caffè
    // 0 -> bevi il caffè e dopo paga

    public abstract void inizia(int i) throws InterruptedException;

    public abstract void finisci(int i);

    public void test(int numPersone){
        for (int i = 0; i < numPersone; i++) {
            new Persona(this).start();
        }
    }
}
