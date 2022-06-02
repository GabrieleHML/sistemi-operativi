package sisop_old.socket;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerReceiver extends Thread {
	private static Server server;
	private static String[] comandi = {"-help", "-list", "-quit"};
	private Utente utente;
	private Socket sockUtente;
	private BufferedReader in;
	private String[] data;
	private PrintWriter out;
	
	
	public ServerReceiver(Socket sockUtente) throws Exception {
		this.sockUtente = sockUtente ;
		in = new BufferedReader(new InputStreamReader(this.sockUtente.getInputStream()));
		out = new PrintWriter(this.sockUtente .getOutputStream(), true);
	}

	public static void setServer(Server server) {
		ServerReceiver.server = server;
	}

	public void login() throws Exception{
		out.println("Inserisci il tuo nome");
		utente = new Utente(in.readLine());
		String psw;
		if(server.contains(utente)) {
			out.println("Inserisci la password");
			psw = in.readLine();
			while(!psw.equals(server.getPassword(utente))) {
				out.println("Password errata! Inserisci la password");
				psw = in.readLine();
			}
		}else {
			out.println("Registrati inserendo una password:");
			psw = in.readLine();
			utente.setPassword(psw);
		}
		server.addUtente(utente, sockUtente);
		out.println("Benvenuto/a "+utente.nome);
		out.println("Per inviare un messaggio scrivi: destinatario:messaggio");
		out.println("Scrivi -help per vedere la lista dei comandi");
	}
	
	public void ascolta() throws Exception{
		data = in.readLine().split(":");
		if(data.length == 1) {
			switch(data[0]) {
			case "-help": 
				out.println("I comandi che puoi utilizzare sono:");
				for(String s : comandi)
					out.println(s);
				break;
			
			case "-list":
				out.println(server.getOnlineUser());
				break;
			
			case "-quit": 
				System.out.println(sockUtente.getInetAddress()+" si � disconnesso");
				sockUtente.close();
				break;
			
			default: out.println("Comando non riconosciuto");
			}
		}
		else if(data != null) {
			Socket dest = server.get(new Utente(data[0]));
			String msg = data[1];
			if(dest != null)
				server.invia(dest, utente, msg);
			else
				out.println("Utente non trovato");
		}
	}
	
	@Override
	public void run() {
		try {
			login();
			while(!sockUtente.isClosed() || !server.serverSocket.isClosed()) 
				ascolta();
		}catch(Exception e) {}
	}
}
