package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorMostrarCartas {

	@FXML
	Button btnStreamers;
	@FXML
	Button btnDibujos;
	@FXML
	Button btnIconos;
	@FXML
	Button btnCsgo;
	
	@FXML
	public void Streamers(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
		try {
			Stage primaryStage= new Stage();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Dibujos.fxml"));
			Scene scene = new Scene(root,1300,830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	
	public void btnDibujos(ActionEvent event) {
		
	}

	public void btnIconos(ActionEvent event) {
	
	}
	
	public void btnCsgo(ActionEvent event) {
		
	}
}
