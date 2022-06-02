package esercizi.seminario;

import java.io.File;

public class Prova {
    public static void main(String[] args) {
        File input = new File("C:\\Java_Workspace\\sistemi_operativi\\src\\esercizi\\seminario\\document.encrypted");
        String newFile = input.getAbsolutePath().substring(0,input.getAbsolutePath().length()-10).concat(".decrypted");
        System.out.println(newFile);
    }
}
