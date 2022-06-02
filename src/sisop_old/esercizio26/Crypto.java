package sisop_old.esercizio26;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto { 
	static long inizio = 0;
 
	public static void cripta(String key, File input, File output) {
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
	}//cripta

	public static String decripta(String key, byte[] input)  {
    	byte[] outputBytes = {};
    	try {
    		Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	       	outputBytes = cipher.doFinal(input);
    	} catch(Exception e){}
    	return new String(outputBytes);
    }//decripta
    
	public static void main(String[] args) throws IOException {
    	if(args[0].equalsIgnoreCase("cripta")) {
    		File in = new File(args[1]);
    		File out = new File(args[1].substring(0, args[1].indexOf("."))+".encrypted");
    		String key = String.format("%016d", Integer.parseInt(args[2]));
    		cripta(key, in, out);
    	}
    	else if(args[0].equalsIgnoreCase("decripta")){
    		inizio = System.currentTimeMillis();
    		CercaChiave[] v = new CercaChiave[Integer.parseInt(args[3])];
    		int op = Integer.MAX_VALUE/v.length;
    		int resto = Integer.MAX_VALUE%v.length;
    		
    		File input = new File(args[1]);
    		CercaChiave.setDati(input, args[2]);
    		
    		for(int i=0; i<v.length; i++) {
    			v[i] = new CercaChiave(i*op, (i*op)+op);
    			v[i].start();
    		 }	
    		 if(resto != 0) {
    		    CercaChiave threadRes = new CercaChiave(v.length*op, (v.length*op+resto));
    		 	threadRes.start();
    		 }
    	}
    }//main
}//CryptoUtils


class CercaChiave extends Thread{
	int da, a;
	static String soluzione;	
	static byte[] testoCifrato;
	
	public CercaChiave(int da, int a) {
		this.da = da;
		this.a = a;
	}//builder
	
	public static void setDati(File f, String sol) throws IOException {
		FileInputStream inputS = new FileInputStream(f);
        byte[] inputBytes = new byte[(int) f.length()];
        inputS.read(inputBytes);	
		testoCifrato = inputBytes;
		soluzione = sol.toLowerCase();
		inputS.close();
	}//setData
	
	@Override
	public void run() {
		for(int i=da; i<=a; i++) {
			//formatto i numeri in modo da essere del tipo 0000000000000001
			String key = String.format("%016d", i);
			try {
				String risultato = Crypto.decripta(key, testoCifrato); 
				if(risultato.toLowerCase().indexOf(soluzione) != -1) {
					System.out.println("Chiave di criptazione:");
					System.out.println(key);
					System.out.println("");
					System.out.println("Contenuto del file in chiaro:");
					System.out.println(risultato);
					System.out.println("");
					System.out.println("Tempo impiegato: "+(System.currentTimeMillis() - Crypto.inizio)/1000+"s");				
					System.exit(0);
				}	
			}catch(Exception e) {}
		}
	}//run
	
}//CercaChiave