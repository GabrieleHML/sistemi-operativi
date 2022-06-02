package esercitazioni.ese_6;

import java.util.concurrent.TimeUnit;

public class Consumatore implements Runnable {

	  private Buffer buffer;
	  

	  public Consumatore(Buffer b) { buffer = b; }

	  public void run() {
	    try {
	      while (true) {
	        Elemento el = buffer.get();
	        consuma(el);
	      }
	    } catch (InterruptedException e) {}
	  }

	  private void consuma(Elemento el) throws InterruptedException {
	    TimeUnit.SECONDS.sleep(el.getI());
	  }
	}

