package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControladorVictoria {
	@FXML
	private Button btnVictoria;
	@FXML
	private Label lblVictoria;
	private int rival=0;

	/**
	 * Boton siguiente rival
	 * 
	 * @param event
	 */
	@FXML
	public void siguienteRival(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		rival = rival + 1;
		if (rival >= 2) {
			btnVictoria.setText("SALIR");
			lblVictoria.setVisible(true);
		}
	}

}
