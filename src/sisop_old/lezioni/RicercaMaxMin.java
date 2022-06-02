package sisop_old.lezioni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RicercaMaxMin {
	
	public static void main(String[] args) throws InterruptedException {
		int[][] matrice = new int[4][4];
		Random r = new Random();
		
		for(int i=0; i<matrice.length; i++)
			for(int j=0; j<matrice[i].length; j++)
				matrice[i][j] = r.nextInt(100);
		
		for(int[] x:matrice)
			System.out.println(Arrays.toString(x));
				
		
		
		CercaMin.setMatrice(matrice);
		CercaMax.setMatrice(matrice);
		
		CercaMin[] threadsMin = new CercaMin[matrice[0].length];
		CercaMax[] threadsMax = new CercaMax[matrice.length];
				
		for(int i=0; i<matrice.length; i++) {
			threadsMax[i] = new CercaMax(i);
			threadsMax[i].start();
		}
		for(int j=0; j<matrice[0].length; j++) {
			threadsMin[j] = new CercaMin(j);
			threadsMin[j].start();
		}
		
		for(int i=0; i<threadsMax.length; i++) {
			List<Coordinate> listaMax = threadsMax[i].getRisultato();
			for(int j=0; j<threadsMin.length; j++) { 
				List<Coordinate> listaMin = threadsMin[j].getRisultato();
				for(int k=0; k<listaMax.size(); k++) 
					for(int l=0; l<listaMin.size(); l++)
						if(listaMax.get(k).equals(listaMin.get(l)))
							System.out.println(listaMax.get(k));
			}
		}
	}//main
}//RicercaMaxMin


class Coordinate{
	public int i, j;
	public Coordinate(int i, int j) {
		this.i = i;
		this.j = j;
	}
	@Override
	public String toString() {
		return "<"+i+","+j+">";
	}
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Coordinate)) return false;
		Coordinate tmp = (Coordinate)o;
		return (this.i==tmp.i && this.j==tmp.j);
	}
}//Coordinate


class CercaMin extends Thread{
	private static int[][] matrice;
	private List<Coordinate> listaIndici;
	private int indiceC;
	
	public CercaMin(int j) {
		super();
		indiceC = j;
		listaIndici = new ArrayList<>();
	}
	public static void setMatrice(int[][] matriceB) {
		matrice = matriceB;
	}
	@Override
	public void run() {
		int min = matrice[0][indiceC];
		for(int i=0; i<matrice.length; i++)
			if(matrice[i][indiceC] < min)
				min = matrice[i][indiceC];
		for(int i=0; i<matrice.length; i++)
			if(matrice[i][indiceC] == min)
				listaIndici.add(new Coordinate(i, indiceC));
	}
	public List<Coordinate> getRisultato() throws InterruptedException {
		this.join();
		return listaIndici;
	}
}//CercaMin


class CercaMax extends Thread{
	private static int[][] matrice;
	private List<Coordinate> listaIndici; 
	private int indiceR = 0;
	
	public CercaMax(int i) {
		super();
		indiceR = i;
		listaIndici = new ArrayList<>();
	}
	public static void setMatrice(int[][] matriceB) {
		matrice = matriceB;
	}
	@Override
	public void run() {
		int max = matrice[indiceR][0];
		for(int j=0; j<matrice[indiceR].length; j++)
			if(matrice[indiceR][j] > max)
				max = matrice[indiceR][j];
		for(int j=0; j<matrice[indiceR].length; j++)
			if(matrice[indiceR][j] == max)
				listaIndici.add(new Coordinate(indiceR, j));
	}
	public List<Coordinate> getRisultato() throws InterruptedException {
		this.join();
		return listaIndici;
	}
}//CercaMax