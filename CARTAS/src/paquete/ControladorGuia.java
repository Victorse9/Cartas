package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorGuia {
	
	@FXML
	Label label1,label2,label3,label4,label5;
	@FXML
	ImageView tinta,flechaAtras,flechaAlante;
	
	
	public void botonVolver(ActionEvent event) {
	((Node)event.getSource()).getScene().getWindow().hide();
	try {
		Stage primaryStage= new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root,1300,830);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
}
	@FXML
	public void flecha(MouseEvent event) {
		
		label5.setText("- El juego finaliza cuando derrotas las 4 cartas del rival.");
		label1.setText("- Acción por turnos");
		label2.setText("- Puedes seleccionar una carta enemiga y una propia para atacarla por turno.");
		label3.setText("- Cuando atacas infringes tu daño de ataque a su vida y viceversa");
		label4.setText("- Cuando la vida de una carta llega a 0 queda inhabilitada.");
		
		tinta.setVisible(false);
		
		flechaAtras.setVisible(true);
		
	}
	
}
