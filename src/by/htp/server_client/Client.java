package by.htp.server_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
	public static void main(String[] args) throws Throwable {
		Socket ss = new Socket("127.0.0.1",8023);
		new Thread(new SocketProcessor(ss)).start();
	}

	private static class SocketProcessor implements Runnable {
		private Socket s;
		private OutputStream os;

		private SocketProcessor(Socket s) throws IOException {
			this.s = s;
			this.os = s.getOutputStream();
		}

		public void run() {
			try {
				os.write("q".getBytes());
				os.flush();
				readResponse(s);
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		private void readResponse(Socket ss){
			try {
				ObjectInputStream des = new ObjectInputStream(ss.getInputStream());
				List<Equipment> equiplist = (List<Equipment>) des.readObject();
				for(Equipment eqip: equiplist){
					System.out.println(eqip.toString());
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
