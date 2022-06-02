package sisop_old.socket;

import java.util.Scanner;

public class ServerCloser extends Thread {
	private Server server;
	private Scanner sc = new Scanner(System.in);

	public ServerCloser(Server server) {
		this.server = server;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		try {
			String cmd = "";
			while (true) {
				System.out.println("Scrivi -stop per chiudere il server");
				cmd = sc.nextLine();
				if (cmd.equals("-stop"))
					server.stop();
			}
		} catch (Exception e) {
		}
	}
}
