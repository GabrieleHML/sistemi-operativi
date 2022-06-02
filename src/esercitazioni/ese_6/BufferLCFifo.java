package esercitazioni.ese_6;

import java.util.LinkedList;

public class BufferLCFifo extends BufferLC {

	private LinkedList<Thread> codaProduttori = new LinkedList<Thread>();

	private LinkedList<Thread> codaConsumatori = new LinkedList<Thread>();

	public BufferLCFifo(int dimensione) {
		super(dimensione);
	}

	public void put(Elemento el) throws InterruptedException {
		l.lock();
		try {
			codaProduttori.add(Thread.currentThread());
			while (!possoInserire()) {
				bufferPieno.await();
			}
			codaProduttori.remove();
			buffer[in] = el;
			in = (in + 1) % buffer.length;
			numElementi++;
			System.out.println(this);
			bufferVuoto.signalAll();
		} finally {
			l.unlock();
		}
	}

	private boolean possoInserire() {
		return numElementi < buffer.length && Thread.currentThread() == codaProduttori.getFirst();
	}

	public Elemento get() throws InterruptedException {
		Elemento el = null;
		l.lock();
		try {
			codaConsumatori.add(Thread.currentThread());
			while (!possoPrelevare()) {
				bufferVuoto.await();
			}
			codaConsumatori.remove();
			el = buffer[out];
			buffer[out] = null;
			out = (out + 1) % buffer.length;
			numElementi--;
			System.out.println(this);
			bufferPieno.signalAll();
		} finally {
			l.unlock();
		}
		return el;
	}

	private boolean possoPrelevare() {
		return numElementi > 0 && Thread.currentThread() == codaConsumatori.getFirst();
	}

	public static void main(String[] args) {
		try{
			int dimensione = 10;
			Buffer buffer = new BufferLCFifo(dimensione);
			int numProduttori = 10;
			int numConsumatori = 10;
			buffer.test(numProduttori, numConsumatori);
		} catch (Exception e) {
			System.err.println("Errore!");
			System.exit(-1);
		}
	}

}
