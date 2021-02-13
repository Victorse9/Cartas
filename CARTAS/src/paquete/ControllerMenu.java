package paquete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerMenu {

	@FXML
	Button btnVolumen;
	@FXML
	AudioClip audio = new AudioClip("file:sonido/audio.mp3");
	@FXML
	Button btnJugar;

	public void initialize() {
		audio.play();
		URL url = getClass().getResource("/complementos/altavoz.png");
		Image imagenAltavoz = new Image(url.toString(), 70, 80, false, true);
		btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
	}

	@FXML
	public void volumen(ActionEvent e) {
		if (audio.isPlaying()) {
			audio.stop();
			URL url = getClass().getResource("/complementos/altavozoff.png");
			Image imagenAltavoz = new Image(url.toString(), 70, 80, false, true);
			btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
		} else {
			audio.play();
			URL url = getClass().getResource("/complementos/altavoz.png");
			Image imagenAltavoz = new Image(url.toString(), 70, 80, false, true);
			btnVolumen.setGraphic((new ImageView(imagenAltavoz)));
		}

	}

	public void mostrarCartas(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MostrarCartas.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jugar(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = null;
		try {
			audio.stop();
			primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Partida.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void botonGuia(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Ayuda.fxml"));
			Scene scene = new Scene(root, 1300, 830);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la app
	 * 
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