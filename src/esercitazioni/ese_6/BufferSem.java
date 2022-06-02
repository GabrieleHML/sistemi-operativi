package esercitazioni.ese_6;

import java.util.concurrent.Semaphore;

public class BufferSem extends Buffer {

	private Semaphore ciSonoElementi = new Semaphore(0);
	private Semaphore ciSonoPostiVuoti;
	private Semaphore mutex = new Semaphore(1);

	public BufferSem(int dimensione) {
		super(dimensione);
		ciSonoPostiVuoti = new Semaphore(dimensione);
	}

	public void put(Elemento el) throws InterruptedException {
		ciSonoPostiVuoti.acquire();
		mutex.acquire();
		buffer[in] = el;
		in = (in + 1) % buffer.length;
		System.out.println(this);
		mutex.release();
		ciSonoElementi.release();
	}

	public Elemento get() throws InterruptedException {
		ciSonoElementi.acquire();
		mutex.acquire();
		Elemento el = buffer[out];
		buffer[out] = null;
		out = (out + 1) % buffer.length;
		System.out.println(this);
		mutex.release();
		ciSonoPostiVuoti.release();
		return el;
	}

	public static void main(String[] args) {
		try{
			int dimensione = 10;
			Buffer buffer = new BufferSem(dimensione);
			int numProduttori = 10;
			int numConsumatori = 10;
			buffer.test(numProduttori, numConsumatori);
		} catch (Exception e) {
			System.err.println("Errore!");
			System.exit(-1);
		}
	}
}
