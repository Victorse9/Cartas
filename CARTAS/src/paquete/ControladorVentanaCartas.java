package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControladorVentanaCartas {

	public void btnVolver(ActionEvent e) {
		((Node)e.getSource()).getScene().getWindow().hide();
		try {
			Stage primaryStage= new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MostrarCartas.fxml"));
			Scene scene = new Scene(root,1300,830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }
	
}
