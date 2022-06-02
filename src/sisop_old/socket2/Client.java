package sisop_old.socket2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Client {
	public Socket socket;
	public KeyPair chiavi;
	public HashMap<String, PublicKey> chiaviF = new HashMap<>();
	private Semaphore mutex = new Semaphore(0);
	public ObjectInputStream ois;
	public ObjectOutputStream oos;
	
	public Client(String ip, int port) throws Exception{
		this.socket = new Socket(ip, port);
		this.chiavi = AsymmetricEncr.generaChiavi();
	}
	
	public void start() throws Exception {
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		new Sender(this).start();
		new Receiver(this).start();
	}
	
	public void aspetta() throws Exception {
		mutex.acquire();
	}
	public void rilascia() throws Exception {
		mutex.release();
	}
	
	
	public static void main(String...args) throws Exception{
		Client c = new Client("82.57.53.149", 23);
		c.start();
	}
}
