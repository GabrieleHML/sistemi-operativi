package sisop_old.lezioni;

import java.util.concurrent.TimeUnit;

public class Cronometro extends Thread {
	public void run() {
		int numSecondi = 1;
		while(!this.isInterrupted()) {
			try {
				TimeUnit.SECONDS.sleep(1);
			}catch(InterruptedException e) {
				break;
			}
			System.out.println(numSecondi);
			numSecondi++;
		}
	}
}
