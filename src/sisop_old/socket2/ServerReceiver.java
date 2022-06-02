package sisop_old.socket2;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ServerReceiver extends Thread {
	private static Server server;
	private String utente;
	private Socket sockUtente;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String[] data;
	private String msg;
	private ObjectOutputStream oosDest = null;
	private Object ob;
	
	public ServerReceiver(Socket sockUtente) throws Exception {
		this.sockUtente = sockUtente ;
		ois = new ObjectInputStream(this.sockUtente.getInputStream());
		oos = new ObjectOutputStream(this.sockUtente.getOutputStream());
	}

	public static void setServer(Server server) {
		ServerReceiver.server = server;
	}

	public void login() throws Exception{
		oos.writeObject("Inserisci il tuo nome");
		utente = (String)ois.readObject();
		server.addUtente(utente, oos);
		oos.writeObject("Benvenuto/a "+utente);
		oos.writeObject("Per inviare un messaggio scrivi: destinatario:messaggio");
	}
	
	public void ascolta() throws Exception{
		ob = ois.readObject();
		if(ob instanceof String) {
			msg = (String)ob;
			data = msg.split(":");
			if(data.length == 1) {
				if(data[0].equals("-list"))
					oos.writeObject(server.getOnlineUser());
				else
					oos.writeObject("Comando non trovato!");
			}
			if(data.length==2) {
				oosDest = server.get(data[0]);
				String msg = data[1];
				if(oosDest != null) {
					oosDest.writeObject(utente+">"+msg);
					if(msg.equals("PublicKey") || msg.equals("MessaggioCriptato")) {
						oosDest.writeObject(ois.readObject());
					}
				}
				else {
					oos.writeObject("Utente non trovato");
				}
			}
		}
	}
	
	@Override
	public void run() {
		try {
			login();
			while(!sockUtente.isClosed() || !server.serverSocket.isClosed()) 
				ascolta();
		}catch(Exception e) {
			System.err.println(e);
		}
	}
}
