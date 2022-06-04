package appelli.piscinaCorsie;

import java.util.concurrent.Semaphore;

public class PiscinaSem extends Piscina{
    /*TODO
     * 4 persone max a corsia
     * ogni persona sceglie la corsia occupata dal minor numero di persone
     * se la piscina è al completo, sceglie una corsia in maniera casuale e attende in ordine FIFO
     * entrato in corsia, nuota (30-60 minuti)
     * dopo si fa la doccia (20 minuti)
     * l'istruttore -> piscina -> 4 ore mattina -> 1 ora pausa -> 5 ore pomeriggio
     * se non è presente l'istruttore nessuna persona può enrrare in piscina
     * alla chiusura della piscina l'istruttore forza le persone ad uscire, e si vanno a fare la doccia
     */

    private Semaphore[] corsie = new Semaphore[numCorsie]; // semaforo che gestisce i 4 posti per ogni corsia
    private Semaphore[] mutexCoda = new Semaphore[numCorsie]; // semaforo che gestisce l'incremento, decremento della coda delle corsie
    private Semaphore istruttoreSem = new Semaphore(0); // semafoto che gestisce l'entrata delle persone
    private Semaphore mutex = new Semaphore(1); // mutex
    private int[] codaCorsie = new int[numCorsie];

    public PiscinaSem(int numPersone){
        super(numPersone);
        for (int i = 0; i < numCorsie; i++) {
            corsie[i] = new Semaphore(4); // 4 è il numero massimo di persone a corsia
            mutexCoda[i] = new Semaphore(1); // per fare l'accesso alla coda in mutua esclusione
            codaCorsie[i] = 0;
        }
    }

    @Override
    public int scegliCorsia() throws InterruptedException{
        istruttoreSem.acquire(1);
        mutex.acquire();
        int min = codaCorsie[0];
        int coda = 0;
        for (int i = 1; i < numCorsie; i++) {
            if (codaCorsie[i] < min){
                min = codaCorsie[i];
                coda = i;
            }
        }
        mutex.release();
        return coda;
    }

    @Override
    public void iniziaANuotare(int c) throws InterruptedException{

    }

    @Override
    public void finisciDiNuotare(int c) throws InterruptedException {

    }

    @Override
    public void faiLaDoccia() throws InterruptedException{

    }

    @Override
    public void iniziaTurno() throws InterruptedException {
        istruttoreSem.release(numPersone);
        System.out.println("Istruttore: Inizio turno!");
    }

    @Override
    public void finisciTurno() throws InterruptedException {
        System.out.println("Istruttore: Fine turno!");
        for (Persona p : listaPersone) {
            p.interrupt();
        }
    }



    public static void main(String[] args) {
        Piscina p = new PiscinaSem(100);
        p.test();
    }
}
