package esercizi.seminario.test;

import esercizi.seminario.CryptoException;
import esercizi.seminario.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainTest {

    private static boolean containString(String str, File file){
        try{
            Scanner k = new Scanner(file);
            while (k.hasNextLine()){
                String line = k.nextLine();
                if (line.contains(str))
                    return true;
            }
            return false;
        }catch (FileNotFoundException e){
            System.out.println("Il file non è stato trovato!");
        }
        return false;
    }//containString

    public static void main(String[] args) {
        String key = "0000000000001234";
        File inputFile = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\test\\document.txt");
        File encryptedFile = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\test\\document.encrypted");
        File decryptedFile = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\test\\document.decrypted");

        System.out.println(containString("you", decryptedFile));

    }
}
