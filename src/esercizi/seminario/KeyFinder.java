package esercizi.seminario;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;

public class KeyFinder  extends Thread{
    private static int N;
    int index;
    static String word;
    static byte[] encryptedText;

    public KeyFinder(int i){
        index = i;
    }// costruttore

    public static void encrypt(String key, File input, File output){
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            FileInputStream inputS = new FileInputStream(input);
            byte[] inputBytes = new byte[(int) input.length()];
            inputS.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputS = new FileOutputStream(output);
            outputS.write(outputBytes);

            inputS.close();
            outputS.close();
        }catch(Exception e) {}
    }// encrypt

    private static String decrypt(String key, byte[] input){
        byte[] outputBytes = {};
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            outputBytes = cipher.doFinal(input);
        } catch(Exception e){}
        return new String(outputBytes);
    }// decrypt

    public static void setData(File f, String str, int num){
        try {
            FileInputStream is = new FileInputStream(f);
            byte[] ib = new byte[(int)f.length()];
            is.read(ib);
            encryptedText = ib;
            word = str.toLowerCase();
            N = num;
            is.close();
        }catch (IOException ex){
            System.out.println("File non trovato");
            System.exit(-1);
        }
    }// setData

    @Override
    public void run(){
        for (int i = index; i < index+(Integer.MAX_VALUE/N); i++){
            String key = String.format("%016d", i);
            String text =  decrypt(key, encryptedText);
            if (text.toLowerCase().contains(word)){
                System.out.println("Chiave di cifratura: "+key);
                System.out.println("Contenuto testo: ");
                System.out.println(text);
                System.out.println("Tempo impiegato: "+(System.currentTimeMillis() - Main.start)/1000+"s");
                System.exit(0);
            }
        }
    }//run
}// KeyFinder
