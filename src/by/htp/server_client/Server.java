package by.htp.server_client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

	public static void main(String[] args) throws Throwable {
		ServerSocket ss = new ServerSocket(8023);
		while (true) {
			Socket s = ss.accept();
			System.err.println("Client accepted");
			new Thread(new SocketProcessor(s)).start();
		}
	}

	private static class SocketProcessor implements Runnable {

		private Socket s;

		private SocketProcessor(Socket s) throws IOException {
			this.s = s;
		}

		public void run() {
			try {
				List<Equipment> equip = resultEqList();
				writeResponse(equip);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} finally {
				try { s.close(); } catch (IOException e) { System.err.println(e.getMessage());}
			}
			System.err.println("Client processing finished");
		}

		private void writeResponse(Object obj) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			oos.flush();
		}

		private List<Equipment> resultEqList(){
			EquipmentDaoImpl equipmentDao = new EquipmentDaoImpl();
			return equipmentDao.fetchAllEquip();
		}
	}
	
}
