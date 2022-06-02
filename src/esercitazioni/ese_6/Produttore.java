package esercitazioni.ese_6;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Produttore implements Runnable {
	private static final int MIN_RANDOM = 1, MAX_RANDOM = 9;

	private static final int MIN_TEMPO_PRODUZIONE = 1, MAX_TEMPO_PRODUZIONE = 10;

	private Random random = new Random();
	private Buffer buffer;

	public Produttore(Buffer b) {
		buffer = b;
	}

	public void run() {
		try {
			while (true) {
				Elemento el = produci();
				buffer.put(el);
			}
		} catch (InterruptedException e) {
		}
	}

	private Elemento produci() throws InterruptedException {
		attendi(MIN_TEMPO_PRODUZIONE, MAX_TEMPO_PRODUZIONE);
		return new Elemento(random.nextInt(MAX_RANDOM - MIN_RANDOM +1) + MIN_RANDOM);
	}

	private void attendi(int min, int max) throws InterruptedException {
		TimeUnit.SECONDS.sleep(random.nextInt(max - min + 1) + min);
	}

}
