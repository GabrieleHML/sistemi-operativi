package sisop_old.socket;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
	private Socket socket;
	private PrintWriter out;
	private Scanner sc;
	private String msg;
	
	public Sender(Socket socket) throws Exception{
		this.socket = socket;
		this.setDaemon(true);
		out = new PrintWriter(this.socket.getOutputStream(), true);
		sc = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		try {
			while(!socket.isClosed()) {
				msg = sc.nextLine();
				out.println(msg);
				if(msg.equals("-quit"))
					socket.close();
			}
		}catch(Exception e) {}
	}
}
