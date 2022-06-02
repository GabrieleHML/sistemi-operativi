package esercizi.esercitazione_2.ese_2_2;

public class ProdottoScalare extends Thread{
    private static int[] v1, v2;
    private static int M;
    private int prodotto;
    private final int indice;

    public ProdottoScalare(int indice){
        this.indice = indice;
    }//costruttore

    public static void setData(int[] a1, int[] a2, int m){
        v1 = a1;
        v2 = a2;
        M = m;
    }//setData

    @Override
    public void run(){
        prodotto = 0;
        for (int i=indice; i<indice+(v1.length/M); i++){
            prodotto += v1[i]*v2[i];
        }
    }//run

    public int getProdotto() throws InterruptedException{
        this.join();
        return prodotto;
    }//getProdotto

    public static void main(String[] args) throws InterruptedException{
        int[] v1 = {1, 2, 3};
        int[] v2 = {4, 5, 6};
        int prodottoS = 0;
        final int m = 3;

        setData(v1, v2, m);

        ProdottoScalare[] v = new ProdottoScalare[m];
        for (int i=0; i<m; i++){
            v[i] = new ProdottoScalare(i*(v1.length/m));
            v[i].start();
        }

        for (int i=0; i<m; i++)
            prodottoS += v[i].getProdotto();

        System.out.println("Il prodotto scalare e': "+prodottoS);
    }//main
}//ProdottoScalare
