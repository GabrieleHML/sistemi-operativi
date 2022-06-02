package sisop_old.lezioni;

import java.security.*;
import javax.crypto.*;

public class Criptografia {
	
	
	public static void SymmetricEncryption(String data) throws GeneralSecurityException{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(192);
		Key key = generator.generateKey();
		System.out.println("key "+new String(key.getEncoded()));
		
		byte[] input = data.getBytes();
		System.out.println("input "+new String(input));
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedOutput = cipher.doFinal(input);
		System.out.println("crypted output "+new String(encryptedOutput));
		
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedOuput = cipher.doFinal(encryptedOutput);
		System.out.println("decrypted output "+new String(decryptedOuput));
		
	}//SymmetricEncryption
	
	public static void hashText(String s) throws NoSuchAlgorithmException{
		MessageDigest msg = MessageDigest.getInstance("SHA-256");
		byte[] input = s.getBytes();
		byte[] digest = msg.digest(input);
		System.out.println("Input: "+s);
		System.out.println("Digest: "+digest);
	}//hashText
	
	public static void main(String[] args) throws GeneralSecurityException {
		SymmetricEncryption("Matteo");
	}//main
	
}//Criptografia
