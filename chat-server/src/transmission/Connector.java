package transmission;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Connector implements Runnable {
	
	private Map<SocketAddress, Long> connectedUsers;
	private int port;
	private DatagramSocket listenerSocket = null;
	private Updater updater;
	
	private final int updatePeriod = 1000;
	
	public Map<SocketAddress, Long> getConnectedUsers() {
		return connectedUsers;
	}

	public Connector(int port) throws SocketException {
		connectedUsers = new HashMap<SocketAddress, Long>();
		updater = new Updater(connectedUsers, updatePeriod);
		this.port = port;
		listenerSocket = new DatagramSocket(port);
	}
	
	@Override
	public void run() {
		(new Timer()).schedule(updater, 0, updatePeriod);
		byte[] receiveData = new byte[1024];
		while(true) {
			//System.out.println("Listening for new connections");
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//InetAddress connectedIp = receivePacket.getAddress();
			//int connectedPort = Integer.parseInt((new String(receivePacket.getData())).trim());
			SocketAddress socketAddress = receivePacket.getSocketAddress();
			if(!connectedUsers.containsKey(socketAddress)) {
				System.out.println(socketAddress + " connected");
			}
			connectedUsers.put(socketAddress, System.currentTimeMillis());
		}
	}

}