package esercizi.esercitazione_4.ese_4_2;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class CronometroInterattivo {
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore avvia = new Semaphore(0, true);

    public static void main(String[] args) throws InterruptedException {
        Scanner k = new Scanner(System.in);
        CronometroT crono = new CronometroT();
        crono.start();
        boolean fine = false;
        while(!fine){
            String input = k.nextLine();
            switch(input){
                case "A":{
                    avvia.release();
                    break;
                }
                case "F":{
                    avvia.acquire();
                    break;
                }
                case "R":{
                    crono.reset();
                    break;
                }
                case "E":{
                    crono.interrupt();
                    k.close();
                    fine = true;
                    break;
                }
                default:
                    System.out.println("Comando non valido");
            }
        }
    }
    static class CronometroT extends Thread {
        private int numSecondi = 1;

        public void reset() throws InterruptedException{
            mutex.acquire();
            numSecondi = 1;
            mutex.release();
        }

        public void run(){
            while(!isInterrupted()){
                try {
                    avvia.acquire();
                    Thread.sleep(1000);
                    System.out.println(numSecondi);
                    mutex.acquire();
                    numSecondi++;
                    mutex.release();
                    avvia.release();
                }catch (InterruptedException e){
                    break;
                }
            }
        }
    }
}
