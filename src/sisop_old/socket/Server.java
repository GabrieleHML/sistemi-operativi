package sisop_old.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
	public ServerSocket serverSocket;
	private Map<Utente, Socket> utenti = new HashMap<>();
	private LinkedList<Utente> saveUtenti = new LinkedList<>();
	private Semaphore mutex = new Semaphore(1), invio = new Semaphore(1);
	private PrintWriter out;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public Server(int porta) throws Exception{
		serverSocket = new ServerSocket(porta);
	}
	
	private void start() {
		try {
			System.out.println("Avvio il server");
			ServerReceiver.setServer(this);
			new ServerCloser(this).start();
			while(!serverSocket.isClosed()) {
				Socket utente = serverSocket.accept();
				System.out.println(utente.getInetAddress()+" si � connesso");
				new ServerReceiver(utente).start();
			}
		}catch(Exception e) {}
	}
	
	public void invia(Socket dest, Utente mitt, String msg) throws Exception{
		try {
			invio.acquire();
			out = new PrintWriter(dest.getOutputStream(), true);
			out.println(mitt.nome+">"+msg);
		}finally {
			invio.release();
		}
	}
	
	public Socket get(Utente utente) throws Exception {
		try{
			mutex.acquire();
			Socket ret = utenti.get(utente);
			return ret;
		}finally {
			mutex.release();
		}
	}
	
	public String getPassword(Utente utente) throws Exception{
		try {
			mutex.acquire();
			for(Utente u : saveUtenti)
				if(u.nome.equals(utente.nome)) 
					return u.getPassword();	
			return null;
		}finally{
			mutex.release();
		}
	}
	
	public void addUtente(Utente u, Socket sock) throws Exception{
		try{
			mutex.acquire();
			if(!utenti.containsKey(u))
				saveUtenti.add(u);
			utenti.put(u, sock);}
		finally {
			mutex.release();
		}
	}
	
	public String getOnlineUser() throws Exception{
		try {
			mutex.acquire();
			StringBuilder sb = new StringBuilder();
			for(Utente u : utenti.keySet())
				if(!utenti.get(u).isClosed()) 
					sb.append(u.nome+"\n");
			return sb.toString();
		}finally {
			mutex.release();
		}
	}
	
	public boolean contains(Utente u) throws Exception{
		try {
			mutex.acquire();
			return utenti.containsKey(u);
		} finally {
			mutex.release();
		}
	}

	@SuppressWarnings("unchecked")
	public void load() throws Exception{
		ois = new ObjectInputStream(new FileInputStream(new File("c:\\server\\save.dat")));
		saveUtenti = (LinkedList<Utente>) ois.readObject();
		for(Utente u : saveUtenti) 
			utenti.put(u, null);
		ois.close();
	}
	
	public void stop() throws Exception{
		mutex.acquire();
		System.out.println("stoppo il server...");
		oos = new ObjectOutputStream(new FileOutputStream(new File("c:\\server\\save.dat")));
		oos.writeObject(saveUtenti);
		oos.flush();
		oos.close();
		serverSocket.close();
		mutex.release();
	}
	
	public static void main(String[] args) throws Exception {
		Server s = new Server(23);
		s.start();
	}
}
