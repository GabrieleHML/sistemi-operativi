package esercizi.esercitazione_2.ese_2_3;

public class Punto {

    private int x;
    private int y;

    public Punto(int x, int y){
        this.x = x;
        this.y = y;
    }//costruttore

    public int getX() {
        return x;
    }//getX

    public int getY() {
        return y;
    }//getY



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Punto)) return false;

        Punto punto = (Punto) o;

        if (x != punto.x) return false;
        return y == punto.y;
    }//equals

    @Override
    public String toString() {
        return "Punto(" + x + "," + y + ')';
    }//toString

    @Override
    public int hashCode(){
        return 17*x+43*y;
    }//hashCode
}//Punto
