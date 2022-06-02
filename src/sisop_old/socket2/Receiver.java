package sisop_old.socket2;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class Receiver extends Thread {
	private Client client;
	private Object obj;
	
	public Receiver(Client client) throws Exception {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			while(!client.socket.isClosed()) {
				obj = client.ois.readObject();
				if(obj instanceof String) {
					String msg = (String)obj;
					String info[] = msg.split(">");
					if(info.length == 2) {
						if(info[1].equals("PublicKeyRequest")) {
							byte[] publicKey = client.chiavi.getPublic().getEncoded();
							client.oos.writeObject(info[0]+":PublicKey");
							client.oos.writeObject(publicKey);
						}
						if(info[1].equals("MessaggioCriptato")) {
							byte[] messaggioCriptato = (byte[]) client.ois.readObject();
							String messaggioInChiaro = AsymmetricEncr.decripta(messaggioCriptato, client.chiavi.getPrivate()); 
							System.out.println(info[0]+">"+messaggioInChiaro);
						}
						if(info[1].equals("PublicKey")) {
							byte[] publicKeyByte = (byte[]) client.ois.readObject();
							PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec((publicKeyByte)));
							client.chiaviF.put(info[0], publicKey);
							client.rilascia();
						}
					}
					else {
						System.out.println(msg);
					}
				}
			}
		}catch(Exception e) {
			System.err.println(e);
		}
	}
	
}
