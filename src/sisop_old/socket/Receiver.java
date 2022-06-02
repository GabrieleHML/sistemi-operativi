package sisop_old.socket;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Receiver extends Thread {
	private Socket socket;
	private BufferedReader in;
	private String msg;
	
	public Receiver(Socket socket) throws Exception {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	@Override
	public void run() {
		try {
			while(!socket.isClosed()) {
				msg = in.readLine();
				if(msg != null) 
					System.out.println(msg);
			}
		}catch(Exception e) {}
	}
	
}
