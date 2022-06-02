package sisop_old.socket2;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;


public class AsymmetricEncr {
	private static final String RSA = "RSA";

	public static KeyPair generaChiavi() throws Exception{
	    SecureRandom secureRandom = new SecureRandom();
	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
	    keyPairGenerator.initialize(2048, secureRandom);
	    return keyPairGenerator.generateKeyPair();
	}

	public static byte[] cripta(String plainText, PublicKey publicKey) throws Exception{
	    Cipher cipher = Cipher.getInstance(RSA);
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    return cipher.doFinal(plainText.getBytes());
	}
		
	public static String decripta(byte[] cipherText, PrivateKey privateKey) throws Exception {
	    Cipher cipher = Cipher.getInstance(RSA);
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    byte[] result = cipher.doFinal(cipherText);
	    return new String(result);
	}
}
