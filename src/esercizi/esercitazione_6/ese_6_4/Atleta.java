package esercizi.esercitazione_6.ese_6_4;

public class Atleta extends Thread{
    private final int numMaglia;
    private OrdineLC or;

    public Atleta(int nMaglia, OrdineLC o) {
        this.numMaglia = nMaglia;
        or = o;
    }

    @Override
    public void run() {
        try {
            or.ordina();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getNumMaglia() {
        return numMaglia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atleta atleta = (Atleta) o;

        return numMaglia == atleta.numMaglia;
    }

    @Override
    public int hashCode() {
        return numMaglia;
    }
}
