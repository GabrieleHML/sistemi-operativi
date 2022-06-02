package esercitazioni.ese_5.barbiere_addormentato;

public class Cliente extends Thread{
    private Sala sala;
    private int ID;

    public Cliente(Sala s, int i){
        sala = s;
        ID = i;
    }

    @Override
    public void run() {
        try {
            System.out.format("Il cliente %d vuole tagliarsi i capelli%n", ID);
            boolean ret = sala.attendiTaglio();
            if (ret)
                System.out.format("Il cliente %d è riuscito a tagliarsi i capelli%n", ID);
            else
                System.out.format("Il cliente %d ha abbandonato la sala%n", ID);
        }catch (InterruptedException e){}
    }

    public int getID(){ return ID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        if (ID != cliente.ID) return false;
        return sala != null ? sala.equals(cliente.sala) : cliente.sala == null;
    }

    @Override
    public int hashCode() {
        int result = sala != null ? sala.hashCode() : 0;
        result = 31 * result + ID;
        return result;
    }
}
