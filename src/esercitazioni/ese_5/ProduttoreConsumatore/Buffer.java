package esercitazioni.ese_5.ProduttoreConsumatore;

public abstract class Buffer <T> {
    protected int[] buffer;
    protected int in = 0;
    protected int out = 0;

    public Buffer(int dimensione){
        buffer = new int[dimensione];
    }

    public abstract void put(int i) throws InterruptedException;

    public abstract int get() throws InterruptedException;

    public void test(int numProdotti, int numConsumatori){
        for (int i = 0; i < numProdotti; i++) {
            new Thread(new Produttore(this)).start();
        }
        for (int i = 0; i < numConsumatori; i++) {
            new Thread(new Consumatore(this)).start();
        }
    }
}
