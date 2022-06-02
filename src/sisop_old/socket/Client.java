package sisop_old.socket;

import java.net.Socket;

public class Client {
	public static void main(String...args) throws Exception{
		Socket s = new Socket("87.1.112.149", 23);
		new Sender(s).start();
		new Receiver(s).start();
	}
}
