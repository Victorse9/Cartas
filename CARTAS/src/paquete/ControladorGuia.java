package paquete;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class ControladorGuia {
	
	@FXML
	private Pane panel1, panel2;

	/**
	 * Ir menu
	 * 
	 * @param e
	 */
	@FXML
	public void btnVolver(MouseEvent e) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Volver al menú");
		alert.setContentText("¿Deseas volver al menú?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				((Node) e.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Menu.fxml"));
				Scene scene = new Scene(root, 1300, 830);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}
	
	/**
	 * Ir a la página 2 de la guía
	 * @param e
	 */
	@FXML
	public void irPagina2(MouseEvent e) {
		panel1.setVisible(false);
		panel2.setVisible(true);
	}
	
	/**
	 * Ir a la página 1 de la guía
	 * @param e
	 */
	@FXML
	public void irPagina1(MouseEvent e) {
		panel1.setVisible(true);
		panel2.setVisible(false);
	}
	
	/**
	 * Cierra la app
	 * @param e
	 */
	@FXML
	public void cerrarApp(MouseEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Cerrar CutreStone");
		alert.setContentText("¿Deseas salir de CutreStone?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
			System.exit(0);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

		}
	}

}
