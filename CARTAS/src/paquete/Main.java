package paquete;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		primaryStage.setTitle("CARTAS");
		primaryStage.setScene(new Scene(root, 1300, 830));
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/complementos/logo.png"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
