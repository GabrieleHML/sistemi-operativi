package appelli.ristorante;

public abstract class Buffer {
    protected int capacità;
    final int maxCap;
    final int ID_CONTENITORE = 0;
    final int ID_SCOLAPIATTI = 1;

    Buffer(int capacità){
        this.capacità = 0;
        this.maxCap = capacità;
    }

    public String lettera(){
        Thread t = Thread.currentThread();
        if (t instanceof Cameriere)
            return "C";
        if (t instanceof Lavapiatti)
            return "L";
        return "A";
    }
    public String parola(int id){
        if (id == 0)
            return "Contenitore";
        return "Scolapiatti";
    }

    public abstract void get(int n, int id) throws InterruptedException;

    public abstract void put(int n, int id) throws InterruptedException;

}
