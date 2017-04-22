package transmission;
import java.net.SocketException;
import java.util.ArrayList;

import statics.Values;
import view.GUI;

public class ChatServer {

	private ArrayList<String> receivedMessages;
	private Connector connector; 
	private Receiver receiver; 
	private Broadcaster broadcaster;
	private GUI gui;
	
	public ChatServer() throws SocketException {
		receivedMessages = new ArrayList<String>();
		connector = new Connector(Values.SERVER_CONNECT_PORT);
		broadcaster = new Broadcaster(Values.SERVER_BROADCAST_PORT);
		receiver = new Receiver(Values.SERVER_RECEIVE_PORT, broadcaster, connector, this);
		gui = new GUI("Chat Server", this);
	}
	
	public void run() {
		(new Thread(connector)).start();
		(new Thread(receiver)).start();
		gui.showGui();
		
	}
	
	public void updateMessageList(String message) {
		receivedMessages.add(message);
		gui.updateMessages(receivedMessages);
	}
	
	public static void main(String[] args) throws Exception {
		(new ChatServer()).run();
		
	}


}

