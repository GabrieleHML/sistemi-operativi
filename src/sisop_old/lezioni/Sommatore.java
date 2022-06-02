package sisop_old.lezioni;

public class Sommatore extends Thread{
	private int da, a;
	private int somma=0;
	
	public Sommatore(int da, int a) {
		super();
		this.da = da;
		this.a = a;
	}
	
	public void run() {
		somma = 0;
		for(int i=da; i<=a; i++) {
			somma += i;
		}
	}
	
	public int getSomma() throws InterruptedException {
		this.join();
		return somma;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		int inizio = 1;
		int fine = 1000;
		int medio = 500;
		
		Sommatore s1 = new Sommatore(inizio, medio);
		Sommatore s2 = new Sommatore(medio+1, fine);
		s1.start();
		s2.start();
		
		System.out.println(s1.getSomma()+s2.getSomma());
	}
}
