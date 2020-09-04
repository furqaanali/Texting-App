import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	Socket socketClient;

	ObjectOutputStream out;
	ObjectInputStream in;

	Info clientData;
	String clientName;
	String prevSender;

	private Consumer<Serializable> callback;

	Client(Consumer<Serializable> call){

		callback = call;
	}

	public void run() {

		clientData = new Info();

		try {
			socketClient= new Socket("127.0.0.1",5555);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}

		while(true) {

			try {
				Info data = (Info) in.readObject();
				updateChatBox(data);
			}
			catch(Exception e) {}
		}

	}

	public void send(Info data) {

		try {
			out.writeObject(data);
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateChatBox(Info data) {
		if (data.action.equals("Update Chatbox")) {
			moveMessagesUp();
			addNewMessage(data);
		}
	}

	public void moveMessagesUp() {
		for (int i = 0; i < 5; ++i) {
			GUI.chatBoxes.get(i).setText(GUI.chatBoxes.get(i + 1).getText());
			GUI.chatBoxAlignments.get(i).setAlignment(GUI.chatBoxAlignments.get(i + 1).getAlignment());
		}
	}

	public void addNewMessage(Info data) {
		if (!data.nickname.equals(clientName) && !data.nickname.equals(prevSender))
			data.message = "\n\n" + data.nickname + ":\n" + data.message;
		GUI.chatBoxes.get(5).setText(data.message);
		prevSender = data.nickname;
		if (data.nickname.equals(clientName))
			GUI.chatBoxAlignments.get(5).setAlignment(Pos.CENTER_RIGHT);
		else
			GUI.chatBoxAlignments.get(5).setAlignment(Pos.CENTER_LEFT);
	}

}
