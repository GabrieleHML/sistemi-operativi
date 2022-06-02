package sisop_old.lezioni;

public class Stampante extends Thread {
	private int da, a;
	
	
	public Stampante(int da, int a, String nome) {
		super(nome);
		this.da = da;
		this.a = a;
	}
	
	
	public void run() {
		for(int i=da; i<=a; i++) {
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		Stampante s1 = new Stampante(1, 10, "s1");
		s1.start();
		Stampante s2 = new Stampante(11, 20, "s2");
		
		//stampa non sequenziale
		//s1.start();
		//s2.start();
		//fine stampa non sequenziale
		
		
		
		//Stampa sequenziale
		s1.start();
		try {
			s1.join();
			s2.start();
			s2.join();
		}catch(InterruptedException e) {}
		System.out.println("fine");
		//fine stampa sequenziale
	}
	
}
