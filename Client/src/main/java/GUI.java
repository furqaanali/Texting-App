
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.tools.jstat.Alignment;

public class GUI extends Application{

	TextField nicknameField, chatField;
	Button groupChatBtn, singleChatBtn, sendMessageBtn;
	HashMap<String, Scene> sceneMap;
	HBox selectionBtns, messageCreation;
	VBox selectionScreen, groupScreen, chatMessages;
	Client clientConnection;
	static ArrayList<Text> chatBoxes = new ArrayList<>();
	static ArrayList<HBox> chatBoxAlignments = new ArrayList<>();

	ListView<String> listItems, listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Texting Application");

		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("selection",  createSelectionGui());
		sceneMap.put("group",  createGroupGui());

		groupChatBtn.setOnAction(event -> {
			clientConnection.clientName = nicknameField.getText();
			primaryStage.setScene(sceneMap.get("group"));
		});

		sendMessageBtn.setOnAction(e->{
			clientConnection.clientData = new Info("Send Message", chatField.getText(), clientConnection.clientName);
			clientConnection.send(clientConnection.clientData);
			chatField.clear();
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setScene(sceneMap.get("selection"));
		clientConnection = new Client(data->{
			Platform.runLater(()->{});
		});
		clientConnection.start();
		primaryStage.show();

	}

	public Scene createSelectionGui() {

		nicknameField = new TextField("Nickname");
		singleChatBtn = new Button("Single chat");
		groupChatBtn = new Button("Group chat");
		selectionBtns = new HBox(singleChatBtn, groupChatBtn);
		selectionScreen = new VBox(20, nicknameField, selectionBtns);
		return new Scene(selectionScreen, 400, 400);
	}

	public Scene createGroupGui() {

		chatMessages = new VBox();
		for (int i = 0; i < 6; ++i) {
			chatBoxes.add(new Text(""));
			chatBoxAlignments.add(new HBox(chatBoxes.get(i)));
			chatMessages.getChildren().add(chatBoxAlignments.get(i));
		}
		chatField = new TextField();
		sendMessageBtn = new Button("Send");
		messageCreation = new HBox(chatField, sendMessageBtn);
		groupScreen = new VBox(chatMessages, messageCreation);
		groupScreen.setAlignment(Pos.BOTTOM_CENTER);
		groupScreen.setSpacing(50);
		messageCreation.setAlignment(Pos.CENTER);
		chatMessages.setStyle("-fx-padding: 0 30 0 30;");
		return new Scene(groupScreen, 400, 400);
	}

}
