package sisop_old.socket2;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Server {
	public ServerSocket serverSocket;
	private Map<String, ObjectOutputStream> utenti = new HashMap<>();
	private Semaphore mutex = new Semaphore(1);


	public Server(int porta) throws Exception{
		serverSocket = new ServerSocket(porta);
	}
	
	private void start() {
		try {
			System.out.println("Avvio il server");
			ServerReceiver.setServer(this);

			while(!serverSocket.isClosed()) {
				Socket utente = serverSocket.accept();
				System.out.println(utente.getInetAddress()+" si � connesso");
				new ServerReceiver(utente).start();
			}
		}catch(Exception e) {}
	}
	
	
	public ObjectOutputStream get(String utente) throws Exception {
		try{
			mutex.acquire();
			ObjectOutputStream ret = utenti.get(utente);
			return ret;
		}finally {
			mutex.release();
		}
	}
	
	public void addUtente(String u, ObjectOutputStream oos) throws Exception{
		try{
			mutex.acquire();
			utenti.put(u, oos);}
		finally {
			mutex.release();
		}
	}
	
	public String getOnlineUser() throws Exception{
		try {
			mutex.acquire();
			StringBuilder sb = new StringBuilder();
			for(String u : utenti.keySet()) 
				sb.append(u+"\n");
			return sb.toString();
		}finally {
			mutex.release();
		}
	}

	
	public static void main(String[] args) throws Exception {
		Server s = new Server(23);
		s.start();
	}
}
