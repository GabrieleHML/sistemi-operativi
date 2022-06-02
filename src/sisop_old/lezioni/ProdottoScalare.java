package sisop_old.lezioni;

public class ProdottoScalare extends Thread{
	private static int[] v1, v2;
	private static int M;
	private int prodotto;
	private int indice;
	
	public ProdottoScalare(int indice) {
		super(indice+"");
		this.indice = indice;
	}
	
	public static void setData(int[] a1, int[] a2, int m) {
		v1 = a1;
		v2 = a2;
		M = m;
	}
	
	@Override
	public void run() {
		prodotto = 0;
		for(int i=indice; i<indice+(v1.length/M); i++) {
			prodotto += v1[i]*v2[i];
		}
	}
	
	public int getProdotto() throws InterruptedException {
		this.join();
		return prodotto;
	}
	
	public static void main(String[] args) throws InterruptedException{
		int[] v1 = {4, 5, 6};
		int[] v2 = {1, 2, 3};
		int prodotto = 0;
		final int M = 3;
		
		//Utilizzo un metodo statico per passare i vettori e l'intero M 
		setData(v1, v2, M);
		
		//Creo M prodotti scalari (il nome di ogni prodotto scalare � l'indice da cui dovr� partire per fare il prodotto)
		ProdottoScalare[] v = new ProdottoScalare[M];
		for(int i=0; i<M; i++) {
			v[i] = new ProdottoScalare(i*(v1.length/M));
			//Possiamo settare il thread come demone
			v[i].setDaemon(true);
			v[i].start();
		}
		
		//Combino i prodotti calcolati dai vari oggetti ProdottoScalare
		for(int i=0; i<M; i++) {
			prodotto += v[i].getProdotto();
		}
		System.out.println(prodotto);
	}
}