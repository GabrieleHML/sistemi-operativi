package sisop_old.socket2;

import java.util.Scanner;

public class Sender extends Thread {
	private Client client;
	private Scanner sc;
	private String msg;
	
	public Sender(Client client) throws Exception{
		this.client = client;
		sc = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		try {
			while(!client.socket.isClosed()) {
				msg = sc.nextLine();
				String[] info = msg.split(":");
				if(info.length > 1) {
					String nome = info[0];
					String testo = info[1];
					if(!client.chiaviF.containsKey(nome)) {
						client.oos.writeObject(nome+":PublicKeyRequest");
						client.aspetta();
					}
					byte[] messaggioCriptato = AsymmetricEncr.cripta(testo, client.chiaviF.get(nome)); 
					client.oos.writeObject(nome+":MessaggioCriptato");
					client.oos.writeObject(messaggioCriptato);
				}else {
					client.oos.writeObject(msg);
				}
			}
		}catch(Exception e) {
			System.err.println(e);
		}
	}
}
