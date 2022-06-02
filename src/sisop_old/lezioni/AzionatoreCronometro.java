package sisop_old.lezioni;

import java.util.Scanner;

public class AzionatoreCronometro {
	public static void main(String[] args) {
		Cronometro c = new Cronometro();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Premi invio per partire");
		System.out.println("Premi invio per stoppare");
		sc.nextLine();
		c.setDaemon(true);
		c.start();
		sc.nextLine();
		System.out.println("Fine");
		sc.close();
	}
}
