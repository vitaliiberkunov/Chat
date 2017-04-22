import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Sender implements Runnable {

	private InetAddress serverIp;
	private int serverPort;
	private DatagramSocket sender;
	
	private String message;
	
	public Sender(DatagramSocket socket, int serverPort, InetAddress serverIp) throws SocketException {
		this.serverPort = serverPort; 
		this.serverIp = serverIp;
		sender = socket;
		message = null;
	}

	public void setServerIp(InetAddress serverIp) {
		this.serverIp = serverIp;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		byte[] sendData = message.getBytes();
		DatagramPacket sendPacket =
			new DatagramPacket(sendData, sendData.length, serverIp, serverPort);
		try {
			sender.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}