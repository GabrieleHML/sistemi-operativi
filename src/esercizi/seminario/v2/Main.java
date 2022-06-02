package esercizi.seminario.v2;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.util.Scanner;

public class Main {
    static long start;

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

    public static String decrypt(String key, byte[] input){
        byte[] outputBytes = {};
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            outputBytes = cipher.doFinal(input);
        } catch(Exception e){}
        return new String(outputBytes);
    }// decrypt

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        System.out.println("cripta o decripta?");
        String mode = k.nextLine();
        File in = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\document.encrypted");
        if (mode.equalsIgnoreCase("cripta")){
            System.out.print("Digitare la chiave con cui si vuole criptare >  ");
            int intKey = k.nextInt();
            File out = new File(in.getAbsolutePath().substring(0,in.getAbsolutePath().indexOf("."))+".encrypted");
            String key = String.format("%016d", intKey);
            encrypt(key, in, out);
        }else if (mode.equalsIgnoreCase("decripta")){
            // TODO decripta
        }else
            System.out.println("Digitare 'cripta' o 'decripta'");
    }// main
}// Main
