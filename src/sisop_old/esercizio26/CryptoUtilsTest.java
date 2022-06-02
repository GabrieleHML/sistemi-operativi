package sisop_old.esercizio26;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.File;
/*
public class CryptoUtilsTest{
    public static void main(String[] args) {


        File inputFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\document.txt");
        File encryptedFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\filecriptato.encrypted");
        File decryptedFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\filedecriptato.decrypted");

        String key = "0000000000000011";

        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
            CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
*/
class chiave extends Thread{

    public static String chiave(File inputFile, File outputFile) throws CryptoException, IOException {
        String key = "0000000000000000";
        for (int i = 0; i < Math.pow(10.0, 16.0); i++) {
            try{
                CryptoUtils.decrypt(key,inputFile,outputFile);
            } catch (CryptoException ex){
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

            FileInputStream fis = new FileInputStream(outputFile);
            byte[] buffer = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();

            String testo = sb.toString();


            if (testo.equals("bella zio")){
                return key;
            }else{
                int chiavenuova = Integer.parseInt(key);
                chiavenuova++;
                key = String.valueOf(chiavenuova);
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, CryptoException {
        File inputFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\document.txt");
        File encryptedFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\filecriptato.encrypted");
        File decryptedFile = new File("D:\\Coding\\sisop\\src\\esercizio23mod\\filedecriptato.decrypted");

        chiave.chiave(encryptedFile,inputFile);

    }

}

class CryptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

}

class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}